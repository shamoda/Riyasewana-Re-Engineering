package com.app.riyasewana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DonationsActivity extends AppCompatActivity {

    private TextView close;
    private TextInputEditText name;
    private TextInputEditText number;
    private TextInputEditText date;
    private TextInputEditText cvv;
    private TextInputEditText amount;
    private Button donationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        close = findViewById(R.id.sm_donation_close_btn);
        name = findViewById(R.id.ud_donation_owner_value);
        number = findViewById(R.id.ud_donation_card_number_value);
        date = findViewById(R.id.ud_donation_exp_date_value);
        cvv = findViewById(R.id.ud_donation_cvv_value);
        amount = findViewById(R.id.ud_donation_amount_value);
        donationBtn = findViewById(R.id.ud_donation_btn);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DonationsActivity.this, HomeActivity.class));
                finish();
            }
        });

        donationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(DonationsActivity.this, "Name cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(number.getText().toString())) {
                    Toast.makeText(DonationsActivity.this, "Card Number cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(date.getText().toString())) {
                    Toast.makeText(DonationsActivity.this, "Exp Date cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(cvv.getText().toString())) {
                    Toast.makeText(DonationsActivity.this, "CVV cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(amount.getText().toString())) {
                    Toast.makeText(DonationsActivity.this, "Amount cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DonationsActivity.this, "Thank you for your contribution.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DonationsActivity.this, HomeActivity.class));
                    finish();
                }
            }
        });

    }
}