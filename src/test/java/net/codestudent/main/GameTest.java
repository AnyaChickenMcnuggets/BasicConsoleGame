package net.codestudent.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTest {

    @Test
    void checkXPLevelsUpWhenXpReachesMax() {
        ScriptedConsole console = new ScriptedConsole()
                .withInt(1)  // initial chooseTrait on construction
                .withInt(2); // chooseTrait triggered by lvlUP
        Game game = new Game(console);
        game.player = new Player("Hero", console);
        game.player.xp = game.player.maxXP; // 15

        game.checkXP();

        assertEquals(2, game.player.lvl);
        assertEquals(25, game.player.maxXP);
        assertEquals(0, game.player.xp);
    }

    @Test
    void checkActAdvancesFromFirstToSecondAct() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Game game = new Game(console);
        game.player = new Player("Hero", console);
        game.player.lvl = 2;

        game.checkAct();

        assertEquals(2, game.actNumber);
        assertEquals("Призрачная Низина", game.currentAct.place());
    }

    @Test
    void checkActAdvancesFromSecondToThirdAct() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Game game = new Game(console);
        game.player = new Player("Hero", console);
        game.player.lvl = 3;
        game.actNumber = 2;
        game.currentAct = new Act("Призрачная Низина", new String[0], new Encounter[0]);

        game.checkAct();

        assertEquals(3, game.actNumber);
        assertEquals("Кровавый Перевал", game.currentAct.place());
    }

    @Test
    void checkActDoesNothingBelowThreshold() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Game game = new Game(console);
        game.player = new Player("Hero", console);
        // lvl stays 1, actNumber stays 1: threshold not met

        game.checkAct();

        assertEquals(1, game.actNumber);
        assertEquals("Бесконечные Горы", game.currentAct.place());
    }

    @Test
    void startRunsThroughNameEntryAndTraitChoiceThenExits() {
        ScriptedConsole console = new ScriptedConsole()
                .withLine("TestHero")
                .withInt(1)  // confirm name
                .withInt(2)  // initial chooseTrait
                .withInt(3); // exit at main menu
        Game game = new Game(console);

        game.start();

        assertFalse(game.running);
        assertEquals("TestHero", game.player.name);
    }
}
