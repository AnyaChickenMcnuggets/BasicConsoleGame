package net.codestudent.main;

public record Weapon(String id, String name, String description, DiceExpr damageDice, int attackBonus) implements Item {}
