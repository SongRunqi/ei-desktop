package com.ei.desktop.exception;

/**
 * @author yitiansong
 * 2024/6/23-18:58
 */
public class FXMLLoadException extends EIException{
    public FXMLLoadException(String message) {
        super(message);
    }

    public FXMLLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
