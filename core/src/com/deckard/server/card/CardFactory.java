package com.deckard.server.card;

import java.util.List;
import java.util.stream.IntStream;

public class CardFactory {

    public List<Card> createCards(int count, CardType type) {
        return IntStream.range(0, count)
                .boxed()
                .map(n -> new Card(type))
                .toList();
    }

}
