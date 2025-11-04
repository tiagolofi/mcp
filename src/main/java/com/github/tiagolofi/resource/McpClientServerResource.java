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
import com.github.tiagolofi.core.Request;
import com.github.tiagolofi.core.Tool;
import com.github.tiagolofi.core.Tools;
import com.github.tiagolofi.security.LoginUsuarioSenha;
import com.github.tiagolofi.security.TokenJwt;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
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

    @Inject
    TokenJwt tokenJwt;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    @Path("/login")
    public Response login(LoginUsuarioSenha requisicao) {
        if (tokenJwt.validar(requisicao)) {
            return Response.status(200).entity(tokenJwt.geraToken()).build();
        }
        return Response.status(401).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON) 
    @Path("/tools")
    @RolesAllowed({"user"})
    public Tools getTools() {
        return tools;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user"})
    public OpenAiResponse mcpClientServerPost(@RestQuery Long toolId, Request request) {
        Tool tool = tools.getTool(toolId);
        tool.execute(request);
        log.infof("Tool Executada: %s", tool);
        return openAi.getResponse(BEARER + openAiConfigs.apiKey(), getRequestOpenAi(tool));
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
