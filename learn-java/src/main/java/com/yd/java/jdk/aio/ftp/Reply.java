package com.yd.java.jdk.aio.ftp;

public class Reply {
	public int code;
	public String message;
	public StringBuilder other = new StringBuilder();

	public void reset() {
		code = 0;
		message = null;
		other.setLength(0);
	}

	@Override
	public String toString() {
		if (other.length() > 0)
			return String.format("%s%s %s", other, code, message);
		return String.format("%s %s", code, message);
	}

}
