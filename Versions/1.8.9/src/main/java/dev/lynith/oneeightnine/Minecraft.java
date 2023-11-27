package dev.lynith.oneeightnine;

import dev.lynith.core.bridge.IMinecraft;
import net.minecraft.client.MinecraftClient;

public class Minecraft implements IMinecraft {

    @Override
    public int getFps() {
        return MinecraftClient.getCurrentFps();
    }

    @Override
    public String getGameVersion() {
        return "1.8.9";
    }

    @Override
    public boolean isFullscreen() {
        return MinecraftClient.getInstance().isFullscreen();
    }

    @Override
    public void setFullscreen(boolean fullscreen) {
        if (isFullscreen() && !fullscreen) {
            MinecraftClient.getInstance().toggleFullscreen();
        } else if (!isFullscreen() && fullscreen) {
            MinecraftClient.getInstance().toggleFullscreen();
        }
    }

    @Override
    public void scheduleStop() {
        MinecraftClient.getInstance().scheduleStop();
    }

}
