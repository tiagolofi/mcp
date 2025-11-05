package com.github.tiagolofi.core;

import java.util.List;

public class ExternalAuthenticationRequest<T> {
    private String uri;
    private List<NameValuePair> headers;
    private T data;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<NameValuePair> getHeaders() {
        return headers;
    }

    public void setHeaders(List<NameValuePair> headers) {
        this.headers = headers;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
