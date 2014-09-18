package com.replash;

public class StaticTextPromptProvider implements PromptProvider {
    private final String prompt;

    public StaticTextPromptProvider() {
        this("> ");
    }

    public StaticTextPromptProvider(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public String getPrompt(ReplashRuntime runtime) {
        return prompt;
    }
}
