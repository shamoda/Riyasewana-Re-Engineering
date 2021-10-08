package com.app.riyasewana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.riyasewana.model.Spare;
import com.app.riyasewana.model.Vehicle;
import com.app.riyasewana.viewholder.DoctorDetailsViewHolder;
import com.app.riyasewana.viewholder.VehicleListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class AdminDashboard extends AppCompatActivity {

    private TextView logoutBtn;
    private TextView vehicleSwitch;
    private TextView spareSwitch;
    private Button addSpare;
    private Button addVehicle;
    private CardView vCard;
    private CardView sCard;
    private DatabaseReference spareRef;
    private DatabaseReference vehicleRef;
    private RecyclerView spareRecyclerView;
    RecyclerView.LayoutManager spareLayoutManager;
    private RecyclerView vehicleRecyclerView;
    RecyclerView.LayoutManager vehicleLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        logoutBtn = findViewById(R.id.admin_dashboard_logout_btn);
        addSpare = findViewById(R.id.sm_add_spare);
        addVehicle = findViewById(R.id.sm_add_vehicle);
        vehicleSwitch = findViewById(R.id.sm_switch_vehicle);
        spareSwitch = findViewById(R.id.sm_switch_spares);
        vCard = findViewById(R.id.card5);
        sCard = findViewById(R.id.Card6);

        spareRef = FirebaseDatabase.getInstance().getReference().child("Spare");

        spareRecyclerView = findViewById(R.id.sm_admin_spare_parts_list_view);
        spareRecyclerView.setHasFixedSize(true);
        spareLayoutManager = new LinearLayoutManager(this);
        spareRecyclerView.setLayoutManager(spareLayoutManager);

        vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");

        vehicleRecyclerView = findViewById(R.id.sm_admin_vehicle_list_view);
        vehicleRecyclerView.setHasFixedSize(true);
        vehicleLayoutManager = new LinearLayoutManager(this);
        vehicleRecyclerView.setLayoutManager(vehicleLayoutManager);

        spareRecyclerView.setVisibility(View.INVISIBLE);

        Paper.init(this);

        logoutBtn = findViewById(R.id.admin_dashboard_logout_btn);

        addSpare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, AddSpareActivity.class));
            }
        });

        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashboard.this, AddVehicleActivity.class));
            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();

                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        vehicleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spareRecyclerView.setVisibility(View.INVISIBLE);
                vehicleRecyclerView.setVisibility(View.VISIBLE);
                vCard.setCardBackgroundColor(Color.GRAY);
                sCard.setCardBackgroundColor(Color.WHITE);
            }
        });

        spareSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spareRecyclerView.setVisibility(View.VISIBLE);
                vehicleRecyclerView.setVisibility(View.INVISIBLE);
                sCard.setCardBackgroundColor(Color.GRAY);
                vCard.setCardBackgroundColor(Color.WHITE);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Spare> options = new FirebaseRecyclerOptions.Builder<Spare>().setQuery(spareRef, Spare.class).build();

        FirebaseRecyclerAdapter<Spare, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Spare, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Spare spare) {
                doctorDetailsViewHolder.specialization.setText("Rs. " + spare.getPrice());
                doctorDetailsViewHolder.name.setText(spare.getTitle());
                Picasso.get().load(spare.getImg1()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashboard.this, SpareDetailedActivity.class);
                        intent.putExtra("id", spare.getId());
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
        spareRecyclerView.setAdapter(adapter);
        adapter.startListening();


        FirebaseRecyclerOptions<Vehicle> optionsV = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(vehicleRef, Vehicle.class).build();

        FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder> vehicleAdapters = new FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder>(optionsV) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleListViewHolder vehicleListViewHolder, int i, @NonNull final Vehicle vehicle) {
                vehicleListViewHolder.specialization.setText("Rs. " + vehicle.getPrice());
                vehicleListViewHolder.name.setText(vehicle.getName());
                Picasso.get().load(vehicle.getImg1()).into(vehicleListViewHolder.image);

                vehicleListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminDashboard.this, VehicleDetailedView.class);
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
        vehicleRecyclerView.setAdapter(vehicleAdapters);
       vehicleAdapters.startListening();

    }


}