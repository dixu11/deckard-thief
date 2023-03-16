package com.deckard.server.card;

public class Card {
    private CardType type;

    public Card(CardType type) {
        this.type = type;
    }

    public String getName() {
        return type.getName();
    }
}
