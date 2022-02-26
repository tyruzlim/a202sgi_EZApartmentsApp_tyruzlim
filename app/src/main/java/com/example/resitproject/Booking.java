package com.example.resitproject;

public class Booking {
    private long id;
    private String ownerName;
    private int paid;
    private int floorNumber, unitNumber,price;

    public Booking(){}

    public long getId() {return id;}
    public String getOwnerName() {return ownerName;}
    public int isPaid() {return paid;}
    public int getPrice() {return price;}
    public int getFloorNumber() {return floorNumber;}
    public int getUnitNumber() {return unitNumber;}

    public void setId(long id) {this.id = id;}
    public void setOwnerName(String ownerName) {this.ownerName = ownerName;}
    public void setPrice(int price) {this.price = price;}
    public void setPaid(int paid) {this.paid = paid;}
    public void setFloorNumber(int floorNumber) {this.floorNumber = floorNumber;}
    public void setUnitNumber(int unitNumber) {this.unitNumber = unitNumber;}
}