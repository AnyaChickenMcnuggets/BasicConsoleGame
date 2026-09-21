package net.codestudent.main;

public sealed interface Item permits Weapon, Armor, Consumable {
    String id();
    String name();
    String description();
}
