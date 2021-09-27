package ru.job4j.collection.list;

import java.util.*;

public class SimpleLinkedList<E> implements List<E> {

    private int size = 0;
    private Node<E> first;
    private Node<E> last;
    private int modCount;

    @Override
    public void add(E value) {
        Node<E> last = this.last;
        Node<E> newNode = new Node<E>(last, value, null);
        this.last = newNode;
        if (last == null) {
            first = newNode;
        } else {
            last.next = newNode;
        }
        size++;
        modCount++;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, size);
        int firstPosition = 0;
        Node<E> currentNode = first;
        while (firstPosition != index) {
            currentNode = currentNode.next;
            firstPosition++;
        }
        return currentNode.item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            int index;
            int expectedModCount = modCount;
            private Node<E> currentNode = first;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return index < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E value = currentNode.item;
                currentNode = currentNode.next;
                index++;
                return value;
            }
        };
    }

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
}


