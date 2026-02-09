package me.noibecoded.xpbottles.api;

public enum ServerType {
    SPIGOT("Spigot"),
    PAPER("Paper"),
    FORK("Fork"),
    UNKNOWN("Unknown");

    private final String name;

    ServerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ServerType detect() {
        String serverName = org.bukkit.Bukkit.getServer().getName().toLowerCase();
        String version = org.bukkit.Bukkit.getServer().getVersion().toLowerCase();

        if (serverName.contains("paper") || version.contains("paper")) {
            return PAPER;
        } else if (serverName.contains("spigot") || version.contains("spigot")) {
            return SPIGOT;
        } else if (serverName.contains("purpur") || serverName.contains("tuinity") ||
                   serverName.contains("airplane") || serverName.contains("leaf")) {
            return FORK;
        }

        return UNKNOWN;
    }
}
