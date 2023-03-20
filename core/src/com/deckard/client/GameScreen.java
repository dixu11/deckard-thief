package com.deckard.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deckard.server.combat.Combat;
import com.deckard.server.combat.CombatFactory;

public class GameScreen extends Game {
    private SpriteBatch batch;
    private BitmapFont font;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        CombatFactory factory = new CombatFactory();
        Combat combat = factory.createCombat();
        combat.start();
        setScreen(new CombatScreen(this, combat));
    }

    public void render() {
        super.render();
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
