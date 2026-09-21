package net.codestudent.main;

import java.util.Random;

public class Dice {

    private final Random random;

    public Dice(Random random) {
        this.random = random;
    }

    public int roll(int sides) {
        return random.nextInt(sides) + 1;
    }

    public int roll(DiceExpr expr) {
        int total = expr.modifier();
        for (int i = 0; i < expr.count(); i++)
            total += roll(expr.sides());
        return total;
    }
}
