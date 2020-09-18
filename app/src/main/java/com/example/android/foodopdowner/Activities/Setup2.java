package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Response;

public class Setup2 extends AppCompatActivity {
    private static final String TAG = "this";
    Button btn_next;
    private static final int CAMERA_REQUEST = 1888;
    EditText buisness_name, address, street, city, pin, country, buisness_phone, delivery_boy_pin, edit_buisness_id;
    ImageView im_logo;
    String photoUrl = "";
    User_Service apiInterface;
    String owner_id ="";
    String buisness_id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        btn_next = (findViewById(R.id.nextbutton));
        im_logo = (findViewById(R.id.image_view_logo));
        buisness_name = findViewById(R.id.edit_businessname);
        edit_buisness_id = findViewById(R.id.edit_buisness_id);
        address = findViewById(R.id.edit_address);
        street = findViewById(R.id.edit_Street);
        city = findViewById(R.id.edit_city);
        pin = findViewById(R.id.edit_pin);
        country = findViewById(R.id.edit_country);
        buisness_phone = findViewById(R.id.edit_buisness_phone);
        delivery_boy_pin = findViewById(R.id.edit_deliverypin);

        Bundle bundle = getIntent().getExtras();

        owner_id = bundle.getString("owner_id");
        buisness_id = bundle.getString("buisness_id");

        edit_buisness_id.setText(buisness_id);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        if (checkPermission()) {

        } else {
            requestPermission();
        }
        im_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInternetConenction();

            }
        });

    }

    private void saveBuisnessDetails() {


        JsonObject js = new JsonObject();
        js.addProperty("owner_id",owner_id);
        js.addProperty("buisness_id",buisness_id);
        js.addProperty("buisness_name", buisness_name.getText().toString());
        js.addProperty("address", address.getText().toString());
        js.addProperty("street", street.getText().toString());
        js.addProperty("city", city.getText().toString());
        js.addProperty("phone_no", buisness_phone.getText().toString());
        js.addProperty("country", country.getText().toString());
        js.addProperty("delivery_boy_pin", delivery_boy_pin.getText().toString());
        js.addProperty("business_logo", photoUrl);
        js.addProperty("pin", pin.getText().toString());


        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<JsonObject> call = apiInterface.buisness_setup(owner_id,buisness_id,buisness_name.getText().toString(),address.getText().toString(),street.getText().toString()
        ,city.getText().toString(),buisness_phone.getText().toString(),country.getText().toString(),delivery_boy_pin.getText().toString(),photoUrl,pin.getText().toString());

        call.enqueue(new retrofit2.Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("atag" + response.body());

                JsonObject js = response.body();
                if (js != null) {
                    if (js.get("status").getAsString().equalsIgnoreCase("success")) {
                        Toast.makeText(Setup2.this, "Saved", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Setup2.this, Setup3.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("buisness_id",buisness_id);
                        bundle.putString("owner_id",owner_id);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Setup2.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Setup2.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private String getEncoadedString(Bitmap bitmap){
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
        byte[]imageArr=os.toByteArray();
        return Base64.encodeToString(imageArr,Base64.URL_SAFE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                photoUrl=getEncoadedString(photo);
                im_logo.setImageBitmap(photo);
            }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
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

    public boolean checkInternetConenction() {

        ConnectivityManager connec
                =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if ( connec.getNetworkInfo(0).getState() ==
                NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED ) {
            final ProgressDialog progressDialog = new ProgressDialog(Setup2.this);
            progressDialog.setMessage("Logging in");
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
            progressDialog.setCancelable(false);
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    progressDialog.dismiss();
                    saveBuisnessDetails();
                }

            }).start();

        }else if (
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(Setup2.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;

    }


}