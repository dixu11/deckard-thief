package com.deckard.server.card.effect;

import com.deckard.server.card.Card;
import com.deckard.server.card.CardContext;
import com.deckard.server.game.GameParams;

public class BasicAttackEffect extends BasicEffect implements AttackEffect{

    EnemySelection type;
    boolean isPiercing = false;
    public BasicAttackEffect(int value,EnemySelection type, Card card) {
        super(value, card);
        this.type = type;
    }
    @Override
    public void execute(CardContext context) {
        context.getEnemyTeam()
                .applyDmgTo(value,this);
    }

    @Override
    public String getDescription() {
        String modifiers = "";
        if (isPiercing) {
            modifiers += " Attack ignores block.";
        }

        return " " + value + GameParams.ATTACK_ICON + modifiers;
    }

    @Override
    public int getAttack() {
        return value;
    }

    @Override
    public int getBlock() {
        return 0;
    }

    public EnemySelection getType() {
        return type;
    }

    @Override
    public void setPiercing(boolean piercing) {
        isPiercing = piercing;
    }

    public boolean isPiercing() {
        return isPiercing;
    }
}
