package com.example.bbms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SingleDonorDetail extends AppCompatActivity {

    TextView tvName, tvEmail, tvPhone, tvAadhar, tvBloodGroup, tvAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_donor_detail);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAadhar = findViewById(R.id.tvAadhar);
        tvBloodGroup = findViewById(R.id.tvBloodGroup);
        tvAmount = findViewById(R.id.tvAmount);

        Intent a1 = getIntent();

        String name = a1.getStringExtra("name");
        String email = a1.getStringExtra("email");
        String phone = a1.getStringExtra("phone");
        String aadhar = a1.getStringExtra("aadhar");
        String bg = a1.getStringExtra("bg");
        String amount = a1.getStringExtra("amount");

        tvName.setText("Name : "+name);
        tvEmail.setText("Email : "+email);
        tvPhone.setText("Phone No. : " + phone);
        tvAadhar.setText("Aadhar : " + aadhar);
        tvBloodGroup.setText("BloodGroup : " + bg);
        tvAmount.setText("Amount : " + amount + " ml");


    }
}