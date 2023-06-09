package com.deckard.server.card.effect;

import com.deckard.server.card.Card;
import com.deckard.server.card.CardContext;

public class EffectDecorator<T extends CardEffect> implements CardEffect {
    T decorated;

    public EffectDecorator(T decorated) {
        this.decorated = decorated;
    }

    @Override
    public void execute(CardContext context) {
        decorated.execute(context);
    }

    @Override
    public String getDescription() {
        return decorated.getDescription();
    }

    @Override
    public int getAttack() {
        return decorated.getAttack();
    }

    @Override
    public int getBlock() {
        return decorated.getBlock();
    }

    @Override
    public void setValue(int value) {
        decorated.setValue(value);
    }

    @Override
    public int getInitialValue() {
        return decorated.getInitialValue();
    }

    @Override
    public void modifyValueBy(int value) {
        decorated.modifyValueBy(value);
    }

    @Override
    public Card getCard() {
        return decorated.getCard();
    }
}
