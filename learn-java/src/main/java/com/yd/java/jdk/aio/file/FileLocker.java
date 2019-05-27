package com.yd.java.jdk.aio.file;

import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileLock;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLocker implements CompletionHandler<FileLock, FileLockCallback> {
	public void lock(String filename, long position, long size, boolean shared, FileLockCallback client,
			OpenOption... options) throws IOException {
		Path path = Paths.get(filename);
		AsynchronousFileChannel file = AsynchronousFileChannel.open(path, options);
		file.lock(position, size, shared, client, this);
	}

	@Override
	public void completed(FileLock result, FileLockCallback attachment) {
		attachment.start(result);
	}

	@Override
	public void failed(Throwable cause, FileLockCallback attachment) {
		attachment.lockFailed(cause);
	}

}
