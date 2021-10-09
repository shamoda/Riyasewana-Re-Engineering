package com.app.riyasewana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.riyasewana.model.Vehicle;
import com.app.riyasewana.viewholder.DoctorDetailsViewHolder;
import com.app.riyasewana.viewholder.UserDetailsViewHolder;
import com.app.riyasewana.viewholder.VehicleListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class VehicleListActivity extends AppCompatActivity {

    private TextView closeBtn;
    private SearchView searchSpare;
    private ChipGroup chipGroup;

    private DatabaseReference vehicleRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    String[] types = {"SUV","Jeep","BMW","Auto","Lorry","Allion","Hybrid","Pulsar","Hutch back"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);


        closeBtn = findViewById(R.id.ss_vehicles_list_close_btn);
        searchSpare = findViewById(R.id.ss_vehicle_list_search_view);
        chipGroup = findViewById(R.id.ss_options);
        vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        recyclerView = findViewById(R.id.ss_vehicle_list_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        for(int i=0; i < types.length ; i++){
            Chip chip = new Chip(this);
            ChipDrawable drawable =  ChipDrawable.createFromAttributes(this,null,0,R.style.Widget_MaterialComponents_Chip_Choice);

            chip.setChipDrawable(drawable);
            chip.setPadding(10,10,10,10);
            chip.setChipBackgroundColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
            chip.setTextColor(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.CardColor)));
            chip.setText(types[i]);
            chipGroup.addView(chip);
        }

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
            Query firebaseSearchQuery = vehicleRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(firebaseSearchQuery, Vehicle.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder> adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleListViewHolder vehicleListViewHolder, int i, @NonNull final Vehicle vehicle) {
                vehicleListViewHolder.specialization.setText("Rs. " + vehicle.getPrice());
                vehicleListViewHolder.name.setText(vehicle.getName());
//                doctorDetailsViewHolder.date.setText(spare.getD);
                Picasso.get().load(vehicle.getImg1()).into(vehicleListViewHolder.image);

                vehicleListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(VehicleListActivity.this, VehicleDetailedView.class);
                        intent.putExtra("id",vehicle.getId());
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
                        Intent intent = new Intent(VehicleListActivity.this, VehicleDetailedView.class);
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

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
