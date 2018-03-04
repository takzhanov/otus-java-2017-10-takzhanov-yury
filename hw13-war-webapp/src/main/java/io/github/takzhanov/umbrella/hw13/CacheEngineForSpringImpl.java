package io.github.takzhanov.umbrella.hw13;

import io.github.takzhanov.umbrella.hw11.cache.CacheEngineImpl;
import org.springframework.stereotype.Component;

@Component
public class CacheEngineForSpringImpl extends CacheEngineImpl {
    public CacheEngineForSpringImpl() {
        super(7, 10000, 0, false);
    }
}
