package dev.lynith.javaagent;

import dev.lynith.Core.ClientStartup;
import dev.lynith.Core.Logger;
import dev.lynith.Core.versions.IVersion;
import dev.lynith.Core.versions.IVersionMain;
import dev.lynith.javaagent.mixin.ClientMixinTransformer;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        Logger logger = new Logger("agent");

        try(ClassWrapper _wrapper = new ClassWrapper(new URL[0])) {
            MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
            Mixins.addConfiguration("client.mixins.json");

            inst.addTransformer(new ClientMixinTransformer(), true);

            logger.log("Hooked");
            try {
                Class<?> versionMain = Class.forName("%gradle.package%.start.VersionMain");
                IVersionMain versionMainInstance = (IVersionMain) versionMain.getConstructor().newInstance();

                Class<? extends IVersion> versionClass = versionMainInstance.getVersion();

                IVersion version = versionClass.getConstructor().newInstance();
                ClientStartup.start(version, inst);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
    }
}
