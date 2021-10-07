package com.app.riyasewana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.riyasewana.prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SpareDetailedActivity extends AppCompatActivity {

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

    private StorageReference spareImageRef;
    private DatabaseReference spareRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_detailed);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        close = findViewById(R.id.sm_spare_detailed_close_btn);
        img = findViewById(R.id.sm_spare_detailed_img);
        title = findViewById(R.id.sm_spare_detailed_title);
        price = findViewById(R.id.sm_spare_detailed_price);
        date = findViewById(R.id.sm_spare_detailed_date);
        condition = findViewById(R.id.sm_spare_detailed_condition);
        additional = findViewById(R.id.sm_spare_detailed_additional_info);
        name = findViewById(R.id.sm_spare_detailed_name);
        contact = findViewById(R.id.sm_spare_detailed_contact);
        location = findViewById(R.id.sm_spare_detailed_location);

//        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentUser.getPhone());
        spareImageRef = FirebaseStorage.getInstance().getReference().child("SpareImages");
        spareRef = FirebaseDatabase.getInstance().getReference().child("Spare").child(id);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SpareDetailedActivity.this, HomeActivity.class));
                finish();
            }
        });

        spareRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.child("img1").exists()){
                        String tmpImg1 = dataSnapshot.child("img1").getValue().toString();
                        String tmpName = dataSnapshot.child("name").getValue().toString();
                        String tmpContact = dataSnapshot.child("contact").getValue().toString();
                        String tmpAddress = dataSnapshot.child("address").getValue().toString();
                        String tmpTitle = dataSnapshot.child("title").getValue().toString();
                        String tmpCondition = dataSnapshot.child("condition").getValue().toString();
                        String tmpPrice = dataSnapshot.child("price").getValue().toString();
                        String tmpAdditional = dataSnapshot.child("additional").getValue().toString();
                        String tmpId = dataSnapshot.child("id").getValue().toString();

                        Picasso.get().load(tmpImg1).into(img);
                        name.setText("Name: "+tmpName);
                        contact.setText("Contact: "+tmpContact);
                        location.setText("Location: "+tmpAddress);
                        title.setText(tmpTitle);
                        condition.setText("Condition: "+tmpCondition);
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