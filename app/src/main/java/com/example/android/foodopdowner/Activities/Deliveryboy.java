package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.android.foodopdowner.R;


public class Deliveryboy extends AppCompatActivity {

    TextView txcurrenttime,Back_owner;
    Toolbar toolbar;
    RadioGroup rg_delivery_choice;
    RadioButton rb_yes, rb_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboy);
        Back_owner=findViewById(R.id.backtoowner);
        txcurrenttime = findViewById(R.id.curenttime);
        rg_delivery_choice = findViewById(R.id.rg_delivery_choice);
        rb_yes = findViewById(R.id.rb_yes);
        rb_no = findViewById(R.id.rb_no);
        toolbar=findViewById(R.id.toolbar);

        isDeliveryChecked();

        //for getting system real time
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txcurrenttime.setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });

    }

    private void isDeliveryChecked() {
        rg_delivery_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rb_yes.isChecked())
                {
                    Back_owner.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                    Back_owner.invalidate();

                }
                else if(rb_no.isChecked())
                {
                    Back_owner.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                    Back_owner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Deliveryboy.this,Businesspage.class);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }

    public void Deliveryview(View view) {
        startActivity(new Intent(getApplicationContext(), Deliveryaddress.class));
    }

}
