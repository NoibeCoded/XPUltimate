package me.noibecoded.xpbottles;

import me.noibecoded.xpbottles.api.MinecraftVersion;
import me.noibecoded.xpbottles.api.ServerType;
import me.noibecoded.xpbottles.annotations.SupportedVersions;
import me.noibecoded.xpbottles.commands.XpBottleCommand;
import me.noibecoded.xpbottles.data.XpDataManager;
import me.noibecoded.xpbottles.listeners.BottleUseListener;
import me.noibecoded.xpbottles.utils.VersionUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@SupportedVersions(versions = {
    "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4",
    "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6",
    "1.21", "1.21.1", "1.21.2", "1.21.3"
}, serverType = "BOTH")
public final class XpBottles extends JavaPlugin {

    private static XpBottles instance;
    private MinecraftVersion currentVersion;
    private ServerType serverType;
    private boolean isSupported = false;

    @Override
    public void onLoad() {
        instance = this;
        detectServer();
        loadVersion();
    }

    @Override
    public void onEnable() {
        if (!isSupported) {
            getLogger().warning("This server version is not fully supported.");
        }

        XpDataManager.init(this);
        registerCommands();
        registerListeners();

        getLogger().info("XpBottles v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        XpDataManager.saveData();
        getLogger().info("XpBottles disabled!");
    }

    private void detectServer() {
        serverType = ServerType.detect();
        getLogger().info("Detected server type: " + serverType.getName());
    }

    private void loadVersion() {
        currentVersion = VersionUtils.getCurrentVersion();
        if (currentVersion != null) {
            isSupported = true;
            getLogger().info("Loaded version support for: " + currentVersion.getDisplayVersion());
        } else {
            getLogger().warning("Could not determine server version.");
        }
    }

    private void registerCommands() {
        getCommand("xpbottle").setExecutor(new XpBottleCommand());
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new BottleUseListener(this), this);
    }

    public static XpBottles getInstance() {
        return instance;
    }

    public MinecraftVersion getCurrentVersion() {
        return currentVersion;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public boolean isPaper() {
        return serverType == ServerType.PAPER;
    }

    public boolean isSpigot() {
        return serverType == ServerType.SPIGOT;
    }

    public boolean isFork() {
        return serverType == ServerType.FORK;
    }
}
