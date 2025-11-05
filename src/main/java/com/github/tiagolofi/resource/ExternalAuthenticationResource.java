package com.github.tiagolofi.resource;

import com.github.tiagolofi.client.ExternalAuthenticationService;
import com.github.tiagolofi.core.ExternalAuthenticationRequest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/external")
public class ExternalAuthenticationResource {
    
    @Inject
    ExternalAuthenticationService externalAuthenticationService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    @RolesAllowed({"user"})
    public Response externalLogin(ExternalAuthenticationRequest request) {
        return externalAuthenticationService.authentication(request);
    }

}
