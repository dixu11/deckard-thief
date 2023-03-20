package com.deckard.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.deckard.server.card.Card;
import com.deckard.server.event.ActionEvent;
import com.deckard.server.event.ActionEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;
import com.deckard.server.minion.Minion;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MinionGroup extends Group implements EventHandler {
    private MinionBodyActor minionBody;
    private HandGroup hand;
    private Minion minion;
    private CountDownAction countDown = null;
   private Texture cardTexture = new Texture(Gdx.files.internal("card.png"));

    public MinionGroup(MinionBodyActor minionBody, HandGroup hand, Minion minion) {
        this.minionBody = minionBody;
        this.hand = hand;
        this.minion = minion;
        addActor(hand);
        hand.setPosition(90, GuiParams.CARD_HEIGHT * 0.5f);
        addActor(minionBody);

        Bus.register(this, ActionEventType.MINION_CARD_DRAW);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (countDown == null) return;
        if (countDown.isComplete()) {
            countDown = null;
            AnimationManager.getInstance().animationFinished();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (!event.getMinion().equals(minion)) return;
        AnimationCommand animationCommand = new AnimationCommand(() -> animatePlayCard(event));
        AnimationManager.getInstance().registerAnimation(animationCommand);
    }

    private void animatePlayCard(ActionEvent event) {
        Card card = event.getCard();
        CardActor cardActor = new CardActor(card, cardTexture);
        hand.addActor(cardActor);
       cardActor.setVisible(false);

        countDown = new CountDownAction(1);
        cardActor.setPosition(0,0);
        cardActor.addAction(sequence(rotateTo(-140,0),fadeOut(0), hand.getUpdateAction(), fadeIn(1f), countDown));
        System.out.println("Animation started");
    }
}
