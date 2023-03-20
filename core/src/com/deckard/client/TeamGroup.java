package com.deckard.client;

import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.List;

public class TeamGroup extends Group {

    private MinionGroup firstMinion;
    private MinionGroup secondMinion;

    public TeamGroup(MinionGroup firstMinion, MinionGroup secondMinion) {
        this.firstMinion = firstMinion;
        this.secondMinion = secondMinion;
        addActor(firstMinion);
        addActor(secondMinion);
        secondMinion.setPosition(400,0);
    }
}
