package net.codestudent.main;

public record Consumable(String id, String name, String description, int healAmount, int value) implements Item {}
