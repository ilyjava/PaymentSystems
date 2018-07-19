package com.example.admin.paymentsystems.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.paymentsystems.R;
import com.example.admin.paymentsystems.model.CarData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.admin.paymentsystems.model.CarData.getNotEntered;

public class DriverLicenseActivity extends AppCompatActivity {

    private Button mDriverLicenseButton;
    private Button mSkipButton;

    private EditText mDriverLicenseEditText;

    private boolean isUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_license);

        mDriverLicenseButton = findViewById(R.id.driverLicenseButton);
        mSkipButton = findViewById(R.id.skipDriverLicense);

        mDriverLicenseEditText = findViewById(R.id.dlET);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               final SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                final boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

         mDriverLicenseButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (isFirstStart) {
                     CarData.getInstance().setmDriverLicenseNumber(mDriverLicenseEditText.getText().toString());
                     CarData.getInstance().setPassedWizard(true);
                     startActivity(new Intent(DriverLicenseActivity.this, OnboardingActivity.class));

                     SharedPreferences.Editor e = getPrefs.edit();
                     e.putBoolean("firstStart", false);
                     e.apply();
                 }
             }
         });
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog(getString(R.string.driverLicenseMessage));
            }
        });

        t.start();
        mDriverLicenseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Editable e = mDriverLicenseEditText.getText();
                if (e.length() > 12) {
                    showError();
                } else {
                    mDriverLicenseButton.setEnabled(true);
                }
                if (e.length() == 10 && !isUsed) {
                    StringBuilder stringBuilder = new StringBuilder(e);
                    stringBuilder.insert(2, " ");
                    stringBuilder.insert(5, " ");
                    mDriverLicenseEditText.setText(stringBuilder);
                    mDriverLicenseEditText.setSelection(e.length() + 2);
                    isUsed = true;
                }else if (e.length() == 10 && isUsed){
                    Pattern p = Pattern.compile("^[0-9]{2}[0-9|А-Я]{2}[0-9]{6}$");
                    Matcher m = p.matcher(e.toString());
                    if (!m.matches()) {
                        showError();
                    } else {
                        mDriverLicenseButton.setEnabled(true);
                    }
                }  else if(e.length() == 12){
                    Pattern p = Pattern.compile("^[0-9]{2}[\\s]{1}[0-9|А-Я]{2}[\\s]{1}[0-9]{6}$");
                    Matcher m = p.matcher(e.toString());
                    if (!m.matches()) {
                        showError();
                    } else {
                        mDriverLicenseButton.setEnabled(true);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    private void showError() {
            mDriverLicenseEditText.setError(getString(R.string.invalidNumber));
            mDriverLicenseButton.setEnabled(false);
    }

    public void customDialog(String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                R.string.enterNumber,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
        builderSingle.setPositiveButton(
                R.string.skipNumber,
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        CarData.getInstance().setmDriverLicenseNumber(getNotEntered());
                        CarData.getInstance().setPassedWizard(true);
                        startActivity(new Intent(DriverLicenseActivity.this, OnboardingActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                });
        builderSingle.show();
    }


}

