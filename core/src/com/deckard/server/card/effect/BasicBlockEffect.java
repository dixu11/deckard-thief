package com.deckard.server.card.effect;

import com.deckard.server.card.Card;
import com.deckard.server.card.CardContext;
import com.deckard.server.game.GameParams;

public class BasicBlockEffect extends BasicEffect implements BlockEffect {

    public BasicBlockEffect(int value, Card card) {
        super(value,card);
    }

    @Override
    public void execute(CardContext context) {
        context.getOwnTeam().addBlock(value);
    }

    @Override
    public String getDescription() {
        return  " +" + value + GameParams.BLOCK_ICON;
    }

    @Override
    public int getAttack() {
        return 0;
    }

    @Override
    public int getBlock() {
        return value;
    }
}
