package com.example.uas_risky.response;

public class GetResponse<T> {
    private String message;
    private T data;

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}