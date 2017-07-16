package org.suporma.gears;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Counter<T> {
    private final Map<T, Integer> counts;
    
    public Counter() {
        this.counts = new ConcurrentHashMap<>();
    }
    
    public int add(T val) {
        return add(val, 1);
    }
    
    public int add(T val, int n) {
        return counts.merge(val, n, (key, oldCount) -> oldCount + n);
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((counts == null) ? 0 : counts.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Counter<?> other = (Counter<?>) obj;
        if (counts == null) {
            if (other.counts != null)
                return false;
        } else if (!counts.equals(other.counts))
            return false;
        return true;
    }
}
