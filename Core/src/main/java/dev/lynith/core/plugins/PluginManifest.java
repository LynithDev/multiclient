package dev.lynith.core.plugins;

import dev.lynith.core.utils.gson.JsonRequired;
import lombok.Getter;

import java.util.Arrays;

@Getter
public class PluginManifest {

    @JsonRequired
    private String name;

    @JsonRequired
    private String version;

    @JsonRequired
    private String id;

    @JsonRequired
    private String main;

    private String description = "No description provided.";
    private String[] minecraftVersions = {};
    private String[] authors = {};

    @Override
    public String toString() {
        return "PluginManifest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", version='" + version + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", main='" + main + '\'' +
                ", minecraftVersions=" + Arrays.toString(minecraftVersions) +
                '}';
    }
}
