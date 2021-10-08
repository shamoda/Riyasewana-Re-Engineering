package com.app.riyasewana;
   import androidx.appcompat.app.AppCompatActivity;

   import android.content.Intent;
   import android.os.Bundle;
   import android.view.View;
   import android.widget.ImageView;
   import android.widget.TextView;

   import com.google.firebase.database.DataSnapshot;
   import com.google.firebase.database.DatabaseError;
   import com.google.firebase.database.DatabaseReference;
   import com.google.firebase.database.FirebaseDatabase;
   import com.google.firebase.database.ValueEventListener;
   import com.google.firebase.storage.FirebaseStorage;
   import com.google.firebase.storage.StorageReference;
   import com.squareup.picasso.Picasso;

public class VehicleDetailedView extends AppCompatActivity {

    private ImageView img;
    private TextView title;
    private TextView price;
    private TextView date;
    private TextView condition;
    private TextView additional;
    private TextView name;
    private TextView contact;
    private TextView location;
    private TextView close;

    private StorageReference vehicleImageRef;
    private DatabaseReference vehicleRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detailed_view);


        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        close = findViewById(R.id.ss_v_detailed_close_btn);
        img = findViewById(R.id.ss_vehicle_detailed_img);
        title = findViewById(R.id.ss_vehicle_detailed_title);
        price = findViewById(R.id.ss_vehicle_detailed_price);
        date = findViewById(R.id.ss_vehicle_detailed_date);
        additional = findViewById(R.id.ss_vehicle_detailed_additional);
        name = findViewById(R.id.ss_vehicle_detailed_title);
        contact = findViewById(R.id.ss_vehicle_detailed_contact_info);
        location = findViewById(R.id.ss_vehicle_detailed_location);

//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentUser.getPhone());
        vehicleImageRef = FirebaseStorage.getInstance().getReference().child("VehicleImages");
        vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicle").child(id);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VehicleDetailedView.this, HomeActivity.class));
                finish();
            }
        });

        vehicleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("img1").exists()){
                        String tmpImg1 = dataSnapshot.child("img1").getValue().toString();
                        String tmpName = dataSnapshot.child("name").getValue().toString();
                        String tmpContact = dataSnapshot.child("contact").getValue().toString();
                        String tmpAddress = dataSnapshot.child("location").getValue().toString();
                        String tmpPrice = dataSnapshot.child("price").getValue().toString();
                        String tmpYear = dataSnapshot.child("year").getValue().toString();
                        String tmpAdditional = dataSnapshot.child("more").getValue().toString();
                        String tmpId = dataSnapshot.child("id").getValue().toString();

                        Picasso.get().load(tmpImg1).into(img);
                        name.setText("Name: "+tmpName);
                        contact.setText("Contact: "+tmpContact);
                        location.setText("Location: "+tmpAddress);
                        price.setText("Rs."+tmpPrice);
                        additional.setText(tmpAdditional);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}