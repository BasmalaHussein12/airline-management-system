package com.mycompany.flightsystem;

/**
 * Node class for PassengerQueue
 * Represents a single node in the queue containing a passenger object
 * 
 * @author Jana
 */
public class PassengerQueueNode {
    private passenger data;
    private PassengerQueueNode next;

    public PassengerQueueNode(passenger data) {
        this.data = data;
        this.next = null;
    }

    public passenger getData() { return data; }
    public void setData(passenger data) { this.data = data; }

    public PassengerQueueNode getNext() { return next; }
    public void setNext(PassengerQueueNode next) { this.next = next; }
}

