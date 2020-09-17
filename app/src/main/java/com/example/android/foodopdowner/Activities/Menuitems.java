package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.android.foodopdowner.Adapters.DataAdapter;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menuitems extends AppCompatActivity {
    Toolbar toolbar;
    User_Service userService;
    RecyclerView recyclerView;
    DataAdapter dataAdapter;

    GridLayoutManager mLayoutManager;
    private ArrayList<Food_Items>data;


    // ImageView imageitem1,imageitem2,imageitem3,imageitem4,imageitem5,imageitem6,imageitem7,imageitem8,imageitem9,imageitem10,imageitem11,imageitem12,imageitem13,imageitem14,imageitem15;
   /* EditText item_name;
    EditText item_price;

    TextView name,price;
    SharedPreferences pref;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuitems);
        recyclerView = findViewById(R.id.recyclerView);

      /*  imageitem1=findViewById(R.id.item1);
        imageitem2=findViewById(R.id.item2);
        imageitem3=findViewById(R.id.item3);
        imageitem4=findViewById(R.id.item4);
        imageitem5=findViewById(R.id.item5);
        imageitem6=findViewById(R.id.item6);
        imageitem7=findViewById(R.id.item7);
        imageitem8=findViewById(R.id.item8);
        imageitem9=findViewById(R.id.item9);
        imageitem10=findViewById(R.id.item10);
        imageitem11=findViewById(R.id.item11);
        imageitem12=findViewById(R.id.item12);
        imageitem13=findViewById(R.id.item13);
        imageitem14=findViewById(R.id.item14);
        imageitem15=findViewById(R.id.item15);*/
        ///---


        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });
        mLayoutManager = new GridLayoutManager(Menuitems.this,2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        userService= Api_Client.getClient().create(User_Service.class);
        Call<Food_Items>call=userService.doGetUserList("show_items");
        call.enqueue(new Callback<Food_Items>() {
            @Override
            public void onResponse(Call<Food_Items> call, Response<Food_Items> response) {
                Food_Items userlist=response.body();
                String text=userlist.status;
                ArrayList<Food_Items.Datum>datumList=userlist.data;
                dataAdapter=new DataAdapter(Menuitems.this,datumList);
                recyclerView.setAdapter(dataAdapter);


            }

            @Override
            public void onFailure(Call<Food_Items> call, Throwable t) {
                Toast.makeText(Menuitems.this,"Response is Fail",Toast.LENGTH_SHORT).show();

            }
        });


      /*  if (checkPermission()) {

        } else {
            requestPermission();
        }

           }


    private boolean checkPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                0);
    }




    public void uploaditemone(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 0);

    }


    public void uploaditemtwo(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 1);
    }
    public void uploaditemthree(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 2);


    }
    public void uploaditemfour(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 3);


    }
    public void uploaditemfive(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 4);


    }
    public void uploaditemsix(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 5);


    }
    public void uploaditemseven(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 6);


    }
    public void uploaditemeight(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 7);


    }
    public void uploaditemnine(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 8);


    }
    public void uploaditemten(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 9);


    }
    public void uploaditemeleven(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 10);


    }
    public void uploaditemtwele(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 11);


    }
    public void uploaditemthirteen(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 12);


    }
    public void uploaditemfourteen(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 13);


    }
    public void uploaditemfifteen(View view) {
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent1, 14);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem1.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
              *//*  //-----
               //intent.putExtra("price",item_price.getText().toString());
             //  intent.putExtra("name",item_name.getText().toString());
                //SharedPreferences.Editor editor=pref.edit();
                editor.clear();
                editor.apply();*//*
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

        if (requestCode == 1) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem2.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());


                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

        if (requestCode == 2) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem3.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }

        if (requestCode == 3) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem4.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 4) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem5.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 5) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem6.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 6) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem7.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 7) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem8.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 8) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem9.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 9) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem10.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 10) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem11.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 11) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem12.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 12) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem13.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 13) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem14.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        if (requestCode == 14) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem15.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                intent.putExtra("byteArray", _bs.toByteArray());
                startActivity(intent);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }


*/
    }

    public void Drinksmenu(View view) {
        startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
    }

    public void MenuDetails(View view){
        Intent intent=new Intent(getApplicationContext(),MenuDetails.class);
        startActivity(intent);
    }
    public void onBackPressed()
    {
      /*  SharedPreferences.Editor editor=pref.edit();
        editor.clear();
        editor.apply();*/

     startActivity(new Intent(getApplicationContext(),Businesspage.class));

    }
}