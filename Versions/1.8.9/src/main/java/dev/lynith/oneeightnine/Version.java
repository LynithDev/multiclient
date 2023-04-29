package dev.lynith.oneeightnine;

import dev.lynith.Core.versions.IGame;
import dev.lynith.Core.versions.IVersion;
import net.minecraft.client.Minecraft;

public class Version implements IVersion {

    private final IGame game = new Game();

    @Override
    public String getVersion() {
        return "1.8.9";
    }

    @Override
    public IGame getGame() {
        return game;
    }

}
