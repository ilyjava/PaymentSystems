package com.example.admin.paymentsystems.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.paymentsystems.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StsActivity extends AppCompatActivity {

    private Button mStsButton;
    private Button mSkipButton;

    private  EditText mStsEditText;

    final String CAR_NUMBER_SAVED_TEXT = "car_number";
    final String STS_NUMBER_SAVED_TEXT = "sts_number";
    final String DRIVER_LICENSE_SAVED_TEXT = "dl_number";

    SharedPreferences sPref;

    private boolean isUsed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sts);

        mStsButton = findViewById(R.id.stsButton);
        mSkipButton = findViewById(R.id.skipSts);

        mStsEditText = findViewById(R.id.stsET);

        mStsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPref = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                String savedText = sPref.getString(CAR_NUMBER_SAVED_TEXT, "");
                if(savedText.equals("Пользователь не ввел данные")) {
                    Intent onBoardingIntent = new Intent(StsActivity.this, OnboardingActivity.class);
                    onBoardingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(onBoardingIntent);
                    ed.putString(STS_NUMBER_SAVED_TEXT, mStsEditText.getText().toString());
                    ed.commit();
                    ed.putString(DRIVER_LICENSE_SAVED_TEXT, "Пользователь не ввел данные");
                    ed.commit();
                }else {
                    Intent driver_license = new Intent(StsActivity.this, DriverLicenseActivity.class);
                    startActivity(driver_license);
                    saveText();
                }
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog("Если вы не введете номер свидетельства ТС, то вы не сможете следить за штрафами, выписанными на автомобиль");
            }
        });
        mStsEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Editable e = mStsEditText.getText();
                if (e.length() > 12) {
                    showError();
                } else {
                    mStsButton.setEnabled(true);
                }
                if (e.length() == 10 && isUsed != true) {
                    StringBuilder stringBuilder = new StringBuilder(e);
                    stringBuilder.insert(2, " ");
                    stringBuilder.insert(5, " ");
                    mStsEditText.setText(stringBuilder);
                    mStsEditText.setSelection(e.length() + 2);
                    isUsed = true;
                }else if (e.length() == 10 && isUsed == true){
                    Pattern p = Pattern.compile("^[0-9]{2}[0-9|А-Я]{2}[0-9]{6}$");
                    Matcher m = p.matcher(e.toString());
                    if (!m.matches()) {
                        showError();
                    } else {
                        mStsButton.setEnabled(true);
                    }
                }  else if(e.length() == 12){
                    Pattern p = Pattern.compile("^[0-9]{2}[\\s]{1}[0-9|А-Я]{2}[\\s]{1}[0-9]{6}$");
                    Matcher m = p.matcher(e.toString());
                    if (!m.matches()) {
                        showError();
                    } else {
                        mStsButton.setEnabled(true);
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
   }

    private void showError() {
            mStsEditText.setError("Неверный номер документа");
            mStsButton.setEnabled(false);
        }

    public void saveText() {
        sPref = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(STS_NUMBER_SAVED_TEXT, mStsEditText.getText().toString());
        ed.commit();
    }
    public void customDialog(String message){
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "ВВЕСТИ НОМЕР",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                });
        builderSingle.setPositiveButton(
                "ПРОПУСТИТЬ",
                new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        sPref = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString(STS_NUMBER_SAVED_TEXT, "Пользователь не ввел данные");
                        ed.commit();
                        String savedText = sPref.getString(CAR_NUMBER_SAVED_TEXT, "");
                        if(!savedText.equals("Пользователь не ввел данные")) {
                            Intent nextIntent = new Intent(StsActivity.this, DriverLicenseActivity.class);
                            startActivity(nextIntent);
                        } else {
                            Intent onBoardingIntent = new Intent(StsActivity.this, OnboardingActivity.class);
                            onBoardingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(onBoardingIntent);
                            ed.putString(DRIVER_LICENSE_SAVED_TEXT, "Пользователь не ввел данные");
                            ed.commit();
                        }

                    }
                });
        builderSingle.show();
    }
}
