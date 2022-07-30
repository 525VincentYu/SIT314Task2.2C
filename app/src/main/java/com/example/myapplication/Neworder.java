package com.example.myapplication;

import android.app.Application;
import android.widget.CalendarView;
import android.widget.ImageView;

import java.io.Serializable;
//class for temporarily store order information

public class Neworder   {
    String Reciver;
    String username;
    String pickuptime;
    String pickuolocation;
    String goodtype;
    String weight;
    String width;
    String length;
    String Height;
    int vehicle;
    CalendarView calendarView;

    public Neworder() {

    }

    public String getReciver() {
        return Reciver;
    }

    public void setReciver(String reciver) {
        Reciver = reciver;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public String getPickuolocation() {
        return pickuolocation;
    }

    public void setPickuolocation(String pickuolocation) {
        this.pickuolocation = pickuolocation;
    }

    public String getGoodtype() {
        return goodtype;
    }

    public void setGoodtype(String goodtype) {
        this.goodtype = goodtype;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
}
