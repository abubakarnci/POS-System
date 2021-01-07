package com.example.pos;

import java.util.ArrayList;

public class DataObj {
    long invoiceNo;
    String customerName;
    long date;
    ArrayList<String> item;
    ArrayList<Double> qty;
    ArrayList<Double> price;
    ArrayList<Double> total;
    //String fuelType;
    Double test;

    public Double getTest() {
        return test;
    }

    public void setTest(Double test) {
        this.test = test;
    }

    //Double price;
    double amount;

    public ArrayList<Double> getTotal() {
        return total;
    }

    public void setTotal(ArrayList<Double> total) {
        this.total = total;
    }

    public DataObj() {
    }

    public long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ArrayList<String> getItem() {
        return item;
    }

    public void setItem(ArrayList<String> item) {
        this.item = item;
    }

    public ArrayList<Double> getQty() {
        return qty;
    }

    public void setQty(ArrayList<Double> qty) {
        this.qty = qty;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Double> price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



}
