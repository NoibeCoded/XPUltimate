package me.noibecoded.xpbottles.api;

public enum MinecraftVersion {
    v1_19("1.19", "1.19.4"),
    v1_19_1("1.19.1", "1.19.1"),
    v1_19_2("1.19.2", "1.19.2"),
    v1_19_3("1.19.3", "1.19.3"),
    v1_19_4("1.19.4", "1.19.4"),
    v1_20("1.20", "1.20.1"),
    v1_20_1("1.20.1", "1.20.1"),
    v1_20_2("1.20.2", "1.20.2"),
    v1_20_3("1.20.3", "1.20.3"),
    v1_20_4("1.20.4", "1.20.4"),
    v1_20_5("1.20.5", "1.20.5"),
    v1_20_6("1.20.6", "1.20.6"),
    v1_21("1.21", "1.21.1"),
    v1_21_1("1.21.1", "1.21.1"),
    v1_21_2("1.21.2", "1.21.2"),
    v1_21_3("1.21.3", "1.21.3"),
    v1_21_4("1.21.4", "1.21.4"),
    v1_21_5("1.21.5", "1.21.5"),
    v1_21_6("1.21.6", "1.21.6"),
    v1_21_11("1.21.11", "1.21.11");

    private final String displayVersion;
    private final String apiVersion;

    MinecraftVersion(String displayVersion, String apiVersion) {
        this.displayVersion = displayVersion;
        this.apiVersion = apiVersion;
    }

    public String getDisplayVersion() {
        return displayVersion;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public static MinecraftVersion fromString(String version) {
        for (MinecraftVersion v : values()) {
            if (v.displayVersion.equals(version) || v.apiVersion.equals(version)) {
                return v;
            }
        }
        return null;
    }
}
