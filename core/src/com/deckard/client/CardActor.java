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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Align;
import com.deckard.server.card.Card;
import com.deckard.server.game.GameParams;

public class CardActor extends Group {
    private Card card;
    private Texture texture;
    private BitmapFont titleFont;
    private GlyphLayout titleFontGlyph;
    DraggableCardListener dragListener;
    private boolean selected;
    private Label name;
    private Label description;


    public CardActor(Card card, Texture texture) {
        this.card = card;
        this.texture = texture;
        titleFont = new BitmapFont();
        BitmapFont descriptionFont = new BitmapFont();

        setBounds(getX(), getY(), GuiParams.CARD_WIDTH, GuiParams.CARD_HEIGHT);
        dragListener = new DraggableCardListener();
        addListener(dragListener);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = titleFont;
        titleStyle.fontColor = GuiParams.MAIN_COLOR_DARK;

        Label.LabelStyle descriptionStyle = new Label.LabelStyle();
        descriptionStyle.font = descriptionFont;
        descriptionStyle.fontColor = GuiParams.MAIN_COLOR_DARK;

        name = new Label(card.getName(),titleStyle);
        name.setPosition(0,-GuiParams.CARD_SPACING);
        name.setSize(getWidth(),getHeight());
        name.setAlignment(Align.center);
        name.setAlignment(Align.top);

        description = new Label(card.getDescription(), descriptionStyle);
        description.setPosition(0, 0);
        description.setSize(getWidth(), getHeight());
        description.setAlignment(Align.center);
        description.setWrap(true);


        addActor(name);
        addActor(description);

        float duration = 0.3f;
        //refactor
        addListener(
                new InputListener(){
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        HandGroup hand = (HandGroup) getParent();
                        hand.setSelected(CardActor.this);
                        hand.updateLayout();

                        if (!selected) {
                            getActions().clear();
                            addAction(Actions.moveTo(getX(),GuiParams.CARD_HEIGHT/2,duration, Interpolation.pow3));
                        }
                        animateCard(1.2f, duration);
                        selected = true;
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
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //card
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(),
                getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
                false, false);

        super.draw(batch,parentAlpha);
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
