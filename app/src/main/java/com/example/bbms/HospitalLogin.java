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

import java.util.ArrayList;

public class HospitalLogin extends AppCompatActivity {

    Button btnHospitalLogin, btnHospitalSignup;
    EditText etHospitalUsername, etHospitalPassword;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> h_username1 = new ArrayList<>();
    ArrayList<String> h_password = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);

        btnHospitalLogin = findViewById(R.id.btnHospitalLogin);
        btnHospitalSignup = findViewById(R.id.btnHospitalSignup);
        etHospitalPassword = findViewById(R.id.etHospitalPassword);
        etHospitalUsername = findViewById(R.id.etHospitalUsername);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("hospital");

        btnHospitalLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (etHospitalUsername.getText().toString().equals("") || etHospitalPassword.getText().toString().equals(""))
                    Toast.makeText(HospitalLogin.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                else {

                    String un = etHospitalUsername.getText().toString();
                    String pw = etHospitalPassword.getText().toString();

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            h_username1.clear();
                            h_password.clear();

                            for (DataSnapshot d : snapshot.getChildren()) {
                                Hospital hosp = d.getValue(Hospital.class);
                                String username = hosp.getUn();
                                String password = hosp.getPw();
                                h_username1.add(username);
                                h_password.add(password);
                            }

//                            Toast.makeText(HospitalLogin.this, "" + h_username1, Toast.LENGTH_SHORT).show();

//                            Toast.makeText(HospitalLogin.this, ""+ h_username1.contains((Object)(un)), Toast.LENGTH_SHORT).show();

                            if (h_username1.contains((Object)(un)))
                            {
                                int index = h_username1.indexOf(un);
                                if ((h_password.get(index)).equals(pw))
                                {
                                    Intent a = new Intent(HospitalLogin.this, HospitalLoggedInMenu.class);
                                    a.putExtra("username", etHospitalUsername.getText().toString());
                                    startActivity(a);
                                    etHospitalUsername.setText("");
                                    etHospitalPassword.setText("");
                                }
                                else
                                {
                                    etHospitalPassword.setError("Wrong password");
                                }
                            }
                            else
                            {
                                etHospitalUsername.setError("Username does not exists");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        btnHospitalSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(HospitalLogin.this, HospitalSignup.class);
                startActivity(a);
            }
        });
    }
}