package org.suporma.gears;

import java.util.AbstractCollection;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class HashedLinkedDeque<T> extends AbstractCollection<T> implements Deque<T> {
    private final Map<T, EquivalenceList> nodeMap;
    private Node front, back;
    private int size = 0;

    private class Node {
        private Node prev, next;
        private EquivalenceNode equivalenceNode;
        private T val;
        
        public Node(T val) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }
    }

    private class EquivalenceNode {
        private final Node node;
        private EquivalenceNode prev, next;
        
        public EquivalenceNode(Node node) {
            this.node = node;
            this.prev = null;
            this.next = null;
        }
    }
    
    private class EquivalenceList {
        private EquivalenceNode front, back;
        private int size;
        
        public EquivalenceList() {
            front = new EquivalenceNode(null);
            back = new EquivalenceNode(null);
            front.next = back;
            back.prev = front;
            size = 0;
        }
        
        public void shift(Node node) {
            EquivalenceNode simpleNode = new EquivalenceNode(node);
            simpleNode.next = front.next;
            simpleNode.prev = front;
            front.next = simpleNode;
            simpleNode.next.prev = simpleNode;
            ++size;
        }
        
        public Node unshift() {
            EquivalenceNode node = front.next;
            EquivalenceNode next = node.next;
            front.next = next;
            next.prev = front;
            --size;
            return node.node;
        }
        
        public void push(Node node) {
            EquivalenceNode simpleNode = new EquivalenceNode(node);
            simpleNode.next = back;
            simpleNode.prev = back.prev;
            back.prev = simpleNode;
            simpleNode.prev.next = simpleNode;
            ++size;
        }
        
        public Node pop() {
            EquivalenceNode node = back.prev;
            EquivalenceNode prev = node.prev;
            back.prev = prev;
            prev.next = back;
            --size;
            return node.node;
        }
        
        public Node remove(Node node) {
            EquivalenceNode equivalenceNode = node.equivalenceNode;
            EquivalenceNode prev = equivalenceNode.prev;
            EquivalenceNode next = equivalenceNode.next;
            prev.next = next;
            next.prev = prev;
            --size;
            return equivalenceNode.node;
        }
        
        public int size() { return size; }
    }
    
    public HashedLinkedDeque() {
        nodeMap = new HashMap<>();
        front = new Node(null);
        back = new Node(null);
        front.next = back;
        back.prev = front;
        size = 0;
    }
    
    public int count(T val) {
        return nodeMap.get(val).size();
    }
    
    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        nodeMap.get(node.val).remove(node);
    }
    
    // TODO: Override a ton of the default method impls.

    public boolean removeIf(Predicate<? super T> filter) {
        boolean elementsRemoved = false;
        Node node = front.next;
        while (node != back) {
            if (filter.test(node.val)) {
                removeNode(node);
                --size;
                elementsRemoved = true;
            }
            node = node.next;
        }
        return elementsRemoved;
    }

    public int size() { return size; }
    
    private class HashedLinkedIterator implements Iterator<T> {
        private Node node;
        private int index;
        
        public HashedLinkedIterator() {
            node = front;
            index = -1;
        }

        public boolean hasNext() { return node.next != back; }

        public T next() {
            T val = node.val;
            node = node.next;
            ++index;
            return val;
        }

        public boolean hasPrevious() {
            return node != front && node != front.next;
        }

        public T previous() {
            node = node.prev;
            return node.val;
        }

        public int nextIndex() { return index + 1; }
        public int previousIndex() { return index - 1; }

        public void remove() {
            removeNode(node);
        }

        public void set(T e) {
            node.val = e;
        }
    }

    public void addFirst(T e) {
        Node node = new Node(e);
        node.prev = front;
        node.next = front.next;
        front.next = node;
        node.next.prev = node;
        EquivalenceList list = nodeMap.computeIfAbsent(e, (val) -> new EquivalenceList());
        list.shift(node);
    }

    public void addLast(T e) {
        Node node = new Node(e);
        node.next = back;
        node.prev = back.prev;
        back.prev = node;
        node.prev.next = node;
        EquivalenceList list = nodeMap.computeIfAbsent(e, (val) -> new EquivalenceList());
        list.push(node);
    }

    public boolean offerFirst(T e) {
        addFirst(e);
        return true;
    }
    
    public boolean offerLast(T e) {
        addLast(e);
        return true;
    }
    
    public T removeFirst() {
        if (size > 0) {
            return pollFirst();
        } else {
            throw new NoSuchElementException();
        }
    }

    public T removeLast() {
        if (size > 0) {
            return pollLast();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public T pollFirst() {
        if (size > 0) {
            Node node = front.next;
            removeNode(node);
            return node.val;
        } else {
            return null;
        }
    }

    @Override
    public T pollLast() {
        if (size > 0) {
            Node node = front.next;
            removeNode(node);
            return node.val;
        } else {
            return null;
        }
    }

    @Override
    public T getFirst() {
        if (size > 0) {
            return front.next.val;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public T getLast() {
        if (size > 0) {
            return back.prev.val;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public T peekFirst() {
        if (size > 0) {
            return front.next.val;
        } else {
            return null;
        }
    }

    @Override
    public T peekLast() {
        if (size > 0) {
            return back.prev.val;
        } else {
            return null;
        }
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
    public Iterator<T> descendingIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator<T> iterator() { return new HashedLinkedIterator(); }
}
