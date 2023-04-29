package dev.lynith.Core.events;

import dev.lynith.Core.ClientStartup;
import dev.lynith.Core.versions.IVersion;
import lombok.Getter;
import lombok.Setter;

public abstract class Event {

    @Getter
    private final boolean cancellable;

    @Getter @Setter
    private boolean cancelled = false;

    public Event() {
        this.cancellable = true;
    }

    public Event(boolean cancellable) {
        this.cancellable = cancellable;
    }

    // Helpers

    public void cancel() {
        this.cancelled = true;
    }

    public String getEventName() {
        return this.getClass().getSimpleName();
    }

    public IVersion getBridge() {
        return ClientStartup.getInstance().getBridge();
    }

}
