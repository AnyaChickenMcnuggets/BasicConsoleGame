package net.codestudent.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyTest {

    @Test
    void constructorSetsStatsWithinExpectedRange() {
        int playerLvl = 3;
        for (int i = 0; i < 100; i++) {
            Enemy enemy = new Enemy("Test", playerLvl);
            assertTrue(enemy.maxHp >= playerLvl && enemy.maxHp < playerLvl + 10,
                    "maxHp out of range: " + enemy.maxHp);
            assertEquals(enemy.maxHp, enemy.hp, "hp should start equal to maxHp");
            assertTrue(enemy.xp >= playerLvl && enemy.xp < 2 * playerLvl + 2,
                    "xp out of range: " + enemy.xp);
            assertEquals("Test", enemy.name);
        }
    }

    @Test
    void attackAndDefendAreNeverNegative() {
        Enemy enemy = new Enemy("Test", 3);
        for (int i = 0; i < 200; i++) {
            assertTrue(enemy.attack() >= 0);
            assertTrue(enemy.defend() >= 0);
        }
    }
}
