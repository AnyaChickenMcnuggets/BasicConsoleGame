package net.codestudent.main;

class DummyCombatant extends Character {

    private final int armorClass;
    private final int attackBonus;
    private final DiceExpr damageDice;

    DummyCombatant(int armorClass, int attackBonus, DiceExpr damageDice) {
        super("Dummy", 20, 0);
        this.armorClass = armorClass;
        this.attackBonus = attackBonus;
        this.damageDice = damageDice;
    }

    @Override
    public int armorClass() {
        return armorClass;
    }

    @Override
    public int attackBonus() {
        return attackBonus;
    }

    @Override
    public DiceExpr damageDice() {
        return damageDice;
    }
}
