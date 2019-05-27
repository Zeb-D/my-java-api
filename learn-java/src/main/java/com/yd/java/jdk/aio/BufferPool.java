package com.yd.java.jdk.aio;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;

public abstract class BufferPool<T extends Buffer> {
	private final ArrayList<T> bufferPool;

	public BufferPool() {
		this.bufferPool = new ArrayList<T>(4);
	}

	public static class ByteBufferPool extends BufferPool<ByteBuffer> {
		@Override
		protected ByteBuffer allocate(int size) {
			return ByteBuffer.allocateDirect(size);
		}

	}

	public void clear() {
		synchronized (bufferPool) {
			bufferPool.clear();
		}
	}

	public static class CharBufferPool extends BufferPool<CharBuffer> {
		@Override
		protected CharBuffer allocate(int size) {
			return CharBuffer.allocate(size);
		}
	}

	public T get(int size) {
		T buffer = null;
		synchronized (bufferPool) {
			int poolSize = bufferPool.size();
			if (poolSize > 0) {
				int index = poolSize - 1;
				for (; index >= 0; index--) {
					buffer = bufferPool.get(index);
					if (buffer.capacity() == size) {
						bufferPool.remove(index);
						buffer.clear();
						return buffer;
					}
				}
				index = poolSize - 1;
				int biggerSize = size + size / 2;
				int capacity;
				for (; index >= 0; index--) {
					buffer = bufferPool.get(index);
					capacity = buffer.capacity();
					if (capacity > size && capacity < biggerSize) {
						bufferPool.remove(index);
						buffer.clear();
						return buffer;
					}
				}

				index = poolSize - 1;
				for (; index >= 0; index--) {
					buffer = bufferPool.get(index);
					if (buffer.capacity() > biggerSize) {
						bufferPool.remove(index);
						buffer.clear();
						return buffer;
					}
				}
			}
		}
		return buffer = allocate(size);
	}

	abstract protected T allocate(int size);

	public void releaseBuffer(T buffer) {
		if (buffer == null)
			return;
		if (buffer.isDirect()) {
			synchronized (bufferPool) {
				if (bufferPool.indexOf(buffer) != -1)
					bufferPool.add(buffer);
			}
		}
	}
}
