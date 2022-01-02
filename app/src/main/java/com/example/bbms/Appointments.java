package com.example.bbms;

public class Appointments {
    private String name, email, hospital_name, blood_quantity, date, time_slot, blood_group;

    public Appointments() {
    }

    public Appointments(String name, String email, String hospital_name, String blood_quantity, String date, String time_slot, String blood_group) {
        this.name = name;
        this.email = email;
        this.hospital_name = hospital_name;
        this.blood_quantity = blood_quantity;
        this.date = date;
        this.time_slot = time_slot;
        this.blood_group = blood_group;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public String getBlood_quantity() {
        return blood_quantity;
    }

    public void setBlood_quantity(String blood_quantity) {
        this.blood_quantity = blood_quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    @Override
    public String toString() {
        return "Appointments{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", hopital_name='" + hospital_name + '\'' +
                ", blood_quantity='" + blood_quantity + '\'' +
                ", date='" + date + '\'' +
                ", time_slot='" + time_slot + '\'' +
                ", blood_group='" + blood_group + '\'' +
                '}';
    }
}
