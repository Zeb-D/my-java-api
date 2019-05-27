package com.yd.java.jdk.aio.operation;

import com.yd.java.jdk.aio.Callback;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;

public class BufferWriter implements Callback<WriteCallback> {
	private AsynchronousSocketChannel channel;
	private ByteBuffer buffer;
	private Charset charset;

	public BufferWriter(AsynchronousSocketChannel channel, Charset charset) {
		this.channel = channel;
		this.charset = charset;
	}

	public void write(String string, WriteCallback write) {
		buffer = ByteBuffer.wrap(string.getBytes(charset));
		channel.write(buffer, write, this);
	}

	@Override
	public void completed(Integer result, WriteCallback context) {
		if (buffer.hasRemaining())
			channel.write(buffer, context, this);
		else {
			buffer = null;
			context.writeCompleted();
		}
	}

	@Override
	public void failed(Throwable cause, WriteCallback context) {
		buffer = null;
		context.writeFailed(cause);
	}

}
