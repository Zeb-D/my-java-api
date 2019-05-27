package com.yd.java.jdk.aio.operation;

import com.yd.java.jdk.aio.Connector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

public class SocketConnector implements Connector<Object[]> {
	public void connect(InetSocketAddress remote, ConnectionCallback client) throws IOException {
		AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
		Object[] attachment = { client, remote, channel };
		channel.connect(remote, attachment, this);
	}

	public void connect(InetSocketAddress remote, InetSocketAddress local, ConnectionCallback client)
			throws IOException {
		AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
		channel.bind(local);
		Object[] attachment = { client, remote, channel };
		channel.connect(remote, attachment, this);
	}

	@Override
	public void completed(Void result, Object[] attachment) {
		((ConnectionCallback) attachment[0]).start((AsynchronousSocketChannel) attachment[2]);
	}

	@Override
	public void failed(Throwable cause, Object[] attachment) {
		((ConnectionCallback) attachment[0]).connectFailed(new Exception(attachment[1].toString(), cause));
	}

}
