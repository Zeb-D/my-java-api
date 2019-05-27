package com.yd.java.jdk.aio.file;

import java.nio.ByteBuffer;

public interface FileWriteCallback {
	void writeCompleted(ByteBuffer buffer, long position);

	void writeFailed(Throwable cause);
}
