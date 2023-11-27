package dev.lynith.core.utils;

import lombok.Getter;
import lombok.Setter;

public class Tuple<K, V> {

    @Getter
    private final K key;

    @Getter @Setter
    private V value;

    public Tuple(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
