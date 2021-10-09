package com.app.riyasewana;

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
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddVehicleActivity extends AppCompatActivity {
    TextInputLayout textInputLayoutcategory;
    TextInputLayout textInputLayoutmanufacturer;
    AutoCompleteTextView category;
    AutoCompleteTextView manufacturer;

    private String id;
    private String txtModel;
    private String txtCondition;
    private String txtCategory;
    private String txtYear;
    private String txtManufacturer;
    private String txtTransmission;
    private String txtPrice;
    private String txtMore;
    private String txtImg1;
    private String txtImg2;
    private String txtImg3;
    private String txtName;
    private String txtContact;
    private String txtLocation;

    private NumberPicker yearValue;
    private TextView close;
    private TextView year;
    private TextInputEditText model;
    private TextInputEditText price;
    private TextInputEditText more;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private TextInputEditText name;
    private TextInputEditText contact;
    private TextInputEditText location;
    private Button addVehicleBtn;
    private MaterialRadioButton brandNew, reConditioned, antique, auto, manual;
    private String condition;
    private String transmission;

    private static final int galleryPick = 1;
    private Uri img1Uri;
    private Uri img2Uri;
    private Uri img3Uri;
    private StorageReference vehicleImageRef;
    private String downloadImg1Url;
    private String downloadImg2Url;
    private String downloadImg3Url;
    private DatabaseReference vehicleRef;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        close = findViewById(R.id.add_vehicle_close_btn);
        model = findViewById(R.id.add_vehicle_model_value);
        price = findViewById(R.id.add_vehicle_price_value);
        more = findViewById(R.id.add_vehicle_more_value);
        img1 = findViewById(R.id.add_vehicle_img1);
        img2 = findViewById(R.id.add_vehicle_img2);
        img3 = findViewById(R.id.add_vehicle_img3);
        name = findViewById(R.id.add_vehicle_contact_name_value);
        contact = findViewById(R.id.add_vehicle_contact_number_value);
        location = findViewById(R.id.add_vehicle_contact_location_value);
        addVehicleBtn = findViewById(R.id.add_vehicle_btn);
        brandNew = findViewById(R.id.radio_vehicle_condition_brand_new);
        reConditioned = findViewById(R.id.radio_vehicle_condition_reconditioned);
        antique = findViewById(R.id.radio_vehicle_condition_antique);
        auto = findViewById(R.id.radio_vehicle_transmission_auto);
        manual = findViewById(R.id.radio_vehicle_transmission_manual);

        vehicleImageRef = FirebaseStorage.getInstance().getReference().child("VehicleImages");
        vehicleRef = FirebaseDatabase.getInstance().getReference().child("Vehicle");
        pd = new ProgressDialog(this);

        textInputLayoutcategory = findViewById(R.id.add_vehicle_category);
        category = findViewById(R.id.add_vehicle_category_value);

        textInputLayoutmanufacturer = findViewById(R.id.add_vehicle_manufacturer);
        manufacturer = findViewById(R.id.add_vehicle_manufacturer_value);

        yearValue = findViewById(R.id.add_vehicle_year_picker);
        yearValue.setMinValue(1990);
        yearValue.setMaxValue(2021);

        yearValue.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                    year.setText("" + newVal);
            }
        });
        year = findViewById(R.id.add_vehicle_year_value);

        String[] categories = new String[]{
                "Car",
                "Van",
                "Bus",
                "SUV",
                "Lorry",
                "Bike",
                "Three Wheel",
                "Truck"
        };

        String[] manufacturers = new String[]{
                "BMW",
                "Toyota",
                "Nissan",
                "Honda",
                "Suzuki",
                "TATA",
                "Audi",
                "ISUZU",
                "Bajaj"
        };

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(
                AddVehicleActivity.this,
                R.layout.drop_down_item,
                categories
        );

        ArrayAdapter<String> categoriesManufacturers = new ArrayAdapter<>(
                AddVehicleActivity.this,
                R.layout.drop_down_item,
                manufacturers
        );

        category.setAdapter(categoriesAdapter);
        manufacturer.setAdapter(categoriesManufacturers);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddVehicleActivity.this, AdminDashboard.class));
                finish();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        addVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    private void validateData() {

        if (brandNew.isChecked()){
            condition = "Brand New";
        } else if (reConditioned.isChecked()){
            condition = "Re-Conditioned";
        } else if (antique.isChecked()){
            condition = "Antique";
        }

        if (auto.isChecked()){
            transmission = "Auto";
        } else if (manual.isChecked()){
            transmission = "Manual";
        }

        txtPrice = price.getText().toString();
        txtModel = model.getText().toString();
        txtMore = more.getText().toString();
        txtCategory = category.getText().toString();
        txtManufacturer = manufacturer.getText().toString();
        txtName = name.getText().toString();
        txtLocation = location.getText().toString();
        txtContact = contact.getText().toString();
        txtYear = year.getText().toString();

        if(img1Uri == null){
            Toast.makeText(this, "Main image is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtName)){
            //Toast.makeText(this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
            name.setError("Name is Required");
        }
        else if (TextUtils.isEmpty(txtCategory)){
            Toast.makeText(this, "Category cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (!brandNew.isChecked() && !reConditioned.isChecked() && !antique.isChecked()){
            Toast.makeText(this, "Select your vehicle condition", Toast.LENGTH_SHORT).show();
        }
        else if (!auto.isChecked() && !manual.isChecked()){
            Toast.makeText(this, "Select your vehicle transmission", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtManufacturer)){
            Toast.makeText(this, "Manufacturer cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtYear)){
            Toast.makeText(this, "Year cannot be empty.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(txtLocation)){
            Toast.makeText(this, "Location cannot be empty.", Toast.LENGTH_SHORT).show();
            location.setError("Location is Required");
        }
        else if (TextUtils.isEmpty(txtContact)){
            Toast.makeText(this, "Contact cannot be empty.", Toast.LENGTH_SHORT).show();
            contact.setError("Number is Required");
        }
        else if(TextUtils.isEmpty(txtPrice)){
            Toast.makeText(this, "Price cannot be empty.", Toast.LENGTH_SHORT).show();
            price.setError("Price is Required");
        }
        else if (TextUtils.isEmpty(txtModel)){
            Toast.makeText(this, "Title cannot be empty.", Toast.LENGTH_SHORT).show();
            model.setError("Model is Required");
        }
        else if (txtContact.length() != 10){
            Toast.makeText(this, "Contact number must contain 10 digits.", Toast.LENGTH_SHORT).show();
        }
        else {
            storeVehicleInfo();
        }
    }


    private void storeVehicleInfo() {
        pd.setMessage("Inserting");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        id = String.valueOf(System.currentTimeMillis());

        final StorageReference filePath = vehicleImageRef.child(id + ".jpg");

        final UploadTask uploadTask = filePath.putFile(img1Uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddVehicleActivity.this, "Error: " +e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

                            saveVehicleInfo();
                        }
                    }
                });
            }
        });
    }

    private void saveVehicleInfo() {
        HashMap<String, Object> vehicleMap = new HashMap<>();
        vehicleMap.put("id", id);
        vehicleMap.put("model", txtModel);
        vehicleMap.put("condition", txtCondition);
        vehicleMap.put("category", txtCategory);
        vehicleMap.put("year", txtYear);
        vehicleMap.put("manufacturer", txtManufacturer);
        vehicleMap.put("transmission", txtTransmission);
        vehicleMap.put("price", txtPrice);
        vehicleMap.put("more", txtMore);
        vehicleMap.put("img1", downloadImg1Url);
        vehicleMap.put("name", txtName);
        vehicleMap.put("contact", txtContact);
        vehicleMap.put("location", txtLocation);

        vehicleRef.child(id).updateChildren(vehicleMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(AddVehicleActivity.this, "Vehicle Added Successfully.", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(AddVehicleActivity.this, AdminDashboard.class));
                    finish();
                }
                else {
                    pd.dismiss();
                    Toast.makeText(AddVehicleActivity.this, "Error: "+task.getException().toString(), Toast.LENGTH_SHORT).show();
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
