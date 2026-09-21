package net.codestudent.main;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    @Test
    void rollStaysWithinDieRange() {
        Dice dice = new Dice(new Random());
        for (int i = 0; i < 500; i++) {
            int roll = dice.roll(6);
            assertTrue(roll >= 1 && roll <= 6, "roll out of range: " + roll);
        }
    }

    @Test
    void rollExprAppliesCountAndModifier() {
        Dice dice = new Dice(new Random());
        for (int i = 0; i < 500; i++) {
            int roll = dice.roll(DiceExpr.of(2, 6, 3));
            assertTrue(roll >= 5 && roll <= 15, "2d6+3 out of range: " + roll);
        }
    }

    @Test
    void seededRandomIsDeterministic() {
        Dice a = new Dice(new Random(42));
        Dice b = new Dice(new Random(42));
        for (int i = 0; i < 50; i++)
            assertEquals(a.roll(20), b.roll(20));
    }
}
