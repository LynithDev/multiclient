package dev.lynith.Core.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private static EventBus instance;

    public static EventBus getEventBus() {
        if (instance == null)
            instance = new EventBus();
        return instance;
    }

    private final List<Object> clazzes = new ArrayList<>();

    public void register(Object clazz) {
        if (!clazzes.contains(clazz))
            clazzes.add(clazz);
    }

    public void unregister(Object clazz) {
        clazzes.remove(clazz);
    }

    public void post(Event event) {
        for (Object clazz : clazzes) {
            for (Method method : clazz.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Subscribe.class)) {
                    if (method.getParameterTypes().length == 1) {
                        if (method.getParameterTypes()[0].equals(event.getClass())) {
                            try {
                                method.setAccessible(true);
                                method.invoke(clazz, event);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

}
