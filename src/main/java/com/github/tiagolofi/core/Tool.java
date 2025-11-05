package com.github.tiagolofi.core;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tiagolofi.client.GenericClient;

import jakarta.ws.rs.core.Response;

public class Tool {

    private static final String DATA = "data";
    private static final String PROMPT = "prompt";
    private static final String GET = "GET";
    private static final String POST = "POST";

    @JsonProperty
    private Long id;
    @JsonProperty
    private String description;
    @JsonProperty
    private String uri;
    @JsonProperty
    private String method;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public <T> Map<String, String> execute(Request<T> request) {

        Map<String, String> variables = new HashMap<>();

        if (request.getPrompt() != null) {
            variables.put(PROMPT, request.getPrompt());
        }

        RestClientBuilder clientBuilder = RestClientBuilder.newBuilder();

        if (request != null && request.getHeaders() != null) {
            request.getHeaders().forEach(header -> clientBuilder.header(header.getName(), header.getValue()));
        }

        if (this.uri != null) {
            if (request != null && request.getPaths() != null) {
                request.getPaths().forEach(v -> this.uri += String.format("/%s/", v));
                this.uri = this.uri.substring(0, this.uri.length() - 1);
            }

            if (request != null && request.getParams() != null) {
                this.uri = this.uri + "?";
                request.getParams().forEach(param -> this.uri += String.format("%s=%s&", param.getName(), param.getValue()));
                this.uri = this.uri.substring(0, this.uri.length() - 1);
            }

            clientBuilder.baseUri(this.uri);
        }

        if(this.method != null) {
            try {

                GenericClient client = clientBuilder.build(GenericClient.class); 

                Response response;

                switch (this.method) {
                    case GET:
                        response = client.get();
                        break;
                    case POST:
                        response = client.post(request.getData());
                        break;
                    default:
                        response = Response.status(400).build();
                        break;
                }

                variables.put(DATA, response.readEntity(String.class));
                return variables;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        variables.put(DATA, " ");
        return variables;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    id: %s,
                    description: %s,
                    uri: %s,
                    method: %s
                }
                """, String.valueOf(id), description, uri, method);
    }
}
