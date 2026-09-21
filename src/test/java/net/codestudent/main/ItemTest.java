package net.codestudent.main;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    void startingEquipmentSetsBaseStats() {
        ScriptedConsole console = new ScriptedConsole().withInt(1); // pick Might at tier 0
        Player player = new Player("Hero", console);

        assertEquals(11, player.armorClass()); // 10 base + 1 leather jerkin
        assertEquals(0, player.attackBonus()); // rusted shortsword has no bonus
        assertEquals(new DiceExpr(1, 6, 0), player.damageDice());
    }

    @Test
    void equippingWeaponChangesAttackBonusAndDamage() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Player player = new Player("Hero", console);

        player.equip(ItemCatalog.STEEL_LONGSWORD);

        assertEquals(1, player.attackBonus());
        assertEquals(new DiceExpr(1, 8, 0), player.damageDice());
    }

    @Test
    void equippingArmorChangesArmorClass() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Player player = new Player("Hero", console);

        player.equip(ItemCatalog.CHAINMAIL_VEST);

        assertEquals(13, player.armorClass()); // 10 base + 3 chainmail
    }

    @Test
    void unequippedGearGoesBackToInventory() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Player player = new Player("Hero", console);

        player.equip(ItemCatalog.STEEL_LONGSWORD);

        assertEquals(1, player.inventory.size());
        assertEquals(ItemCatalog.RUSTED_SHORTSWORD, player.inventory.get(0));
    }

    @Test
    void consumableHealsAndIsRemovedFromInventory() {
        ScriptedConsole console = new ScriptedConsole().withInt(1);
        Player player = new Player("Hero", console);
        player.hp = 5;
        player.addItem(ItemCatalog.MINOR_HEALING_POTION);

        player.useConsumable(ItemCatalog.MINOR_HEALING_POTION);

        assertEquals(15, player.hp); // 5 + 10 heal
        assertEquals(0, player.inventory.size());
    }
}
