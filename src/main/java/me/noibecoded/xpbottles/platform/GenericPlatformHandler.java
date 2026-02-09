package me.noibecoded.xpbottles.platform;

import me.noibecoded.xpbottles.api.MinecraftVersion;
import me.noibecoded.xpbottles.api.ServerType;
import me.noibecoded.xpbottles.utils.BukkitUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GenericPlatformHandler implements IPlatformHandler {

    @Override
    public void initialize() {
        // Generic initialization if needed
    }

    @Override
    public boolean isCompatible(MinecraftVersion version, ServerType serverType) {
        return true; // Generic handler works on all versions
    }

    @Override
    public Object getNMSItem(Object bukkitItem) {
        if (bukkitItem instanceof ItemStack item) {
            return BukkitUtils.getNMSItem(item);
        }
        return null;
    }

    @Override
    public Object createXPBottle(int amount) {
        ItemStack bottle = new ItemStack(Material.EXPERIENCE_BOTTLE, amount);
        return BukkitUtils.getNMSItem(bottle);
    }

    @Override
    public void setXPBottleData(Object nmsItem, int amount) {
        // Generic implementation - may not work on all versions
        // Override in version-specific handlers
    }

    @Override
    public int getXPBottleData(Object nmsItem) {
        // Generic implementation - may not work on all versions
        // Override in version-specific handlers
        return 0;
    }
}
