package net.codestudent.main;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestTest {

    private static final Npc ELDER_MAREN = Region.GREYWOOD_MARCHES.hub().npcs().get(0);

    @Test
    void acceptingQuestFromNpcMarksItActive() {
        ScriptedConsole console = new ScriptedConsole()
                .withInt(1)  // Might at tier 0
                .withInt(1); // accept the quest
        Game game = new Game(console, new Dice(new Random()));
        game.player = new Player("Hero", console);

        game.talkTo(ELDER_MAREN);

        QuestProgress progress = game.player.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id());
        assertEquals(QuestProgress.State.ACTIVE, progress.state);
    }

    @Test
    void killsAgainstTargetAdvanceActiveQuestOnly() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Game game = new Game(console, new Dice(new Random()));
        game.player = new Player("Hero", console);
        game.player.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id()).state = QuestProgress.State.ACTIVE;

        game.registerKill(Bestiary.HIGHWAY_BANDIT.id()); // not the quest target, ignored
        QuestProgress progress = game.player.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id());
        assertEquals(0, progress.killCount);

        game.registerKill(Bestiary.MARSH_WOLF.id());
        game.registerKill(Bestiary.MARSH_WOLF.id());
        assertEquals(2, progress.killCount);
        assertEquals(QuestProgress.State.ACTIVE, progress.state);

        game.registerKill(Bestiary.MARSH_WOLF.id());
        assertEquals(3, progress.killCount);
        assertEquals(QuestProgress.State.READY_TO_TURN_IN, progress.state);
    }

    @Test
    void turningInQuestGrantsReward() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Game game = new Game(console, new Dice(new Random()));
        game.player = new Player("Hero", console);
        int startingGold = game.player.gold;
        game.player.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id()).state = QuestProgress.State.READY_TO_TURN_IN;

        game.talkTo(ELDER_MAREN);

        QuestProgress progress = game.player.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id());
        assertEquals(QuestProgress.State.COMPLETE, progress.state);
        assertEquals(startingGold + QuestBook.WOLVES_AT_THE_DOOR.rewardGold(), game.player.gold);
        assertTrue(game.player.inventory.contains(ItemCatalog.WARDENS_CLOAK));
    }
}
