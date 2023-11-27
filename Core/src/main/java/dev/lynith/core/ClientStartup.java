package dev.lynith.core;

import dev.lynith.core.bridge.IVersion;
import dev.lynith.core.events.EventBus;
import dev.lynith.core.events.impl.MinecraftInit;
import dev.lynith.core.events.impl.MouseClick;
import dev.lynith.core.events.impl.ShutdownEvent;
import dev.lynith.core.plugins.PluginManager;
import lombok.Getter;

import java.lang.instrument.Instrumentation;

public class ClientStartup {
    private final static Logger logger = new Logger("main");

    @Getter
    private static ClientStartup instance;

    private static PluginManager pluginManager;

    public static void main(String[] args) {
        System.out.println("This shouldn't be run directly.");
        System.exit(1);
    }

    public static void launch(IVersion version, Instrumentation inst) {
        if (instance != null) {
            logger.log("ClientStartup instance already exists. This shouldn't happen.");
            return;
        }

        pluginManager = new PluginManager();
        pluginManager.init(inst);
        logger.log("Initialized PluginManager and preinitialized plugins");

        logger.log("Launching version " + version.getMinecraft().getGameVersion());
        instance = new ClientStartup();
        instance.launchClient(version);
    }

    @Getter
    private IVersion version;

    @Getter
    private EventBus eventBus;

    public void launchClient(IVersion version) {
        this.version = version;
        logger.log("Initialized Version");

        this.eventBus = new EventBus();
        logger.log("Initialized EventBus");

        pluginManager.initPlugins();
        logger.log("Initialized Plugins");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            getEventBus().emit(ShutdownEvent.class);
        }));

        getEventBus().on(ShutdownEvent.class, () -> {
            logger.log("Preparing for shutdown");
        });
    }

}
