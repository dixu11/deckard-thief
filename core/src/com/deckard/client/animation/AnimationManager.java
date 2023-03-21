package com.deckard.client.animation;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class AnimationManager {

    private final Queue<AnimationCommand> globalQueue = new LinkedList<>();
    private final Map<Actor, Queue<AnimationCommand>> individualQueue = new HashMap<>();
    private final static AnimationManager instance = new AnimationManager();
    private AnimationManager() {
    }

    public static AnimationManager getInstance() {
        return instance;
    }

    public void registerGlobal(AnimationCommand animation) {
        globalQueue.add(animation);
        if (globalQueue.size() == 1) {
            globalQueue.element().animate();
        }
    }

    public void registerIndividual(AnimationCommand animation, Actor actor) {
        individualQueue.computeIfAbsent(actor, value -> new LinkedList<>());
        Queue<AnimationCommand> queue = individualQueue.get(actor);
        queue.add(animation);
        if (queue.size() == 1) {
            queue.element().animate();
        }
    }

    public void finishedGlobal() {
        globalQueue.remove();
        if (globalQueue.isEmpty()) {
            return;
        }
        globalQueue.element().animate();
    }

    public void finishedIndividual(Actor actor) {
        Queue<AnimationCommand> queue = individualQueue.get(actor);
        queue.remove();
        if (queue.isEmpty()) {
            return;
        }
        queue.element().animate();
    }
}
