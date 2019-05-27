package com.yd.java.jdk.aio.client;

public interface CommandName {
	String name();

	/**
	 * <ul>
	 * Use integer count the number of parameters.
	 * <li>+n positive number indicate the determinate number of parameters
	 * <li>-1 like x? of Regex
	 * <li>-2 like x+ of Regex
	 * </ul>
	 * 
	 * @return int count of parameters
	 */
	int parameterCount();
}
