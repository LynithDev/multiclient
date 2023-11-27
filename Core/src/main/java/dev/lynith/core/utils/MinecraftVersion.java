package dev.lynith.core.utils;

import lombok.Getter;

public enum MinecraftVersion {

    V1_8_9("1.8.9"),
    V1_12_2("1.12.2"),
    V1_16_5("1.16.5"),
    V1_18_2("1.18.2"),
    V1_19_4("1.19.4"),
    V1_20_2("1.20.2");

    @Getter
    private final String version;

    MinecraftVersion(String version) {
        this.version = version;
    }

    public int getMajor() {
        return Integer.parseInt(version.split("\\.")[0]);
    }

    public int getMinor() {
        return Integer.parseInt(version.split("\\.")[1]);
    }

    public int getPatch() {
        return Integer.parseInt(version.split("\\.")[2]);
    }

    public static MinecraftVersion fromString(String version) {
        for (MinecraftVersion value : values()) {
            if (value.getVersion().equals(version)) {
                return value;
            }
        }
        return null;
    }

    public static MinecraftVersion fromInt(int minor, int patch) {
        for (MinecraftVersion value : values()) {
            if (value.getVersion().endsWith(minor + "." + patch)) {
                return value;
            }
        }
        return null;
    }

    public static boolean isNewer(MinecraftVersion v1, MinecraftVersion v2) {
        if (v1 == null || v2 == null) {
            return false;
        }

        return v1.getMajor() > v2.getMajor() && v1.getMinor() > v2.getMinor() && v1.getPatch() > v2.getPatch();
    }

    public static boolean isOlder(MinecraftVersion v1, MinecraftVersion v2) {
        if (v1 == null || v2 == null) {
            return false;
        }

        return v1.getMajor() < v2.getMajor() && v1.getMinor() < v2.getMinor() && v1.getPatch() < v2.getPatch();
    }

}
