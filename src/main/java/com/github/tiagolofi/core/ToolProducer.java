package com.github.tiagolofi.core;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class ToolProducer {
    
    @Produces
    public static Tools tools(InjectionPoint point) throws IOException {

        ConfigTools configs = point.getAnnotated().getAnnotation(ConfigTools.class);

        String filename = configs.filename();

        if (filename != null && !filename.isBlank()) {
            File file = new File(filename + ".json");
            if (file.exists() && file.canRead()) {
                ObjectMapper mapper = new ObjectMapper();
                Tools tools = mapper.readValue(file, Tools.class);
                return tools;
            }
        }

        return null;
    }

}
