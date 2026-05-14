package com.mycompany.flightsystem;

/**
 * Custom Queue implementation for managing Passenger objects
 * Uses a linked list-based approach for dynamic sizing
 * Implements all CRUD operations: Add (enqueue), Delete (dequeue), Update, Print, Search
 * 
 * @author Jana
 */
public class PassengerQueue {
    private PassengerQueueNode front;
    private PassengerQueueNode rear;
    private int size;
    private int maxSize;

    public PassengerQueue(int maxSize) {
        this.front = null;
        this.rear = null;
        this.size = 0;
        this.maxSize = Math.max(0, maxSize); // defensive
    }

    public boolean enqueue(passenger p) {
        if (isFull()) return false;
        PassengerQueueNode newNode = new PassengerQueueNode(p);
        if (rear == null) front = rear = newNode;
        else {
            rear.setNext(newNode);
            rear = newNode;
        }
        size++;
        return true;
    }

    public passenger dequeue() {
        if (isEmpty()) return null;
        passenger p = front.getData();
        front = front.getNext();
        if (front == null) rear = null;
        size--;
        return p;
    }

    /**
     * Search by passenger id and return the passenger object or null
     */
    public passenger search(String passengerId) {
        PassengerQueueNode cur = front;
        while (cur != null) {
            if (cur.getData().getId().equals(passengerId)) return cur.getData();
            cur = cur.getNext();
        }
        return null;
    }

    /**
     * Returns true if this passenger id exists in this queue
     */
    public boolean contains(String passengerId) {
        return search(passengerId) != null;
    }

    /**
     * Update passenger record (match by id)
     */
    public boolean update(String passengerId, passenger newData) {
        PassengerQueueNode cur = front;
        while (cur != null) {
            if (cur.getData().getId().equals(passengerId)) {
                cur.setData(newData);
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    /**
     * Remove passenger by id (returns true if removed)
     */
    public boolean remove(String id) {
        if (front == null) return false;
        if (front.getData().getId().equals(id)) {
            dequeue();
            return true;
        }
        PassengerQueueNode cur = front;
        while (cur.getNext() != null) {
            if (cur.getNext().getData().getId().equals(id)) {
                PassengerQueueNode toRemove = cur.getNext();
                cur.setNext(toRemove.getNext());
                if (toRemove == rear) rear = cur;
                size--;
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    /**
     * Human-friendly string listing of the queue (used for console and GUI)
     */
    public String printQueue() {
        if (isEmpty()) return "No passengers in queue.";
        StringBuilder sb = new StringBuilder();
        PassengerQueueNode cur = front;
        int pos = 1;
        while (cur != null) {
            sb.append("Position ").append(pos).append(": ").append(cur.getData().toString());
            if (cur.getNext() != null) sb.append("\n");
            cur = cur.getNext();
            pos++;
        }
        return sb.toString();
    }

    public boolean isEmpty() { return size == 0; }
    public boolean isFull() { return size >= maxSize; }
    public int getSize() { return size; }
}
