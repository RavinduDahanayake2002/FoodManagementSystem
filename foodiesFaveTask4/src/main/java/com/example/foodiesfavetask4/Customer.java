package com.example.foodiesfavetask4;

public class Customer {
    private final String firstName;
    private final String secondName;
    private int noOfBurgers;

    public Customer(String firstName, String secondName, int noOfBurgers) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.noOfBurgers = noOfBurgers;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getNoOfBurgers() {
        return noOfBurgers;
    }

    public void setNoOfBurgers(int noOfBurgers) {
        this.noOfBurgers = noOfBurgers;
    }
}
