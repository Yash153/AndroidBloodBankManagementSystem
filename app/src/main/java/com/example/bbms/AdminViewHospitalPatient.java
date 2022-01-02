package com.example.bbms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.media.MediaRouter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bbms.databinding.ActivityAdminViewHospitalPatientBinding;
import com.example.bbms.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminViewHospitalPatient extends AppCompatActivity {

    private @NonNull ActivityAdminViewHospitalPatientBinding binding;
    RecyclerView recyclerview;
    ArrayList<String> donors  = new ArrayList<>();
    String data;
    Adapter adapter;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
  //  String deleteData = "";
    
    //---------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_hospital_patient);
        recyclerview = findViewById(R.id.recyclerview);
        binding = ActivityAdminViewHospitalPatientBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));

//-----------------------------------------------------------------------------------------------------
        Intent a1 = getIntent();
        String h_username = a1.getStringExtra("h_username");

//        lvHospitalDonor = findViewById(R.id.);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(h_username);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donors.clear();
                for(DataSnapshot d : snapshot.getChildren()){
                    Donor don = d.getValue(Donor.class);
                    String name = don.getName();
                    donors.add(name);
                }

//-----------------------------------------------------------------------------------------------------------------
               adapter = new Adapter(donors);
               binding.recyclerview.setAdapter(adapter);
               adapter.notifyDataSetChanged();


                ItemTouchHelper.SimpleCallback simpleCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        String donor_name_to_view = donors.get(position);

                   //     Toast.makeText(AdminViewHospitalPatient.this, "" + donor_name_to_view, Toast.LENGTH_SHORT).show();


                        Intent a = new Intent(AdminViewHospitalPatient.this, AdminViewSwipe.class);
                        a.putExtra("hospital_name", h_username);
                        a.putExtra("patient_name", donor_name_to_view);
                        startActivity(a);

                        adapter = new Adapter(donors);
                        binding.recyclerview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                };
                ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                        return false;
                    }


                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                       String deleteData;
                        int position = viewHolder.getAdapterPosition();
                        deleteData = donors.get(position);
//
   // Delete from database------------------------------------------------------------------------
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                AlertDialog.Builder builder =new AlertDialog.Builder
                                        (AdminViewHospitalPatient.this);
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

//                                        alertDialog.dismiss();
                                        DatabaseReference d1 = FirebaseDatabase.
                                                getInstance().getReference(h_username).child(String.valueOf(deleteData));
                                        d1.removeValue();

                                        Toast.makeText(AdminViewHospitalPatient.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                });
                                adapter = new Adapter(donors);
                                binding.recyclerview.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                //alertDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                };
                new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recyclerview);
                new ItemTouchHelper(simpleCallback1).attachToRecyclerView(binding.recyclerview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}