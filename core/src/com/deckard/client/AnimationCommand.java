package com.deckard.client;

public class AnimationCommand {

    private Runnable runnable;

    public AnimationCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    public void animate() {
        runnable.run();
    }
}
