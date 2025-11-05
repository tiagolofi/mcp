package com.github.tiagolofi.client;

import java.util.Map;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OpenAiService {

    private static final String BEARER = "Bearer ";

    @Inject
    OpenAiConfigs openAiConfigs;

    @Inject
    @RestClient
    OpenAi openAi;

    public OpenAiResponse getOpenAiResponse(OpenAiRequestType type, String prompt, Map<String, String> dados) {
        OpenAiRequest openAiRequest = null;
        switch (type) {
            case OpenAiRequestType.PROMPT:
                openAiRequest = new OpenAiRequestPrompt(openAiConfigs.model(), openAiConfigs.promptId(), openAiConfigs.promptVersion(), dados);
                break;
            case OpenAiRequestType.WEB_SEARCH:
                openAiRequest = new OpenAiRequestWebSearch(openAiConfigs.model(), type.getType(), prompt);
                break;
            default:
                break;
        }

        return openAi.getResponse(BEARER + openAiConfigs.apiKey(), openAiRequest);
    }

}
