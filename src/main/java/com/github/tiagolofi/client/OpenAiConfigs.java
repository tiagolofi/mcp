package com.github.tiagolofi.client;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "openai")
public interface OpenAiConfigs {
    public String apiKey();
    public String model();
    public String promptId();
    public String promptVersion();
}
