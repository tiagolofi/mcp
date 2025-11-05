package com.github.tiagolofi.client;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;

@RequestScoped
public interface ExternalAuthenticationClient {

    @POST
    <T> Response post(T data);

}
