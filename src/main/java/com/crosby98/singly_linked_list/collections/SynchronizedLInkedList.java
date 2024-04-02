package com.crosby98.singly_linked_list.collections;

public interface SynchronizedLInkedList<T> {
    void push(T element);

    T pop();

    void insertAfter(T element, T after);

    int size();
}
