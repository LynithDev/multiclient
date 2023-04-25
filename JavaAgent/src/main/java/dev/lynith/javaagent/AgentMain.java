package dev.lynith.javaagent;

import dev.lynith.Core.ClientStartup;
import dev.lynith.Core.versions.IVersion;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Hello from premain !!!");
        try {
            Class<?> versionMain = Class.forName("dev.lynith.start.VersionMain");
            Method getVersion = versionMain.getMethod("getVersion" );

            Class<? extends IVersion> versionClass = (Class<? extends IVersion>) getVersion.invoke(null);
            IVersion version = versionClass.getConstructor().newInstance();

            ClientStartup.start(version);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Hello from agentmain");
    }
}
