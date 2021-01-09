package com.example.pos;

import java.util.ArrayList;

public class DataObj {
    long invoiceNo;
    String customerName;
    long date;
    ArrayList<String> item;
    ArrayList<Double> qty;
    ArrayList<Double> price;
    ArrayList<Double> bill;
    Double tBill;

    public DataObj(ArrayList<String> item, ArrayList<Double> price, ArrayList<Double> qty, ArrayList<Double> bill, Double tBill) {
        this.item=item;
        this.qty=qty;
        this.price=price;
        this.bill=bill;
        this.tBill=tBill;
    }

    public Double gettBill() {
        return tBill;
    }

    public void settBill(Double tBill) {
        this.tBill = tBill;
    }

    public ArrayList<Double> getBill() {
        return bill;
    }

    public void setBill(ArrayList<Double> bill) {
        this.bill = bill;
    }

    public ArrayList<Double> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Double> price) {
        this.price = price;
    }

    ArrayList<Double> total;
    //String fuelType;



    public ArrayList<String> getItem() {
        return item;
    }

    public void setItem(ArrayList<String> item) {
        this.item = item;
    }


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



    public ArrayList<Double> getQty() {
        return qty;
    }

    public void setQty(ArrayList<Double> qty) {
        this.qty = qty;
    }






}
