package com.deckard.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deckard.client.actor.CardActor;
import com.deckard.client.actor.HandGroup;
import com.deckard.client.actor.TeamGroupFactory;
import com.deckard.server.card.Card;
import com.deckard.server.combat.Combat;
import com.deckard.server.event.CoreEvent;
import com.deckard.server.event.CoreEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;

public class CombatScreen implements Screen, EventHandler {
    private final GameScreen game;
    private final OrthographicCamera camera;
    private Combat combat;
    private Stage stage;
    private TextButton button;

    public CombatScreen(GameScreen game, Combat combat) {
        this.game = game;
        this.combat = combat;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GuiParams.WIDTH, GuiParams.HEIGHT);

        stage = new Stage(new ScreenViewport(camera), game.getBatch());
        //leader cards
        Texture  cardTexture = new Texture(Gdx.files.internal("card.png"));

        HandGroup leaderHand = new HandGroup();
        for (Card card : combat.getFirstLeader().getHand()) {
            CardActor leaderCard = new CardActor(card, cardTexture);
            leaderHand.addActor(leaderCard);
        }
        leaderHand.setPosition(GuiParams.LEADER_HAND_X, GuiParams.LEADER_HAND_Y);
        stage.addActor(leaderHand);
        leaderHand.updateLayout();
        //minion
        TeamGroupFactory teamGroupFactory = new TeamGroupFactory(game);
        stage.addActor(teamGroupFactory.createTeam(TeamGroupFactory.Side.LEFT,combat.getFirstTeam()));
        stage.addActor(teamGroupFactory.createTeam(TeamGroupFactory.Side.RIGHT,combat.getSecondTeam()));
        Gdx.input.setInputProcessor(stage);

        //button
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        button = new TextButton("End turn", skin); //todo make custom component
        button.setBounds(1500,100,200,50);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                endTurnAction();
            }
        });
        stage.addActor(button);
        Bus.register(this,CoreEventType.SETUP_PHASE_STARTED);
        Bus.register(this,CoreEventType.LEADER_PHASE_STARTED);
    }

    @Override
    public void handle(CoreEvent event) {
        if (event.getType().equals(CoreEventType.SETUP_PHASE_STARTED)) {
            button.setText("Setup phase");
            button.setDisabled(true);
        } else if (event.getType().equals(CoreEventType.LEADER_PHASE_STARTED)) {
            System.out.println("Next turn!");
            button.setText("End turn");
            button.setDisabled(false);
        }
    }

    public void endTurnAction() {
        button.setText("Minion phase");
        button.setDisabled(true);
        Bus.post(CoreEvent.of(CoreEventType.MINION_PHASE_STARTED));
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
