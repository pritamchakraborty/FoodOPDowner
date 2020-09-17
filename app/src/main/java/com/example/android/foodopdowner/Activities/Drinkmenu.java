package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.foodopdowner.Adapters.Drinks_DataAdapter;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.Drinks_Item;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Drinkmenu extends AppCompatActivity {
   // ImageView imageitem1,imageitem2,imageitem3,imageitem4,imageitem5,imageitem6,imageitem7,imageitem8,imageitem9,imageitem10,imageitem11,imageitem12,imageitem13,imageitem14,imageitem15;
    Toolbar toolbar;
    User_Service userService;
    private ArrayList <Drinks_Item>data;
    RecyclerView recyclerView;
    GridLayoutManager mLayoutManager;
    Drinks_DataAdapter dataAdaapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinkmenu);
        recyclerView = findViewById(R.id.drinks_recyclerView);

       /* imageitem1=findViewById(R.id.item1);
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
        imageitem15=findViewById(R.id.item15);
        if (checkPermission()) {

        } else {
            requestPermission();
        }*/
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });
        mLayoutManager = new GridLayoutManager(Drinkmenu.this,2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        userService = Api_Client.getClient().create(User_Service.class);
        Call<Drinks_Item>call=userService.drinksList("show_items_drinks");
        call.enqueue(new Callback<Drinks_Item>() {
            @Override
            public void onResponse(Call<Drinks_Item> call, Response<Drinks_Item> response) {
                Drinks_Item userlist=response.body();
                String status=userlist.status;
                ArrayList<Drinks_Item.DDatum>ddatumList=userlist.data;
                dataAdaapter=new Drinks_DataAdapter(Drinkmenu.this,ddatumList);
                recyclerView.setAdapter(dataAdaapter);



            }

            @Override
            public void onFailure(Call<Drinks_Item> call, Throwable t) {

            }
        });



    }





  /*  private boolean checkPermission() {

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
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }
    public void uploaditemthree(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }
    public void uploaditemfour(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 3);
    }
    public void uploaditemfive(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 4);
    }
    public void uploaditemsix(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 5);
    }
    public void uploaditemseven(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 6);
    }
    public void uploaditemeight(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }
    public void uploaditemnine(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 8);
    }
    public void uploaditemten(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 9);
    }
    public void uploaditemeleven(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 10);
    }
    public void uploaditemtwele(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 11);
    }
    public void uploaditemthirteen(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 12);
    }
    public void uploaditemfourteen(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 13);
    }
    public void uploaditemfifteen(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 14);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem1.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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

        if (requestCode == 1) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageitem2.setImageBitmap(photo);
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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
                Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
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

    }*/

    public void MenuDetails(View view){
        Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
        startActivity(intent);
    }

    public void Foodmenu(View view) {
        startActivity(new Intent(getApplicationContext(),Menuitems.class));
    }
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),Businesspage.class));

    }
}
