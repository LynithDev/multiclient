package dev.lynith.core.plugins;

import dev.lynith.core.bridge.IVersion;

import java.lang.instrument.Instrumentation;

public interface Plugin {

    default void onPreInit(Instrumentation inst) {}

    default void onInit(IVersion bridge) {}

}
