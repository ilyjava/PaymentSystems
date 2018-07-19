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
import com.example.admin.paymentsystems.model.CarData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.admin.paymentsystems.model.CarData.getNotEntered;

public class CarNumberActivity extends AppCompatActivity {

    private Button mCarNumberButton;
    private Button mSkipButton;

    private EditText mCarNumberEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_number);

        mCarNumberEditText = findViewById(R.id.carET);

        mCarNumberButton = findViewById(R.id.carNumberButton);
        mSkipButton = findViewById(R.id.skipCarNumber);

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog(getString(R.string.carNumberMessage));
            }
        });

        mCarNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarData.getInstance().setmCarNumber(mCarNumberEditText.getText().toString());
                startActivity(new Intent(CarNumberActivity.this, StsActivity.class));
            }
        });
        mCarNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Editable e = mCarNumberEditText.getText();
                if (e.length() > 9) {
                    showError();
                }
                if(e.length() == 8 || e.length() == 9){
                   Pattern p = Pattern.compile("^[А-Я|0-9]{1}[0-9]{3}[А-Я]{2}[0-9]{2,3}$");

                    Matcher m = p.matcher(e.toString());
                    if(!m.matches()){
                        showError();
                    }
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (mCarNumberEditText.getText().length()<8){
                    mCarNumberButton.setEnabled(false);
                }
                else {
                    mCarNumberButton.setEnabled(true);
                }

            }
        });

    }
    private void showError() {
            mCarNumberEditText.setError(getString(R.string.invalidNumber));
            mCarNumberButton.setEnabled(false);
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
                        CarData.getInstance().setmCarNumber(getNotEntered());
                        startActivity(new Intent(CarNumberActivity.this, StsActivity.class));
                    }
                });
        builderSingle.show();
    }
}
