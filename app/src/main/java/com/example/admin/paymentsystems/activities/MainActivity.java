package com.example.admin.paymentsystems.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.admin.paymentsystems.R;
import com.example.admin.paymentsystems.model.CarData;

public class MainActivity extends AppCompatActivity {

    private CarData mCar;

    private TextView mCarNumberTextView;
    private TextView mStsTextView;
    private TextView mDriverLicenseTextView;

    final String CAR_NUMBER_SAVED_TEXT = "car_number";
    final String STS_NUMBER_SAVED_TEXT = "sts_number";
    final String DRIVER_LICENSE_SAVED_TEXT = "dl_number";

    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCar = new CarData();

        mCarNumberTextView = findViewById(R.id.carNumberTextView);
        mStsTextView = findViewById(R.id.stsTextView);
        mDriverLicenseTextView = findViewById(R.id.driverLicenseTextView);

       isUserEnteredData();
    }

    private void isUserEnteredData(){
        sPref = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        String savedText = sPref.getString(CAR_NUMBER_SAVED_TEXT, "");
        String savedText2 = sPref.getString(STS_NUMBER_SAVED_TEXT, "");
        String savedText3 = sPref.getString(DRIVER_LICENSE_SAVED_TEXT, "");
        if ((savedText.length()== 0  && savedText2.length() == 0 && savedText3.length() == 0)){
            Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startIntent);
        } else if ((savedText.length()!= 0  || savedText2.length() != 0 || savedText3.length() != 0)) {
            mCarNumberTextView.setText(mCar.getmCarNumber());
            mStsTextView.setText(savedText2);
            mDriverLicenseTextView.setText(savedText3);
        }
    }

    public void showData(){

    }
}
