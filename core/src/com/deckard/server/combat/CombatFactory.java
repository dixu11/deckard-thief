package com.deckard.server.combat;

import com.deckard.server.leader.Leader;
import com.deckard.server.leader.LeaderFactory;
import com.deckard.server.leader.LeaderType;

public class CombatFactory {
    private final LeaderFactory leaderFactory = new LeaderFactory();
    public Combat createCombat() {
        Leader firstLeader = leaderFactory.create(LeaderType.PLAYER);
        Leader secondLeader = leaderFactory.create(LeaderType.SIMPLE_BOT);
        return new Combat(firstLeader,secondLeader);
    }
}
