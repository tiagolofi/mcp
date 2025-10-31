package com.github.tiagolofi.core;

import java.util.Map;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tiagolofi.client.GenericClient;

import jakarta.ws.rs.core.Response;

public class Tool {

    private static final String DATA = "data";
    private static final String GET = "GET";
    private static final String POST = "POST";

    @JsonProperty
    private Long id;
    @JsonProperty
    private String description;
    @JsonProperty
    private Map<String, String> variables;
    @JsonProperty
    private String uri;
    @JsonProperty
    private String token;
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

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public Map<String, String> getVariables() {
        return this.variables;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean execute() {
        return execute(null);
    }

    public <T> boolean execute(T data) {
        if(this.method != null) {
            try {
                GenericClient client = RestClientBuilder.newBuilder()
                    .baseUri(this.uri)
                    .build(GenericClient.class); 

                Response response;

                if (token != null) {
                    switch (this.method) {
                        case GET:
                            response = client.get(token);
                            break;
                        case POST:
                            response = client.post(token, data);
                            break;
                        default:
                            response = Response.status(400).build();
                            break;
                    }
                }

                switch (this.method) {
                    case GET:
                        response = client.get();
                        break;
                    case POST:
                        response = client.post(data);
                        break;
                    default:
                        response = Response.status(400).build();
                        break;
                }

                this.variables.put(DATA, response.readEntity(String.class));
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("""
                {
                    id: %s,
                    uri: %s,
                    token: *****,
                    method: %s
                }
                """, String.valueOf(id), variables, uri, method);
    }
}
