package dev.lynith.onetwentytwo;

import dev.lynith.core.bridge.IMinecraft;
import dev.lynith.core.bridge.IVersion;
import dev.lynith.core.bridge.gui.IGui;
import dev.lynith.onetwentytwo.gui.Gui;
import lombok.Getter;

@Getter
public class Version implements IVersion {

    private final IMinecraft minecraft = new Minecraft();

    private final IGui renderer = new Gui();

}
