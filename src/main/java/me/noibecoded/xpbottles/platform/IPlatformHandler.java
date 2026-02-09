package me.noibecoded.xpbottles.platform;

import me.noibecoded.xpbottles.api.MinecraftVersion;
import me.noibecoded.xpbottles.api.ServerType;

public interface IPlatformHandler {

    void initialize();

    boolean isCompatible(MinecraftVersion version, ServerType serverType);

    Object getNMSItem(Object bukkitItem);

    Object createXPBottle(int amount);

    void setXPBottleData(Object nmsItem, int amount);

    int getXPBottleData(Object nmsItem);
}
