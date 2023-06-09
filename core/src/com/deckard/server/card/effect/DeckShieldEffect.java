package com.deckard.server.card.effect;

import com.deckard.server.card.Card;
import com.deckard.server.card.CardCategory;
import com.deckard.server.card.CardContext;
import com.deckard.server.event.ActionEvent;
import com.deckard.server.event.ActionEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;
import com.deckard.server.minion.Minion;

import java.util.List;

public class DeckShieldEffect extends BlockEffectDecorator implements EventHandler {
    public DeckShieldEffect(BlockEffect decorated) {
        super(decorated);
        Bus.register(this, ActionEventType.MINION_HAND_CHANGED);
        //todo refactor to be better targeted by actions. like on minion owner change or on deck change
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getMinion() == getCard().getOwner()) {
            calculateValue();
        }
    }

    @Override
    public void execute(CardContext context) {
        calculateValue();
        super.execute(context);
    }

    private void calculateValue() {
        Minion owner = getCard().getOwner();
        if (owner == null) {
            setValue(0);
            return;
        }
       List<Card> cards = owner.getAllCards();
        int value = (int) cards.stream()
                .filter(card -> card.getCategory() == CardCategory.BLOCK)
                .filter(card -> !card.equals(getCard()))
                .count();
        setValue(value);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " = other block cards in deck";
    }
}
