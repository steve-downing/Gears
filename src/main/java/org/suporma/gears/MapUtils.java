package org.suporma.gears;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapUtils {
    public static <T, U> Map<U, T> mapByUnique(Collection<T> objects, Function<T, U> mapFunction) {
        Map<U, T> map = new HashMap<>(objects.size());
        for (T object : objects) {
            map.put(mapFunction.apply(object), object);
        }
        return map;
    }
}
