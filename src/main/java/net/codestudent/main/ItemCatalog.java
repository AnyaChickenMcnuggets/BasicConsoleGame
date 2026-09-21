package net.codestudent.main;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemCatalog {

    public static final Weapon UNARMED =
        new Weapon("unarmed", "Bare Fists", "Better than nothing.", DiceExpr.of(1, 4), 0, 0);

    public static final Weapon RUSTED_SHORTSWORD =
        new Weapon("rusted_shortsword", "Rusted Shortsword",
            "A pitted old blade, still sharp enough to matter.", DiceExpr.of(1, 6), 0, 10);

    public static final Weapon STEEL_LONGSWORD =
        new Weapon("steel_longsword", "Steel Longsword",
            "Well-balanced steel, forged in Millhaven's own smithy.", DiceExpr.of(1, 8), 1, 40);

    public static final Weapon IRON_WARAXE =
        new Weapon("iron_waraxe", "Iron Waraxe",
            "Notched from hard use, but the head is still solid iron.", DiceExpr.of(1, 8), 0, 30);

    public static final Weapon WARHAMMER_OF_THE_MARCHES =
        new Weapon("warhammer_of_the_marches", "Warhammer of the Marches",
            "Recovered from the Drowned Knight. Heavy enough to break shields.", DiceExpr.of(2, 6), 1, 80);

    public static final Armor LEATHER_JERKIN =
        new Armor("leather_jerkin", "Leather Jerkin", "Boiled leather, light and forgiving.", 1, 10);

    public static final Armor STUDDED_LEATHER =
        new Armor("studded_leather", "Studded Leather",
            "Iron studs sewn into thick hide. Salvaged, but sound.", 2, 25);

    public static final Armor CHAINMAIL_VEST =
        new Armor("chainmail_vest", "Chainmail Vest", "Dense rings of iron over a padded coat.", 3, 50);

    public static final Armor WARDENS_CLOAK =
        new Armor("wardens_cloak", "Warden's Cloak",
            "Elder Maren's own cloak, warded against the marsh's cold.", 2, 60);

    public static final Consumable MINOR_HEALING_POTION =
        new Consumable("minor_healing_potion", "Minor Healing Potion",
            "A cloudy draught that knits shallow wounds.", 10, 10);

    public static final Consumable GREATER_HEALING_POTION =
        new Consumable("greater_healing_potion", "Greater Healing Potion",
            "Sharp-tasting and potent. Mends far more than it should.", 25, 25);

    public static final Trophy RAT_TAIL =
        new Trophy("rat_tail", "Rat Tail", "Proof of the kill, if Millhaven pays for such things.", 3);

    public static final Trophy WOLF_PELT =
        new Trophy("wolf_pelt", "Wolf Pelt", "A matted pelt, worth a little coin at market.", 8);

    public static final Trophy STOLEN_COIN_PURSE =
        new Trophy("stolen_coin_purse", "Stolen Coin Purse", "Whatever it once held, the coin purse itself still sells.", 12);

    public static final Trophy GHOUL_ICHOR =
        new Trophy("ghoul_ichor", "Ghoul Ichor", "A vial of something best not examined closely. An alchemist might want it.", 15);

    private static final Map<String, Item> BY_ID = Stream.of(
            UNARMED, RUSTED_SHORTSWORD, STEEL_LONGSWORD, IRON_WARAXE, WARHAMMER_OF_THE_MARCHES,
            LEATHER_JERKIN, STUDDED_LEATHER, CHAINMAIL_VEST, WARDENS_CLOAK,
            MINOR_HEALING_POTION, GREATER_HEALING_POTION,
            RAT_TAIL, WOLF_PELT, STOLEN_COIN_PURSE, GHOUL_ICHOR
        ).collect(Collectors.toMap(Item::id, item -> item));

    private ItemCatalog() {}

    public static Item byId(String id) {
        Item item = BY_ID.get(id);
        if (item == null)
            throw new IllegalArgumentException("Unknown item id: " + id);
        return item;
    }
}
