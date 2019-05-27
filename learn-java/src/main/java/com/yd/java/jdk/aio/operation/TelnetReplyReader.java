package com.yd.java.jdk.aio.operation;

import com.yd.java.jdk.aio.BufferPool.ByteBufferPool;
import com.yd.java.jdk.aio.ftp.Reply;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class TelnetReplyReader extends AbstractReadCallback<ResponseCallback<Reply>> {
	private CharsetDecoder decoder;
	private ByteBufferPool pool;
	private ByteBuffer buffer;
	private AsynchronousSocketChannel channel;
	private Reply reply = new Reply();

	public TelnetReplyReader(AsynchronousSocketChannel channel, ByteBufferPool pool, Charset charset) {
		this.channel = channel;
		this.pool = pool;
		decoder = charset.newDecoder();
	}

	public void read(ResponseCallback<Reply> protocol) {
		reply.reset();
		if (buffer == null)
			buffer = pool.get(1024);
		buffer.clear();
		channel.read(buffer, protocol, this);
	}

	@Override
	protected void onChannelClose(ResponseCallback<Reply> context) {
		try {
			channel.close();
		} catch (IOException e) {
			// ignore;
		}
		failed(new ClosedChannelException(), context);
	}

	@Override
	protected void readCompleted(Integer result, ResponseCallback<Reply> context) {
		ByteBuffer buffer = this.buffer;
		try {
			int position = buffer.position();
			if (buffer.get(position - 2) == 13 && buffer.get(position - 1) == 10) {
				// Yes check reply code;
				if (findReplyCode(buffer, position - 2)) {
					// buffer position at the code first char;
					int first = buffer.position();
					reply.code = getReplyCode(buffer, first);
					if (first > 0) {
						buffer.flip();
						reply.other.append(decoder.decode(buffer));
					}
					buffer.limit(position - 2);
					buffer.position(first + 4);
					reply.message = decoder.decode(buffer).toString();
					returnBuffer();
					context.onResponse(reply);
					return;
				}
				buffer.flip();
				reply.other.append(decoder.decode(buffer));
				buffer.clear();
				channel.read(buffer, context, this);
				return;
			}

			// No reply code, consider cache other message
			if (buffer.hasRemaining()) {
				channel.read(buffer, context, this);
				return;
			}

			// Have to cache some message, but may be have reply code, so just check CRLF;
			int index = findLF(buffer, position - 2);
			if (index == -1) {
				buffer.flip();
				reply.other.append(decoder.decode(buffer));
			} else {
				buffer.position(0).limit(index + 1);
				reply.other.append(decoder.decode(buffer));
				buffer.position(index);
			}
			buffer.limit(position);
			buffer.compact();
			channel.read(buffer, context, this);
		} catch (CharacterCodingException ex) {
			failed(ex, context);
		}
	}

	@Override
	public void failed(Throwable cause, ResponseCallback<Reply> context) {
		returnBuffer();
		context.failed(cause);
	}

	private void returnBuffer() {
		pool.releaseBuffer(buffer);
		buffer = null;
	}

	public static boolean isCode(ByteBuffer buffer, int position) {
		return Character.isDigit(buffer.get(position - 1)) && Character.isDigit(buffer.get(position - 2))
				&& Character.isDigit(buffer.get(position - 3));
	}

	public static boolean findReplyCode(ByteBuffer buffer, int position) {
		// 999 message\r\n
		// i i+1 i+2 i+3
		while (position > 0) {
			if (buffer.get(--position) == 32) {
				if (position > 2 && isCode(buffer, position)) {
					if (position > 3) {
						if (buffer.get(position - 4) == 10)
							buffer.position(position - 3);
						else
							continue;
					} else
						buffer.position(0);
					return true;
				}
			}
		}
		return false;
	}

	public static int findLF(ByteBuffer buffer, int position) {
		byte b;
		for (; position >= 0; position--) {
			b = buffer.get(position);
			if (b == 13 || b == 10)
				return position;
		}
		return -1;
	}

	public static int findCRLF(ByteBuffer buffer, int start, int end) {
		while (start < end) {
			if (buffer.get(start++) == 13) {
				if (start < end) {
					if (buffer.get(start) == 10) {
						return start + 1;
					}
				}
			}
		}
		return -1;
	}

	public static int getReplyCode(ByteBuffer buffer, int first) {
		return Character.digit(buffer.get(first), 10) * 100 + Character.digit(buffer.get(first + 1), 10) * 10
				+ Character.digit(buffer.get(first + 2), 10);
	}
}
