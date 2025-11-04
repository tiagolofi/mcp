package com.github.tiagolofi.security;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "mcp")
public interface McpSecurityConfigs {
    public String username();
    public String password();
    public String roles();
}
