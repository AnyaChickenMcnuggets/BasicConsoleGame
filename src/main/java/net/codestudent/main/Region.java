package net.codestudent.main;

import java.util.List;

public record Region(String name, String description, List<EnemyTemplate> wildernessEnemies,
                      Encounter[] encounterTable, Hub hub) {

    public static final Region GREYWOOD_MARCHES = new Region(
        "The Greywood Marches",
        "Fog clings to the reed-choked lowlands between Millhaven and the mountains. Wolves and worse things move here after dark.",
        Bestiary.wildernessPool(),
        new Encounter[] { Encounter.BATTLE, Encounter.BATTLE, Encounter.BATTLE, Encounter.REST, Encounter.SHOP },
        new Hub(
            "Millhaven",
            "A cluster of stone-and-timber houses behind a low palisade, the only real shelter for miles of marsh.",
            List.of(
                new Npc(
                    "Elder Maren",
                    "The village elder, silver-haired and sharp-eyed despite her years.",
                    List.of(
                        "\"Millhaven's stood two hundred years in this marsh. We don't scare easy - but the wolves are close now.\"",
                        "\"You've the look of someone who can hold a sword. Might be you're what we need.\""
                    ),
                    QuestBook.WOLVES_AT_THE_DOOR
                ),
                new Npc(
                    "Borin the Smith",
                    "A broad-shouldered smith, forge-soot worked permanently into his hands.",
                    List.of(
                        "\"Steel doesn't lie, stranger. Take care of your blade and it'll take care of you.\"",
                        "\"Everything on the rack is for sale, if your coin's good.\""
                    )
                ),
                new Npc(
                    "Wren the Herbalist",
                    "An old woman surrounded by drying herbs, humming to herself.",
                    List.of(
                        "\"The marsh gives as much as it takes, if you know where to look.\"",
                        "\"These won't grow back a lost limb, but they'll close most anything smaller.\""
                    )
                )
            ),
            List.of(
                new ShopEntry(ItemCatalog.STEEL_LONGSWORD, 40),
                new ShopEntry(ItemCatalog.CHAINMAIL_VEST, 50),
                new ShopEntry(ItemCatalog.MINOR_HEALING_POTION, 10),
                new ShopEntry(ItemCatalog.GREATER_HEALING_POTION, 25)
            )
        )
    );
}
