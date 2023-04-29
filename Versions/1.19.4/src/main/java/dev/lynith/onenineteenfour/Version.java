package dev.lynith.onenineteenfour;

import dev.lynith.Core.versions.IGame;
import dev.lynith.Core.versions.IVersion;
import net.minecraft.client.Minecraft;

public class Version implements IVersion {
    @Override
    public String getVersion() {
        return "1.19.4";
    }

    private final IGame game = new Game();

    @Override
    public IGame getGame() {
        return game;
    }
}
