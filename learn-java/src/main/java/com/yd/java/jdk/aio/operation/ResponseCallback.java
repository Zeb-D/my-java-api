package com.yd.java.jdk.aio.operation;

public interface ResponseCallback<T> {
	void onResponse(T respone);

	void failed(Throwable cause);
}
