package com.deckard.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deckard.server.card.Card;
import com.deckard.server.combat.Combat;
import com.deckard.server.minion.Minion;

public class CombatScreen implements Screen {
    private final GameScreen game;
    private final OrthographicCamera camera;
    private Combat combat;
    private Stage stage;

    public CombatScreen(GameScreen game, Combat combat) {
        this.game = game;
        this.combat = combat;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WIDTH, GuiParams.HEIGHT);

        stage = new Stage(new ScreenViewport(camera), game.getBatch());
        //leader cards
        Texture  cardTexture = new Texture(Gdx.files.internal("card.png"));
        Texture minionBodyTexture = new Texture(Gdx.files.internal("minion-left.png"));
        HandGroup leaderHand = new HandGroup();
        for (Card card : combat.getFirstLeader().getHand()) {
            CardActor leaderCard = new CardActor(card, cardTexture);
            leaderHand.addActor(leaderCard);
        }
        leaderHand.setPosition(GuiParams.LEADER_HAND_X, GuiParams.LEADER_HAND_Y);
        stage.addActor(leaderHand);
        //minion
        Minion firstMinion = combat.getFirstLeader().getTeam().getMinions().get(0);
        MinionBodyActor firstMinionBody = new MinionBodyActor(firstMinion, minionBodyTexture, game.getFont());
        firstMinionBody.setPosition(300,450);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);
        camera.update();
        SpriteBatch batch = game.getBatch();
        batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
