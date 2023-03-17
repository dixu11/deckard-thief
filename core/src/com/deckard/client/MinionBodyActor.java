package com.deckard.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deckard.server.card.Card;
import com.deckard.server.minion.Minion;

public class MinionBodyActor extends Actor {
    private Minion minion;
    private Texture texture;
    private BitmapFont font;
    private GlyphLayout glyph;

    public MinionBodyActor(Minion minion, Texture texture, BitmapFont font) {
        this.minion = minion;
        this.texture = texture;
        this.font = font;
        this.glyph = new GlyphLayout();

        setBounds(getX(), getY(), GuiParams.MINION_WIDTH, GuiParams.MINION_HEIGHT);
        //font.getData().scale(0.3f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);
    }

}
