package dev.lynith.core.events;

import java.util.List;

public interface EventCallback {

    /*

    Every interface requires an invoke method
        void invoke(int x, int y);

     */

    default boolean allowed(Object... args) {
        return true;
    }

}
