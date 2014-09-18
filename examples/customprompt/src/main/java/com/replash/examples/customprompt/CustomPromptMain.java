package com.replash.examples.customprompt;

import com.replash.*;

public class CustomPromptMain {
    private static class CountingPromptProvider implements PromptProvider {
        private ReplashEventListener eventListener;
        private int commandCount = 0;

        public void reset() {
            commandCount = -1;
        }

        @Override
        public String getPrompt(ReplashRuntime runtime) {
            if(eventListener == null) {
                eventListener = new CustomPromptRunnerEventListener();
                runtime.getReplashRunner().addEventListener(eventListener);
            }

            return String.format("Count = %d >> ", commandCount);
        }

        private class CustomPromptRunnerEventListener extends AbstractReplashEventListener {
            @Override
            public void afterCommand(ReplashRuntime runtime) {
                ++commandCount;
            }
        }
    }

    private static class ResetCountCommand implements BasicCommand {
        @Override
        public void execute(CommandContext executionContext) throws Exception {
            ((CountingPromptProvider)executionContext.getRuntime().getPromptProvider()).reset();
        }
    }

    public static void main(String[] args) {
        Replash replash = new ReplashBuilder()
                .withPromptProvider(new CountingPromptProvider())
                .withCommand("reset", new ResetCountCommand())
                .build();
        replash.run();
    }
}
