package com.example.bbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HospitalSignup extends AppCompatActivity {

    EditText etHospitalName, etHospitalUsername, etHospitalCity, etHospitalPassword1, etHospitalPassword2;
    Button btnHospitalSignup;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_signup);

        etHospitalName = findViewById(R.id.etHospitalName);
        etHospitalUsername = findViewById(R.id.etHospitalUsername);
        etHospitalCity = findViewById(R.id.etHospitalCity);
        etHospitalPassword1 = findViewById(R.id.etHospitalPassword1);
        etHospitalPassword2 = findViewById(R.id.etHospitalPassword2);
        btnHospitalSignup = findViewById(R.id.btnHospitalSignup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hospital");

        btnHospitalSignup.setOnClickListener(new View.OnClickListener() {
            String un = etHospitalUsername.getText().toString();
            @Override
            public void onClick(View view) {
                String un = etHospitalUsername.getText().toString();
                String name = etHospitalName.getText().toString();
                String city = etHospitalCity.getText().toString();
                String pw = etHospitalPassword1.getText().toString();
                if (etHospitalName.getText().toString().equals(""))
                    etHospitalName.setError("Cannot be blank");
                else if (etHospitalName.getText().toString().length() < 2)
                    etHospitalName.setError("Cannot be < 2");
                else if (etHospitalUsername.getText().toString().equals(""))
                    etHospitalUsername.setError("Cannot be blank");
                else if (etHospitalUsername.getText().toString().length() < 2)
                    etHospitalUsername.setError("Cannot be < 2");
                else if (etHospitalCity.getText().toString().equals(""))
                    etHospitalCity.setError("Cannot be blank");
                else if (etHospitalCity.getText().toString().length() < 2)
                    etHospitalCity.setError("Cannot be < 2");
                else if (etHospitalPassword1.getText().toString().equals(""))
                    etHospitalPassword1.setError("Cannot be blank");
                else if (etHospitalPassword2.getText().toString().equals(""))
                    etHospitalPassword2.setError("Cannot be blank");
                else if (etHospitalPassword1.getText().toString().equals(etHospitalPassword2.getText().toString()) == false)
                    etHospitalPassword2.setError("Password dont match");
                else {
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(String.valueOf(un))){
                                Toast.makeText(HospitalSignup.this, "Hospital already exists", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Hospital h = new Hospital(un, name, city, pw);
                                databaseReference.child(String.valueOf(un)).setValue(h);
                                Toast.makeText(HospitalSignup.this, "Hospital Added Successfully", Toast.LENGTH_SHORT).show();

                                BloodGroupDetails(un);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }

    private void BloodGroupDetails(String un) {
        FirebaseDatabase fbdb;
        DatabaseReference dbrf;

        String table_name = un + "_BloodGroupDetails";

        fbdb = FirebaseDatabase.getInstance();
        dbrf = fbdb.getReference(table_name);

        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                BloodGroupDetails bgd = new BloodGroupDetails("0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0", "0.0");
                dbrf.setValue(bgd);
                Toast.makeText(HospitalSignup.this, "BGD table created", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}