package dev.lynith.oneeightnine;

import dev.lynith.Core.versions.IVersion;
import net.minecraft.client.MinecraftClient;

public class Version implements IVersion {

    @Override
    public String getVersion() {
        return "1.8.9";
    }

    @Override
    public String getPlayerName() {
        return MinecraftClient.getInstance().player.getName().asUnformattedString();
    }

    @Override
    public int getFps() {
        return MinecraftClient.getCurrentFps();
    }

}
