package com.revature.utils;

import java.util.Scanner;

abstract public class User {
    private static Scanner sc = new Scanner(System.in);
    static public String askUser(String question) {
        System.out.println(question);
        String userInput = sc.nextLine();
        return userInput;
    }
}
