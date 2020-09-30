package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.foodopdowner.R;

public class Banksetup extends AppCompatActivity {
EditText edbankname,edaccountno,edroutingno,edcity;
    Toolbar toolbar;
    TextView tv_pay;
    String buisnessname ="";
    String firstaddress ="";
    String phoneno ="";
    String owner_id = "";
    String buisness_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksetup);
        edbankname=findViewById(R.id.edit_bankname);
        tv_pay = (findViewById(R.id.tv_pay));
        edaccountno=findViewById(R.id.edit_accountnumber);
        edroutingno=findViewById(R.id.edit_routingnumber);
        edcity=findViewById(R.id.edit_city);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Setup2.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        owner_id = bundle.getString("owner_id");
        buisness_id = bundle.getString("buisness_id");
        buisnessname = bundle.getString("buisness_name");
        firstaddress = bundle.getString("address");
        phoneno = bundle.getString("phone_no");
//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Banksetup.this,SplashScreenActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("buisness_id",buisness_id);
                bundle.putString("owner_id",owner_id);
                bundle.putString("buisness_name",buisnessname);
                bundle.putString("address",firstaddress);
                bundle.putString("phone_no",phoneno);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void Payment(View view)
    {
        startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
    }
}
