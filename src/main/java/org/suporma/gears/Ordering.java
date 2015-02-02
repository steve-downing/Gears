package org.suporma.gears;

import java.util.AbstractCollection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

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
    private final Node front, back;
    
    public Ordering() {
        this.nodeMap = new HashMap<>();
        this.front = new Node(null);
        this.back = new Node(null);
        front.next = back;
        back.prev = front;
    }
    
    private void put(T val, Node precedent) {
        Node node = nodeMap.get(val);
        boolean isNewNode = node == null;
        if (isNewNode) {
            node = new Node(val);
        } else {
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
        put(e, front);
    }

    @Override
    public void addLast(T e) {
        put(e, back.prev);
    }

    @Override
    public boolean offerFirst(T e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(T e) {
        addLast(e);
        return true;
    }
    
    private T removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        T val = node.val;
        nodeMap.remove(val);
        return val;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return removeNode(front.next);
    }

    @Override
    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return removeNode(back.prev);
    }

    @Override
    public T pollFirst() {
        if (isEmpty()) return null;
        return removeNode(front.next);
    }

    @Override
    public T pollLast() {
        if (isEmpty()) return null;
        return removeNode(back.prev);
    }

    @Override
    public T getFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return front.next.val;
    }

    @Override
    public T getLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return back.prev.val;
    }

    @Override
    public T peekFirst() {
        if (isEmpty()) return null;
        return front.next.val;
    }

    @Override
    public T peekLast() {
        if (isEmpty()) return null;
        return back.prev.val;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return remove(o);
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
