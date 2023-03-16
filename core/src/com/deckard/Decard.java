package com.deckard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Decard extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        setScreen(new CombatView(this));
    }

    public void render() {
        super.render(); //!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        screen.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }
}
