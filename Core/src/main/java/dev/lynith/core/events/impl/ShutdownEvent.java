package dev.lynith.core.events.impl;

import dev.lynith.core.events.EventCallback;

public interface ShutdownEvent extends EventCallback {

    void invoke();

}
