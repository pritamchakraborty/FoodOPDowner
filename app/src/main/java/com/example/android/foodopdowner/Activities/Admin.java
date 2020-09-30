package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.foodopdowner.R;

import java.util.Calendar;

public class Admin extends AppCompatActivity {
    Toolbar toolbar;
    private TextView mDisplayDate,toDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    private static final String TAG = "Admin";
    CardView discount_layout;
    EditText edit_discount;
    Switch simpleSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        simpleSwitch = findViewById(R.id.simpleSwitch);
        edit_discount = findViewById(R.id.edit_discount);
        discount_layout = findViewById(R.id.discount_layout);
        toolbar = findViewById(R.id.toolbar);
        toDisplayDate=findViewById(R.id.to_date);
        mDisplayDate = (TextView) findViewById(R.id.from_date);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Admin.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
             Log.d(TAG,"onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        //-----
        toDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        Admin.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG,"onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                toDisplayDate.setText(date);
            }
        };

       //----
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Orderspage.class));
            }
        });

        checkSwitch();
    }

    private void checkSwitch() {
        if(!simpleSwitch.isChecked())
        {
            edit_discount.getText().toString();
        }
        else
        {
            edit_discount.setEnabled(false);

        }
    }


    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Businesspage.class));
    }

    public void menuSales(View view) {
        startActivity(new Intent(getApplicationContext(), FoodMenu.class));

    }

    public void drinssales(View view) {
        startActivity(new Intent(getApplicationContext(), Drinkmenu.class));

    }
}
