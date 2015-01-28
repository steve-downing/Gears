package org.suporma.gears;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Counter<T> {
    private final Map<T, Integer> counts;
    
    public Counter() {
        this.counts = new ConcurrentHashMap<>();
    }
    
    public int add(T val) {
        return counts.merge(val, 1, (key, oldCount) -> oldCount + 1);
    }
    
    public int remove(T val) {
        return counts.merge(val, 0, (key, oldCount) -> {
            int newCount = oldCount - 1;
            if (newCount < 1) return null;
            return newCount;
        });
    }
    
    public int getCount(T val) {
        return counts.getOrDefault(val, 0);
    }
}
