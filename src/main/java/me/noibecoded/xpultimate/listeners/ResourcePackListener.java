package me.noibecoded.xpultimate.listeners;

import me.noibecoded.xpultimate.XpUltimate;
import me.noibecoded.xpultimate.utils.ResourcePackManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent.Status;

public class ResourcePackListener implements Listener {

    private final XpUltimate plugin;

    public ResourcePackListener(XpUltimate plugin) {
        this.plugin = plugin;
    }

    public static void register(XpUltimate plugin) {
        Bukkit.getPluginManager().registerEvents(new ResourcePackListener(plugin), plugin);
    }

    @EventHandler
    public void onPlayerResourcePackStatus(PlayerResourcePackStatusEvent event) {
        Player player = event.getPlayer();
        Status status = event.getStatus();
        String packHash = event.getHash();

        ResourcePackManager.PackStatus packStatus = mapStatus(status);

        ResourcePackManager.setPlayerPackStatus(player, packStatus);

        if (packHash != null && !packHash.isEmpty()) {
            ResourcePackManager.setPlayerPackHash(player, packHash);
        }

        switch (status) {
            case ACCEPTED:
                player.sendMessage("§aResource pack aceptado. Descargando...");
                break;
            case DECLINED:
                player.sendMessage("§cHas rechazado el resource pack. Algunas texturas no se mostrarán.");
                player.sendMessage("§7Usa §e/xpultimate resourcepack §7para volver a pedirlo.");
                break;
            case FAILED_DOWNLOAD:
                player.sendMessage("§cError al descargar el resource pack.");
                player.sendMessage("§7Usa §e/xpultimate resourcepack §7para intentarlo de nuevo.");
                break;
            case SUCCESSFULLY_LOADED:
                player.sendMessage("§aResource pack cargado correctamente!");
                plugin.getLogger().info("Player " + player.getName() + " has applied the resource pack.");
                break;
            case VALIDATED:
            case DOWNLOADED:
                break;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        ResourcePackManager.setPlayerPackStatus(player, ResourcePackManager.PackStatus.UNKNOWN);
    }

    private ResourcePackManager.PackStatus mapStatus(Status status) {
        return switch (status) {
            case ACCEPTED -> ResourcePackManager.PackStatus.DOWNLOADING;
            case DECLINED -> ResourcePackManager.PackStatus.DECLINED;
            case FAILED_DOWNLOAD -> ResourcePackManager.PackStatus.FAILED;
            case SUCCESSFULLY_LOADED -> ResourcePackManager.PackStatus.APPLIED;
            case VALIDATED -> ResourcePackManager.PackStatus.VALIDATING;
            case DOWNLOADED -> ResourcePackManager.PackStatus.DOWNLOADING;
        };
    }
}
