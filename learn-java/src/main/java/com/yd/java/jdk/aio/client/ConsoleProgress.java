package com.yd.java.jdk.aio.client;

import java.util.concurrent.atomic.AtomicReference;

public class ConsoleProgress implements Runnable {
	// private char[] control = { '-', '\\', '|', '/' };
	// private int times;
	private long size;
	private long count;
	private AtomicReference<long[]> parameters = new AtomicReference<long[]>();
	private long time;
	private StringBuffer progressString = new StringBuffer();

	public void update(long... parameters) {
		// int bytes, long start, long end
		this.parameters.set(parameters);
	}

	// 100%[==========================================================>] 1,518,180 136K/s in 12s
	private static String format = "\r%3s%%[%50s] %,d %dK/s in %ss ets %ss %5s";

	@Override
	public void run() {
		long[] parameters = this.parameters.get();
		long bytes = parameters[0];
		long start = parameters[1];
		long end = parameters[2];

		count += bytes;
		time += (end - start);

		start = time / 1000;
		if (start == 0)
			start = 1;

		int percent = (int) (count * 100 / size);

		int rate = (int) (count / 1024 / start);

		int progress = percent / 2 - 1;

		for (int i = 0; i < progress; i++)
			progressString.setCharAt(i, '=');

		if (progress < 0)
			progress = 0;

		progressString.setCharAt(progress, '>');
		for (int i = progress + 1; i < 50; i++)
			progressString.setCharAt(i, ' ');
		// ets
		end = size / 1024 / rate - start;
		System.out.printf(format, percent, progressString, count, rate, start, end, "");
		if (count == size)
			System.out.println();
	}

	public void reset(long size) {
		if (size == 0)
			size = 1;
		progressString.setLength(50);
		this.size = size;
		// times = 0;
		count = 0;
		time = 0;
		parameters.set(new long[3]);
	}
}
