package io.github.takzhanov.umbrella.hw11.cache;


import java.lang.ref.SoftReference;

@SuppressWarnings("WeakerAccess")
class CacheElement<V> {
    private final SoftReference<V> value;
    private final long creationTime;
    private long lastAccessTime;


    public CacheElement(V value) {
        this.value = new SoftReference<>(value);
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    private long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public V getValue() {
        return value.get();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void touch() {
        this.lastAccessTime = getCurrentTime();
    }
}
