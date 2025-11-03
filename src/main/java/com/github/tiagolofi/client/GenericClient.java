package com.github.tiagolofi.client;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Response;

@RequestScoped
public interface GenericClient {
    
    @GET
    Response get();

    @POST
    <T> Response post(T data);

    @PUT
    <T> Response put(T data);

}
