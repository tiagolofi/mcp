package com.github.tiagolofi.client;

import org.jboss.resteasy.reactive.RestHeader;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.Response;

@RequestScoped
public interface GenericClient {
    
    @GET
    Response get();

    @GET
    Response get(@RestHeader("Authorization") String token);

    @POST
    <T> Response post(T data);
  
    @POST
    <T> Response post(@RestHeader("Authorization") String token, T data);

    @PUT
    <T> Response put(@RestHeader("Authorization") String token, T data);

}
