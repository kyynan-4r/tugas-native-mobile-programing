package com.example.uas_risky.response;

import java.util.List;

public class GetAllResponse<T> {
    private String message;
    private List<T> data;

    public String getMessage() {
        return message;
    }

    public List<T> getData() {
        return data;
    }
}