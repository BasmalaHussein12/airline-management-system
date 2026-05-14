/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flightsystem;

/**
 *
 * @author Jana
 */
public class login {
    private final String password = "sim123";

    public void Pass(java.util.Scanner R) {
        System.out.println("Enter the password");
        String p = R.nextLine();
        if (p.equals(password)) {
            System.out.println("Login successful ");
        } else {
            System.out.println("Wrong password. Exiting program!");
            System.exit(0);
        }
    }
}

