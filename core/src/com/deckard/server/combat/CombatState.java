package com.deckard.server.combat;
import com.badlogic.gdx.Gdx;
import com.deckard.server.event.CoreEvent;
import com.deckard.server.event.CoreEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;


public class CombatState implements EventHandler {
    private Phase phase = Phase.SETUP;
    private static CombatState instance;

    private CombatState() {
        Bus.register(this, CoreEventType.COMBAT_OVER);
    }

    public static CombatState getInstance() {
        if (instance == null) {
            instance = new CombatState();
        }
        return instance;
    }

    @Override
    public void handle(CoreEvent event) {
        onCombatOver();
    }

    private void onCombatOver() {
        //todo unregister for next combats
        instance = null;
    }

    void setPhase(Phase phase) {
        this.phase = phase;
        Gdx.app.log("info","Phase set: " + phase.name());
    }
}
