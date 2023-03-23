package com.deckard.client.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.deckard.client.animation.ShowAnimation;
import com.deckard.client.core.GuiParams;
import com.deckard.client.animation.AnimationCommand;
import com.deckard.client.animation.AnimationManager;
import com.deckard.client.animation.CountDownAnimation;
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
    private CountDownAnimation countDownDraw = null;
    private CountDownAnimation countDownDiscard = null;
    private CountDownAnimation countDownPlay = null;
    private Texture cardTexture = new Texture(Gdx.files.internal("card.png"));
    private float delay;
    private Sound drawSound;
    private Sound discardSound;

    public MinionGroup(MinionBodyActor minionBody, HandGroup hand, Minion minion) {
        this.minionBody = minionBody;
        this.hand = hand;
        this.minion = minion;
        addActor(hand);
        hand.setPosition(90, GuiParams.CARD_HEIGHT * 0.5f);
        addActor(minionBody);

        Bus.register(this, ActionEventType.MINION_CARD_DRAW);
        Bus.register(this, ActionEventType.MINION_CARD_PLAYED);
        Bus.register(this, ActionEventType.MINION_CARD_EXECUTED);

        Random random = new Random();
        delay = random.nextFloat() / 5;
//        delay = random.nextFloat() / 4;
        drawSound = Gdx.audio.newSound(Gdx.files.internal("draw2.wav"));
        discardSound = Gdx.audio.newSound(Gdx.files.internal("discard1.wav"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (countDownDraw != null && countDownDraw.isComplete()) {
            countDownDraw = null;
            AnimationManager.getInstance().finishedGlobal();
//            AnimationManager.getInstance().finishedIndividual(this);
        }
        if (countDownDiscard != null && countDownDiscard.isComplete()) {
            countDownDiscard = null;
            AnimationManager.getInstance().finishedGlobal();
        }
        if (countDownPlay != null && countDownPlay.isComplete()) {
            countDownPlay = null;
            AnimationManager.getInstance().finishedGlobal();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (!event.getMinion().equals(minion)) return;

        switch (event.getType()) {
            case MINION_CARD_DRAW -> {
                AnimationCommand animationCommand = new AnimationCommand(() -> animateDrawCard(event));
                AnimationManager.getInstance().registerGlobal(animationCommand);
//        AnimationManager.getInstance().registerIndividual(animationCommand,this);
            }
            case MINION_CARD_PLAYED -> {
                AnimationCommand animationCommand = new AnimationCommand(() -> animatePlayCard(event));
                AnimationManager.getInstance().registerGlobal(animationCommand);
            }
            case MINION_CARD_EXECUTED -> {
                AnimationCommand animationCommand = new AnimationCommand(() -> animateDiscardCard(event));
                AnimationManager.getInstance().registerGlobal(animationCommand);
            }
        }
    }

    private void animatePlayCard(ActionEvent event) {
        countDownDraw = new CountDownAnimation(1);
        hand.selectCard(event.getCard());
        hand.updateLayout();
        CardActor cardActor = hand.getActor(event.getCard());
        cardActor.addAction(sequence(fadeIn(0.25f), countDownDraw));
    }

    private void animateDrawCard(ActionEvent event) {
        Card card = event.getCard();
        CardActor cardActor = new CardActor(card, cardTexture);
        hand.addActor(cardActor);
        cardActor.setVisible(false);

        countDownDraw = new CountDownAnimation(1);
        cardActor.setPosition(0, 0);

        float duration = delay + 0.15f;
//        float duration = 4;
//        float duration = delay + 0.25f;
        cardActor.setPosition(0,-GuiParams.CARD_HEIGHT);
        cardActor.addAction(sequence(rotateTo(-140, 0),moveTo(cardActor.getX()+GuiParams.CARD_WIDTH,cardActor.getY()),scaleTo(0.75f,0.75f), fadeOut(0), new ShowAnimation(),
                hand.getUpdateAction(duration),parallel( fadeIn(duration),scaleTo(1f,1f,duration)), countDownDraw));
        drawSound.play();
    }

    private void animateDiscardCard(ActionEvent event) {
        countDownDiscard = new CountDownAnimation(1);
        CardActor cardActor = hand.getActor(event.getCard());
        float duration = 0.5f;
        cardActor.setLayoutIgnore(true);
        cardActor.addAction(sequence(parallel(fadeOut(duration),rotateTo(140,duration),scaleTo(0.25f,0.25f,duration),
                moveTo(cardActor.getX(),cardActor.getY()-GuiParams.CARD_HEIGHT,duration),hand.getUpdateAction(0.30f)),
                countDownDiscard,run(()-> hand.remove(cardActor))));
        discardSound.play();
    }

    public Minion getMinion() {
        return minion;
    }
}
