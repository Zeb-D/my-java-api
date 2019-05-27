package com.yd.java.jdk.aio;

import java.io.PrintStream;

public class MultipleException extends Exception {
	private static final long serialVersionUID = 7297702563111595223L;
	private final Throwable[] errors;

	public MultipleException(Throwable... errors) {
		this.errors = errors;
	}

	public Throwable[] getFailures() {
		return errors;
	}

	@Override
	public void printStackTrace(PrintStream s) {
		super.printStackTrace(s);
		for (Throwable error : errors)
			error.printStackTrace(s);
	}
}