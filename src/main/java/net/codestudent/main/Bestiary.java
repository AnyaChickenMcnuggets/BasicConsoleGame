package net.codestudent.main;

import java.util.List;

public class Bestiary {

    public static final EnemyTemplate BOG_RAT = new EnemyTemplate(
        "bog_rat", "Bog Rat", "Oversized and hungry, it smells blood before it sees you.",
        DiceExpr.of(1, 6, 2), 10, 1, DiceExpr.of(1, 3), 5, DiceExpr.of(1, 4));

    public static final EnemyTemplate MARSH_WOLF = new EnemyTemplate(
        "marsh_wolf", "Marsh Wolf", "Lean and mud-streaked, it hunts in packs along the reeds.",
        DiceExpr.of(2, 6, 2), 12, 3, DiceExpr.of(1, 6), 10, DiceExpr.of(1, 6));

    public static final EnemyTemplate HIGHWAY_BANDIT = new EnemyTemplate(
        "highway_bandit", "Highway Bandit", "A deserter turned brigand, desperate and well-armed.",
        DiceExpr.of(2, 8, 4), 13, 3, DiceExpr.of(1, 8), 15, DiceExpr.of(2, 6));

    public static final EnemyTemplate MIRE_GHOUL = new EnemyTemplate(
        "mire_ghoul", "Mire Ghoul", "Something that drowned in the marsh and did not stay down.",
        DiceExpr.of(3, 8, 6), 13, 4, DiceExpr.of(1, 8, 1), 25, DiceExpr.of(1, 8));

    public static final EnemyTemplate THE_DROWNED_KNIGHT = new EnemyTemplate(
        "the_drowned_knight", "The Drowned Knight",
        "A knight of some forgotten order, armor black with marsh-rot, sword still true.",
        DiceExpr.of(8, 10, 16), 16, 6, DiceExpr.of(2, 8, 2), 100, DiceExpr.of(4, 6));

    private static final List<EnemyTemplate> WILDERNESS_POOL =
        List.of(BOG_RAT, MARSH_WOLF, HIGHWAY_BANDIT, MIRE_GHOUL);

    private Bestiary() {}

    public static List<EnemyTemplate> wildernessPool() {
        return WILDERNESS_POOL;
    }
}
