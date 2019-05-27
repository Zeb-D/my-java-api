package com.yd.java.jdk.aio.file;

import java.nio.channels.FileLock;

public interface FileLockCallback {
	void start(FileLock fileLock);

	void lockFailed(Throwable cause);
}
