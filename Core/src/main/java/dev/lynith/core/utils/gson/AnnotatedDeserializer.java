package dev.lynith.core.utils.gson;

import com.google.gson.*;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

// https://stackoverflow.com/a/21634867
public class AnnotatedDeserializer<T> implements JsonDeserializer<T> {

    public T deserialize(JsonElement element, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        T clazz = new Gson().fromJson(element, type);

        for (Field field : clazz.getClass().getDeclaredFields()) {
            if (field.getAnnotation(JsonRequired.class) != null) {
                try {
                    field.setAccessible(true);
                    if (field.get(clazz) == null) {
                        throw new JsonParseException("Missing field '" + field.getName() + "' in JSON");
                    }
                } catch (IllegalArgumentException ex) {
                    throw new JsonParseException("Illegal argument: " + field.getName());
                } catch (IllegalAccessException ex) {
                    throw new JsonParseException("Illegal access: " + field.getName());
                }
            }
        }

        return clazz;
    }
}
