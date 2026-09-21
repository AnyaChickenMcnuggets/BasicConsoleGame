package net.codestudent.main;

import java.util.List;

public record Hub(String name, String description, List<Npc> npcs, List<ShopEntry> shop) {}
