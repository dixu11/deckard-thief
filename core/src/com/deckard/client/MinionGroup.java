package com.deckard.client;

import com.badlogic.gdx.scenes.scene2d.Group;

public class MinionGroup extends Group {
    private MinionBodyActor minionBody;
    private HandGroup hand;

    public MinionGroup(MinionBodyActor minionBody, HandGroup hand) {
        this.minionBody = minionBody;
        this.hand = hand;
        addActor(hand);
        hand.setPosition(90,GuiParams.CARD_HEIGHT * 0.5f);
        addActor(minionBody);
    }
}
