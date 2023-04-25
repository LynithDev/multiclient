package dev.lynith.onenineteenfour;

import dev.lynith.Core.versions.IVersion;
import net.minecraft.client.Minecraft;

public class Version implements IVersion {
    @Override
    public String getVersion() {
        return "1.19.4";
    }

    @Override
    public String getPlayerName() {
        return Minecraft.getInstance().player.getName().getString();
    }

    @Override
    public int getFps() {
        return Minecraft.getInstance().getFps();
    }
}
