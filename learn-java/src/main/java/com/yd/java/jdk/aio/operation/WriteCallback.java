package com.yd.java.jdk.aio.operation;

public interface WriteCallback {
	void writeCompleted();

	void writeFailed(Throwable cause);
}
