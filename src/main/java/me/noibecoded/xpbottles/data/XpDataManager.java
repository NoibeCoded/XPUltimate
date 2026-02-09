package me.noibecoded.xpbottles.data;

import me.noibecoded.xpbottles.XpBottles;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class XpDataManager {

    private static final Map<UUID, Integer> playerXp = new HashMap<>();
    private static File dataFile;
    private static File permissionsFile;
    private static XpBottles plugin;

    public static void init(XpBottles instance) {
        plugin = instance;
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        dataFile = new File(plugin.getDataFolder(), "xpdata.yml");
        permissionsFile = new File(plugin.getDataFolder(), "permissions.yml");
        loadData();
        saveDefaultPermissions();
    }

    private static void saveDefaultPermissions() {
        if (!permissionsFile.exists()) {
            try (InputStream is = plugin.getClass().getResourceAsStream("/permissions.yml")) {
                if (is != null) {
                    Files.copy(is, permissionsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    plugin.getLogger().info("Created default permissions.yml");
                }
            } catch (IOException e) {
                plugin.getLogger().warning("Failed to create permissions.yml: " + e.getMessage());
            }
        }
    }

    public static void loadData() {
        playerXp.clear();

        if (!dataFile.exists()) {
            return;
        }

        try {
            org.bukkit.configuration.file.YamlConfiguration config =
                org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(dataFile);

            for (String key : config.getKeys(false)) {
                try {
                    UUID uuid = UUID.fromString(key);
                    int xp = config.getInt(key, 0);
                    playerXp.put(uuid, xp);
                } catch (IllegalArgumentException e) {
                    // Skip invalid UUIDs
                }
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to load XP data: " + e.getMessage());
        }
    }

    public static void saveData() {
        try {
            org.bukkit.configuration.file.YamlConfiguration config =
                new org.bukkit.configuration.file.YamlConfiguration();

            for (Map.Entry<UUID, Integer> entry : playerXp.entrySet()) {
                config.set(entry.getKey().toString(), entry.getValue());
            }

            config.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save XP data: " + e.getMessage());
        }
    }

    public static int getTotalXp(Player player) {
        return playerXp.getOrDefault(player.getUniqueId(), 0);
    }

    public static void addXp(Player player, int amount) {
        UUID uuid = player.getUniqueId();
        int current = playerXp.getOrDefault(uuid, 0);
        playerXp.put(uuid, current + amount);
        saveData();
    }

    public static void removeXp(Player player, int amount) {
        UUID uuid = player.getUniqueId();
        int current = playerXp.getOrDefault(uuid, 0);
        int newAmount = Math.max(0, current - amount);
        playerXp.put(uuid, newAmount);
        saveData();
    }

    public static void setXp(Player player, int amount) {
        playerXp.put(player.getUniqueId(), Math.max(0, amount));
        saveData();
    }

    public static void giveXpBottle(Player player, int amount) {
        ItemStack bottle = createXpBottle(amount);
        player.getInventory().addItem(bottle);
    }

    public static ItemStack createXpBottle(int amount) {
        ItemStack bottle = new ItemStack(Material.EXPERIENCE_BOTTLE, 1);

        ItemMeta meta = bottle.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6XP Bottle");
            List<String> lore = new ArrayList<>();
            lore.add("§7Contains: §e" + amount + " XP");
            meta.setLore(lore);
            bottle.setItemMeta(meta);
        }

        return bottle;
    }
}
