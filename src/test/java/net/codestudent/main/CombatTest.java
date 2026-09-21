package net.codestudent.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CombatTest {

    @Test
    void naturalTwentyAlwaysHitsAndCrits() {
        Dice dice = new Dice(new QueuedRandom(19, 0, 0)); // natural roll 20, then two damage dice rolls
        DummyCombatant attacker = new DummyCombatant(10, 0, DiceExpr.of(1, 6));
        DummyCombatant defender = new DummyCombatant(100, 0, DiceExpr.of(1, 4));

        AttackResult result = Combat.resolveAttack(attacker, defender, dice);

        assertTrue(result.hit());
        assertTrue(result.critical());
        assertEquals(20, result.naturalRoll());
    }

    @Test
    void naturalOneAlwaysMisses() {
        Dice dice = new Dice(new QueuedRandom(0)); // natural roll 1
        DummyCombatant attacker = new DummyCombatant(10, 100, DiceExpr.of(1, 6));
        DummyCombatant defender = new DummyCombatant(1, 0, DiceExpr.of(1, 4));

        AttackResult result = Combat.resolveAttack(attacker, defender, dice);

        assertFalse(result.hit());
        assertFalse(result.critical());
        assertEquals(1, result.naturalRoll());
        assertEquals(0, result.damage());
    }

    @Test
    void totalMustMeetOrBeatArmorClass() {
        DummyCombatant attacker = new DummyCombatant(10, 5, DiceExpr.of(1, 6));
        DummyCombatant defender = new DummyCombatant(14, 0, DiceExpr.of(1, 4));

        Dice hitDice = new Dice(new QueuedRandom(8, 0)); // natural 9, total 14 -> hits, then damage roll
        assertTrue(Combat.resolveAttack(attacker, defender, hitDice).hit());

        Dice missDice = new Dice(new QueuedRandom(7)); // natural 8, total 13 -> misses
        assertFalse(Combat.resolveAttack(attacker, defender, missDice).hit());
    }

    @Test
    void hitRollsDamageDiceWithModifier() {
        DummyCombatant attacker = new DummyCombatant(10, 0, DiceExpr.of(1, 6, 2));
        DummyCombatant defender = new DummyCombatant(10, 0, DiceExpr.of(1, 4));
        // natural 15 (index 14) to-hit, then damage die shows 4 (index 3)
        Dice dice = new Dice(new QueuedRandom(14, 3));

        AttackResult result = Combat.resolveAttack(attacker, defender, dice);

        assertTrue(result.hit());
        assertFalse(result.critical());
        assertEquals(6, result.damage()); // 4 + 2 modifier
    }

    @Test
    void criticalHitRollsDamageDiceTwiceWithoutDoublingModifier() {
        DummyCombatant attacker = new DummyCombatant(10, 0, DiceExpr.of(1, 6, 2));
        DummyCombatant defender = new DummyCombatant(10, 0, DiceExpr.of(1, 4));
        // natural 20 (index 19), first damage die 3 (index 2), extra crit die 5 (index 4)
        Dice dice = new Dice(new QueuedRandom(19, 2, 4));

        AttackResult result = Combat.resolveAttack(attacker, defender, dice);

        assertTrue(result.hit());
        assertTrue(result.critical());
        assertEquals(10, result.damage()); // (3+2) + 5
    }
}
