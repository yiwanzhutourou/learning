package com.youdu.stack;

public class LinkedStack<E> implements Stack<E> {

    private int size;

    private Node<E> head;

    public LinkedStack() {
        size = 0;
    }

    @Override
    public boolean push(E element) {
        if (head == null) {
            head = new Node<>(element, null);
        } else {
            head = new Node<>(element, head);
        }
        size++;
        return true;
    }

    @Override
    public E pop() {
        if (head == null) {
            return null;
        }
        E element = head.element;
        Node<E> next = head.next;
        head.next = null;
        head = next;
        size--;
        return element;
    }

    @Override
    public E peak() {
        if (head == null) {
            return null;
        }
        return head.element;
    }

    @Override
    public int size() {
        return size;
    }

    private static class Node<E> {

        private E element;

        private Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }
}
