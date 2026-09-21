package net.codestudent.main;

public record EnemyTemplate(
    String id,
    String name,
    String description,
    DiceExpr hitDice,
    int armorClass,
    int attackBonus,
    DiceExpr damageDice,
    int xpReward,
    DiceExpr goldDice
) {}
