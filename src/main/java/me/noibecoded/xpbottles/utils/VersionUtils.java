package me.noibecoded.xpbottles.utils;

import me.noibecoded.xpbottles.api.MinecraftVersion;
import org.bukkit.Bukkit;

public final class VersionUtils {

    private VersionUtils() {
        throw new UnsupportedOperationException();
    }

    public static MinecraftVersion getCurrentVersion() {
        String version = Bukkit.getVersion();
        String bukkitVersion = Bukkit.getBukkitVersion();

        if (version == null && bukkitVersion == null) {
            return null;
        }

        // Try to parse from version string
        String versionStr = version != null ? version : bukkitVersion;

        // Extract version number (e.g., "1.21.11" from "1.21.11-R0.1" or "1.21.1-R0.1-SNAPSHOT")
        if (versionStr.contains("1.21.11")) {
            return MinecraftVersion.v1_21_11;
        } else if (versionStr.contains("1.21.6")) {
            return MinecraftVersion.v1_21_6;
        } else if (versionStr.contains("1.21.5")) {
            return MinecraftVersion.v1_21_5;
        } else if (versionStr.contains("1.21.4")) {
            return MinecraftVersion.v1_21_4;
        } else if (versionStr.contains("1.21.3")) {
            return MinecraftVersion.v1_21_3;
        } else if (versionStr.contains("1.21.2")) {
            return MinecraftVersion.v1_21_2;
        } else if (versionStr.contains("1.21.1")) {
            return MinecraftVersion.v1_21_1;
        } else if (versionStr.contains("1.21")) {
            return MinecraftVersion.v1_21;
        } else if (versionStr.contains("1.20.6")) {
            return MinecraftVersion.v1_20_6;
        } else if (versionStr.contains("1.20.5")) {
            return MinecraftVersion.v1_20_5;
        } else if (versionStr.contains("1.20.4")) {
            return MinecraftVersion.v1_20_4;
        } else if (versionStr.contains("1.20.3")) {
            return MinecraftVersion.v1_20_3;
        } else if (versionStr.contains("1.20.2")) {
            return MinecraftVersion.v1_20_2;
        } else if (versionStr.contains("1.20.1")) {
            return MinecraftVersion.v1_20_1;
        } else if (versionStr.contains("1.20")) {
            return MinecraftVersion.v1_20;
        } else if (versionStr.contains("1.19.4")) {
            return MinecraftVersion.v1_19_4;
        } else if (versionStr.contains("1.19.3")) {
            return MinecraftVersion.v1_19_3;
        } else if (versionStr.contains("1.19.2")) {
            return MinecraftVersion.v1_19_2;
        } else if (versionStr.contains("1.19.1")) {
            return MinecraftVersion.v1_19_1;
        } else if (versionStr.contains("1.19")) {
            return MinecraftVersion.v1_19;
        }

        return null;
    }

    public static boolean isAtLeastVersion(String requiredVersion) {
        MinecraftVersion current = getCurrentVersion();
        MinecraftVersion required = MinecraftVersion.fromString(requiredVersion);

        if (current == null || required == null) {
            return false;
        }

        return current.ordinal() >= required.ordinal();
    }

    public static boolean isBelowVersion(String maxVersion) {
        MinecraftVersion current = getCurrentVersion();
        MinecraftVersion max = MinecraftVersion.fromString(maxVersion);

        if (current == null || max == null) {
            return false;
        }

        return current.ordinal() < max.ordinal();
    }
}
