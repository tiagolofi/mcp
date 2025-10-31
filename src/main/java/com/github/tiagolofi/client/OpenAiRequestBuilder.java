package com.github.tiagolofi.client;

import java.util.Map;

public class OpenAiRequestBuilder {
    private OpenAiRequest request = new OpenAiRequest();

    public static OpenAiRequestBuilder builder() {
        return new OpenAiRequestBuilder();
    }

    public OpenAiRequestBuilder model(String model) {
        this.request.setModel(model);
        return this;
    }

    public OpenAiRequestBuilder promptId(String promptId) {
        this.request.getPrompt().setId(promptId);
        return this;
    }

    public OpenAiRequestBuilder version(String version) {
        this.request.getPrompt().setVersion(version);
        return this;
    }

    public OpenAiRequestBuilder variables(Map<String, String> variables) {
        this.request.getPrompt().setVariables(variables);
        return this;
    }

    public OpenAiRequest build() {
        return this.request;
    }

}
