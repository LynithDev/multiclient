package dev.lynith.core.bridge.gui;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public interface IGui {

    GuiType getCurrentScreen();
    HashMap<Class<?>, GuiType> getScreenMap();

    boolean displayScreen(GuiType screen, Object... args);
    default boolean displayScreen(GuiType screen) {
        return displayScreen(screen, new Object[0]);
    }

    enum GuiType {
        MAIN_MENU,
        PAUSE_MENU,
        MULTIPLAYER_SELECTOR,
        SINGLEPLAYER_SELECTOR,
        INGAME,
        CUSTOM,
        UNKNOWN;

        @Getter @Setter
        private Class<?> clazz;

    }

}
