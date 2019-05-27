package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.operation.ConnectionCallback;
import com.yd.java.jdk.aio.operation.SocketConnector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class TransferConnector extends SocketConnector {
	private InetAddress localAddress;
	private InetAddress remoteAddress;

	public TransferConnector(InetAddress remoteAddress, InetAddress localAddress) {
		this.remoteAddress = remoteAddress;
		this.localAddress = localAddress;
	}

	protected InetSocketAddress createRemoteAddress(int port) {
		return new InetSocketAddress(remoteAddress, port);
	}

	protected InetSocketAddress createLocalAddress() {
		return new InetSocketAddress(localAddress, 0);
	}

	public void connect(int port, ConnectionCallback client) throws IOException {
		if (port < 1)
			throw new IOException("Error remote server port number: " + port);
		super.connect(createRemoteAddress(port), createLocalAddress(), client);

	}
}
