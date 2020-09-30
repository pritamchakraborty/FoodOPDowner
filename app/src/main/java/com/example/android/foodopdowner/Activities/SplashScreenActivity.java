package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.foodopdowner.R;


public class SplashScreenActivity extends AppCompatActivity {

    Button btn_owner, btn_delivery_boy;
    String buisnessname ="";
    String firstaddress ="";
    String phoneno ="";
    String owner_id = "";
    String buisness_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        btn_owner = findViewById(R.id.btn_owner);
        btn_delivery_boy = findViewById(R.id.btn_delivery_boy);

        Bundle bundle = getIntent().getExtras();
        owner_id = bundle.getString("owner_id");
        buisness_id = bundle.getString("buisness_id");
        buisnessname = bundle.getString("buisness_name");
        firstaddress = bundle.getString("address");
        phoneno = bundle.getString("phone_no");
//        byte[] byteArray = getIntent().getByteArrayExtra("image");
//        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        btn_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SplashScreenActivity.this, Businesspage.class);
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
