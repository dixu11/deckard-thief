package com.deckard.server.combat;

import com.deckard.server.leader.LeaderFactory;
import com.deckard.server.leader.LeaderType;

public class CombatFactory {
    public Combat createCombat() {
        LeaderFactory factory = new LeaderFactory();
        return new Combat(factory.create(LeaderType.PLAYER),factory.create(LeaderType.SIMPLE_BOT));
    }
}
