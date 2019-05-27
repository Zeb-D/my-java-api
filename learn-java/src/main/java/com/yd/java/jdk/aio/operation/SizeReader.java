package com.yd.java.jdk.aio.operation;

import com.yd.java.jdk.aio.MultipleException;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class SizeReader extends AbstractReadCallback<ByteBuffer> {
	private AsynchronousSocketChannel channel;
	private long size;
	private int bufferSize;
	private long count;
	private long startTime;
	private ReadCallback read;

	public SizeReader(AsynchronousSocketChannel channel, long size, ReadCallback read) {
		this.channel = channel;
		this.size = size;
		this.read = read;
		if (size > 64 * 1024) {
			try {
				bufferSize = channel.getOption(StandardSocketOptions.SO_RCVBUF);
			} catch (IOException e) {
				bufferSize = 8192;
			}
		} else
			bufferSize = (int) size;
	}

	public void read() {
		startTime = System.currentTimeMillis();
		ByteBuffer buffer = read.getBuffer(bufferSize);
		channel.read(buffer, buffer, this);
	}

	@Override
	protected void readCompleted(Integer result, ByteBuffer context) {
		count += result;
		read.readCompletedBytes(result, startTime, System.currentTimeMillis());
		if (count == size) {
			read.completedReadBuffer(context);
			read.readCompleted();
			close();
			return;
		}
		if (context.hasRemaining()) {
			startTime = System.currentTimeMillis();
			channel.read(context, context, this);
		} else {
			read.completedReadBuffer(context);
			startTime = size - count;
			ByteBuffer buffer = read.getBuffer(startTime > bufferSize ? bufferSize : (int) startTime);
			startTime = System.currentTimeMillis();
			channel.read(buffer, buffer, this);
		}
	}

	@Override
	protected void onChannelClose(ByteBuffer context) {
		close();
	}

	private void close() {
		try {
			channel.close();
		} catch (IOException e) {
			read.readFailed(e);
		}
	}

	@Override
	public void failed(Throwable cause, ByteBuffer context) {
		if (channel.isOpen())
			try {
				channel.close();
			} catch (IOException e) {
				read.readFailed(new MultipleException(e, cause));
				return;
			}
		read.readFailed(cause);
	}

}
