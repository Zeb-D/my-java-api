package com.yd.java.jdk.aio;

import java.nio.channels.CompletionHandler;

public interface Connector<T> extends CompletionHandler<Void, T> {
	@Override
	public void completed(Void result, T attachment);

	@Override
	public void failed(Throwable cause, T attachment);
}
