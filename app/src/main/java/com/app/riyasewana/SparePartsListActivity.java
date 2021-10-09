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
import android.widget.SearchView;
import android.widget.TextView;

import com.app.riyasewana.model.Spare;
import com.app.riyasewana.viewholder.DoctorDetailsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class SparePartsListActivity extends AppCompatActivity {

    private TextView closeBtn;
//    private Button addNewCustomer;
    private SearchView searchSpare;

    private DatabaseReference spareRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_parts_list);

        closeBtn = findViewById(R.id.sm_spare_parts_list_close_btn);
        searchSpare = findViewById(R.id.sm_spare_parts_list_search_view);

        spareRef = FirebaseDatabase.getInstance().getReference().child("Spare");

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
                startActivity(new Intent(SparePartsListActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void searchText(String s) {
        FirebaseRecyclerOptions<Spare> options;

        if (s == null){
            options = new FirebaseRecyclerOptions.Builder<Spare>().setQuery(spareRef, Spare.class).build();
        }
        else {
            Query firebaseSearchQuery = spareRef.orderByChild("title").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Spare>().setQuery(firebaseSearchQuery, Spare.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Spare, DoctorDetailsViewHolder> adapter = new FirebaseRecyclerAdapter<Spare, DoctorDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorDetailsViewHolder doctorDetailsViewHolder, int i, @NonNull final Spare spare) {
                doctorDetailsViewHolder.specialization.setText("Rs. " + spare.getPrice());
                doctorDetailsViewHolder.name.setText(spare.getTitle());
//                doctorDetailsViewHolder.date.setText(spare.getD);
                Picasso.get().load(spare.getImg1()).into(doctorDetailsViewHolder.image);

                doctorDetailsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(SparePartsListActivity.this, SpareDetailedActivity.class);
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();
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
                        Intent intent = new Intent(SparePartsListActivity.this, SpareDetailedActivity.class);
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


}