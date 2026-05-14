package com.mycompany.flightsystem;

/**
 * Node class for FlightLinkedList
 * Represents a single node in the linked list containing a flight object
 * 
 * @author Jana
 */
public class FlightNode {
    private flight data;
    private FlightNode next;

    public FlightNode(flight data) {
        this.data = data;
        this.next = null;
    }

    public flight getData() { return data; }
    public void setData(flight data) { this.data = data; }

    public FlightNode getNext() { return next; }
    public void setNext(FlightNode next) { this.next = next; }
}

