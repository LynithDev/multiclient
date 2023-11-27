package dev.lynith.core.events.impl;

import dev.lynith.core.events.EventCallback;

import java.util.List;

public interface MouseClick extends EventCallback {

    void invoke(int x, int y);

}
