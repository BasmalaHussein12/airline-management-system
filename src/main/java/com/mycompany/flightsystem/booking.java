/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flightsystem;

/**
 * Booking class handles flight booking and cancelling and ticket printing
 * Uses custom PassengerQueue to manage all passenger bookings
 * 
 * @author Jana
 */
public class booking {

    private int seatCounter = 1;
    private double seatprice;

    /**
     * Book a passenger on a specific flight (uses that flight's queue).
     * This method is strict: it requires a valid flight object passed in.
     */
    public void bookFlight(passenger passenger, flight flight, String seatType, String paymentMethod) {

        if (seatType == null) seatType = "economy";
        if (seatType.equalsIgnoreCase("vip")) seatprice = 300;
        else if (seatType.equalsIgnoreCase("economy")) seatprice = 150;
        else {
            System.out.println("Invalid seat type");
            return;
        }

        // Try to reserve seat first
        if (!flight.bookSeats()) {
            System.out.println("No seats available.");
            return;
        }

        PassengerQueue queue = flight.getPassengerQueue();

        if (!queue.enqueue(passenger)) {
            // if enqueue failed, rollback seat booking
            flight.cancelSeats();
            System.out.println("Queue full for this flight!");
            return;
        }

        System.out.println("Booking Successful! Total: $" + seatprice + " via " + paymentMethod);
        printTicket(passenger, flight, seatprice, seatCounter);
        seatCounter++;
    }

    /**
     * Option 1 (VERY STRICT): cancel requires passenger ID exists in the specific flight's queue.
     * The caller must provide the correct flight object.
     */
    public void cancelFlight(passenger passenger, flight flight, String paymentMethod) {
        if (passenger == null || flight == null) {
            System.out.println("Invalid passenger or flight.");
            return;
        }

        PassengerQueue queue = flight.getPassengerQueue();

        // Very strict: check by passenger id only (Option 1)
        if (!queue.contains(passenger.getId())) {
            System.out.println("Passenger not found in this flight.");
            return;
        }

        // Remove and restore seat
        boolean removed = queue.remove(passenger.getId());
        if (removed) {
            flight.cancelSeats();
            System.out.println("Booking canceled. Refunded via " + paymentMethod);
        } else {
            System.out.println("Unexpected error while removing passenger.");
        }
    }

    public void printTicket(passenger passenger, flight flight, double amountPaid, int seatNumber) {
        System.out.println("\n----- TICKET -----");
        System.out.println("Passenger: " + passenger.getName() + " (ID: " + passenger.getId() + ")");
        System.out.println("Flight: " + flight.getFlightId());
        System.out.println("From: " + flight.getDeparture() + " -> " + flight.getDestination());
        System.out.println("Take Off: " + flight.getTakeOffTime());
        System.out.println("Seat Number: " + seatNumber);
        System.out.println("Amount Paid: $" + amountPaid);
        System.out.println("-------------------\n");
    }
}


