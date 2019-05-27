package com.yd.java.jdk.aio.ftp;

import java.nio.ByteBuffer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class Transfer {
	protected BlockingDeque<ByteBuffer> bufferQueue = new LinkedBlockingDeque<ByteBuffer>();
	protected Context context;

	public Transfer(Context context) {
		this.context = context;
	}

	public ByteBuffer getBuffer(int size) {
		return context.pool().get(size);
	}

	protected void releaseBuffer(ByteBuffer buffer) {
		context.pool().releaseBuffer(buffer);
	}

}
