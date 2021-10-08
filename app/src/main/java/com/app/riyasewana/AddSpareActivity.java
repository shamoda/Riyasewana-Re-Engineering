package com.app.riyasewana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddSpareActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView condition;

    private String id;
    private String txtName;
    private String txtContact;
    private String txtAddress;
    private String txtTitle;
    private String txtCondition;
    private String txtPrice;
    private String txtAdditional;
    private String txtImg1;
    private String txtImg2;
    private String txtImg3;

    private TextView close;
    private TextInputEditText name;
    private TextInputEditText contact;
    private TextInputEditText address;
    private TextInputEditText title;
    private TextInputEditText price;
    private TextInputEditText additional;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private Button addSpareBtn;

    private static final int galleryPick = 1;
    private Uri img1Uri;
    private Uri img2Uri;
    private Uri img3Uri;
    private StorageReference spareImageRef;
    private String downloadImg1Url;
    private String downloadImg2Url;
    private String downloadImg3Url;
    private DatabaseReference spareRef;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spare);

        close = findViewById(R.id.sm_add_spare_close_btn);
        name = findViewById(R.id.sm_add_spare_name_value);
        contact = findViewById(R.id.sm_add_spare_contact_value);
        address = findViewById(R.id.ud_donation_exp_date_value);
        title = findViewById(R.id.sm_add_spare_title_value);
//        condition = findViewById(R.id.sm_add_spare_condition_value);
        price = findViewById(R.id.sm_add_spare_price_value);
        additional = findViewById(R.id.sm_add_spare_additional_value);
        img1 = findViewById(R.id.sm_add_spare_img1);
        img2 = findViewById(R.id.sm_add_spare_img2);
        img3 = findViewById(R.id.sm_add_spare_img3);
        addSpareBtn = findViewById(R.id.sm_add_spare_btn);

        spareImageRef = FirebaseStorage.getInstance().getReference().child("SpareImages");
        spareRef = FirebaseDatabase.getInstance().getReference().child("Spare");
        pd = new ProgressDialog(this);

        textInputLayout = findViewById(R.id.sm_add_spare_condition);
        condition = findViewById(R.id.sm_add_spare_condition_value);

        String[] conditions = new String[]{
                "Brand New",
                "Used",
                "Antique"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                AddSpareActivity.this,
                R.layout.drop_down_item,
                conditions
        );

        condition.setAdapter(adapter);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddSpareActivity.this, AdminDashboard.class));
                finish();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addSpareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData() {

//        getting all the values to string variables
        txtName = name.getText().toString();
        txtAddress = address.getText().toString();
        txtContact = contact.getText().toString();
        txtPrice = price.getText().toString();
        txtTitle = title.getText().toString();
        txtAdditional = additional.getText().toString();
        txtCondition = condition.getText().toString();


//        validating data
        if(img1Uri == null){
            Toast.makeText(this, "Main image is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtName)){
            Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtCondition)){
            Toast.makeText(this, "Condition cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtAddress)){
            Toast.makeText(this, "Address cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtContact)){
            Toast.makeText(this, "Contact cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(txtPrice)){
            Toast.makeText(this, "Price cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtTitle)){
            Toast.makeText(this, "Title cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (txtContact.length() != 10){
            Toast.makeText(this, "Contact number must contain 10 digits.", Toast.LENGTH_SHORT).show();
        }
        else {
            storeSpareInfo();
        }
    }

    private void storeSpareInfo() {


        pd.setMessage("Inserting");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        id = String.valueOf(System.currentTimeMillis());

//        Calendar calendar = Calendar.getInstance();
//
//        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd yyyy");
//        saveCurrentDate = currentDate.format(calendar.getTime());
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//
//        doctorImageRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = spareImageRef.child(id + ".jpg");

        final UploadTask uploadTask = filePath.putFile(img1Uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddSpareActivity.this, "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(DoctorRegistrationActivity.this, "Profile image uploaded successfully.", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImg1Url = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            downloadImg1Url = task.getResult().toString();
//                            Toast.makeText(DoctorRegistrationActivity.this, "Profile image download URL taken successfully.", Toast.LENGTH_SHORT).show();

                            saveSpareInfo();
                        }
                    }
                });
            }
        });
    }

    private void saveSpareInfo() {

//        adding data into a hashmap
        HashMap<String, Object> spareMap = new HashMap<>();
        spareMap.put("id", id);
        spareMap.put("name", txtName);
        spareMap.put("contact", txtContact);
        spareMap.put("condition", txtCondition);
        spareMap.put("title", txtTitle);
        spareMap.put("address", txtAddress);
        spareMap.put("price", txtPrice);
        spareMap.put("additional", txtAdditional);
        spareMap.put("img1", downloadImg1Url);

        spareRef.child(id).updateChildren(spareMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.dismiss();
//                    if (dId != null){
//                        Toast.makeText(DoctorRegistrationActivity.this, "Doctor details updated successfully.", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
                    Toast.makeText(AddSpareActivity.this, "Spare Part Added Successfully.", Toast.LENGTH_SHORT).show();
//                    }

                    startActivity(new Intent(AddSpareActivity.this, AdminDashboard.class));
                    finish();
                }
                else {
                    pd.dismiss();
                    Toast.makeText(AddSpareActivity.this, "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
//        opening gallery using implicit intent
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            img1Uri = data.getData();
            img1.setImageURI(img1Uri);
        }
    }
}