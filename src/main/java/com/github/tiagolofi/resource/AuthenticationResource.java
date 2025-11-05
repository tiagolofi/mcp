package com.github.tiagolofi.resource;

import com.github.tiagolofi.security.LoginUsuarioSenha;
import com.github.tiagolofi.security.TokenJwt;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/login")
public class AuthenticationResource {
    
    @Inject
    TokenJwt tokenJwt;


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
    public Response login(LoginUsuarioSenha requisicao) {
        if (tokenJwt.validar(requisicao)) {
            return Response.status(200).entity(tokenJwt.geraToken()).build();
        }
        return Response.status(401).build();
    }

}
