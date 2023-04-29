package dev.lynith.javaagent.mixin;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

import java.util.HashMap;
import java.util.Map;

public class ClientMixinGlobalPropertyService implements IGlobalPropertyService {

    private final Map<IPropertyKey, Object> props = new HashMap<>();

    @Override
    public IPropertyKey resolveKey(String name) {
        return new Key(name);
    }

    @Override
    public <T> T getProperty(IPropertyKey key) {
        return (T) props.get(key);
    }

    @Override
    public void setProperty(IPropertyKey key, Object value) {
        props.put(key, value);
    }

    @Override
    public <T> T getProperty(IPropertyKey key, T defaultValue) {
        return (T) props.getOrDefault(key, defaultValue);
    }

    @Override
    public String getPropertyString(IPropertyKey key, String defaultValue) {
        return getProperty(key, defaultValue);
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    final class Key implements IPropertyKey {
        final String name;
    }
}
