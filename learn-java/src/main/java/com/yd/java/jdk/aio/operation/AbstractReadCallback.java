package com.yd.java.jdk.aio.operation;

import com.yd.java.jdk.aio.Callback;

public abstract class AbstractReadCallback<T> implements Callback<T> {
	protected abstract void readCompleted(Integer result, T context);

	protected abstract void onChannelClose(T context);

	@Override
	public void completed(Integer result, T context) {
		if (result > 0)
			readCompleted(result, context);
		else
			onChannelClose(context);
	}
}
