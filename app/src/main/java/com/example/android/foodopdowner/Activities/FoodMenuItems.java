package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.foodopdowner.R;

import java.io.File;

public class FoodMenuItems extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    //

    //
    private Bitmap bitmap;
    private static final int REQUEST_CODE = 1;
    SharedPreferences imageprefernce;
    SharedPreferences.Editor imageeditor;
    String pictureFile = "Hudson";
    private String selectedImagePath;
    private String price,name;

    Uri setimguri;
    File file;
    ImageView imageView1;
//TextView back;

    EditText item_name,item_price;
    private static final String IMAGE_DIRECTORY = "/demonuts";

    String owner_id ="";
    String buisness_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_details);
        imageView=findViewById(R.id.imageitem1);
        imageView1=findViewById(R.id.imageitem1);

        // item_name=findViewById(R.id.edit_itemname);
        //  item_price=findViewById(R.id.edit_price);
        // back=findViewById(R.id.back);

        Bundle bundle = getIntent().getExtras();

        owner_id = bundle.getString("owner_id");
        buisness_id = bundle.getString("buisness_id");


        if(getIntent().hasExtra("byteArray")) {
            Bitmap _bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            imageView1.setImageBitmap(_bitmap);
        }
        toolbar=findViewById(R.id.toolbar);
        imageprefernce=getSharedPreferences("image",MODE_PRIVATE);
        imageeditor=imageprefernce.edit();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), FoodMenu.class));


            }
        });

    }


    public void MenuItems(View view) {
        startActivity(new Intent(getApplicationContext(), FoodMenu.class));
    }

    public void backtomenu(View view) {

        onBackPressed();
    }

    public void saveitem(View view) {
        Toast.makeText(this, "Item Added Successfully...", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences=getSharedPreferences("AppSharedPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("ImagePath", selectedImagePath);
        // editor.putString("NAME",name);
        //  editor.putString("PRICE",price);


        editor.commit();

    }
}
