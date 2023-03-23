package com.deckard.client.actor;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.SnapshotArray;
import com.deckard.client.core.GuiParams;
import com.deckard.server.card.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class HandGroup extends Group {
    private static final float MAX_ROTATION = 30f;
    private static final float LAYOUT_ANIMATION_DURATION = 0.25f; //was 1

    private CardActor selected;

    @Override
    public void addActor(Actor actor) {
        CardActor cardActor = (CardActor) actor;
        cardActor.setLayoutIgnore(false);
        super.addActor(actor);
        // updateLayout();
    }

    public void updateLayout() {
        addAction(new UpdateLayoutAction());
    }

    public void updateLayout(float duration) {
        addAction(new UpdateLayoutAction(duration));
    }

    public UpdateLayoutAction getUpdateAction(float duration) {
        return new UpdateLayoutAction(duration);
    }

    private static float countTotalWidth(Iterable<? extends Actor> cards) {
        float totalWidth = 0f;
        for (Actor card : cards) {
            totalWidth += card.getWidth() - GuiParams.CARD_SPACING;
        }
        return totalWidth;
    }

    private static float calculateOffsetX(int selectedIndex, int i, CardActor cardActor) {
        float xOffset = 0;
        if (i < selectedIndex) {
            xOffset += GuiParams.CARD_SPACING / 8f;
        } else if (selectedIndex != -1) {
            xOffset += GuiParams.CARD_SPACING * 1.3;
        }

        if (cardActor.isSelected()) {
            xOffset += cardActor.getWidth() * (cardActor.getScaleX() - 1f) / 2f;
        }
        return xOffset;
    }

    private static float calculateRotation(int size, int i, float howFarFromCenter) {
        if (howFarFromCenter == 0) {
            return 0;
        }
        float rotation = (howFarFromCenter / (size - 1) / 2.0f) * MAX_ROTATION;
        if (i > (size - 1) / 2.0f) {
            rotation = -rotation;
        }
        return rotation;
    }

    public void setSelected(CardActor selected) {
        this.selected = selected;
    }

    public void selectCard(Card card) {
        CardActor cardActor = getActor(card);
        cardActor.select();
        selected = cardActor;
    }

    public void remove(CardActor cardActor) {
        if (selected!= null && selected.equals(cardActor)) {
            selected.unselect();
            selected = null;
        }
        removeActor(cardActor);
    }

    public CardActor getActor(Card card) {
        for (Actor child : getChildren()) {
            CardActor cardActor = (CardActor) child;
            if (cardActor.getCard().equals(card)) {
                return cardActor;
            }
        }
        throw new NoSuchElementException();
    }

    public class UpdateLayoutAction extends Action {

        private float duration = 1f;

        public UpdateLayoutAction(float duration) {
            this.duration = duration;
        }

        public UpdateLayoutAction() {
        }

        @Override
        public boolean act(float delta) {
            updateLayout(duration);
            return true;
        }

        private List<CardActor> getNotIgnored() {
            SnapshotArray<Actor> cards = getChildren();
           return Arrays.stream(cards.toArray())
                   .map(card -> ((CardActor) card))
                    .filter(cardActor -> !cardActor.isLayoutIgnore())
                    .toList();
        }

        public void updateLayout(float duration) {
            List<CardActor> cards = getNotIgnored();


            int selectedIndex = cards.indexOf(selected);

            float totalWidth = countTotalWidth(cards);
            float startingX = -totalWidth / 2;

            for (int i = 0; i < cards.size(); i++) {
                if (selectedIndex == i) {
                    continue;
                }
                CardActor cardActor = (CardActor) cards.get(i);
                float xOffset = calculateOffsetX(selectedIndex, i, cardActor);

                Interpolation interpolation = Interpolation.circleOut;
                float howFarFromCenter = Math.abs(i - (cards.size() - 1) / 2.0f);

                //animate moveTo
                cardActor.addAction(Actions.moveTo(startingX + xOffset + i * (cardActor.getWidth() - GuiParams.CARD_SPACING),
                        GuiParams.CARD_SPACING - howFarFromCenter * 25, duration, interpolation));

                //animate rotation
                float rotation = calculateRotation(cards.size(), i, howFarFromCenter);
                cardActor.addAction(Actions.rotateTo(rotation, duration, interpolation));
            }
        }
    }

}


