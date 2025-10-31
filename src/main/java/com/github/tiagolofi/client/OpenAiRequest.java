package com.github.tiagolofi.client;

import java.util.Map;

public class OpenAiRequest {
    private String model;
    private Prompt prompt = new Prompt();

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static class Prompt {
        private String id;
        private String version;
        private Map<String, String> variables;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Map<String, String> getVariables() {
            return variables;
        }

        public void setVariables(Map<String, String> variables) {
            this.variables = variables;
        }

        @Override
        public String toString() {
            return "id: " + this.id + "\nversion: " + this.version + "\nvariables: " + this.variables; 
        }
    }

    @Override
    public String toString() {
        return "model: " + this.model + "\nprompt: " + this.prompt;
    }

}
