package dev.lynith.test;

import dev.lynith.core.bridge.IVersion;
import dev.lynith.core.plugins.Plugin;

import java.lang.instrument.Instrumentation;

public class TestPlugin extends Plugin {

    @Override
    public void onPreInit(Instrumentation inst) {
        logger.log("PREINIT TestPlugin {}", getManifest().getName());
    }

    @Override
    public void onInit(IVersion bridge) {
        logger.log("TestPlugin {}", getManifest().getName());


    }

}
