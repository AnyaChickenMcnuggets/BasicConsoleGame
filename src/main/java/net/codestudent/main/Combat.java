package net.codestudent.main;

public class Combat {

    private Combat() {}

    public static AttackResult resolveAttack(Character attacker, Character defender, Dice dice) {
        int naturalRoll = dice.roll(20);
        boolean critical = naturalRoll == 20;
        boolean automaticMiss = naturalRoll == 1;
        boolean hit = critical || (!automaticMiss && naturalRoll + attacker.attackBonus() >= defender.armorClass());

        int damage = 0;
        if (hit) {
            DiceExpr damageDice = attacker.damageDice();
            damage = dice.roll(damageDice);
            if (critical)
                damage += dice.roll(new DiceExpr(damageDice.count(), damageDice.sides(), 0));
        }
        return new AttackResult(hit, critical, damage, naturalRoll);
    }
}
