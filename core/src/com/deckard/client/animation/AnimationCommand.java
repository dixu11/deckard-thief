package com.deckard.client.animation;

public class AnimationCommand {

    private final Runnable runnable;

    public AnimationCommand(Runnable runnable) {
        this.runnable = runnable;
    }

    public void animate() {
        runnable.run();
    }
}
