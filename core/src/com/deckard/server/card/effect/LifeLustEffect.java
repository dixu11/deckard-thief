package com.deckard.server.card.effect;

import com.deckard.server.card.CardContext;
import com.deckard.server.event.ActionEvent;
import com.deckard.server.event.ActionEventType;
import com.deckard.server.event.EventHandler;
import com.deckard.server.event.bus.Bus;

public class LifeLustEffect extends AttackEffectDecorator implements EventHandler {
    public LifeLustEffect(AttackEffect decorated) {
        super(decorated);
    }

    @Override
    public void execute(CardContext context) {
        Bus.register(this, ActionEventType.MINION_DAMAGED);
        super.execute(context);
        Bus.unregister(this, ActionEventType.MINION_DAMAGED);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " unblocked ⚔️ regenerates your ♥"; //todo put icons to separate class
    }

    @Override
    public void handle(ActionEvent event) {
        if (isFromOtherTeam(event)) {
            getCard().getOwner().applyRegen(event.getOldValue() - event.getValue());
        }
    }

    private boolean isFromOtherTeam(ActionEvent event) {
        return !event.getMinion().getTeam().equals(getCard().getOwner().getTeam());
    }
}
