package com.yd.java.jdk.aio.operation;

import java.nio.channels.AsynchronousSocketChannel;

public interface ConnectionCallback {
	void start(AsynchronousSocketChannel channel);

	void connectFailed(Throwable cause);
}
