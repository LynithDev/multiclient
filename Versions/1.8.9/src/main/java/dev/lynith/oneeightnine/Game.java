package dev.lynith.oneeightnine;

import dev.lynith.Core.versions.IGame;
import net.minecraft.client.Minecraft;

public class Game implements IGame {
    @Override
    public int getFps() {
        return Minecraft.getDebugFPS();
    }
}
