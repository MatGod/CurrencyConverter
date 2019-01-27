package com.example.currencyconverter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Market {

    private String currencyName;
    private String derivedCurrencyName;
    private double price;

    @PrimaryKey
    @NonNull
    private String id = UUID.randomUUID().toString();

    public Market(String currencyName, String derivedCurrencyName, double price){
        this.currencyName = currencyName;
        this.derivedCurrencyName = derivedCurrencyName;
        this.price = price;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getDerivedCurrencyName() {
        return derivedCurrencyName;
    }

    public double getPrice() {
        return price;
    }

    public String getId(){
        return id;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public void setDerivedCurrencyName(String derivedCurrencyName) {
        this.derivedCurrencyName = derivedCurrencyName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setId(String id) {
        this.id = id;
    }
}
