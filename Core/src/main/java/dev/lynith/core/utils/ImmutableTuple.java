package dev.lynith.core.utils;

import lombok.Getter;

public class ImmutableTuple<K, V> {

    @Getter
    private final K key;

    @Getter
    private final V value;

    public ImmutableTuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
