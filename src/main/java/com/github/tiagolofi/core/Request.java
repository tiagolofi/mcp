package com.github.tiagolofi.core;

import java.util.Map;

public class Request<T> {
    private Map<String, String> headers;
    private T data;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
