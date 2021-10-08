package com.app.riyasewana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.riyasewana.model.Doctor;
import com.app.riyasewana.model.Spare;
import com.app.riyasewana.model.Users;
import com.app.riyasewana.model.Vehicle;
import com.app.riyasewana.viewholder.DoctorDetailsViewHolder;
import com.app.riyasewana.viewholder.UserDetailsViewHolder;
import com.app.riyasewana.viewholder.VehicleListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class VehicleListActivity extends AppCompatActivity {

    private TextView closeBtn;
    private SearchView searchSpare;

    private DatabaseReference vehicleRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);


        closeBtn = findViewById(R.id.sm_spare_parts_list_close_btn);
        searchSpare = findViewById(R.id.sm_spare_parts_list_search_view);

        vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        recyclerView = findViewById(R.id.sm_spare_parts_list_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        searchSpare.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchText(s);
                return false;
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VehicleListActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void searchText(String s) {
        FirebaseRecyclerOptions<Vehicle> options;

        if (s == null){
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(vehicleRef,Vehicle.class).build();
        }
        else {
            Query firebaseSearchQuery = vehicleRef.orderByChild("title").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(firebaseSearchQuery, Vehicle.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Vehicle, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Vehicle, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Vehicle vehicle) {
                doctorDetailsViewHolder.specialization.setText("Rs. " + vehicle.getPrice());
                doctorDetailsViewHolder.name.setText(vehicle.getName());
//                doctorDetailsViewHolder.date.setText(spare.getD);
                Picasso.get().load(vehicle.getImg1()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(VehicleListActivity.this, SpareDetailedActivity.class);
                        intent.putExtra("id",vehicle.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public DoctorDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                DoctorDetailsViewHolder holder = new DoctorDetailsViewHolder(view);
                return holder;
            }
        };

//        setting adaptor to the recyclerview
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Vehicle> options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(vehicleRef, Vehicle.class).build();

        FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder> adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleListViewHolder vehicleListViewHolder, int i, @NonNull final Vehicle vehicle) {
                vehicleListViewHolder.specialization.setText("Rs. " + vehicle.getPrice());
                vehicleListViewHolder.name.setText(vehicle.getName());
                Picasso.get().load(vehicle.getImg1()).into(vehicleListViewHolder.image);

                vehicleListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(VehicleListActivity.this, SpareDetailedActivity.class);
                        intent.putExtra("id", vehicle.getId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public VehicleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_details_row, parent, false);
                VehicleListViewHolder holder = new VehicleListViewHolder(view);
                return holder;
            }
        };

//        setting adaptor to the recyclerview
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}

