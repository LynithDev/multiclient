package dev.lynith.core;

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

    public void log(Object message) {
        System.out.println(getString(message.toString()));
    }

    public void log(Object message, Object... args) {
        System.out.println(getString(format(message.toString(), args)));
    }

    public void error(Object message) {
        System.err.println(getString(message.toString()));
    }

    public void error(Object message, Object... args) {
        System.err.println(getString(format(message.toString(), args)));
    }

    private String format(String message, Object... args) {
        for (Object arg : args) {
            message = message.replaceFirst("\\{\\}", arg.toString());
        }
        return message;
    }

    private String getString(String message) {
        return "[" + name + "] " + message;
    }

}
