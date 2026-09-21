package net.codestudent.main;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCatalog {

    public static final Weapon UNARMED =
        new Weapon("unarmed", "Bare Fists", "Better than nothing.", DiceExpr.of(1, 4), 0);

    public static final Weapon RUSTED_SHORTSWORD =
        new Weapon("rusted_shortsword", "Rusted Shortsword",
            "A pitted old blade, still sharp enough to matter.", DiceExpr.of(1, 6), 0);

    public static final Weapon STEEL_LONGSWORD =
        new Weapon("steel_longsword", "Steel Longsword",
            "Well-balanced steel, forged in Millhaven's own smithy.", DiceExpr.of(1, 8), 1);

    public static final Weapon WARHAMMER_OF_THE_MARCHES =
        new Weapon("warhammer_of_the_marches", "Warhammer of the Marches",
            "Recovered from the Drowned Knight. Heavy enough to break shields.", DiceExpr.of(2, 6), 1);

    public static final Armor LEATHER_JERKIN =
        new Armor("leather_jerkin", "Leather Jerkin", "Boiled leather, light and forgiving.", 1);

    public static final Armor CHAINMAIL_VEST =
        new Armor("chainmail_vest", "Chainmail Vest", "Dense rings of iron over a padded coat.", 3);

    public static final Armor WARDENS_CLOAK =
        new Armor("wardens_cloak", "Warden's Cloak",
            "Elder Maren's own cloak, warded against the marsh's cold.", 2);

    public static final Consumable MINOR_HEALING_POTION =
        new Consumable("minor_healing_potion", "Minor Healing Potion",
            "A cloudy draught that knits shallow wounds.", 10);

    public static final Consumable GREATER_HEALING_POTION =
        new Consumable("greater_healing_potion", "Greater Healing Potion",
            "Sharp-tasting and potent. Mends far more than it should.", 25);

    private static final Map<String, Item> BY_ID = Stream.of(
            UNARMED, RUSTED_SHORTSWORD, STEEL_LONGSWORD, WARHAMMER_OF_THE_MARCHES,
            LEATHER_JERKIN, CHAINMAIL_VEST, WARDENS_CLOAK,
            MINOR_HEALING_POTION, GREATER_HEALING_POTION
        ).collect(Collectors.toMap(Item::id, item -> item));

    private ItemCatalog() {}

    public static Item byId(String id) {
        Item item = BY_ID.get(id);
        if (item == null)
            throw new IllegalArgumentException("Unknown item id: " + id);
        return item;
    }
}
