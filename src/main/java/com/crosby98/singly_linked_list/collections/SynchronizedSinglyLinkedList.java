package com.crosby98.singly_linked_list.collections;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of a thread-safe singly linked list.
 *
 * @param <T> the type of elements in the list
 */
public class SynchronizedSinglyLinkedList<T> implements SynchronizedLInkedList<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Add an element to the end of the list.
     *
     * @param data the data to be added
     */
    @Override
    public void push(T data) {
        writeLock.lock();
        try {
            Node<T> newNode = new Node<>(data);
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Remove and return the last element from the list.
     *
     * @return the removed element, or null if the list is empty
     */
    @Override
    public T pop() {
        writeLock.lock();
        try {
            if (head == null) {
                return null;
            }

            if (head.next == null) {
                T data = head.data;
                head = null;
                tail = null;
                return data;
            }

            Node<T> current = head;
            while (current.next.next != null) {
                current = current.next;
            }

            T data = current.next.data;
            current.next = null;
            tail = current;
            return data;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Insert an element after a specified element in the list.
     *
     * @param data  the data to be inserted
     * @param after the element after which to insert
     */
    @Override
    public void insertAfter(T data, T after) {
        writeLock.lock();
        try {
            Node<T> newNode = new Node<>(data);
            Node<T> current = head;

            while (current != null && !current.data.equals(after)) {
                current = current.next;
            }

            if (current != null) {
                newNode.next = current.next;
                current.next = newNode;
                if (newNode.next == null) {
                    tail = newNode;
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Get the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        readLock.lock();
        try {
            int count = 0;
            Node<T> current = head;
            while (current != null) {
                count++;
                current = current.next;
            }
            return count;
        } finally {
            readLock.unlock();
        }
    }
}
