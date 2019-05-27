package com.yd.java.jdk.aio.client;


public interface CommandProvider {
	CommandName[] commands();

	void handle(CommandName command, String... parameters);
}
