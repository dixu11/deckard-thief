package com.deckard.client;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

public class HandGroup extends Group {

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
        updateLayout();
    }

    private void updateLayout() {
        float totalWidth = 0f;
        SnapshotArray<Actor> cards = getChildren();
        for (int i = 0; i < cards.size; i++) {
            totalWidth += GuiParams.CARD_WIDTH + GuiParams.CARD_PADDING;
        }

        totalWidth -= GuiParams.CARD_PADDING;

        float startingX = -totalWidth / 2f;

        for (int i = 0; i < cards.size; i++) {
            Actor cardActor = cards.get(i);
            cardActor.setPosition(startingX + i * (GuiParams.CARD_WIDTH + GuiParams.CARD_PADDING), 0);
        }
    }
}
