package com.deckard.client;

import java.util.LinkedList;
import java.util.Queue;

public class AnimationManager {

    private Queue<AnimationCommand>registerAnimation = new LinkedList<>();
    private static AnimationManager instance = new AnimationManager();
    private AnimationManager() {
    }

    public static AnimationManager getInstance() {
        return instance;
    }

    public void registerAnimation(AnimationCommand animation) {
        registerAnimation.add(animation);
        if (registerAnimation.size() == 1) {
            registerAnimation.element().animate();
        }
    }

    public void animationFinished() {
        registerAnimation.remove();
        if (registerAnimation.isEmpty()) {
            return;
        }
        registerAnimation.element().animate();
    }
}
