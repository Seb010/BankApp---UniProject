import java.util.*;

class MyQueue<T> {
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node head, tail;
    private int size;
    private final int capacity;

    // Only stores a total capacity of 4 transactions
    public MyQueue(int capacity) {
        this.capacity = capacity;
    }

    // add element to end of queue
    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (size == capacity) {
            dequeue(); // Remove the oldest element if capacity is reached
        }
        if (tail != null) {
            tail.next = newNode;
        }
        tail = newNode;
        if (head == null) {
            head = tail;
        }
        size++;
    }

    // remove element from start of queue
    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }


    //check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // converts queue to a list
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        Node current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    //returns the size of the queue
    public int size() {
        return size;
    }
}
