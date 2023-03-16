package com.deckard.server.team;

import com.deckard.server.card.Card;
import com.deckard.server.card.CardFactory;
import com.deckard.server.game.GameParams;
import com.deckard.server.leader.LeaderType;
import com.deckard.server.minion.Minion;
import com.deckard.server.minion.MinionDeck;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class TeamFactory {

    public Team create(LeaderType type) {
        return new Team(IntStream.range(0, GameParams.MINION_PER_TEAM)
                .boxed()
                .map(num -> createMinion(type))
                .toList()
        );
    }

    private Minion createMinion(LeaderType type) {
        CardFactory cardFactory = new CardFactory();
        List<Card> deckCards = cardFactory.createDeckCards(type);
        Collections.shuffle(deckCards);
        MinionDeck deck = new MinionDeck(deckCards);
        return new Minion(deck);
    }
}
