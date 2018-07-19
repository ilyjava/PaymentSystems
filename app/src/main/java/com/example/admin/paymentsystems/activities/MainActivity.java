package com.example.admin.paymentsystems.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.admin.paymentsystems.R;
import com.example.admin.paymentsystems.model.CarData;

public class MainActivity extends AppCompatActivity {

    private TextView mCarNumberTextView;
    private TextView mStsTextView;
    private TextView mDriverLicenseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCarNumberTextView = findViewById(R.id.carNumberTextView);
        mStsTextView = findViewById(R.id.stsTextView);
        mDriverLicenseTextView = findViewById(R.id.driverLicenseTextView);

       isUserEnteredData();
    }

    private void isUserEnteredData(){
        String carNumber = CarData.getInstance().getmCarNumber();
        String stsNumber = CarData.getInstance().getmStsNumber();
        String driverLicenseNumber = CarData.getInstance().getmDriverLicenseNumber();

        if (!CarData.getInstance().isPassedWizard()){
            startActivity(new Intent(MainActivity.this, StartActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        } else {
           mCarNumberTextView.setText(carNumber);
           mStsTextView.setText(stsNumber);
           mDriverLicenseTextView.setText(driverLicenseNumber);
        }
    }
}
