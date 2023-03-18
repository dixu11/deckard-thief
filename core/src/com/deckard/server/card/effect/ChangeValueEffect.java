package com.deckard.server.card.effect;

import com.deckard.server.card.CardContext;
import com.deckard.server.game.GameParams;

public class ChangeValueEffect extends AttackEffectDecorator {
    private int valueModification;

    public ChangeValueEffect(AttackEffect attackEffect, int valueModification) {
        super(attackEffect);
        this.valueModification = valueModification;
    }

    public void execute(CardContext context) {
        super.execute(context);
        decorated.setValue(valueModification+decorated.getAttack());
        if (decorated.getAttack() < 0) {
            decorated.setValue(0);
        }
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " -1" + GameParams.ATTACK_ICON+ " after each play";
    }
}
