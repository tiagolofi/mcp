package com.github.tiagolofi.core;

import java.util.List;

import com.github.tiagolofi.client.OpenAiRequestType;

public class Request<T> {
    private List<NameValuePair> headers;
    private List<NameValuePair> params;
    private List<String> paths;
    private String prompt;
    private OpenAiRequestType type;
    private T data;

    public List<NameValuePair> getHeaders() {
        return headers;
    }

    public void setHeaders(List<NameValuePair> headers) {
        this.headers = headers;
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public OpenAiRequestType getType() {
        return type;
    }

    public void setType(OpenAiRequestType type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class NameValuePair {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
