package dev.lynith.javaagent;

import dev.lynith.Core.ClientStartup;
import dev.lynith.Core.Logger;
import dev.lynith.Core.versions.IVersion;
import dev.lynith.Core.versions.IVersionMain;
import dev.lynith.javaagent.mixin.ClientMixinTransformer;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        Logger logger = new Logger("agent");

        MixinBootstrap.init();
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        Mixins.addConfiguration("client.mixins.json");

        inst.addTransformer(new ClientMixinTransformer(), true);

        logger.log("Hooked");
        try {
            Class<?> versionMain = Class.forName("dev.lynith.start.VersionMain");
            IVersionMain versionMainInstance = (IVersionMain) versionMain.getConstructor().newInstance();

            Class<? extends IVersion> versionClass = versionMainInstance.getVersion();

            IVersion version = versionClass.getConstructor().newInstance();
            ClientStartup.start(version, inst);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("AgentMain.agentmain");
    }
}
