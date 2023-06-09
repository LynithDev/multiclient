package dev.lynith.Core;

import dev.lynith.Core.events.EventBus;
import dev.lynith.Core.events.Subscribe;
import dev.lynith.Core.events.impl.MinecraftInitEvent;
import dev.lynith.Core.events.impl.MinecraftShutdownEvent;
import dev.lynith.Core.versions.IVersion;
import lombok.Getter;

import java.lang.instrument.Instrumentation;

public class ClientStartup {

    private final Logger logger;

    @Getter
    private final IVersion bridge;

    @Getter
    private static ClientStartup instance;

    public static void start(IVersion version, Instrumentation inst) {
        instance = new ClientStartup(version, inst);
    }

    public ClientStartup(IVersion version, Instrumentation inst) {
        if (instance == null)
            instance = this;
        this.logger = new Logger("main");
        logger.log("Client Startup");

        this.bridge = version;
        logger.log("Loaded version " + bridge.getVersion());

        EventBus.getEventBus().register(this);
        logger.log("Registered self");
    }

    @Subscribe
    private void onMinecraftInit(MinecraftInitEvent event) {
        logger.log("Minecraft Init MULTICLIENT");
    }

    @Subscribe
    private void onMinecraftShutdown(MinecraftShutdownEvent event) {
        logger.log("Minecraft Shutdown " + event.getBridge().getVersion());
    }

}
