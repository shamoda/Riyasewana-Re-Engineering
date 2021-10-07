package com.app.riyasewana;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DonationsActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;

    //private String id;
    private String txtOwner;
    private String txtcardNumber;
    private String txtExpDate;
    private String txtCvv;
    private String txtAmount;


    private TextView close;
    private TextInputEditText owner;
    private TextInputEditText cardNumber;
    private TextInputEditText expDate;
    private TextInputEditText cvv;
    private TextInputEditText amount;
    private Button donationBtn;
    private DatabaseReference donationRef;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        close = findViewById(R.id.ud_donation_close_btn);
        owner = findViewById(R.id.ud_donation_owner_value);
        cardNumber = findViewById(R.id.ud_donation_card_number_value);
        expDate = findViewById(R.id.ud_donation_exp_date_value);
        cvv = findViewById(R.id.ud_donation_cvv_value);
        amount = findViewById(R.id.ud_donation_amount_value);
        donationBtn = findViewById(R.id.ud_donation_btn);

        donationRef = FirebaseDatabase.getInstance().getReference().child("Donation");
        pd = new ProgressDialog(this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationsActivity.this, AdminDashboard.class));
                finish();
            }
        });

        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }

            private void validateData() {
                txtOwner = owner.getText().toString();
                txtcardNumber = cardNumber.getText().toString();
                txtExpDate = expDate.getText().toString();
                txtCvv = cvv.getText().toString();
                txtAmount = amount.getText().toString();


                if (TextUtils.isEmpty(txtOwner)){
                    //Toast.makeText(this, "Owner cannot be empty.", Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(txtcardNumber)){
                    //Toast.makeText(this, "Card Number cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txtExpDate)){
                    //Toast.makeText(this, "Expire Date cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txtCvv)){
                    //Toast.makeText(this, "CVV cannot be empty.", Toast.LENGTH_SHORT).show();
               }
                else if(TextUtils.isEmpty(txtAmount)){
                   //Toast.makeText(this, "Amount cannot be empty.", Toast.LENGTH_SHORT).show();
                }
                else {
                    //saveDonationDetails();
                }
            }



            private void saveDonationDetails() {
                HashMap<String, Object> donationMap = new HashMap<>();
                donationMap.put("owner",txtOwner);
                donationMap.put("cardNumber",txtcardNumber);
                donationMap.put("expDate",txtExpDate);
                donationMap.put("cvv",txtCvv);
                donationMap.put("amount",txtAmount);

                
            }
        });

    }
}