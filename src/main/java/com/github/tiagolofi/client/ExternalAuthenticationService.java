package com.github.tiagolofi.client;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import com.github.tiagolofi.core.ExternalAuthenticationRequest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ExternalAuthenticationService {

    public <T> Response authentication(ExternalAuthenticationRequest<T> request) {
        RestClientBuilder builder = RestClientBuilder.newBuilder()
            .baseUri(request.getUri());

        if (request.getHeaders() != null) {
            request.getHeaders().forEach(header -> builder.header(header.getName(), header.getValue()));
        }

        ExternalAuthenticationClient client = builder.build(ExternalAuthenticationClient.class);

        return client.post(request.getData());
    }

}
