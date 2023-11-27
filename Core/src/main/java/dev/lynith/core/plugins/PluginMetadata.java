package dev.lynith.core.plugins;

import dev.lynith.core.utils.MinecraftVersion;

public @interface PluginMetadata {
    String name();
    String version();
    MinecraftVersion[] minecraftVersions();

    String[] authors() default {};
    String description() default "No description provided.";
}
