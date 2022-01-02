package com.example.bbms;

public class Donor {

    private String name, em, phone, aadhar, amount, bg;

    public Donor() {}

    public Donor (String name, String em, String phone, String aadhar, String amount, String bg){
        this.name = name;
        this.em = em;
        this.phone = phone;
        this.aadhar = aadhar;
        this.amount = amount;
        this.bg = bg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEm() {
        return em;
    }

    public void setEm(String em) {
        this.em = em;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String toString(){
        return "aadhar : "+aadhar;
    }
}
