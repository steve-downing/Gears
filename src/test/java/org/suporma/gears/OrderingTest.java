package org.suporma.gears;

import junit.framework.TestCase;

public class OrderingTest extends TestCase {
    private Ordering<Integer> makeOrdering(int[] arr) {
        Ordering<Integer> ordering = new Ordering<>();
        for (int val : arr) {
            ordering.add(val);
        }
        return ordering;
    }
    
    public void testEmpty() {
        Ordering<Object> ordering = new Ordering<>();
        assertTrue(ordering.isEmpty());
    }
    
    public void testAdd() {
        Ordering<Integer> ordering = makeOrdering(new int[]{1, 2, 3});
        assertEquals(3, ordering.size());
    }
    
    public void testSwap() {
        Ordering<Integer> ordering = makeOrdering(new int[]{1, 2});
        ordering.swap(1, 2);
        assertEquals(makeOrdering(new int[]{2, 1}), ordering);
    }
    
    public void testRotate() {
        Ordering<Integer> ordering = makeOrdering(new int[]{1, 2, 3, 4});
        ordering.rotate(3);
        assertEquals(makeOrdering(new int[]{3, 4, 1, 2}), ordering);
    }
    
    public void testRemove() {
        Ordering<Integer> ordering = makeOrdering(new int[]{1, 2, 3});
        ordering.remove(3);
        assertEquals(makeOrdering(new int[]{1, 2}), ordering);
    }
}
