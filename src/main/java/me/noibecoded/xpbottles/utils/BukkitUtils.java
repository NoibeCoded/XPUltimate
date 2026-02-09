package me.noibecoded.xpbottles.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

public final class BukkitUtils {

    private BukkitUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean isNMSAvailable() {
        try {
            Class.forName("net.minecraft.server.MinecraftServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Object getNMSItem(ItemStack item) {
        try {
            // Try Paper's method first
            Class<?> craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_21_R1.inventory.CraftItemStack");
            Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
            return asNMSCopy.invoke(null, item);
        } catch (Exception e) {
            try {
                // Try legacy method
                Class<?> craftItemStackClass = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack");
                Method asNMSCopy = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class);
                return asNMSCopy.invoke(null, item);
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static ItemStack getBukkitItem(Object nmsItem) {
        try {
            Class<?> craftItemStackClass = Class.forName("org.bukkit.craftbukkit.inventory.CraftItemStack");
            Method asBukkitCopy = craftItemStackClass.getMethod("asBukkitCopy", nmsItem.getClass());
            return (ItemStack) asBukkitCopy.invoke(null, nmsItem);
        } catch (Exception e) {
            return null;
        }
    }

    public static void updatePlayerInventory(Player player) {
        try {
            player.updateInventory();
        } catch (Exception e) {
            // Method deprecated but still works
        }
    }

    public static boolean hasAdventureSupport() {
        try {
            Class.forName("net.kyori.adventure.text.Component");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
