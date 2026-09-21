package net.codestudent.main;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LootTest {

    @Test
    void dropsTrophyWhenRollFallsWithinChance() {
        Dice dice = new Dice(new QueuedRandom(0)); // roll = 1, within Bog Rat's 40% chance
        Game game = new Game(new ScriptedConsole(), dice);

        List<Item> loot = game.rollLoot(Bestiary.BOG_RAT);

        assertEquals(List.of(ItemCatalog.RAT_TAIL), loot);
    }

    @Test
    void noDropWhenRollExceedsChance() {
        Dice dice = new Dice(new QueuedRandom(99)); // roll = 100, exceeds Bog Rat's 40% chance
        Game game = new Game(new ScriptedConsole(), dice);

        List<Item> loot = game.rollLoot(Bestiary.BOG_RAT);

        assertTrue(loot.isEmpty());
    }

    @Test
    void multipleLootEntriesRollIndependently() {
        Dice bothHit = new Dice(new QueuedRandom(0, 0)); // both rolls = 1
        Game game = new Game(new ScriptedConsole(), bothHit);

        List<Item> loot = game.rollLoot(Bestiary.HIGHWAY_BANDIT);

        assertEquals(List.of(ItemCatalog.STOLEN_COIN_PURSE, ItemCatalog.STUDDED_LEATHER), loot);
    }

    @Test
    void trophyCanDropWithoutRareGear() {
        Dice trophyOnly = new Dice(new QueuedRandom(0, 99)); // trophy roll hits, gear roll misses
        Game game = new Game(new ScriptedConsole(), trophyOnly);

        List<Item> loot = game.rollLoot(Bestiary.HIGHWAY_BANDIT);

        assertEquals(List.of(ItemCatalog.STOLEN_COIN_PURSE), loot);
    }

    @Test
    void bossHasNoRandomLootTable() {
        Dice dice = new Dice(new QueuedRandom(0));
        Game game = new Game(new ScriptedConsole(), dice);

        assertTrue(game.rollLoot(Bestiary.THE_DROWNED_KNIGHT).isEmpty());
    }
}
