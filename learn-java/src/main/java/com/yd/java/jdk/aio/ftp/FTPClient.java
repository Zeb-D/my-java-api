package com.yd.java.jdk.aio.ftp;

import com.yd.java.jdk.aio.client.CommandName;
import com.yd.java.jdk.aio.client.CommandProvider;
import com.yd.java.jdk.aio.client.Console;
import com.yd.java.jdk.aio.operation.BufferWriter;
import com.yd.java.jdk.aio.operation.ConnectionCallback;
import com.yd.java.jdk.aio.operation.ResponseCallback;
import com.yd.java.jdk.aio.operation.SocketConnector;
import com.yd.java.jdk.aio.operation.TelnetReplyReader;
import com.yd.java.jdk.aio.operation.WriteCallback;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Semaphore;

/**
 * https://www.ibm.com/developerworks/cn/java/j-lo-nio2/index.html
 * 在 Java 中体会 NIO.2 异步执行的快乐
 */
public class FTPClient implements ResponseCallback<Reply>, WriteCallback, CommandProvider {
	private TelnetReplyReader reader;
	private BufferWriter writer;
	private Semaphore semaphore = new Semaphore(0);
	private TransferContext transferContext;

	protected void start(Context context, AsynchronousSocketChannel channel) {
		InetSocketAddress remote;
		try {
			remote = (InetSocketAddress) channel.getRemoteAddress();
		} catch (IOException e) {
			failed(e);
			return;
		}
		InetSocketAddress local;
		try {
			local = (InetSocketAddress) channel.getLocalAddress();
		} catch (IOException e) {
			failed(e);
			return;
		}
		Charset charset = Charset.forName("UTF-8");
		reader = new TelnetReplyReader(channel, context.pool(), charset);
		writer = new BufferWriter(channel, charset);
		reader.read(this);
		transferContext = new SimpleTransferContext(context, remote.getAddress(), local.getAddress());
	}

	@Override
	public void onResponse(Reply reply) {
		try {
			transferContext.check(reply);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		// If reply not process right, just pending any advance operation.
		if (reply.code / 100 == 1)
			reader.read(this);
		else
			semaphore.release();
	}

	@Override
	public void writeCompleted() {
		reader.read(this);
	}

	@Override
	public void failed(Throwable cause) {
		System.out.println("Read failed because: ");
		cause.printStackTrace();
	}

	@Override
	public void writeFailed(Throwable cause) {
		System.out.println("Write failed because: ");
		cause.printStackTrace();
	}

	@Override
	public CommandName[] commands() {
		return FTPCommandName.values();
	}

	private StringBuilder commandString = new StringBuilder();

	@Override
	public void handle(CommandName command, String... parameters) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
		commandString.append(command);
		for (String parameter : parameters)
			commandString.append(' ').append(parameter);
		commandString.append("\r\n");
		String string = commandString.toString();
		System.out.print("--->");
		System.out.print(string);
		commandString.setLength(0);
		writer.write(string, this);
		try {
			transferContext.select((FTPCommandName) command, parameters);
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		SocketConnector connector = new SocketConnector();
		final FTPClient client = new FTPClient();
		final Context context = new Context();
		connector.connect(new InetSocketAddress("ftp.gnu.org", 21), new ConnectionCallback() {
			@Override
			public void start(AsynchronousSocketChannel channel) {
				client.start(context, channel);
			}

			@Override
			public void connectFailed(Throwable cause) {
				cause.printStackTrace();
				System.exit(-1);
			}
		});
		Console console = new Console();
		console.add(client);
		client.handle(FTPCommandName.USER, "anonymous");
		client.handle(FTPCommandName.TYPE, "I");
		// client.handle(FTPCommands.SIZE, "README");
		// client.handle(FTPCommands.EPSV);
		// client.handle(FTPCommands.RETR, "README");

		client.handle(FTPCommandName.CWD, "gnu/bash/");
		client.handle(FTPCommandName.SIZE, "bash-1.14.7.tar.gz");
		client.handle(FTPCommandName.EPSV);
		client.handle(FTPCommandName.RETR, "bash-1.14.7.tar.gz");

		client.handle(FTPCommandName.QUIT);
		console.run();

		context.close();
	}

}
