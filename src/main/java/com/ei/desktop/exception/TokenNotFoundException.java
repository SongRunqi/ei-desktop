package com.ei.desktop.exception;

/**
 * @author yitiansong
 * 2024/8/4
 */

public class TokenNotFoundException extends Exception {
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public TokenNotFoundException() {
        super("token为null");
    }
}
