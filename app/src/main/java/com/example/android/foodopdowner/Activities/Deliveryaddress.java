package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.android.foodopdowner.Activities.Deliveryboy;
import com.example.android.foodopdowner.Activities.Deliverystatus;
import com.example.android.foodopdowner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Deliveryaddress extends AppCompatActivity {
    TextView txcurrenttime;
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryaddress);

        txcurrenttime=findViewById(R.id.curenttime);

        //for getting system real time
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txcurrenttime.setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);



        time = simpleDateFormat.format(calander.getTime());
        txcurrenttime.setText(time);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Deliveryboy.class));
            }
        });
    }
    public void delivered(View view)
    {
        startActivity(new Intent(getApplicationContext(), Deliverystatus.class));
    }
}
