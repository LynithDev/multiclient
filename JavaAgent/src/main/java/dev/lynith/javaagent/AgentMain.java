package dev.lynith.javaagent;

import dev.lynith.core.ClientStartup;
import dev.lynith.core.Logger;
import dev.lynith.core.bridge.IVersion;
import dev.lynith.core.bridge.IVersionMain;
import dev.lynith.javaagent.mixin.ClientMixinTransformer;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.lang.instrument.Instrumentation;
import java.net.URL;

public class AgentMain {

    private static final Logger logger = new Logger("agent");

    public static void main(String[] args) {
        System.out.println("This should be started as an agent. It is not intended to be run directly.");
        System.exit(1);
    }

    public static void premain(String args, Instrumentation inst) {
        init(args, inst, true);
    }

    public static void agentmain(String args, Instrumentation inst) {
        init(args, inst, false);
    }

    private static void init(String args, Instrumentation inst, boolean premain) {
        logger.log("Agent starting up");
        if (!premain) {
            logger.log("## WARNING ##: Agent started dynamically");
        }

        try(ClassWrapper ignored = new ClassWrapper(new URL[0])) {
            MixinBootstrap.init();
            MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);

            if (inst != null) {
                inst.addTransformer(new ClientMixinTransformer(), true);
            }

            ignored.findClass("org.spongepowered.asm.mixin.Mixins");
            Mixins.addConfiguration("client.mixins.json");

            Class<?> versionMain;

            try {
                versionMain = Class.forName("dev.lynith.start.VersionMain");
            } catch (Exception e) {
                logger.error("Invalid build");
                logger.error("dev.lynith.start.VersionMain was not found!");
                return;
            }

            IVersionMain versionMainInstance = (IVersionMain) versionMain.getConstructor().newInstance();
            Class<? extends IVersion> versionClass = versionMainInstance.getVersion();

            IVersion version = versionClass.getConstructor().newInstance();
            ClientStartup.launch(version, inst);

            logger.log("Hooked");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}