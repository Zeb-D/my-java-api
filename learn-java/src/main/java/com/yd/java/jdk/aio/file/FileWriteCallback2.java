package com.yd.java.jdk.aio.file;

import java.nio.ByteBuffer;

public interface FileWriteCallback2 {
	void writeCompleted();

	void writeCompleted(ByteBuffer buffer);

	void writeFailed(Throwable cause);
}
