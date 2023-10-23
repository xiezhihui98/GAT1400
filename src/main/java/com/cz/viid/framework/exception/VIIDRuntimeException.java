package com.cz.viid.framework.exception;

public class VIIDRuntimeException extends RuntimeException{

    public VIIDRuntimeException() {
        super("");
    }

    public VIIDRuntimeException(String message) {
        super(message);
    }
}
