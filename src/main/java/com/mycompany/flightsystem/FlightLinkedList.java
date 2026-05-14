package com.mycompany.flightsystem;

/**
 * Custom LinkedList implementation for managing Flight objects
 * Implements all CRUD operations: Add, Update, Delete, Print, Search
 * 
 * @author Jana
 */
public class FlightLinkedList {
    private FlightNode head;
    private int size;

    public FlightLinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(flight f) {
        FlightNode newNode = new FlightNode(f);
        if (head == null) head = newNode;
        else {
            FlightNode cur = head;
            while (cur.getNext() != null) cur = cur.getNext();
            cur.setNext(newNode);
        }
        size++;
    }

    public boolean delete(String flightID) {
        if (head == null) return false;
        if (head.getData().getFlightId().equals(flightID)) {
            head = head.getNext();
            size--;
            return true;
        }
        FlightNode cur = head;
        while (cur.getNext() != null) {
            if (cur.getNext().getData().getFlightId().equals(flightID)) {
                cur.setNext(cur.getNext().getNext());
                size--;
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }

    public flight search(String flightID) {
        FlightNode cur = head;
        while (cur != null) {
            if (cur.getData().getFlightId().equals(flightID)) return cur.getData();
            cur = cur.getNext();
        }
        return null;
    }

    public boolean update(String flightID, flight newData) {
        FlightNode cur = head;
        while (cur != null) {
            if (cur.getData().getFlightId().equals(flightID)) {
                cur.setData(newData);
                return true;
            }
            cur = cur.getNext();
        }
        return false;
    }
    
    public boolean updateDepartureTime(String flightID, String newTime) {
    FlightNode cur = head;

    while (cur != null) {
        if (cur.getData().getFlightId().equals(flightID)) {
            cur.getData().setDepartureTime(newTime);
            return true;
        }
        cur = cur.getNext();
    }

    return false;
}


    public String printList() {
        if (head == null) return "No flights available.";
        StringBuilder sb = new StringBuilder();
        FlightNode cur = head;
        while (cur != null) {
            flight f = cur.getData();
            sb.append("Flight ID: ").append(f.getFlightId())
              .append(" | From: ").append(f.getDeparture())
              .append(" | To: ").append(f.getDestination())
              .append(" | Take Off: ").append(f.getTakeOffTime())
              .append(" | Available Seats: ").append(f.getAvailableSeats());
            if (cur.getNext() != null) sb.append("\n");
            cur = cur.getNext();
        }
        return sb.toString();
    }

    public boolean isEmpty() { return head == null; }
    public int getSize() { return size; }

    public flight getFlightAt(int index) {
        if (index < 0 || index >= size) return null;
        FlightNode cur = head;
        for (int i = 0; i < index; i++) cur = cur.getNext();
        return cur.getData();
    }

    public flight[] toArray() {
        flight[] arr = new flight[size];
        FlightNode cur = head;
        int i = 0;
        while (cur != null) {
            arr[i++] = cur.getData();
            cur = cur.getNext();
        }
        return arr;
    }
}
