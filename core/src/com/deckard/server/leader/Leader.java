package com.deckard.server.leader;

import com.deckard.server.card.Card;

import java.util.List;

public class Leader {
    private List<Card> cards;

    public Leader(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
