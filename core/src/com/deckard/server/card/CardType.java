package com.deckard.server.card;

import com.deckard.server.game.MyRandom;

import java.util.Arrays;

import static com.deckard.server.card.CardCategory.*;
import static com.deckard.server.card.CardRarity.BASIC;
import static com.deckard.server.card.CardRarity.COMMON;

public enum CardType {
    BASIC_ATTACK(ATTACK, BASIC, "Basic Attack ", 1),
    UPGRADED_ATTACK(ATTACK, COMMON, "Better Attack", 3),
    BASIC_BLOCK(BLOCK, BASIC, "Basic Block", 1),
    UPGRADED_BLOCK(BLOCK, COMMON, "Better Block", 2),
    BASIC_MINION(MINION, BASIC, "Minion", 10),
    UNSTABLE_ATTACK(ATTACK, COMMON, "Unstable Attack", 3),
    PIERCING_ATTACK(ATTACK, COMMON, "Piercing Attack", 1),
    AREA_ATTACK(ATTACK, COMMON, "Area Attack", 1),
    GIFT_ATTACK(ATTACK, COMMON, "Gift Attack", 1),
    COMBO_ATTACK(ATTACK, COMMON, "Combo Attack", 1),
    SOLO_ATTACK(ATTACK, COMMON, "Solo Attack", 3),
    DECK_SHIELD(BLOCK, COMMON, "Deck Shield", 0),
    BLOCK_BOOSTER(BLOCK, COMMON, "Block Booster", 0),
    LIFE_LUST(ATTACK, COMMON, "Life Lust", 1),
    HEAL(SKILL, COMMON, "Heal", 1);

    private final CardCategory category;
    private final CardRarity rarity;
    private final String name;
    private int baseValue;

    CardType(CardCategory category, CardRarity rarity, String name, int baseValue) {
        this.category = category;
        this.rarity = rarity;
        this.name = name;
        this.baseValue = baseValue;
    }

    public static CardType getRandom(CardRarity rarity) {
        return MyRandom.getRandomElement(
                        Arrays.stream(CardType.values())
                                .filter(type -> type.getRarity() == rarity)
                                .toList())
                .orElseThrow();
    }

    public static CardType getRandom() {
        return MyRandom.getRandomElement(Arrays.stream(values()).toList())
                .orElseThrow();
    }

    public CardCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public CardRarity getRarity() {
        return rarity;
    }

    public int getValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }
}
