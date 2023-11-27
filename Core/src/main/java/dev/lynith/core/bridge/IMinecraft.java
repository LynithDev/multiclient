package dev.lynith.core.bridge;

public interface IMinecraft {

    int getFps();

    String getGameVersion();

    boolean isFullscreen();
    void setFullscreen(boolean fullscreen);
    default void toggleFullscreen() {
        setFullscreen(!isFullscreen());
    }

    void scheduleStop();

}
