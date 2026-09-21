package net.codestudent.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerStatsTest {

    @Test
    void modifierMatchesStandardDndTable() {
        assertEquals(2, Player.modifier(15));
        assertEquals(2, Player.modifier(14));
        assertEquals(1, Player.modifier(13));
        assertEquals(1, Player.modifier(12));
        assertEquals(0, Player.modifier(10));
        assertEquals(-1, Player.modifier(8));
    }

    @Test
    void strengthFocusedBuildGetsExpectedStats() {
        // Always pick the first remaining ability: STR 15, DEX 14, CON 13, INT 12, WIS 10, CHA 8.
        ScriptedConsole console = new ScriptedConsole()
                .withInt(1).withInt(1).withInt(1).withInt(1).withInt(1) // ability assignment
                .withInt(1); // Might at tier 0
        Player player = new Player("Hero", console);

        assertEquals(15, player.str);
        assertEquals(14, player.dex);
        assertEquals(13, player.con);
        assertEquals(4, player.attackBonus()); // 2 STR + 2 proficiency + 0 weapon
        assertEquals(13, player.armorClass()); // 10 + 2 DEX + 1 leather jerkin
        assertEquals(13, player.maxHp); // 12 + 1 CON
    }

    @Test
    void dumpStatStrengthStillHasPositiveAttackBonusThanksToProficiency() {
        // Pick the second remaining ability each round: STR ends up last (auto-assigned 8).
        ScriptedConsole console = new ScriptedConsole()
                .withInt(2).withInt(2).withInt(2).withInt(2).withInt(2) // ability assignment
                .withInt(1); // Might at tier 0
        Player player = new Player("Hero", console);

        assertEquals(8, player.str);
        assertEquals(-1, Player.modifier(player.str));
        assertEquals(1, player.attackBonus()); // -1 STR + 2 proficiency + 0 weapon, still positive
        assertTrue(player.attackBonus() > 0);
    }
}
