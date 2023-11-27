package dev.lynith.core.plugins;

import dev.lynith.core.ClientStartup;
import dev.lynith.core.Logger;
import dev.lynith.core.bridge.IVersion;
import lombok.Getter;

import java.lang.instrument.Instrumentation;

@Getter
public abstract class Plugin {

    private PluginManifest manifest;
    protected Logger logger;

    public void onPreInit(Instrumentation inst) {}
    public abstract void onInit(IVersion bridge);

}
