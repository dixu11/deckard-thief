package com.deckard.client.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.deckard.client.animation.AnimationCommand;
import com.deckard.client.animation.AnimationManager;
import com.deckard.client.animation.CountDownAnimation;
import com.deckard.server.event.ActionEvent;
import com.deckard.server.event.ActionEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class TeamGroup extends Group implements EventHandler {

    private MinionGroup firstMinion;
    private MinionGroup secondMinion;
    private CountDownAnimation countDownAnimation = null;
    private Sound deathSound;

    public TeamGroup(MinionGroup firstMinion, MinionGroup secondMinion) {
        this.firstMinion = firstMinion;
        this.secondMinion = secondMinion;
        addActor(firstMinion);
        addActor(secondMinion);
        secondMinion.setPosition(400,0);
        Bus.register(this, ActionEventType.MINION_DIED);
        deathSound = Gdx.audio.newSound(Gdx.files.internal("death1.wav"));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (countDownAnimation != null && countDownAnimation.isComplete()) {
            countDownAnimation = null;
            AnimationManager.getInstance().finishedGlobal();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        MinionGroup died;
        if (firstMinion.getMinion().equals(event.getMinion())) {
            died = firstMinion;
        } else if (secondMinion.getMinion().equals(event.getMinion())) {
            died = secondMinion;
        } else {
            return;
        }
        AnimationManager.getInstance().registerGlobal(new AnimationCommand(()-> animateDeath(died)));
    }

    private void animateDeath(MinionGroup died) {
        countDownAnimation = new CountDownAnimation(1);
        died.addAction(sequence(fadeOut(0.5f), countDownAnimation, Actions.removeActor(died)));
        deathSound.play();
    }
}
