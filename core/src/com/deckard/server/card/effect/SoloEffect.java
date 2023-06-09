package com.deckard.server.card.effect;

import com.deckard.server.card.Card;
import com.deckard.server.card.CardCategory;
import com.deckard.server.card.CardContext;
import com.deckard.server.event.*;
import com.deckard.server.event.bus.Bus;
import com.deckard.server.game.GameParams;

import java.util.List;

public class  SoloEffect extends AttackEffectDecorator implements EventHandler {
    private static final int ATTACK_NEGATIVE_MODIFIER = -2;
    private boolean active = true;

    public SoloEffect(AttackEffect decorated) {
        super(decorated);
        Bus.register(this, ActionEventType.MINION_HAND_CHANGED);
        Bus.register(this, CoreEventType.SETUP_PHASE_STARTED);
        Bus.register(this, CoreEventType.MINION_PHASE_STARTED);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " Has +2"+ GameParams.ATTACK_ICON +  " if it is the only attack team plays"; //todo refactor symbols
    }

    @Override
    public void handle(ActionEvent event) {
        if (!active) {
            return;
        }

        resetValue();
        if (getCard().getOwner() == null) {
            return;
        }

        List<Card> hands = getCard().getOwner().getTeam().getMinionsHands();
        for (Card card : hands) {
            if (card.equals(getCard())) {
                continue;
            }
            if (card.getCategory() == CardCategory.ATTACK) {
                setValue(getInitialValue() + ATTACK_NEGATIVE_MODIFIER);
                break;
            }
        }
    }

    private void resetValue() {
        setValue(getInitialValue());
    }

    @Override
    public void execute(CardContext context) {
        super.execute(context);
    }

    @Override
    public void handle(CoreEvent event) {
        if (event.getType() == CoreEventType.MINION_PHASE_STARTED) {
            active = false;
        }
        if (event.getType() == CoreEventType.SETUP_PHASE_STARTED) {
            active = true;
            setValue(getInitialValue());
        }
    }
}
