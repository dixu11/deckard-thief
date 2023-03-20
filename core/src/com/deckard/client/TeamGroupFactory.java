package com.deckard.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.deckard.server.minion.Minion;
import com.deckard.server.team.Team;

public class TeamGroupFactory {

    private GameScreen game;
    private Texture minionBodyTexture;
    private Texture cardTexture;

    public TeamGroupFactory(GameScreen game) {
        this.game = game;
        minionBodyTexture = new Texture(Gdx.files.internal("minion-left.png"));
        cardTexture = new Texture(Gdx.files.internal("card.png"));
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
        minion.getHand().stream()
                .map(card -> new CardActor(card, cardTexture))
                .forEach(minionHand::addActor);
        return new MinionGroup(firstMinionBody, minionHand);
    }
}
