package com.deckard.client;

import com.badlogic.gdx.scenes.scene2d.Action;

public class CountDownAction extends Action {
    private int count;

    public CountDownAction(int count) {
        this.count = count;
    }

    @Override
    public boolean act(float delta) {
        System.out.println("Countdown done");
        count--;
        return true;
    }

    public boolean isComplete() {
        return count == 0;
    }
}
