package org.suporma.gears;

import java.util.AbstractCollection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * This class is intended to track an ordering of distinct objects. An object cannot appear in the
 * ordering more than once. Most operations run in constant time.
 */
public class Ordering<T> extends AbstractCollection<T> implements Deque<T> {
    private class Node {
        final T val;
        Node prev, next;
        boolean alive;
        
        public Node(T val) {
            this.val = val;
            this.prev = null;
            this.next = null;
            this.alive = true;
        }
    }
    
    private final Map<T, Node> nodeMap;
    private final Node front, back;
    private int size;
    
    public Ordering() {
        this.nodeMap = new HashMap<>();
        this.front = new Node(null);
        this.back = new Node(null);
        this.size = 0;
        front.next = back;
        back.prev = front;
    }
    
    private void put(T val, Node precedent) {
        Node node = nodeMap.get(val);
        boolean isNewNode = node == null;
        if (isNewNode) {
            node = new Node(val);
        } else {
            if (node == precedent) return;
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
            ++size;
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
    
    public boolean rotate(T newFirstVal) {
        Node newFirst = nodeMap.get(newFirstVal);
        if (newFirst == null) return false;
        if (newFirst.prev == front) return false;
        Node oldFirst = front.next;
        Node oldLast = back.prev;
        Node newLast = newFirst.prev;
        front.next = newFirst;
        back.prev = newLast;
        oldFirst.prev = oldLast;
        oldLast.next = oldFirst;
        return true;
    }
    
    public boolean swap(T val1, T val2) {
        Node node1 = nodeMap.get(val1);
        Node node2 = nodeMap.get(val2);
        if (node1 == null || node2 == null || node1 == node2) return false;
        Node prev1 = node1.prev;
        Node prev2 = node2.prev;
        if (node2 == prev1) {
            // We need to do special stuff if the nodes are adjacent. If node2 is node1's prev,
            // we'll swap them and handle it in the next if-statement.
            Node temp = node1;
            node1 = node2;
            node2 = temp;
            temp = prev1;
            prev1 = prev2;
            prev2 = temp;
        }
        if (node1 == prev2) {
            // If the two nodes are adjacent, we need to be a little careful.
            Node next = node2.next;
            node1.prev = node2;
            node1.next = next;
            node2.prev = prev1;
            node2.next = node1;
            prev1.next = node2;
            next.prev = node1;
        } else {
            put(val1, prev2);
            put(val2, prev1);
        }
        return true;
    }

    @Override
    public boolean add(T e) {
        if (nodeMap.containsKey(e)) {
            return false;
        } else {
            addLast(e);
            return true;
        }
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
        T val = node.val;
        if (node.alive) {
            node.alive = false;
            Node prev = node.prev;
            Node next = node.next;
            prev.next = next;
            next.prev = prev;
            nodeMap.remove(val);
            --size;
        }
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
    public boolean offer(T e) { return offerLast(e); }

    @Override
    public T remove() { return removeFirst(); }

    @Override
    public T poll() { return pollFirst(); }

    @Override
    public T element() { return getFirst(); }

    @Override
    public T peek() { return peekFirst(); }

    @Override
    public void push(T e) { addFirst(e); }

    @Override
    public T pop() { return removeFirst(); }

    @Override
    public boolean remove(Object o) {
        Node node = nodeMap.get(o);
        if (node == null) {
            return false;
        } else {
            removeNode(node);
            return true;
        }
    }
    
    private class OrderingIterator implements Iterator<T> {
        private Node node;

        public OrderingIterator() {
            this.node = front;
        }

        @Override
        public boolean hasNext() {
            return node != null && node != back && node != back.prev;
        }

        @Override
        public T next() {
            node = node.next;
            return node.val;
        }
        
        @Override
        public void remove() {
            removeNode(node);
        }
    }

    @Override
    public Iterator<T> iterator() { return new OrderingIterator(); }

    private class OrderingDescendingIterator implements Iterator<T> {
        private Node node;

        public OrderingDescendingIterator() {
            this.node = back;
        }

        @Override
        public boolean hasNext() {
            return node != null && node != front && node != front.next;
        }

        @Override
        public T next() {
            node = node.prev;
            return node.val;
        }
        
        @Override
        public void remove() {
            removeNode(node);
        }
    }

    @Override
    public Iterator<T> descendingIterator() { return new OrderingDescendingIterator(); }

    @Override
    public int size() { return size; }

    @Override
    public boolean contains(Object o) {
        return nodeMap.containsKey(o);
    }
    
    @Override
    public void clear() {
        nodeMap.clear();
        front.next = back;
        back.prev = front;
        size = 0;
    }
}
