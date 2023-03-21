package com.deckard.client.animation;

import com.badlogic.gdx.scenes.scene2d.Action;

public class CountDownAction extends Action {
    private int count;

    public CountDownAction(int count) {
        this.count = count;
    }

    @Override
    public boolean act(float delta) {
        count--;
        return true;
    }

    public boolean isComplete() {
        return count == 0;
    }
}
