package com.revature.exceptions;

public class NoCategoriesFoundException extends Exception {
    public NoCategoriesFoundException () {
        super("No categories found.");
    }
}
