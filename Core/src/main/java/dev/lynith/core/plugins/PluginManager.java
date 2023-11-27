package dev.lynith.core.plugins;

import dev.lynith.core.ClientStartup;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;

public class PluginManager {

    private final CopyOnWriteArrayList<Plugin> plugins = new CopyOnWriteArrayList<>();

    private final String pluginPath = "/home/lynith/Documents/client_plugins/"; // Hardcoded for testing

    public void init(Instrumentation inst) {
        File pluginDir = new File(pluginPath);
        if (!pluginDir.exists()) pluginDir.mkdirs();

        File[] files = pluginDir.listFiles();
        if (files == null) return;

        List<String> pluginClasses = new ArrayList<>();

        for (File file : files) {
            if (!file.getName().endsWith(".jar")) continue;

            try {
                JarFile jar = new JarFile(file);

                String pluginClass = null;

                for (Map.Entry<Object, Object> entry : jar.getManifest().getMainAttributes().entrySet()) {
                    if (entry.getKey().toString().equals("Plugin-Class")) {
                        pluginClass = entry.getValue().toString();
                        break;
                    }
                }

                if (pluginClass == null) {
                    System.out.println("Plugin " + file.getName() + " has no Plugin-Class attribute.");
                    continue;
                }

                System.out.println("Loading plugin " + pluginClass);
                pluginClasses.add(pluginClass);

                inst.appendToSystemClassLoaderSearch(jar);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String pluginClass : pluginClasses) {
            try {
                Class<?> clazz = Class.forName(pluginClass);
                Plugin plugin = (Plugin) clazz.getConstructor().newInstance();
                plugins.add(plugin);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        preInitPlugins(inst);
    }

    public void preInitPlugins(Instrumentation inst) {
        for (Plugin plugin : plugins) {
            plugin.onPreInit(inst);
        }
    }

    public void initPlugins() {
        for (Plugin plugin : plugins) {
            plugin.onInit(ClientStartup.getInstance().getVersion());
        }
    }

}
