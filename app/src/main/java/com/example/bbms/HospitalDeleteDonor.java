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

public class HospitalDeleteDonor extends AppCompatActivity {
    ListView lvDonorDelete;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<String> donors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_delete_donor);

        lvDonorDelete = findViewById(R.id.lvDonorDelete);

        Intent a1 = getIntent();
        final String username = a1.getStringExtra("username");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(username);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donors.clear();
                for (DataSnapshot d : snapshot.getChildren())
                {
                    Donor donor = d.getValue(Donor.class);
                    String name = donor.getName();
                    donors.add(name);
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter
                        (HospitalDeleteDonor.this, android.R.layout.simple_list_item_1, donors);

                lvDonorDelete.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvDonorDelete.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = donors.get(i);

                AlertDialog.Builder builder =new AlertDialog.Builder(HospitalDeleteDonor.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View v = layoutInflater.inflate(R.layout.del_layout, null);
                builder.setView(v);
                builder.setTitle("Do you want to delete?");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button btnDelete = v.findViewById(R.id.btnDelete);

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference d1 = FirebaseDatabase.getInstance().getReference(username).child(String.valueOf(name));
                        d1.removeValue();

                        Toast.makeText(HospitalDeleteDonor.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        Intent a = new Intent(HospitalDeleteDonor.this, HospitalLoggedInMenu.class);
                        startActivity(a);
                    }
                });

                return false;
            }
        });

    }
}