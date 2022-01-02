package com.example.bbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HospitalViewDonor extends AppCompatActivity {

    ListView lvDonor;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> donors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_view_donor);

        Intent a1 = getIntent();
        final String username = a1.getStringExtra("username");

        lvDonor = findViewById(R.id.lvDonor);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(username);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donors.clear();
                for(DataSnapshot d : snapshot.getChildren()){
                    Donor don = d.getValue(Donor.class);
                    String name = don.getName();
                    donors.add(name);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(HospitalViewDonor.this, android.R.layout.simple_list_item_1, donors);
                lvDonor.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvDonor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String NAME = donors.get(i);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()){
                            Donor don = d.getValue(Donor.class);
                            String name = don.getName();

                            if(name.equals(NAME)){
                                String n = don.getName();
                                String e = don.getEm();
                                String p = don.getPhone();
                                String bg = don.getBg();
                                String aa = don.getAadhar();
                                String am = don.getAmount();

                                Intent a  =new Intent(HospitalViewDonor.this, SingleDonorDetail.class);
                                a.putExtra("name", n);
                                a.putExtra("email", e);
                                a.putExtra("phone", p);
                                a.putExtra("bg", bg);
                                a.putExtra("aadhar", aa);
                                a.putExtra("amount", am);
                                startActivity(a);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                Intent a = new Intent(HospitalViewDonor.this, SingleDonorDetail.class);
//                startActivity(a);
            }
        });



    }
}