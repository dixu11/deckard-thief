package com.deckard.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.deckard.server.card.Card;

public class CardActor extends Actor {
    private Card card;
    private Texture texture;
    private BitmapFont font;
    private GlyphLayout glyph;

    public CardActor(Card card, Texture texture, BitmapFont font) {
        this.card = card;
        this.texture = texture;
        this.font = font;
        this.glyph = new GlyphLayout();

        setBounds(getX(), getY(), GuiParams.CARD_WIDTH, GuiParams.CARD_HEIGHT);
        font.getData().scale(0.3f);
        addListener(new DraggableCardListener());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                GuiParams.CARD_WIDTH, GuiParams.CARD_HEIGHT, getScaleX(), getScaleY(),
                getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);
        glyph.setText(font, card.getName());
        float centerX = (getWidth() - glyph.width) / 2;
        font.setColor(GuiParams.MAIN_COLOR_DARK);
        font.draw(batch, card.getName(), getX() + centerX, getY() + getHeight() - GuiParams.CARD_PADDING);
    }

    private class DraggableCardListener extends DragListener {
        private float deltaX;
        private float deltaY;

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            deltaX = x;
            deltaY = y;
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void drag(InputEvent event, float x, float y, int pointer) {
            float newX = getX() + x - deltaX;
            float newY = getY() + y - deltaY;
            setPosition(newX, newY);
        }
    }


}
