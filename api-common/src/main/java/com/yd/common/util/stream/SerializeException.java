package com.yd.common.util.stream;

public class SerializeException extends RuntimeException {
    public SerializeException() {
    }

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(Throwable cause) {
        super(cause);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}