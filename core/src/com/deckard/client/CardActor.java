package com.deckard.client;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.deckard.server.card.Card;

public class CardActor extends Actor {
    private Card card;
    private Texture texture;
    private BitmapFont font;
    private GlyphLayout glyph;
    DraggableCardListener dragListener;
    private boolean selected;

    public CardActor(Card card, Texture texture) {
        this.card = card;
        this.texture = texture;
        font = new BitmapFont();
        this.glyph = new GlyphLayout();

        setBounds(getX(), getY(), GuiParams.CARD_WIDTH, GuiParams.CARD_HEIGHT);
        dragListener = new DraggableCardListener();
        addListener(dragListener);

        //refactor
        addListener(
                new InputListener(){
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        animateCard(1.2f, 0.2f);
                        spreadCards(CardActor.this);
                        HandGroup hand = (HandGroup) getParent();
                        hand.setSelected(CardActor.this);
                        //replace with event
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        if (pointer == 0) { //source - drag
                            return;
                        }
                        animateCard(1f, 0.2f);
                        spreadCards(null);
                        HandGroup hand = (HandGroup) getParent();
                        hand.setSelected(null);
                    }
                }
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //card
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);

        glyph.setText(font, card.getName());
        float centerX = (getWidth()*getScaleX() - glyph.width) / 2;
        font.setColor(GuiParams.MAIN_COLOR_DARK);
        float fontScale = 1.6f * getScaleX();
     //   float scaledCardPadding = GuiParams.CARD_PADDING * getScaleY();
        font.getData().setScale(fontScale);
        font.draw(batch, card.getName(), getX() + centerX, getY() + getHeight()*getScaleX() - GuiParams.CARD_SPACING);

    }

    void animateCard(float targetScale, float duration) {
        addAction(Actions.scaleTo(targetScale,targetScale,duration, Interpolation.pow3));
    }

    void animatePosition(float targetX, float targetY, float duration) {
        addAction(Actions.moveTo(targetX, targetY, duration, Interpolation.pow3));
    }

    private void spreadCards( CardActor selectedCard){
      /*  if (selectedCard == null) {
            return;
        }
        float spacing = GuiParams.CARD_SPACING;
        float duration = 0.2f;

        Group cardGroup = selectedCard.getParent();
        Array<Actor> cards = cardGroup.getChildren();

        float totalWidth = 0;
        for (Actor card : cards) {
            if (card != selectedCard) {
                totalWidth += card.getWidth();
                totalWidth += spacing;
            }
        }

        float currentX = (cardGroup.getWidth() - totalWidth) / 2;
        for (Actor card : cards) {
            if (card != selectedCard) {
                ((CardActor) card).animatePosition(currentX, card.getY(), duration);
                currentX += card.getWidth() + spacing;
            }
        }*/
    }

    public boolean isSelected() {
        return selected;
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
