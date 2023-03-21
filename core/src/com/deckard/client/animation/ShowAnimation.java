package com.deckard.client.animation;

import com.badlogic.gdx.scenes.scene2d.Action;

public class ShowAnimation extends Action {


    @Override
    public boolean act(float delta) {
        getActor().setVisible(true);
        return true;
    }
}
