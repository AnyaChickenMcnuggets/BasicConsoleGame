package net.codestudent.main;

import java.util.List;

public record EnemyTemplate(
    String id,
    String name,
    String description,
    DiceExpr hitDice,
    int armorClass,
    int attackBonus,
    DiceExpr damageDice,
    int xpReward,
    DiceExpr goldDice,
    List<LootEntry> lootTable
) {}
