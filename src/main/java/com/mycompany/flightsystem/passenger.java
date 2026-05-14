/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flightsystem;

/**
 *
 * @author Jana
 */
public class passenger extends person {

    public passenger(String name, String id) {
        super(name, id);
    }

    @Override
    public void displayInfo() {
        System.out.println("Passenger Name: " + getName() + ", ID: " + getId());
    }

    @Override
    public String toString() {
        return getName() + " (ID: " + getId() + ")";
    }
}

