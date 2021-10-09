package com.app.riyasewana;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.riyasewana.model.Vehicle;
import com.app.riyasewana.prevalent.Prevalent;
import com.app.riyasewana.viewholder.VehicleListViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private SearchView searchDoctor;

    private DatabaseReference vehicleRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setNavigationViewListener();

        searchDoctor = findViewById(R.id.sm_home_search_view);

        Paper.init(this);

       vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");


        searchDoctor.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("RiyaSewana");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);


        recyclerView = findViewById(R.id.sm_home_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


    }



    private void searchText(String s) {
        FirebaseRecyclerOptions<Vehicle> options;

        if (s == null){
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(vehicleRef,Vehicle.class).build();
        }
        else {
            Query firebaseSearchQuery = vehicleRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(firebaseSearchQuery,Vehicle.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder> adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleListViewHolder vehicleListViewHolder, int i, @NonNull final Vehicle vehicle) {
                vehicleListViewHolder.specialization.setText(vehicle.getPrice());
                vehicleListViewHolder.name.setText(vehicle.getName());
                Picasso.get().load(vehicle.getImg1()).into(vehicleListViewHolder.image);

                vehicleListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this, vehicle.getName(), Toast.LENGTH_SHORT).show();
                        //Add intent for session list
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



    private void filterDoctor(String s) {
        FirebaseRecyclerOptions<Vehicle> options;

        if (s.equals("All")){
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(vehicleRef,Vehicle.class).build();
        }
        else {
            Query firebaseSearchQuery = vehicleRef.orderByChild("name").startAt(s).endAt(s + "\uf8ff");
            options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(firebaseSearchQuery,Vehicle.class).setLifecycleOwner(this).build();
        }


        FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder> adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleListViewHolder vehicleListViewHolder, int i, @NonNull final Vehicle vehicle) {
                vehicleListViewHolder.specialization.setText("Rs "+vehicle.getPrice());
               vehicleListViewHolder.name.setText(vehicle.getName());
                Picasso.get().load(vehicle.getImg1()).into(vehicleListViewHolder.image);

              vehicleListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this, vehicle.getName(), Toast.LENGTH_SHORT).show();
                        //Add intent for session list
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


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Vehicle> options = new FirebaseRecyclerOptions.Builder<Vehicle>().setQuery(vehicleRef, Vehicle.class).build();

        FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder> adapter = new FirebaseRecyclerAdapter<Vehicle, VehicleListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull VehicleListViewHolder vehicleListViewHolder, int i, @NonNull final Vehicle vehicle) {
              vehicleListViewHolder.specialization.setText("Rs. "+vehicle.getPrice());
               vehicleListViewHolder.name.setText(vehicle.getName());
                Picasso.get().load(vehicle.getImg1()).into(vehicleListViewHolder.image);

                vehicleListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, VehicleDetailedView.class);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.sm_side_drawer_user_name);
        CircleImageView userProfileImage = headerView.findViewById(R.id.sm_side_drawer_user_profile_img);

        if (Prevalent.currentUser != null) {
            userNameTextView.setText(Prevalent.currentUser.getName());
            Picasso.get().load(Prevalent.currentUser.getProfileImage()).placeholder(R.drawable.ic_user).into(userProfileImage);
        }


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.sm_home){

        }
        else  if(id == R.id.sm_my_account){
            startActivity(new Intent(HomeActivity.this, UpdateUserAccountActivity.class));
        }
        else if(id == R.id.sm_my_sales){
            startActivity(new Intent(HomeActivity.this, AdminDashboard.class));
        }
        else if(id == R.id.sm_vehicles){
            startActivity(new Intent(HomeActivity.this, VehicleListActivity.class));
        }
        else if (id == R.id.sm_spare_parts){
            startActivity(new Intent(HomeActivity.this, SparePartsListActivity.class));
        }
//        else if (id == R.id.sm_donations){
//            startActivity(new Intent(HomeActivity.this, DonationsActivity.class));
//        }
//        else if (id == R.id.sm_nav_logout){
//            Paper.book().destroy();
//
//            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}