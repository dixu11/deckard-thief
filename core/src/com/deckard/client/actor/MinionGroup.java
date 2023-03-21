package com.deckard.client.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.deckard.client.animation.ShowAnimation;
import com.deckard.client.core.GuiParams;
import com.deckard.client.animation.AnimationCommand;
import com.deckard.client.animation.AnimationManager;
import com.deckard.client.animation.CountDownAction;
import com.deckard.server.card.Card;
import com.deckard.server.event.ActionEvent;
import com.deckard.server.event.ActionEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;
import com.deckard.server.minion.Minion;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class MinionGroup extends Group implements EventHandler {
    private MinionBodyActor minionBody;
    private HandGroup hand;
    private Minion minion;
    private CountDownAction countDown = null;
   private Texture cardTexture = new Texture(Gdx.files.internal("card.png"));
   private float delay;

    public MinionGroup(MinionBodyActor minionBody, HandGroup hand, Minion minion) {
        this.minionBody = minionBody;
        this.hand = hand;
        this.minion = minion;
        addActor(hand);
        hand.setPosition(90, GuiParams.CARD_HEIGHT * 0.5f);
        addActor(minionBody);

        Bus.register(this, ActionEventType.MINION_CARD_DRAW);
        Random random = new Random();
        delay = random.nextFloat() / 5;
//        delay = random.nextFloat() / 4;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (countDown == null) return;
        if (countDown.isComplete()) {
            countDown = null;
            AnimationManager.getInstance().finishedGlobal();
//            AnimationManager.getInstance().finishedIndividual(this);
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (!event.getMinion().equals(minion)) return;
        AnimationCommand animationCommand = new AnimationCommand(() -> animatePlayCard(event));
        AnimationManager.getInstance().registerGlobal(animationCommand);
//        AnimationManager.getInstance().registerIndividual(animationCommand,this);
    }

    private void animatePlayCard(ActionEvent event) {
        Card card = event.getCard();
        CardActor cardActor = new CardActor(card, cardTexture);
        hand.addActor(cardActor);
       cardActor.setVisible(false);

        countDown = new CountDownAction(1);
        cardActor.setPosition(0,0);

        float duration = delay + 0.15f;
//        float duration = delay + 0.25f;
        cardActor.addAction(sequence(rotateTo(-140,0),fadeOut(0),new ShowAnimation(),
                hand.getUpdateAction(duration), fadeIn(duration), countDown));
    }
}
