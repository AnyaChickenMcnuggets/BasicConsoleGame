package net.codestudent.main;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyTest {

    @Test
    void constructorRollsHitPointsFromTemplate() {
        Dice dice = new Dice(new Random());
        for (int i = 0; i < 100; i++) {
            Enemy enemy = new Enemy(Bestiary.MARSH_WOLF, dice);
            DiceExpr hitDice = Bestiary.MARSH_WOLF.hitDice();
            int min = hitDice.count() + hitDice.modifier();
            int max = hitDice.count() * hitDice.sides() + hitDice.modifier();
            assertTrue(enemy.maxHp >= min && enemy.maxHp <= max,
                    "maxHp out of range: " + enemy.maxHp);
            assertEquals(enemy.maxHp, enemy.hp);
            assertEquals(Bestiary.MARSH_WOLF.name(), enemy.name);
            assertEquals(Bestiary.MARSH_WOLF.xpReward(), enemy.xp);
        }
    }

    @Test
    void combatStatsComeStraightFromTemplate() {
        Enemy enemy = new Enemy(Bestiary.HIGHWAY_BANDIT, new Dice(new Random()));
        assertEquals(Bestiary.HIGHWAY_BANDIT.armorClass(), enemy.armorClass());
        assertEquals(Bestiary.HIGHWAY_BANDIT.attackBonus(), enemy.attackBonus());
        assertEquals(Bestiary.HIGHWAY_BANDIT.damageDice(), enemy.damageDice());
    }
}
