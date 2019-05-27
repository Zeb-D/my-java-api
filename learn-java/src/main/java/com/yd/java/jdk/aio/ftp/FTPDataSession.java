package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.Connector;
import com.yd.java.jdk.aio.file.FileWriteCallback;
import com.yd.java.jdk.aio.file.FileWriter;
import com.yd.java.jdk.aio.operation.ReadCallback;
import com.yd.java.jdk.aio.operation.SizeReader;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.Channel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class FTPDataSession implements ReadCallback, FileWriteCallback, TransferContext {
	private InetAddress localAddress;
	private InetAddress remoteAddress;
	private FTPCommandName currentCommand;
	private String filename;
	private long size;
	private int port;
	private AsynchronousSocketChannel channel;
	private SizeReader reader;
	private FileWriter writer;
	private BlockingDeque<ByteBuffer> bufferQueue = new LinkedBlockingDeque<ByteBuffer>();
	private AtomicBoolean writing = new AtomicBoolean(false);
	protected FileLock fileLock;
//	private ConsoleProgress progress;
	private Context context;

	public FTPDataSession(Context context, InetAddress remoteAddress, InetAddress localAddress) {
		this.context = context;
		this.localAddress = localAddress;
		this.remoteAddress = remoteAddress;
	}

	@Override
	public void writeCompleted(ByteBuffer buffer, long position) {
		context.pool().releaseBuffer(buffer);
		if (size == position) {
			closeFile();
			return;
		}
		buffer = bufferQueue.poll();
		if (buffer != null) {
			writing.set(true);
			writer.write(buffer);
		} else
			writing.set(false);
	}

	private void closeFile() {
		Channel file = fileLock.acquiredBy();
		if (!file.isOpen())
			return;
		try {
			((AsynchronousFileChannel) file).force(false);
		} catch (IOException e2) {
			writeFailed(e2);
		}
		try {
			fileLock.release();
		} catch (IOException e) {
			writeFailed(e);
		}
		try {
			file.close();
		} catch (IOException e1) {
			writeFailed(e1);
		}
	}

	protected void startWriteToFile(FileLock lock) {
		this.fileLock = lock;
		AsynchronousFileChannel file = (AsynchronousFileChannel) lock.acquiredBy();
		try {
			file.truncate(size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer = new FileWriter(lock, this);
		ByteBuffer buffer = bufferQueue.poll();
		if (buffer != null) {
			writing.set(true);
			writer.write(buffer);
		}
	}

	@Override
	public void readCompletedBytes(Integer bytes, long start, long end) {
		// progress.show(context.executor(), bytes, start, end);
	}

	@Override
	public void completedReadBuffer(ByteBuffer buffer) {
		if (writing.compareAndSet(false, true)) {
			if (writer == null) {
				bufferQueue.offer(buffer);
				createWriter();
			} else {
				writer.write(buffer);
			}
		} else {
			bufferQueue.offer(buffer);
		}
	}

	@Override
	public ByteBuffer getBuffer(int size) {
		return context.pool().get(size);
	}

	@Override
	public void readCompleted() {
		System.out.println("file transfer OK");
	}

	@Override
	public void readFailed(Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void writeFailed(Throwable cause) {
		cause.printStackTrace();
		closeFile();
	}

	private long retrStartTime;

	@Override
	public void select(FTPCommandName command, String... parameters) {
		switch (command) {
		case SIZE:
			filename = parameters[0];
			currentCommand = command;
			break;
		case RETR:
			if (filename == null) {
				filename = parameters[0];
			} else if (!filename.equals(parameters[0])) {
				filename = parameters[0];
				size = 0;
			}
			currentCommand = command;
			retrStartTime = System.currentTimeMillis();
			connect();
			break;
		case EPSV:
			// 229 Entering Extended Passive Mode (|||55006|)
			currentCommand = command;
			break;
		case REST:
			// 350 Restart position accepted (1024).
			currentCommand = command;
			break;
		default:
			currentCommand = null;
		}
	}

	@Override
	public void check(Reply reply) {
		if (currentCommand == null)
			return;
		int code = reply.code;
		String message = reply.message;
		switch (currentCommand) {
		case SIZE:
			if (code / 100 != 2)
				filename = null;
			else
				try {
					size = Long.parseLong(message);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					size = 0;
				}
			break;
		case EPSV:
			if (code == 229) {
				int end = message.lastIndexOf("|)");
				if (end != -1) {
					int start = message.lastIndexOf("(|||", end - 1);
					if (start != -1) {
						try {
							port = Integer.parseInt(message.substring(start + 4, end));
						} catch (NumberFormatException ex) {
							// server error, notify client
						}
					}
				}
			}
			break;
		case RETR:
			if (code == 150) {
				// 150 Opening BINARY mode data connection for README (1765 bytes).
				int end = message.lastIndexOf(')');
				if (end != -1) {
					int start = message.lastIndexOf('(', end - 1);
					if (start != -1) {
						try {
							size = Long.parseLong(message.substring(start + 1, end - 6));
						} catch (NumberFormatException ex) {
							size = 0;
						}
					}
				}
				if (size > 0) {
					// progress = new ConsoleProgress(size);
					reader = new SizeReader(channel, size, this);
					reader.read();
				}
			} else if (code == 226) {
				System.out.println("download completed: " + (System.currentTimeMillis() - retrStartTime) + "ms");
			}
			break;
		case REST:
			break;
		}
	}

	private void connect() {
		try {
			AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
			InetSocketAddress local = new InetSocketAddress(localAddress, 0);
			channel.bind(local);
			System.out.println(local);
			InetSocketAddress remote = new InetSocketAddress(remoteAddress, port);
			System.out.println(remote);
			channel.connect(remote, channel, new Connector<AsynchronousSocketChannel>() {
				@Override
				public void completed(Void result, AsynchronousSocketChannel attachment) {
					FTPDataSession.this.channel = attachment;
				}

				@Override
				public void failed(Throwable cause, AsynchronousSocketChannel attachment) {
					cause.printStackTrace();
				}

			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createWriter() {
		Path path = Paths.get(filename);
		AsynchronousFileChannel file;
		try {
			file = AsynchronousFileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.READ,
					StandardOpenOption.WRITE);
		} catch (IOException e) {
			System.err.println("Open file with group failed because: ");
			e.printStackTrace();
			return;
		}
		file.lock(0, size, false, null, new CompletionHandler<FileLock, Object>() {
			@Override
			public void completed(FileLock result, Object attachment) {
				startWriteToFile(result);
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.err.println("lock file failed because: ");
				exc.printStackTrace();
			}
		});
	}

}
