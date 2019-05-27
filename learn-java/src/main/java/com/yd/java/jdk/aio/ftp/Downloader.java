package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.client.ConsoleProgress;
import com.yd.java.jdk.aio.file.FileWriteCallback2;
import com.yd.java.jdk.aio.file.FileWriter2;
import com.yd.java.jdk.aio.operation.ReadCallback;
import com.yd.java.jdk.aio.operation.SizeReader;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.FileLock;
import java.util.concurrent.atomic.AtomicBoolean;

public class Downloader extends Transfer implements ReadCallback, FileWriteCallback2, Runnable {
	private SizeReader reader;
	private FileWriter2 writer;
	private AtomicBoolean writable = new AtomicBoolean(true);
	private ConsoleProgress progress = new ConsoleProgress();

	public Downloader(Context context, AsynchronousSocketChannel socket, FileLock fileLock, long size) {
		super(context);
		reader = new SizeReader(socket, size, this);
		writer = new FileWriter2(fileLock, this);
		progress.reset(size);
	}

	@Override
	public void run() {
		reader.read();
	}

	@Override
	public void writeCompleted(ByteBuffer buffer) {
		releaseBuffer(buffer);
		buffer = bufferQueue.poll();
		if (buffer != null)
			writer.write(buffer);
		else
			writable.set(true);
	}

	@Override
	public void readCompletedBytes(Integer bytes, long start, long end) {
		progress.update(bytes, start, end);
//		context.executor().execute(progress);
		progress.run();
	}

	@Override
	public void completedReadBuffer(ByteBuffer buffer) {
		if (writable.compareAndSet(true, false)) {
			writer.write(buffer);
		} else {
			bufferQueue.offer(buffer);
		}
	}

	@Override
	public void writeCompleted() {
		System.out.println("file saved OK");
	}

	@Override
	public void readCompleted() {
		System.out.println("file transfer OK");
	}

	@Override
	public void readFailed(Throwable cause) {
		cause.printStackTrace();
	}

	@Override
	public void writeFailed(Throwable cause) {
		cause.printStackTrace();
	}
}
