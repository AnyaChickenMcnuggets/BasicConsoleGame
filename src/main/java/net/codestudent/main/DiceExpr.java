package net.codestudent.main;

public record DiceExpr(int count, int sides, int modifier) {

    public static DiceExpr of(int count, int sides, int modifier) {
        return new DiceExpr(count, sides, modifier);
    }

    public static DiceExpr of(int count, int sides) {
        return new DiceExpr(count, sides, 0);
    }

    @Override
    public String toString() {
        String base = count + "d" + sides;
        if (modifier > 0) return base + "+" + modifier;
        if (modifier < 0) return base + modifier;
        return base;
    }
}
