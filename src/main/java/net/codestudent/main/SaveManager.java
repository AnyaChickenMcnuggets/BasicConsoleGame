package net.codestudent.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class SaveManager {

    private SaveManager() {}

    public static boolean exists(Path path) {
        return Files.exists(path);
    }

    public static void save(Path path, Player player) {
        java.util.Properties props = new java.util.Properties();
        props.setProperty("name", player.name);
        props.setProperty("hp", String.valueOf(player.hp));
        props.setProperty("maxHp", String.valueOf(player.maxHp));
        props.setProperty("xp", String.valueOf(player.xp));
        props.setProperty("lvl", String.valueOf(player.lvl));
        props.setProperty("maxXP", String.valueOf(player.maxXP));
        props.setProperty("gold", String.valueOf(player.gold));
        props.setProperty("restsLeft", String.valueOf(player.restsLeft));
        props.setProperty("str", String.valueOf(player.str));
        props.setProperty("dex", String.valueOf(player.dex));
        props.setProperty("con", String.valueOf(player.con));
        props.setProperty("intel", String.valueOf(player.intel));
        props.setProperty("wis", String.valueOf(player.wis));
        props.setProperty("cha", String.valueOf(player.cha));
        props.setProperty("branch", player.branch != null ? player.branch.name() : "");
        props.setProperty("skillTier", String.valueOf(player.skillTier));
        props.setProperty("unlockedSkills",
            player.unlockedSkills.stream().map(SkillNode::id).collect(Collectors.joining(",")));
        props.setProperty("equippedWeapon", player.equippedWeapon != null ? player.equippedWeapon.id() : "");
        props.setProperty("equippedArmor", player.equippedArmor != null ? player.equippedArmor.id() : "");
        props.setProperty("inventory",
            player.inventory.stream().map(Item::id).collect(Collectors.joining(",")));
        props.setProperty("quests", player.questProgress.entrySet().stream()
            .map(e -> e.getKey() + ":" + e.getValue().state.name() + ":" + e.getValue().killCount)
            .collect(Collectors.joining(";")));

        try {
            if (path.getParent() != null)
                Files.createDirectories(path.getParent());
            try (OutputStream out = Files.newOutputStream(path)) {
                props.store(out, "The Hollow Crown save file");
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not write save file: " + path, e);
        }
    }

    public static Player load(Path path, Console console) {
        java.util.Properties props = new java.util.Properties();
        try (InputStream in = Files.newInputStream(path)) {
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Could not read save file: " + path, e);
        }

        Player player = Player.blank(props.getProperty("name"), console);
        player.hp = Integer.parseInt(props.getProperty("hp"));
        player.maxHp = Integer.parseInt(props.getProperty("maxHp"));
        player.xp = Integer.parseInt(props.getProperty("xp"));
        player.lvl = Integer.parseInt(props.getProperty("lvl"));
        player.maxXP = Integer.parseInt(props.getProperty("maxXP"));
        player.gold = Integer.parseInt(props.getProperty("gold"));
        player.restsLeft = Integer.parseInt(props.getProperty("restsLeft", "1"));
        player.str = Integer.parseInt(props.getProperty("str", "10"));
        player.dex = Integer.parseInt(props.getProperty("dex", "10"));
        player.con = Integer.parseInt(props.getProperty("con", "10"));
        player.intel = Integer.parseInt(props.getProperty("intel", "10"));
        player.wis = Integer.parseInt(props.getProperty("wis", "10"));
        player.cha = Integer.parseInt(props.getProperty("cha", "10"));

        String branch = props.getProperty("branch", "");
        player.branch = branch.isEmpty() ? null : SkillNode.Branch.valueOf(branch);
        player.skillTier = Integer.parseInt(props.getProperty("skillTier"));

        String unlocked = props.getProperty("unlockedSkills", "");
        if (!unlocked.isEmpty())
            for (String id : unlocked.split(","))
                player.unlockedSkills.add(SkillTree.byId(id));

        String weaponId = props.getProperty("equippedWeapon", "");
        player.equippedWeapon = weaponId.isEmpty() ? null : (Weapon) ItemCatalog.byId(weaponId);
        String armorId = props.getProperty("equippedArmor", "");
        player.equippedArmor = armorId.isEmpty() ? null : (Armor) ItemCatalog.byId(armorId);

        String inventory = props.getProperty("inventory", "");
        if (!inventory.isEmpty())
            for (String id : inventory.split(","))
                player.inventory.add(ItemCatalog.byId(id));

        String quests = props.getProperty("quests", "");
        if (!quests.isEmpty()) {
            for (String entry : quests.split(";")) {
                String[] parts = entry.split(":");
                QuestProgress progress = player.questProgress(parts[0]);
                progress.state = QuestProgress.State.valueOf(parts[1]);
                progress.killCount = Integer.parseInt(parts[2]);
            }
        }

        return player;
    }
}
