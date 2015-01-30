package org.suporma.gears;

import java.util.AbstractCollection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is intended to track an ordering of objects. The expectation is that each object will
 * appear no more than once.
 */
public class Ordering<T> extends AbstractCollection<T> implements Deque<T> {
    private class Node {
        final T val;
        Node prev, next;
        
        public Node(T val) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }
    }
    
    private final Map<T, Node> nodeMap;
    private final Node first, last;
    
    public Ordering() {
        this.nodeMap = new HashMap<>();
        this.first = new Node(null);
        this.last = new Node(null);
        first.next = last;
        last.prev = first;
    }
    
    private void put(T val, Node precedent) {
        Node node = nodeMap.get(val);
        boolean isNewNode = false;
        if (node == null) {
            isNewNode = true;
            node = new Node(val);
        }
        if (node.prev != null) {
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
        }
        Node next = precedent.next;
        node.prev = precedent;
        node.next = next;
        precedent.next = node;
        next.prev = node;
        if (isNewNode) {
            nodeMap.put(val, node);
        }
    }
    
    public boolean putAfter(T val, T precedent) {
        Node precedentNode = nodeMap.get(precedent);
        if (precedentNode == null) return false;
        put(val, precedentNode);
        return true;
    }
    
    public boolean putBefore(T val, T follower) {
        Node followerNode = nodeMap.get(follower);
        if (followerNode == null) return false;
        put(val, followerNode.prev);
        return true;
    }
    
    public boolean swap(T val1, T val2) {
        // TODO
        return false;
    }

    @Override
    public void addFirst(T e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addLast(T e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean offerFirst(T e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean offerLast(T e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public T removeFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T removeLast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T pollFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T pollLast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T getFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T getLast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T peekFirst() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T peekLast() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean offer(T e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public T remove() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T poll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T element() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T peek() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void push(T e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public T pop() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Iterator<T> descendingIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
}
