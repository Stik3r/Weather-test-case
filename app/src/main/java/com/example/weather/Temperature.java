package com.example.weather;

public class Temperature {
    private String date;
    private double temp;

    public Temperature(String date, double temp){
        setDate(date);
        setTemp(temp);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }
}
