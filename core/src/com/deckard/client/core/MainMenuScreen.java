package com.deckard.client.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.deckard.client.core.CombatScreen;
import com.deckard.client.core.GameScreen;
import com.deckard.server.combat.CombatFactory;

public class MainMenuScreen implements Screen {

    private final GameScreen game;
    private final OrthographicCamera camera;

    public MainMenuScreen(GameScreen game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 400);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 2f, 1);

        camera.update();
        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Welcome to Drop!", 100, 150); //could be replaced with convenience method
        game.getFont().draw(game.getBatch(), "Tap anywhere to begin!", 100, 100);
        game.getBatch().end();

        if (Gdx.input.isTouched()) {
            CombatFactory factory = new CombatFactory();
            game.setScreen(new CombatScreen(game, factory.createCombat()));
            dispose();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

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

    @Override
    public void dispose() {

    }
}
