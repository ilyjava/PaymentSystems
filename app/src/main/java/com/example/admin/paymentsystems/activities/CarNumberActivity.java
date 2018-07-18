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
import com.example.admin.paymentsystems.model.CarData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarNumberActivity extends AppCompatActivity {

    private CarData mCar;

    private Button mCarNumberButton;
    private Button mSkipButton;

    private EditText mCarNumberEditText;

    final String CAR_NUMBER_SAVED_TEXT = "car_number";

    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_number);

        mCar = new CarData();

        mCarNumberEditText = findViewById(R.id.carET);

        mCarNumberButton = findViewById(R.id.carNumberButton);
        mSkipButton = findViewById(R.id.skipCarNumber);

        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog("Если вы не введете регистрационный номер ТС, то вы не сможете следить за штрафами, выписанными на автомобиль");
            }
        });

        mCarNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCar.setmCarNumber(mCarNumberEditText.getText().toString());
                Intent sts_number = new Intent(CarNumberActivity.this, StsActivity.class);
                startActivity(sts_number);


                //saveText();

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
                else if (mCarNumberEditText.getText().length()>=8){
                    mCarNumberButton.setEnabled(true);
                }

            }
        });

    }
    private void showError() {
            mCarNumberEditText.setError("Неверный номерной знак");
            mCarNumberButton.setEnabled(false);
        }


   /* public void saveText() {
        sPref = getSharedPreferences("MYPREFS", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CAR_NUMBER_SAVED_TEXT, mCarNumberEditText.getText().toString());
        ed.commit();
    }*/
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
                        Intent nextIntent = new Intent(CarNumberActivity.this, StsActivity.class);
                        startActivity(nextIntent);
                        /*sPref = getSharedPreferences("MYPREFS", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString(CAR_NUMBER_SAVED_TEXT, "Пользователь не ввел данные");
                        ed.commit();*/
                    }
                });
        builderSingle.show();
    }
}
