package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.foodopdowner.R;

public class DrinkMenuDetails extends AppCompatActivity {
    Toolbar toolbar;
ImageView imageView1;
//Drinks_Item.DDatum ddatum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu_details);
        toolbar=findViewById(R.id.toolbar);
        imageView1=findViewById(R.id.imageitem1);
        //ddatum=(Drinks_Item.DDatum)getIntent().getSerializableExtra("drink_name");

        if(getIntent().hasExtra("byteArray")) {
            Bitmap _bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imageView1.setImageBitmap(_bitmap);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
            }
        });
        /*if (ddatum!= null){
            item_name.*//*
        }*/
    }

    public void Drinkmenu(View view) {
        startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
    }

    public void backtomenu(View view) {
        //startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
onBackPressed();
    }

    public void saveitem(View view) {
        Toast.makeText(this, "Your Item is added Successfully!", Toast.LENGTH_SHORT).show();
    }
}
