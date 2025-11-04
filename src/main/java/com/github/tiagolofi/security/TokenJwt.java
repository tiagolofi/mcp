package com.github.tiagolofi.security;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class TokenJwt {

    @Inject
    McpSecurityConfigs mcpSecurityConfigs;

    public boolean validar(LoginUsuarioSenha login) {
        return mcpSecurityConfigs.username().equals(login.getUsername()) && 
            mcpSecurityConfigs.password().equals(login.getPassword());
    }

    public String geraToken() {
        return Jwt
            .issuer("https://github.com.br/tiagolofi")
            .upn(mcpSecurityConfigs.username())
            .groups(Set.of(mcpSecurityConfigs.roles().split(",")))
            .claim("dataHora", LocalDateTime.now())
            .expiresIn(Duration.ofHours(1))
            .innerSign()
            .encrypt();
    }

}