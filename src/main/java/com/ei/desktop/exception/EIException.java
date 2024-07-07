package com.ei.desktop.exception;

/**
 * @author yitiansong
 * 2024/6/23-18:57
 */
public class EIException extends RuntimeException{
    public EIException(String message) {
        super(message);
    }

    public EIException(String message, Throwable cause) {
        super(message, cause);
    }

}
