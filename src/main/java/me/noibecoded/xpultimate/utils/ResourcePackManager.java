package me.noibecoded.xpultimate.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourcePackManager {

    public enum PackStatus {
        NOT_APPLIED("§cNo aplicado"),
        APPLIED("§aAplicado"),
        FAILED("§cFalló"),
        DOWNLOADING("§6Descargando..."),
        DECLINED("§cRechazado"),
        VALIDATING("§6Validando..."),
        UNKNOWN("§7Desconocido");

        private final String displayName;

        PackStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private static final Map<UUID, PackStatus> playerPackStatus = new HashMap<>();
    private static final Map<UUID, String> playerPackHash = new HashMap<>();

    public static void setPlayerPackStatus(Player player, PackStatus status) {
        playerPackStatus.put(player.getUniqueId(), status);
    }

    public static PackStatus getPlayerPackStatus(Player player) {
        return playerPackStatus.getOrDefault(player.getUniqueId(), PackStatus.UNKNOWN);
    }

    public static boolean hasResourcePackApplied(Player player) {
        PackStatus status = getPlayerPackStatus(player);
        return status == PackStatus.APPLIED;
    }

    public static void setPlayerPackHash(Player player, String hash) {
        playerPackHash.put(player.getUniqueId(), hash);
    }

    public static String getPlayerPackHash(Player player) {
        return playerPackHash.get(player.getUniqueId());
    }

    public static void removePlayer(Player player) {
        playerPackStatus.remove(player.getUniqueId());
        playerPackHash.remove(player.getUniqueId());
    }

    public static String getStatusMessage(Player player) {
        PackStatus status = getPlayerPackStatus(player);
        String hash = getPlayerPackHash(player);
        String hashInfo = (hash != null && !hash.isEmpty()) ? " §7(Hash: " + hash.substring(0, Math.min(8, hash.length())) + "...)" : "";

        return "§7Estado del Resource Pack: " + status.getDisplayName() + hashInfo;
    }
}
