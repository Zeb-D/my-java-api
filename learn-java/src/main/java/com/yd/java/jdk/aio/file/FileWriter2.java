package com.yd.java.jdk.aio.file;

import com.yd.java.jdk.aio.Callback;
import com.yd.java.jdk.aio.MultipleException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.FileLock;

public class FileWriter2 implements Callback<ByteBuffer> {
	private FileLock fileLock;
	private long position;
	private FileWriteCallback2 client;

	public FileWriter2(FileLock fileLock, FileWriteCallback2 client) {
		this.fileLock = fileLock;
		this.client = client;
	}

	public void write(ByteBuffer buffer) {
		buffer.flip();
		file().write(buffer, position, buffer, this);
	}

	protected AsynchronousFileChannel file() {
		return ((AsynchronousFileChannel) fileLock.acquiredBy());
	}

	@Override
	public void completed(Integer result, ByteBuffer context) {
		position += result;
		if (position == fileLock.size()) {
			client.writeCompleted();
			try {
				close();
			} catch (IOException e) {
				client.writeFailed(e);
			}
			return;
		}

		if (context.hasRemaining())
			file().write(context, position, context, this);
		else {
			client.writeCompleted(context);
		}
	}

	@Override
	public void failed(Throwable cause, ByteBuffer context) {
		if (isOpen())
			try {
				close();
			} catch (IOException e) {
				client.writeFailed(new MultipleException(e, cause));
				return;
			}
		client.writeFailed(cause);
	}

	protected boolean isOpen() {
		return fileLock.acquiredBy().isOpen();
	}

	protected void close() throws IOException {
		Channel file = fileLock.acquiredBy();
		try {
			((AsynchronousFileChannel) file).force(false);
		} finally {
			file.close();
		}
	}
}