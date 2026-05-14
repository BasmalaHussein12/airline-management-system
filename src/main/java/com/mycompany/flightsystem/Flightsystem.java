/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.flightsystem;

/**
 *
 * @author Jana
 */
import java.util.Scanner;

public class Flightsystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        login loginSystem = new login();
        loginSystem.Pass(scanner);

        addFlight flightManager = new addFlight();
        booking bookingSystem = new booking();

        System.out.println("Welcome to NOGOM Airport");

        // Add sample flights
        flightManager.addFlight(new flight("FL123", "New York", "Los Angeles", "10:00 AM", 5));
        flightManager.addFlight(new flight("FL456", "Paris", "Berlin", "2:00 PM", 3));

        while (true) {
            System.out.println("\n--- Flight System Menu ---");
            System.out.println("1. Add Flight");
            System.out.println("2. Book a Flight");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Flights");
            System.out.println("5. Update Flight Takeoff Time");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter Flight ID: ");
                    String flightId = scanner.nextLine();
                    System.out.print("Enter Destination: ");
                    String destination = scanner.nextLine();
                    System.out.print("Enter Departure: ");
                    String departure = scanner.nextLine();
                    System.out.print("Enter Take Off Time: ");
                    String takeOffTime = scanner.nextLine();
                    System.out.print("Enter Total Seats: ");
                    int totalSeats;
                    try {
                        totalSeats = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Seats must be a number.");
                        break;
                    }
                    flightManager.addFlight(new flight(flightId, destination, departure, takeOffTime, totalSeats));
                    break;

                case "2":
                    System.out.print("Enter Passenger Name: ");
                    String passengerName = scanner.nextLine();
                    System.out.print("Enter Passenger ID: ");
                    String passengerId = scanner.nextLine();
                    passenger p = new passenger(passengerName, passengerId);

                    System.out.print("Enter Flight ID to book: ");
                    String bookFlightId = scanner.nextLine();
                    flight bookFlight = flightManager.getFlightById(bookFlightId);
                    if (bookFlight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }
                    System.out.print("Enter Seat Type (vip/economy): ");
                    String seatType = scanner.nextLine();
                    System.out.print("Enter Payment Method: ");
                    String paymentMethod = scanner.nextLine();

                    bookingSystem.bookFlight(p, bookFlight, seatType, paymentMethod);
                    break;

                case "3":
                    System.out.print("Enter Passenger Name: ");
                    String cancelPassengerName = scanner.nextLine();
                    System.out.print("Enter Passenger ID: ");
                    String cancelPassengerId = scanner.nextLine();
                    passenger cancelPassenger = new passenger(cancelPassengerName, cancelPassengerId);

                    System.out.print("Enter Flight ID to cancel: ");
                    String cancelFlightId = scanner.nextLine();
                    flight cancelFlight = flightManager.getFlightById(cancelFlightId);
                    if (cancelFlight == null) {
                        System.out.println("Flight not found.");
                        break;
                    }

                    System.out.print("Enter Payment Method: ");
                    String cancelPaymentMethod = scanner.nextLine();

                    bookingSystem.cancelFlight(cancelPassenger, cancelFlight, cancelPaymentMethod);
                    break;

                case "4":
                    flightManager.displayAllFlights();
                    break;
                    
               

  case "5":
    System.out.print("Enter Flight ID to update: ");
    String updateId = scanner.nextLine();

    // get the flight from the REAL list
    flight f = flightManager.getFlightById(updateId);

    if (f == null) {
        System.out.println("Flight not found.");
        break;
    }

    System.out.print("Enter new departure time: ");
    String time = scanner.nextLine();

    boolean updated = flightManager.getFlightLinkedList()
                                   .updateDepartureTime(updateId, time);

    if (updated) {
        System.out.println("Departure time updated successfully!");
    } else {
        System.out.println("Failed to update departure time.");
    }

    break;

                case "6":
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
