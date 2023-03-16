package com.deckard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen implements Screen {

    private final Decard game;
    private final OrthographicCamera camera;

    public MainMenuScreen(Decard game) {
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
            game.setScreen(new CombatView(game));
            dispose();
            System.out.println("test");
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
