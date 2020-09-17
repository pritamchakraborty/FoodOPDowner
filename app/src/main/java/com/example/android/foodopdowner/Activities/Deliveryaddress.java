package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.foodopdowner.Activities.Deliveryboy;
import com.example.android.foodopdowner.Activities.Deliverystatus;
import com.example.android.foodopdowner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        txcurrenttime=findViewById(R.id.currrenttime);

        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("kk:mm:ss");

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
