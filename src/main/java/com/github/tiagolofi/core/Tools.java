package com.github.tiagolofi.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Tools {

    @JsonProperty
    private List<Tool> tools;

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public List<Tool> getTools() {
        return this.tools;
    }

    public Tool getTool(Long id) {
        return tools.stream()
            .filter(tool -> tool.getId().equals(id))
            .findFirst()
            .orElseThrow();
    }

    @Override
    public String toString() {
        return this.tools.toString();
    }
}
