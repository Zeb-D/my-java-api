package com.yd.java.jdk.aio;
import java.nio.channels.CompletionHandler;

public interface Callback<T> extends CompletionHandler<Integer, T> {
	@Override
	void completed(Integer result, T context);

	@Override
	void failed(Throwable cause, T context);
}
