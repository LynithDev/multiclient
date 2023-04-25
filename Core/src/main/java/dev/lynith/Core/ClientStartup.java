package dev.lynith.Core;

import dev.lynith.Core.versions.IVersion;

public class ClientStartup {

    private static IVersion version;

    public static void start(IVersion ver) {
        version = ver;
        System.out.println("Agent hooked and using Minecraft version" + version.getVersion());
    }

    public static IVersion getBridge() {
        return version;
    }

}
