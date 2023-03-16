package com.deckard.server.leader;

import com.deckard.server.card.CardFactory;
import com.deckard.server.card.CardType;

import java.util.ArrayList;

public class LeaderFactory {
    private final CardFactory cardFactory = new CardFactory();

    public Leader create(LeaderType leaderType) {
        return switch (leaderType) {
            case PLAYER -> new Leader(cardFactory.createCards(1, CardType.BASIC_ATTACK));
            case SIMPLE_BOT -> new Leader(new ArrayList<>());
        };
    }
}
