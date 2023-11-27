package dev.lynith.onetwentytwo;

import dev.lynith.core.bridge.IMinecraft;
import net.minecraft.client.MinecraftClient;

public class Minecraft implements IMinecraft {

    @Override
    public int getFps() {
        return MinecraftClient.getInstance().getCurrentFps();
    }

    @Override
    public String getGameVersion() {
        return "1.20.2";
    }

    @Override
    public boolean isFullscreen() {
        return MinecraftClient.getInstance().getWindow().isFullscreen();
    }

    @Override
    public void setFullscreen(boolean fullscreen) {
        if (isFullscreen() && !fullscreen) {
            MinecraftClient.getInstance().getWindow().toggleFullscreen();
        } else if (!isFullscreen() && fullscreen) {
            MinecraftClient.getInstance().getWindow().toggleFullscreen();
        }
    }

    @Override
    public void scheduleStop() {
        MinecraftClient.getInstance().scheduleStop();
    }

}
