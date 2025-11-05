package com.github.tiagolofi.client;

import java.util.ArrayList;
import java.util.List;

public class OpenAiRequestWebSearch implements OpenAiRequest {
    private String model;
    private List<TypeTools> tools = new ArrayList<>();
    private String input;

    public OpenAiRequestWebSearch(String model, String type, String input) {
        this.model = model;
        this.tools.add(new TypeTools(type));
        this.input = input;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<TypeTools> getTools() {
        return tools;
    }

    public void setTools(List<TypeTools> tools) {
        this.tools = tools;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public static class TypeTools {
        private String type;

        public TypeTools(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
