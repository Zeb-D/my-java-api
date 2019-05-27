package com.yd.java.jdk.aio.file;

import com.yd.java.jdk.aio.Callback;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;

public class FileWriter implements Callback<ByteBuffer> {
	private FileLock fileLock;
	private long position;
	private FileWriteCallback source;

	public FileWriter(FileLock fileLock, FileWriteCallback source) {
		this.fileLock = fileLock;
		this.source = source;
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
		if (context.hasRemaining())
			file().write(context, position, context, this);
		else {
			source.writeCompleted(context, position);
		}
	}

	@Override
	public void failed(Throwable cause, ByteBuffer context) {
		source.writeFailed(cause);
	}
}
