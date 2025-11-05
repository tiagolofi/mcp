package com.github.tiagolofi.client;

public enum OpenAiRequestType {
    PROMPT("prompt"),
    WEB_SEARCH("web_search");

    private String type;

    OpenAiRequestType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static OpenAiRequestType fromType(String type) {
        for (OpenAiRequestType t : values()) {
            if (t.getType().equals(type)) {
                return t;
            }
        }
        throw new IllegalArgumentException(String.format("Type '%s' é inválido!", type));
    }
}
