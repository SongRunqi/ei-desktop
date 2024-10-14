package com.ei.desktop.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author yitiansong
 * 2024/8/4
 * 响应实体类
 */
@SuppressWarnings("unused")
public class ResponseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String resultType;
    private String operation;
    private String message;
    @JsonDeserialize
    private JsonNode data;
    private String content;


    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "resultType='" + resultType + '\'' +
                ", operation='" + operation + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", content='" + content + '\'' +
                '}';
    }
}
