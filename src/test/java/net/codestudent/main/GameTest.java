package net.codestudent.main;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {

    @Test
    void checkLevelUpAdvancesLevelAndGrantsATalent() {
        ScriptedConsole console = new ScriptedConsole().withInt(1); // Might at tier 0
        Game game = new Game(console, new Dice(new Random()));
        game.player = new Player("Hero", console);
        game.player.xp = game.player.maxXP; // 20

        game.checkLevelUp();

        assertEquals(2, game.player.lvl);
        assertEquals(30, game.player.maxXP);
        assertEquals(0, game.player.xp);
        assertEquals(1, game.player.skillTier);
        assertEquals(SkillNode.Branch.MIGHT, game.player.branch);
        assertTrue(game.player.unlockedSkills.contains(SkillTree.POWER_STRIKE));
    }

    @Test
    void checkLevelUpDoesNothingBelowThreshold() {
        ScriptedConsole console = new ScriptedConsole().withInt(2); // Guard at tier 0
        Game game = new Game(console, new Dice(new Random()));
        game.player = new Player("Hero", console);
        // xp stays at 0, well below maxXP

        game.checkLevelUp();

        assertEquals(1, game.player.lvl);
        assertEquals(0, game.player.skillTier);
    }

    @Test
    void startRunsThroughNameEntryAndTraitChoiceThenExits(@org.junit.jupiter.api.io.TempDir Path tempDir) {
        ScriptedConsole console = new ScriptedConsole()
                .withLine("TestHero")
                .withInt(1)  // confirm name
                .withInt(1)  // pick Might at tier 0
                .withInt(5); // quit at main menu
        Game game = new Game(console, new Dice(new Random()), tempDir.resolve("save.properties"));

        game.start();

        assertFalse(game.running);
        assertEquals("TestHero", game.player.name);
    }
}
