package com.deckard.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deckard.server.card.Card;

public class CardActor extends Actor {
    private Card card;
    private Texture texture;

    public CardActor(Card card, Texture texture) {
        this.card = card;
        this.texture = texture;
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                GuiParams.CARD_WIDTH, GuiParams.CARD_HEIGHT, getScaleX(), getScaleY());
    }
}
