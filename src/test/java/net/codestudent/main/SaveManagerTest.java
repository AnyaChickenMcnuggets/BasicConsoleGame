package net.codestudent.main;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaveManagerTest {

    @TempDir
    Path tempDir;

    @Test
    void saveThenLoadRestoresPlayerState() {
        ScriptedConsole console = new ScriptedConsole()
                .withInt(1).withInt(1).withInt(1).withInt(1).withInt(1) // ability score assignment
                .withInt(1); // Might at tier 0
        Player player = new Player("Hero", console);
        player.gold = 99;
        player.xp = 5;
        player.hp = 12;
        player.equip(ItemCatalog.STEEL_LONGSWORD);
        player.addItem(ItemCatalog.MINOR_HEALING_POTION);
        QuestProgress progress = player.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id());
        progress.state = QuestProgress.State.ACTIVE;
        progress.killCount = 2;

        Path savePath = tempDir.resolve("save.properties");
        SaveManager.save(savePath, player);

        assertTrue(SaveManager.exists(savePath));
        Player loaded = SaveManager.load(savePath, console);

        assertEquals(player.name, loaded.name);
        assertEquals(player.gold, loaded.gold);
        assertEquals(player.xp, loaded.xp);
        assertEquals(player.hp, loaded.hp);
        assertEquals(player.maxHp, loaded.maxHp);
        assertEquals(player.lvl, loaded.lvl);
        assertEquals(player.maxXP, loaded.maxXP);
        assertEquals(player.restsLeft, loaded.restsLeft);
        assertEquals(player.str, loaded.str);
        assertEquals(player.dex, loaded.dex);
        assertEquals(player.con, loaded.con);
        assertEquals(player.intel, loaded.intel);
        assertEquals(player.wis, loaded.wis);
        assertEquals(player.cha, loaded.cha);
        assertEquals(player.branch, loaded.branch);
        assertEquals(player.skillTier, loaded.skillTier);
        assertEquals(player.unlockedSkills, loaded.unlockedSkills);
        assertEquals(ItemCatalog.STEEL_LONGSWORD, loaded.equippedWeapon);
        assertEquals(ItemCatalog.LEATHER_JERKIN, loaded.equippedArmor);
        assertTrue(loaded.inventory.contains(ItemCatalog.MINOR_HEALING_POTION));

        QuestProgress loadedProgress = loaded.questProgress(QuestBook.WOLVES_AT_THE_DOOR.id());
        assertEquals(QuestProgress.State.ACTIVE, loadedProgress.state);
        assertEquals(2, loadedProgress.killCount);
    }
}
