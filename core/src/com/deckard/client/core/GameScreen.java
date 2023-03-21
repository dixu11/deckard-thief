package com.deckard.client.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
        setScreen(new CombatScreen(this, combat));
        combat.start();
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
