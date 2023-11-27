package dev.lynith.core.plugins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.lynith.core.ClientStartup;
import dev.lynith.core.Logger;
import dev.lynith.core.events.impl.MinecraftInit;
import dev.lynith.core.utils.gson.AnnotatedDeserializer;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class PluginManager {

    private final Logger logger = new Logger("PluginManager");
    private final CopyOnWriteArrayList<Plugin> plugins = new CopyOnWriteArrayList<>();

    public PluginManager(Instrumentation inst) {
        String pluginPath = System.getProperty("pluginPath") != null
                ? System.getProperty("pluginPath")
                : System.getProperty("user.dir") + File.separator + "plugins";

        logger.log("Loading plugins from '{}'", pluginPath);

        File pluginDir = new File(pluginPath);
        if (!pluginDir.exists()) pluginDir.mkdirs();

        File[] files = pluginDir.listFiles();
        if (files == null) return;

        List<PluginManifest> pluginManifests = new ArrayList<>();

        for (File file : files) {
            if (!file.getName().endsWith(".jar")) continue;

            try {
                JarFile jar = new JarFile(file);

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(PluginManifest.class, new AnnotatedDeserializer<PluginManifest>())
                        .create();

                PluginManifest manifest = gson.fromJson(
                        new InputStreamReader(jar.getInputStream(jar.getEntry("plugin.json"))),
                        PluginManifest.class
                );

                if (manifest == null) {
                    logger.error("Failed to load plugin from file '{}': plugin.json is missing or invalid", file.getName());
                    continue;
                }

                pluginManifests.add(manifest);

                inst.appendToSystemClassLoaderSearch(jar);
            } catch (Exception e) {
                logger.error("Failed to load plugin from file '{}': {}", file.getName(), e.getMessage());
            }
        }

        for (PluginManifest manifest : pluginManifests) {
            try {
                Class<?> clazz = Class.forName(manifest.getMain());

                Plugin plugin = (Plugin) clazz.getConstructor().newInstance();

                Field manifestField = plugin.getClass().getSuperclass().getDeclaredField("manifest");
                manifestField.setAccessible(true);
                manifestField.set(plugin, manifest);

                Field loggerField = plugin.getClass().getSuperclass().getDeclaredField("logger");
                loggerField.setAccessible(true);
                loggerField.set(plugin, new Logger("Plugin/" + manifest.getName()));

                plugins.add(plugin);
            } catch (Exception e) {
                logger.error("Failed to load plugin '{}': {}", manifest.getName());
                e.printStackTrace();
            }
        }

        ClientStartup.getInstance().getEventBus().once(MinecraftInit.class, this::initPlugins);
        preInitPlugins(inst);
    }

    private void preInitPlugins(Instrumentation inst) {
        for (Plugin plugin : plugins) {
            plugin.onPreInit(inst);
        }
    }

    private void initPlugins() {
        for (Plugin plugin : plugins) {
            plugin.onInit(ClientStartup.getInstance().getVersion());
        }
    }

}
