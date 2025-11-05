package com.github.tiagolofi.resource;

import java.util.Map;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestQuery;

import com.github.tiagolofi.client.OpenAiResponse;
import com.github.tiagolofi.client.OpenAiService;
import com.github.tiagolofi.core.ConfigTools;
import com.github.tiagolofi.core.Request;
import com.github.tiagolofi.core.Tool;
import com.github.tiagolofi.core.Tools;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/mcp")
@RequestScoped
public class McpClientServerResource {


    @Inject
    @ConfigTools
    Tools tools;

    @Inject
    Logger log;

    @Inject
    OpenAiService openAiService;

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
        Map<String, String> dados = tool.execute(request);
        log.infof("Tool Executada: %s", tool);
        log.infof("Resposta da Tool %s", dados);
        if (dados == null) {
            throw new RuntimeException("Erro na execução da tool");
        }
        return openAiService.getOpenAiResponse(request.getType(), request.getPrompt(), dados);
    }

}
