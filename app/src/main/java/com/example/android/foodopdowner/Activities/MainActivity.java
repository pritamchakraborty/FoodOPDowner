package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.foodopdowner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Setup(View view) {
        startActivity(new Intent(getApplicationContext(),Setup.class));
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(),login.class));

    }
}
