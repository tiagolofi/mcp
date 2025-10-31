package com.github.tiagolofi.resource;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestQuery;

import com.github.tiagolofi.client.OpenAi;
import com.github.tiagolofi.client.OpenAiConfigs;
import com.github.tiagolofi.client.OpenAiRequest;
import com.github.tiagolofi.client.OpenAiRequestBuilder;
import com.github.tiagolofi.client.OpenAiResponse;
import com.github.tiagolofi.core.ConfigTools;
import com.github.tiagolofi.core.Tool;
import com.github.tiagolofi.core.Tools;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/mcp")
@RequestScoped
public class McpClientServerResource {

    private static final String BEARER = "Bearer ";

    @Inject
    @ConfigTools(filename = "tools")
    Tools tools;

    @Inject
    Logger log;

    @Inject
    @RestClient
    OpenAi openAi;

    @Inject
    OpenAiConfigs openAiConfigs;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OpenAiResponse mcpClient(@RestQuery Long toolId) {
        Tool tool = tools.getTool(toolId);
        tool.execute();
        return openAi.getResponse(BEARER + openAiConfigs.apiKey(), getRequestOpenAi(tool));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Path("/tools")
    public Tools getTools() {
        return tools;
    }

    private OpenAiRequest getRequestOpenAi(Tool tool) {
        return OpenAiRequestBuilder.builder()
            .model(openAiConfigs.model())
            .promptId(openAiConfigs.promptId())
            .version(openAiConfigs.promptVersion())
            .variables(tool.getVariables())
            .build();
    }

}
