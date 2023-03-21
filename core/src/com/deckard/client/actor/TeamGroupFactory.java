package com.deckard.client.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.deckard.client.core.GameScreen;
import com.deckard.server.minion.Minion;
import com.deckard.server.team.Team;

public class TeamGroupFactory {

    private GameScreen game;
    private Texture minionBodyTexture;

    public TeamGroupFactory(GameScreen game) {
        this.game = game;
        minionBodyTexture = new Texture(Gdx.files.internal("minion-left.png"));
    }

    public enum Side {
        LEFT, RIGHT
    }

    public TeamGroup createTeam(Side side, Team team) {
        TeamGroup teamGroup = new TeamGroup(createMinionGroup(side,team.getMinions().get(0))
                , createMinionGroup(side,team.getMinions().get(1)));

        if (side == Side.LEFT) {
            teamGroup.setPosition(150, 350);
        } else {
            teamGroup.setPosition(1150,350);
        }

        return teamGroup;
    }

    private MinionGroup createMinionGroup(Side side,Minion minion) {
        MinionBodyActor firstMinionBody = new MinionBodyActor(minion, minionBodyTexture, game.getFont());
        if (side == Side.RIGHT) {
            firstMinionBody.setFlipX(true);
        }
        HandGroup minionHand = new HandGroup();
        return new MinionGroup(firstMinionBody, minionHand, minion);
    }
}
