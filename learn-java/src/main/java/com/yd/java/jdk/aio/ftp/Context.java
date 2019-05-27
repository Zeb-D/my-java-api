package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.BufferPool.ByteBufferPool;
import com.yd.java.jdk.aio.util.Concurrents;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Context {
	private ByteBufferPool pool;
	private ExecutorService executor;

	public Context() {
		pool = new ByteBufferPool();
		executor = Executors.newFixedThreadPool(1);
	}

	public Context(ByteBufferPool pool, ExecutorService executor) {
		this.pool = pool;
		this.executor = executor;
	}

	public ByteBufferPool pool() {
		return pool;
	}

	public ExecutorService executor() {
		return executor;
	}

	public void close() {
		pool.clear();
		Concurrents.shutdownAndAwaitTermination(executor, 2);
	}
}
