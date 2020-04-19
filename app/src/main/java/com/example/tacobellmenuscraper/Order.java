package com.example.tacobellmenuscraper;
import java.util.Scanner;

public class Order {
    /** user's money amount **/
    public int mainAmount;

    public int firstQuestion() {
        Scanner q1 = new Scanner(System.in);  // Create a Scanner object
        System.out.println("How much money would you like to spend?");
        mainAmount = q1.nextInt();
        return mainAmount;
    }
    public int secondQuestion() {
        Scanner q2 = new Scanner(System.in);  // Create a Scanner object
        System.out.println("How many dollar menu items would you like?");
        int amount2 = q2.nextInt();
        if (amount2 > 11 || amount2 > mainAmount) {
            System.out.println("Please enter a valid amount");
        }
        return amount2;
    }
}
