package com.yd.java.jdk.aio.operation;

import java.nio.ByteBuffer;

public interface ReadCallback {
	void readCompletedBytes(Integer bytes, long start, long end);

	void completedReadBuffer(ByteBuffer buffer);

	ByteBuffer getBuffer(int size);

	void readCompleted();

	void readFailed(Throwable cause);

}
