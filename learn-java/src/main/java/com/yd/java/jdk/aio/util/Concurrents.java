package com.yd.java.jdk.aio.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Concurrents {
	public static void shutdownAndAwaitTermination(ExecutorService executor, int seconds) {
		// Disable new tasks from being submitted
		executor.shutdown();

		try {
			// Wait a while for existing tasks to terminate
			if (executor.awaitTermination(seconds, TimeUnit.SECONDS))
				return;

			// Cancel currently executing tasks
			executor.shutdownNow();

			// Wait a while for tasks to respond to being cancelled
			if (executor.awaitTermination(seconds, TimeUnit.SECONDS))
				return;
			// TODO: find log service to log
			System.err.println("Pool did not terminate");
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			executor.shutdownNow();

			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

}
