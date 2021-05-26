package com.example.addressline.dao;

public class DetailedAddress {
    private String street;
    private String houseNumber;

    public DetailedAddress(String street, String houseNumber) {
        this.street = street.trim();
        this.houseNumber = houseNumber.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
