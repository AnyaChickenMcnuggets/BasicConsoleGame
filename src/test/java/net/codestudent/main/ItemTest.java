package net.codestudent.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    // Deterministic chargen: always pick the first remaining ability at each assignment step,
    // giving STR 15 (+2), DEX 14 (+2), CON 13 (+1), INT 12 (+1), WIS 10 (+0), CHA 8 (-1).
    // Then pick Might (1) at tier 0.
    private static ScriptedConsole newHeroConsole() {
        return new ScriptedConsole().withInt(1).withInt(1).withInt(1).withInt(1).withInt(1).withInt(1);
    }

    @Test
    void startingEquipmentSetsBaseStats() {
        Player player = new Player("Hero", newHeroConsole());

        assertEquals(13, player.armorClass()); // 10 base + 2 DEX + 1 leather jerkin
        assertEquals(4, player.attackBonus()); // 2 STR + 2 proficiency + 0 weapon
        assertEquals(new DiceExpr(1, 6, 2), player.damageDice()); // 1d6 + 2 STR
    }

    @Test
    void equippingWeaponChangesAttackBonusAndDamage() {
        Player player = new Player("Hero", newHeroConsole());

        player.equip(ItemCatalog.STEEL_LONGSWORD);

        assertEquals(5, player.attackBonus()); // 2 STR + 2 proficiency + 1 weapon
        assertEquals(new DiceExpr(1, 8, 2), player.damageDice()); // 1d8 + 2 STR
    }

    @Test
    void equippingArmorChangesArmorClass() {
        Player player = new Player("Hero", newHeroConsole());

        player.equip(ItemCatalog.CHAINMAIL_VEST);

        assertEquals(15, player.armorClass()); // 10 base + 2 DEX + 3 chainmail
    }

    @Test
    void unequippedGearGoesBackToInventory() {
        Player player = new Player("Hero", newHeroConsole());

        player.equip(ItemCatalog.STEEL_LONGSWORD);

        assertEquals(1, player.inventory.size());
        assertEquals(ItemCatalog.RUSTED_SHORTSWORD, player.inventory.get(0));
    }

    @Test
    void consumableHealsAndIsRemovedFromInventory() {
        Player player = new Player("Hero", newHeroConsole());
        player.hp = 5;
        player.addItem(ItemCatalog.MINOR_HEALING_POTION);

        player.useConsumable(ItemCatalog.MINOR_HEALING_POTION);

        assertEquals(player.maxHp, player.hp); // 5 + 10 heal, capped at maxHp (13: 12 + 1 CON)
        assertEquals(0, player.inventory.size());
    }
}
