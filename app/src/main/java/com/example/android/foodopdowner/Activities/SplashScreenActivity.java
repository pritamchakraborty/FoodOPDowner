package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.foodopdowner.R;


public class SplashScreenActivity extends AppCompatActivity {

    Button btn_owner, btn_delivery_boy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        btn_owner = findViewById(R.id.btn_owner);
        btn_delivery_boy = findViewById(R.id.btn_delivery_boy);

        btn_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_delivery_boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreenActivity.this, Deliveryboy.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }
}
