package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.file.FileLockCallback;
import com.yd.java.jdk.aio.file.FileLocker;
import com.yd.java.jdk.aio.operation.ConnectionCallback;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.FileLock;
import java.nio.file.StandardOpenOption;

public class SimpleTransferContext implements TransferContext, ConnectionCallback, FileLockCallback {
	private TransferConnector connector;
	private FileLocker locker = new FileLocker();
	private FTPCommandName currentCommand;

	private Context context;
	// prepared state related to transfer context
	private String filename;
	private long size;
	private int port;

	// transfer execute context
	private AsynchronousSocketChannel channel;
	private FileLock fileLock;
	private long transferStartTime;

	public SimpleTransferContext(Context context, InetAddress remoteAddress, InetAddress localAddress) {
		this.context = context;
		connector = new TransferConnector(remoteAddress, localAddress);
	}

	@Override
	public void start(AsynchronousSocketChannel channel) {
		// connect to remote maybe before or after retrieve reply process, so single
		this.channel = channel;
	}

	@Override
	public void connectFailed(Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void start(FileLock fileLock) {
		this.fileLock = fileLock;
		// at here socket channel already prepared
		startDownload();
	}

	private Downloader download;

	private void startDownload() {
		download = new Downloader(context, channel, fileLock, size);
		channel = null;
		fileLock = null;
		download.run();
	}

	@Override
	public void lockFailed(Throwable cause) {
		try {
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			channel = null;
		}
		cause.printStackTrace();
		// if download should close socket channel
		// if upload should close file channel
	}

	@Override
	public void select(FTPCommandName command, String... parameters) throws Exception {
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
			transferStartTime = System.currentTimeMillis();
			connector.connect(port, this);
			// clear for next time
			port = 0;
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
		if (currentCommand == null) {
			System.out.println(reply);
			return;
		}
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
							port = 0;
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
						lockFile(checkSize(message.substring(start + 1, end - 6)));
						break;
					}
				}
			} else if (code == 226) {
				context.executor().execute(new Runnable() {
					@Override
					public void run() {
						long time = (System.currentTimeMillis() - transferStartTime) / 1000;
						System.out.println("download completed: " + time + "ms");
					}
				});
				return;
			} else {
				clearDownloadContextResouce();
			}
			break;
		case REST:
			break;
		}
		System.out.println(reply);
	}

	private void clearDownloadContextResouce() {
		if (channel != null) {
			if (channel.isOpen())
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			channel = null;
		}

		if (fileLock != null) {
			if (fileLock.isValid())
				try {
					fileLock.acquiredBy().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			fileLock = null;
		}
	}

	protected long checkSize(String sizeString) {
		try {
			return Long.parseLong(sizeString);
		} catch (NumberFormatException ex) {
			return size;
		}
	}

	protected void lockFile(long size) {
		try {
			locker.lock(filename, 0, size, false, this, StandardOpenOption.CREATE, StandardOpenOption.READ,
					StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
