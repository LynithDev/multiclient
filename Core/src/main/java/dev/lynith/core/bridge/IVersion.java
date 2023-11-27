package dev.lynith.core.bridge;

import dev.lynith.core.bridge.gui.IGui;

public interface IVersion {

    IMinecraft getMinecraft();

    IGui getRenderer();

}
