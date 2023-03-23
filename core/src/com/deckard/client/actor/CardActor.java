package com.deckard.client.actor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.deckard.client.core.GuiParams;
import com.deckard.server.card.Card;

public class CardActor extends Group {
    private Card card;
    private Texture texture;
    private boolean selected;
    private static final float ANIMATION_DURATION = 0.3f;
    private boolean layoutIgnore;

    public CardActor(Card card, Texture texture) {
        this.card = card;
        this.texture = texture;
        addListener(new DragCardListener());
        addListener(new HoverCardListener());
        setBounds(getX(), getY(), GuiParams.CARD_WIDTH, GuiParams.CARD_HEIGHT);

        createCardName(card);
        createCardDescription(card);
    }

    private void createCardDescription(Card card) {
        BitmapFont descriptionFont = new BitmapFont();
        Label.LabelStyle descriptionStyle = new Label.LabelStyle();
        descriptionStyle.font = descriptionFont;
        descriptionStyle.fontColor = GuiParams.MAIN_COLOR_DARK;
        Label description = new Label(card.getDescription(), descriptionStyle);
        description.setPosition(0, 0);
        description.setSize(getWidth(), getHeight());
        description.setAlignment(Align.center);
        description.setWrap(true);
        addActor(description);
    }

    private void createCardName(Card card) {
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = new BitmapFont();
        titleStyle.fontColor = GuiParams.MAIN_COLOR_DARK;
        Label name = new Label(card.getName(), titleStyle);
        name.setPosition(0, -GuiParams.CARD_SPACING);
        name.setSize(getWidth(), getHeight());
        name.setAlignment(Align.center);
        name.setAlignment(Align.top);
        addActor(name);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //card
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);
        batch.setColor(Color.WHITE);
        super.draw(batch, parentAlpha);
    }

    void animateCard(float targetScale, float duration) {
        addAction(Actions.scaleTo(targetScale, targetScale, duration, Interpolation.pow3));
    }

    public boolean isSelected() {
        return selected;
    }

    public Card getCard() {
        return card;
    }

    public void select() {
        animateCard(1.2f, ANIMATION_DURATION);
        selected = true;
    }

    public void unselect() {
        selected = false;
    }

    private class HoverCardListener extends InputListener {

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            HandGroup hand = (HandGroup) getParent();
            hand.setSelected(CardActor.this);
            hand.updateLayout();

            if (!selected) {
                getActions().clear();
                addAction(Actions.moveTo(getX(), GuiParams.CARD_HEIGHT / 2, ANIMATION_DURATION, Interpolation.pow3));
            }
          select();
            //replace with event
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {

            if (pointer == 0) { //source - drag
                //    return;
            }
            animateCard(1f, 1f);
            HandGroup hand = (HandGroup) getParent();
            hand.setSelected(null);
            selected = false;
            hand.updateLayout();
        }
    }

    private class DragCardListener extends DragListener {
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

    public void setLayoutIgnore(boolean layoutIgnore) {
        this.layoutIgnore = layoutIgnore;
    }

    public boolean isLayoutIgnore() {
        return layoutIgnore;
    }
}
