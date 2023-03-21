package com.deckard.client.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deckard.client.core.GuiParams;
import com.deckard.server.minion.Minion;

public class MinionBodyActor extends Actor {
    private static final float HEALTH_BAR_WIDTH = GuiParams.MINION_WIDTH * 0.8f;
    private static final float HEALTH_BAR_HEIGHT = 5f;
    private Minion minion;
    private Texture texture;
    private BitmapFont font;
    private GlyphLayout glyph;
    private boolean flipX = false;

    public MinionBodyActor(Minion minion, Texture texture, BitmapFont font) {
        this.minion = minion;
        this.texture = texture;
        this.font = font;
        this.glyph = new GlyphLayout();

        minion.setHp(6);
        setBounds(getX(), getY(), GuiParams.MINION_WIDTH, GuiParams.MINION_HEIGHT);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                flipX, false);
        batch.setColor(Color.WHITE);
        renderHealthBard(batch);

    }

    private void renderHealthBard(Batch batch) {
        float healthBarX = getX() + (getWidth() - HEALTH_BAR_WIDTH) / 2;
        float healthBarY = getY() - 10f - HEALTH_BAR_HEIGHT;
        batch.setColor(Color.BLACK);
        batch.draw(texture, healthBarX, healthBarY, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);


        float healthPercentage = (float) minion.getHp() / minion.getMaxHp();
        float healthBarWidth = HEALTH_BAR_WIDTH * healthPercentage;
        batch.setColor(Color.GREEN);
        font.setColor(GuiParams.MAIN_COLOR_BRIGHT);
        batch.draw(texture, healthBarX, healthBarY, healthBarWidth, HEALTH_BAR_HEIGHT);
        batch.setColor(Color.WHITE);

        // Rysujemy liczbę życia miniona
        String hpText = minion.getHp() + " / " + minion.getMaxHp();
        glyph.setText(font, hpText);
        float textX = getX() + (getWidth() - glyph.width) / 2;
        float textY = healthBarY - 5f;
        font.draw(batch, hpText, textX, textY);
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }
}
