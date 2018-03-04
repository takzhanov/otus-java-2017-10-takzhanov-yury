package io.github.takzhanov.umbrella.hw11.cache;


public interface CacheEngine<K, V> extends CacheInfo {
    void put(K key, V value);

    V get(K key);

    void dispose();
}
