package com.deckard.server.leader;

import com.deckard.server.card.CardFactory;
import com.deckard.server.card.CardType;
import com.deckard.server.team.TeamFactory;

public class LeaderFactory {
    private final TeamFactory teamFactory = new TeamFactory();

    public Leader create(LeaderType type) {
        Leader leader = new Leader(teamFactory.create(type));
        if (type == LeaderType.PLAYER) {
            leader.addCard(new CardFactory().createRandomCard());
            leader.addCard(new CardFactory().createRandomCard());
            leader.addCard(new CardFactory().createRandomCard());
            leader.addCard(new CardFactory().createRandomCard());
            leader.addCard(new CardFactory().createRandomCard());
        }
        return leader;
    }
}
