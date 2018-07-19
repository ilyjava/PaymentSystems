package com.example.admin.paymentsystems.activities;

import android.content.DialogInterface;
import android.content.Intent;
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

import static com.example.admin.paymentsystems.model.CarData.getNotEntered;

public class StsActivity extends AppCompatActivity {

    private Button mStsButton;
    private Button mSkipButton;

    private  EditText mStsEditText;


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
                if(CarData.getInstance().getmCarNumber().equals(getNotEntered())) {
                    CarData.getInstance().setmStsNumber(mStsEditText.getText().toString());
                    CarData.getInstance().setmDriverLicenseNumber(getNotEntered());
                    CarData.getInstance().setPassedWizard(true);
                    startActivity(new Intent(StsActivity.this, OnboardingActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }else {
                    CarData.getInstance().setmStsNumber(mStsEditText.getText().toString());
                    startActivity(new Intent(StsActivity.this, DriverLicenseActivity.class));

                }
            }
        });

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog(getString(R.string.stsMessage));
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
                if (e.length() == 10 && !isUsed) {
                    StringBuilder stringBuilder = new StringBuilder(e);
                    stringBuilder.insert(2, " ");
                    stringBuilder.insert(5, " ");
                    mStsEditText.setText(stringBuilder);
                    mStsEditText.setSelection(e.length() + 2);
                    isUsed = true;
                }else if (e.length() == 10 && isUsed){
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
            mStsEditText.setError(getString(R.string.invalidNumber));
            mStsButton.setEnabled(false);
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
                        CarData.getInstance().setmStsNumber(getNotEntered());
                        if(!CarData.getInstance().getmCarNumber().equals(getNotEntered())){
                            startActivity(new Intent(StsActivity.this, DriverLicenseActivity.class));
                        } else {
                            CarData.getInstance().setmDriverLicenseNumber(getNotEntered());
                            CarData.getInstance().setPassedWizard(true);
                            startActivity(new Intent(StsActivity.this, OnboardingActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }

                    }
                });
        builderSingle.show();
    }
}
