package dev.lynith.oneeightnine;

import dev.lynith.Core.versions.IVersion;
import net.minecraft.client.Minecraft;

public class Version implements IVersion {

    @Override
    public String getVersion() {
        return "1.8.9";
    }

    @Override
    public String getPlayerName() {
        return Minecraft.getMinecraft().getSession().getUsername();
    }

    @Override
    public int getFps() {
        return Minecraft.getDebugFPS();
    }

}
