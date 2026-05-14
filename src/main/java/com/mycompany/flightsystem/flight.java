/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flightsystem;
//import java.util.List;
//import java.util.ArrayList;
public class flight {

    private String flightId;
    private String departure;
    private String destination;
    private String takeOffTime;

    private int totalSeats;
    private int bookedSeats;

    // Each flight has its own passenger queue
    private PassengerQueue passengerQueue;

    public flight(String flightId, String departure, String destination, String takeOffTime, int totalSeats) {
        this.flightId = flightId;
        this.departure = departure;
        this.destination = destination;
        this.takeOffTime = takeOffTime;
        this.totalSeats = Math.max(0, totalSeats);
        this.bookedSeats = 0;
        this.passengerQueue = new PassengerQueue(this.totalSeats);
    }

    public boolean bookSeats() {
        if (bookedSeats < totalSeats) {
            bookedSeats++;
            return true;
        }
        return false;
    }

    public boolean cancelSeats() {
        if (bookedSeats > 0) {
            bookedSeats--;
            return true;
        }
        return false;
    }

    public void setDepartureTime(String takeOffTime) {
    this.takeOffTime = takeOffTime;
    }
    
    public PassengerQueue getPassengerQueue() { return passengerQueue; }

    public String getFlightId() { return flightId; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public String getTakeOffTime() { return takeOffTime; }
    public int getTotalSeats() { return totalSeats; }
    public int getBookedSeats() { return bookedSeats; }

    /**
     * Available seats (useful for listing)
     */
    public int getAvailableSeats() {
        return totalSeats - bookedSeats;
    }

    public void displayInfo() {
        System.out.println("Flight ID: " + flightId);
        System.out.println("From " + departure + " To " + destination);
        System.out.println("Take Off: " + takeOffTime);
        System.out.println("Seats booked: " + bookedSeats + " / " + totalSeats);
        System.out.println("Passengers in queue: " + passengerQueue.printQueue());
        System.out.println("---------------------");
    }
}
