package com.deckard.server.combat;

import com.deckard.server.leader.Leader;

public class Combat {

    private Leader firstLeader;
    private Leader secondLeader;

    public Combat(Leader firstLeader, Leader secondLeader) {
        this.firstLeader = firstLeader;
        this.secondLeader = secondLeader;
    }
}
