package net.codestudent.main;

public sealed interface Item permits Weapon, Armor, Consumable, Trophy {
    String id();
    String name();
    String description();
    int value();
}
