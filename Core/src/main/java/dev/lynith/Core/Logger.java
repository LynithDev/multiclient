package dev.lynith.Core;

public class Logger {

    private final String name;

    public Logger(String name) {
        this.name = name;
    }

    public Logger(String... name) {
        this(String.join(".", name));
    }

    public Logger(Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    public Logger(Object obj) {
        this(obj.getClass());
    }

    public Logger() {
        this("unknown");
    }

    public void log(String message) {
        System.out.println(getString(message));
    }

    public void log(String message, Object... args) {
        System.out.println(String.format(getString(message), args));
    }

    public void error(String message) {
        System.err.println(getString(message));
    }

    public void error(String message, Object... args) {
        System.err.println(String.format(getString(message), args));
    }

    private String getString(String message) {
        return "[" + name + "] " + message;
    }

}
