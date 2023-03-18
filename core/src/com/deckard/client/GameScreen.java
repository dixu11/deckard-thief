package com.deckard.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deckard.server.combat.CombatFactory;

public class GameScreen extends Game {
    private SpriteBatch batch;

    private BitmapFont font;
    private GlyphLayout glyphLayout;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        glyphLayout = new GlyphLayout();
        CombatFactory factory = new CombatFactory();
        setScreen(new CombatScreen(this, factory.createCombat()));
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
