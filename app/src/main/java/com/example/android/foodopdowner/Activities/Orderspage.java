package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.foodopdowner.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Orderspage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
TextView txcurrenttime;
Button deliveryboy;
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    Toolbar toolbar;
    Spinner orderDetails;
  //  String[] order_names={"Order_Details","Print"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderspage);
        //--
        orderDetails=findViewById(R.id.sp_order);
       /* orderDetails.setOnItemSelectedListener(this);
        ArrayAdapter aa=new ArrayAdapter(this,android.R.layout.simple_spinner_item,order_names);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderDetails.setAdapter(aa);*/
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.details, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        orderDetails.setAdapter(adapter);
        //--
        txcurrenttime=findViewById(R.id.currrenttime);
        deliveryboy=findViewById(R.id.deliveryboy);
        deliveryboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Orderspage.this,Deliveryboy.class);
               startActivity(intent);
            }
        });

        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("kk:mm:ss a");

        time = simpleDateFormat.format(calander.getTime());
        txcurrenttime.setText(time);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });
    }

    public void Admin(View view) {
        startActivity(new Intent(getApplicationContext(),Admin.class));
    }

    public void Deliverytrack(View view) {
        startActivity(new Intent(getApplicationContext(),Deliverytrack.class));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getApplicationContext(), order_names[position], Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Spinner orderDetails = (Spinner) findViewById(R.id.sp_order);
        orderDetails.setOnItemSelectedListener(this);

    }
}
