package com.github.tiagolofi.resource;

import java.io.InputStream;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestQuery;

import com.github.tiagolofi.client.OpenAi;
import com.github.tiagolofi.client.OpenAiConfigs;
import com.github.tiagolofi.client.OpenAiRequest;
import com.github.tiagolofi.client.OpenAiRequestBuilder;
import com.github.tiagolofi.client.OpenAiResponse;
import com.github.tiagolofi.core.ConfigTools;
import com.github.tiagolofi.core.Request;
import com.github.tiagolofi.core.Tool;
import com.github.tiagolofi.core.Tools;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mcp")
@RequestScoped
public class McpClientServerResource {

    private static final String BEARER = "Bearer ";

    @Inject
    @ConfigTools
    Tools tools;

    @Inject
    Logger log;

    @Inject
    @RestClient
    OpenAi openAi;

    @Inject
    OpenAiConfigs openAiConfigs;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/feed")
    public Response feed() {
        InputStream html = getClass().getResourceAsStream("/META-INF/resources/feed.html");

        if (html == null) {
            return Response.status(Response.Status.NOT_FOUND)
                .entity("Página não encontrada")
                .build();
        }

        return Response.ok(html).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OpenAiResponse mcpClient(@RestQuery Long toolId) {
        Tool tool = tools.getTool(toolId);
        tool.execute();
        return openAi.getResponse(BEARER + openAiConfigs.apiKey(), getRequestOpenAi(tool));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OpenAiResponse mcpClient(@RestQuery Long toolId, Request request) {
        Tool tool = tools.getTool(toolId);
        tool.execute(request);
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
