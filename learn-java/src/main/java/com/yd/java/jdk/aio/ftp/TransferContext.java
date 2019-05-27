package com.yd.java.jdk.aio.ftp;

public interface TransferContext {
	void select(FTPCommandName command, String... parameters) throws Exception;

	void check(Reply reply) throws Throwable;
}
