package com.mycompany.flightsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Jana
 */
public class addFlight {
    private FlightLinkedList flightList = new FlightLinkedList();

    public void addFlight(flight flight) {
        if (getFlightById(flight.getFlightId()) != null) {
            System.out.println("Flight with ID " + flight.getFlightId() + " already exists. Not adding.");
            return;
        }
        flightList.add(flight);
        System.out.println("Flight added successfully!");
    }

    public void displayAllFlights() {
        if (flightList.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        flight[] arr = flightList.toArray();
        for (flight f : arr) {
            if (f != null) f.displayInfo();
        }
    }

    public flight getFlightById(String id) { return flightList.search(id); }
    public flight[] getFlightList() { return flightList.toArray(); }
    public boolean deleteFlight(String flightId) { return flightList.delete(flightId); }
    public boolean updateFlight(String flightId, flight newFlight) { return flightList.update(flightId, newFlight); }
    public FlightLinkedList getFlightLinkedList() { return flightList; }
    public String printAllFlights() { return flightList.printList(); }

//    Object getFlights() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    boolean updateDepartureTime(String updateId, String time) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}

