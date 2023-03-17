package com.deckard.client;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.SnapshotArray;

public class HandGroup extends Group {

    private CardActor selected;

    @Override
    public void addActor(Actor actor) {
        super.addActor(actor);
         updateLayout();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
       // updateLayout();
    }

    public void updateLayout() {
        float totalWidth = 0f;
        float maxRotation = 30f;
        float duration = 2f;
        SnapshotArray<Actor> cards = getChildren();
        int selectedIndex = cards.indexOf(selected, false);

        for (Actor card : cards) {
            totalWidth += card.getWidth() - GuiParams.CARD_SPACING;
        }

        float startingX = -totalWidth / 2f;
        for (int i = 0; i < cards.size; i++) {
            float xOffset = 0;
            if (i < selectedIndex) {
                xOffset -= GuiParams.CARD_SPACING/2;
            } else if (i > selectedIndex && selectedIndex != -1) {
                xOffset +=  GuiParams.CARD_SPACING*1.4;
            }
            if (i == selectedIndex) {
                continue;
            }
            CardActor cardActor = (CardActor) cards.get(i);
            Interpolation interpolation = Interpolation.circleOut;
            float howFarFromCenter = Math.abs(i - (cards.size - 1) / 2.0f);

            if (cardActor.isSelected()) {
                xOffset += cardActor.getWidth() * (cardActor.getScaleX() - 1f) / 2f;
            }
            cardActor.addAction(Actions.moveTo(startingX + xOffset + i * (cardActor.getWidth() - GuiParams.CARD_SPACING),
                    GuiParams.CARD_SPACING - howFarFromCenter * 25, duration, interpolation));

            float rotation = (howFarFromCenter / (cards.size - 1) / 2.0f) * maxRotation;
            if (i > (cards.size - 1) / 2.0f) {
                rotation = -rotation;
            }
            cardActor.addAction(Actions.rotateTo(rotation, duration, interpolation));
        }
    }

    public void setSelected(CardActor selected) {
        this.selected = selected;
    }
}
