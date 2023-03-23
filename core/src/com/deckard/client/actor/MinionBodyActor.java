package com.deckard.client.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.deckard.client.animation.AnimationCommand;
import com.deckard.client.animation.AnimationManager;
import com.deckard.client.animation.CountDownAnimation;
import com.deckard.client.core.GuiParams;
import com.deckard.server.event.ActionEvent;
import com.deckard.server.event.ActionEventType;
import com.deckard.server.event.Event;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;
import com.deckard.server.minion.Minion;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MinionBodyActor extends Actor implements EventHandler {
    private static final float HEALTH_BAR_WIDTH = GuiParams.MINION_WIDTH * 0.8f;
    private static final float HEALTH_BAR_HEIGHT = 5f;
    private Minion minion;
    private Texture texture;
    private BitmapFont font;
    private GlyphLayout glyph;
    private boolean flipX = false;
    private CountDownAnimation countDown;
    private Sound dmgSound;
    private int value;

    public MinionBodyActor(Minion minion, Texture texture, BitmapFont font) {
        this.minion = minion;
        this.texture = texture;
        this.font = font;
        this.glyph = new GlyphLayout();

        setBounds(getX(), getY(), GuiParams.MINION_WIDTH, GuiParams.MINION_HEIGHT);
        Bus.register(this, ActionEventType.MINION_DAMAGED);
        dmgSound = Gdx.audio.newSound(Gdx.files.internal("push1.wav"));
        Bus.register(this,ActionEventType.MINION_HEALTH_CHANGED);
        value = minion.getHp();
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


        float healthPercentage = (float) value/ minion.getMaxHp();
        float healthBarWidth = HEALTH_BAR_WIDTH * healthPercentage;
        batch.setColor(Color.GREEN);
        font.setColor(GuiParams.MAIN_COLOR_BRIGHT);
        batch.draw(texture, healthBarX, healthBarY, healthBarWidth, HEALTH_BAR_HEIGHT);
        batch.setColor(Color.WHITE);

        // Rysujemy liczbę życia miniona
        String hpText = value + " / " + minion.getMaxHp();
        glyph.setText(font, hpText);
        float textX = getX() + (getWidth() - glyph.width) / 2;
        float textY = healthBarY - 5f;
        font.draw(batch, hpText, textX, textY);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (countDown == null) return;
        if (countDown.isComplete()) {
            countDown = null;
            AnimationManager.getInstance().finishedGlobal();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (!event.getMinion().equals(minion)) {
            return;
        }
        if (event.getType() == ActionEventType.MINION_DAMAGED) {
            AnimationManager.getInstance().registerGlobal(new AnimationCommand(()->animateDamaged(event)));
        }
    }

    public void animateDamaged(ActionEvent event) {
        countDown = new CountDownAnimation(1);
        int rotation = 0; // left
        int move = -50;
        int moveY = -50;
        float time = 0.1f;
        if (flipX) { //right
            move =50;
            rotation = 0;
        }

        addAction(sequence(parallel(rotateTo(-rotation,time), moveTo(getX()+move,getY() + moveY,time),color(Color.RED,time)), run(()-> value = event.getValue()),
                parallel(rotateTo(0,time), moveTo(getX(),getY(),time),color(Color.WHITE,time)),countDown));
        dmgSound.play();
    }

    public void setFlipX(boolean flipX) {
        this.flipX = flipX;
    }
}
