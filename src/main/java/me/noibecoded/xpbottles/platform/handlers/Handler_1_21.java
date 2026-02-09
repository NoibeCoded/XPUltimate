package me.noibecoded.xpbottles.platform.handlers;

import me.noibecoded.xpbottles.api.MinecraftVersion;
import me.noibecoded.xpbottles.api.ServerType;
import me.noibecoded.xpbottles.platform.GenericPlatformHandler;
import me.noibecoded.xpbottles.utils.BukkitUtils;

public class Handler_1_21 extends GenericPlatformHandler {

    @Override
    public boolean isCompatible(MinecraftVersion version, ServerType serverType) {
        return version.name().startsWith("v1_21");
    }

    @Override
    public Object createXPBottle(int amount) {
        // 1.21 specific implementation using reflection
        try {
            Class<?> nmsItemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
            Object itemStack = nmsItemStackClass.getDeclaredMethod("b", String.class, int.class)
                .invoke(null, "minecraft:experience_bottle", amount);

            Class<?> nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
            Object nbt = nbtTagCompoundClass.getDeclaredConstructor().newInstance();

            nbtTagCompoundClass.getMethod("setInt", String.class, int.class)
                .invoke(nbt, "BottleXP", amount);

            nmsItemStackClass.getMethod("c", nbtTagCompoundClass)
                .invoke(itemStack, nbt);

            return itemStack;
        } catch (Exception e) {
            return super.createXPBottle(amount);
        }
    }

    @Override
    public int getXPBottleData(Object nmsItem) {
        try {
            Class<?> nbtTagCompoundClass = Class.forName("net.minecraft.nbt.NBTTagCompound");
            Object nbt = nmsItem.getClass().getMethod("u").invoke(nmsItem);

            if (nbt != null && nbtTagCompoundClass.isInstance(nbt)) {
                return (int) nbtTagCompoundClass.getMethod("getInt", String.class).invoke(nbt, "BottleXP");
            }
        } catch (Exception e) {
            // Fall through to generic implementation
        }
        return 0;
    }

    private Class<?> getNMSItemClass() throws ClassNotFoundException {
        return Class.forName("net.minecraft.world.item.ItemStack");
    }
}
