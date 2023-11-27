package dev.lynith.core.events;

import dev.lynith.core.Logger;

import java.lang.reflect.Method;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBus {

    private static final Logger logger = new Logger("eventbus");

    private final CopyOnWriteArrayList<StoredEvent> events = new CopyOnWriteArrayList<>();

    public <T extends EventCallback> void on(Class<T> event, T callback) {
        events.add(new StoredEvent(event, callback, false));
    }

    public <T extends EventCallback> void once(Class<T> event, T callback) {
        events.add(new StoredEvent(event, callback, true));
    }

    public <T extends EventCallback> void emit(Class<T> callbackClass, Object... args) {
        mainLoop:
        for (StoredEvent event : events) {
            try {
                Class<T> eventClass = (Class<T>) event.getEvent();
                T callback = (T) event.getCallback();

                if (eventClass.isAssignableFrom(callbackClass) && eventClass.getName().equals(callbackClass.getName())) {

                    for (Method method : callbackClass.getMethods()) {
                        if (method.getName().equals("invoke")) {
                            if (method.getParameterCount() == args.length) {
                                try {
                                    method.invoke(callback, args);
                                } catch (Exception e) {
                                    logger.error("Error while invoking event {}: {}", eventClass.getName());
                                    e.printStackTrace();
                                }
                                break;
                            }

                            logger.error("Invalid number of arguments for event " + eventClass.getName());
                            break;
                        }

                    }

                    if (event.isOnce()) {
                        events.remove(event);
                    }
                }
            } catch (Exception e) {
                logger.error("Error while emitting event {}", event.getEvent().getName());
                e.printStackTrace();
            }
        }
    }

    private static class StoredEvent {
        private final Class<? extends EventCallback> event;
        private final EventCallback callback;
        private final boolean once;

        public StoredEvent(Class<? extends EventCallback> event, EventCallback callback, boolean once) {
            this.event = event;
            this.callback = callback;
            this.once = once;
        }

        public Class<? extends EventCallback> getEvent() {
            return event;
        }

        public EventCallback getCallback() {
            return callback;
        }

        public boolean isOnce() {
            return once;
        }
    }

}
