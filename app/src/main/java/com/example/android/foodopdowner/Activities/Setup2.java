package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.Utils.FileUtil;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class Setup2 extends AppCompatActivity {
    private static final String TAG = "this";
    Button btn_next;
    private static final int CAMERA_REQUEST = 1888;
    EditText buisness_name, address, street, city, pin, country, buisness_phone, delivery_boy_pin, edit_buisness_id;
    ImageButton im_logo;
    User_Service apiInterface;
    String owner_id ="";
    String buisness_id ="";
    String buisnessname ="";
    String first_address ="";
    String phoneno ="";
    private Uri mCropImageUri;
    Uri imageUri;
    String imageUrl = "";
    Bitmap photo;

    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    public static final int MULTIPLE_PERMISSIONS = 10; // code you want.
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

        if (checkPermissions()){
        //  permissions  granted.
         }

        Bundle bundle = getIntent().getExtras();

        owner_id = bundle.getString("owner_id");
        buisness_id = bundle.getString("business_id");

        edit_buisness_id.setText(buisness_id);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        if (checkPermission()) {
//
//        } else {
//            requestPermission();
//        }
        im_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSelectImageClick(view);
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkInternetConenction();
                Intent intent = new Intent(Setup2.this, Setup3.class);
                Bundle bundle = new Bundle();
                bundle.putString("business_id", buisness_id);
                bundle.putString("owner_id", owner_id);
                bundle.putString("buisness_name", buisness_name.getText().toString());
                bundle.putString("address", address.getText().toString());
                bundle.putString("phone_no", buisness_phone.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Start pick image activity with chooser.
     */
    public void onSelectImageClick(View view) {
        CropImage.startPickImageActivity(this);
    }




    private void saveBuisnessDetails() {

        //File file = new File(getRealPathFromURI(imageUri));

        // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
        //Uri tempUri = getImageUri(getApplicationContext(), photo);

        // CALL THIS METHOD TO GET THE ACTUAL PATH
        File file = new File(FileUtil.getPath(imageUri, this)); // *this* here is context, which can be Activity/Fragment
        Log.d(TAG, "saveBuisnessDetails: "  +file);


        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

//
//        JsonObject js = new JsonObject();
//        js.addProperty("owner_id",owner_id);
//        js.addProperty("business_id",buisness_id);
//        js.addProperty("business_name", buisness_name.getText().toString());
//        js.addProperty("address", address.getText().toString());
//        js.addProperty("street", street.getText().toString());
//        js.addProperty("city", city.getText().toString());
//        js.addProperty("phone_no", buisness_phone.getText().toString());
//        js.addProperty("country", country.getText().toString());
//        js.addProperty("delivary_boy_pin", delivery_boy_pin.getText().toString());
//        js.addProperty("pin", pin.getText().toString());

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("owner_id", owner_id)
                .addFormDataPart("business_id", buisness_id)
                .addFormDataPart("business_name", buisness_name.getText().toString())
                .addFormDataPart("address",  address.getText().toString())
                .addFormDataPart("street", street.getText().toString())
                .addFormDataPart("city", city.getText().toString())
                .addFormDataPart("phone_no", buisness_phone.getText().toString())
                .addFormDataPart("country", country.getText().toString())
                .addFormDataPart("delivary_boy_pin", delivery_boy_pin.getText().toString())
                .addFormDataPart("pin", pin.getText().toString());


        if (file.exists()) {
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, bos);

            builder.addFormDataPart("business_logo", file.getName(), RequestBody.create(MultipartBody.FORM, bos.toByteArray()));
        }
            apiInterface = Api_Client.getClient().create(User_Service.class);
            Call<JsonObject> call = apiInterface.buisness_setup(owner_id, buisness_id, buisness_name.getText().toString(), address.getText().toString(), street.getText().toString()
                    , city.getText().toString(), buisness_phone.getText().toString(), country.getText().toString(), delivery_boy_pin.getText().toString(),body, pin.getText().toString());

        //RequestBody requestBody = builder.build();
         //Call<JsonObject> call = apiInterface.buisness_setup(requestBody);


        call.enqueue(new retrofit2.Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                     Log.d(TAG, "onResponse: " + response.body().toString());

                    JsonObject js = response.body();
                    if (js != null) {
                        if (js.get("status").getAsString().equalsIgnoreCase("success")) {
                            Toast.makeText(Setup2.this, "Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Setup2.this, Setup3.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("buisness_id", buisness_id);
                            bundle.putString("owner_id", owner_id);
                            bundle.putString("buisness_name", buisness_name.getText().toString());
                            bundle.putString("address", address.getText().toString());
                            bundle.putString("phone_no", buisness_phone.getText().toString());
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


    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(this, data);
            //photo = (Bitmap) data.getExtras().get("data");
            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                im_logo.setImageURI(result.getUri());
                Toast.makeText(this, "Cropping successful" + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Start crop image activity for the given image.
     */
    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }



//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            // required permissions granted, start crop image activity
//            startCropImageActivity(mCropImageUri);
//        } else {
//            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
//        }
//    }




//    private boolean checkPermission() {
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission is not granted
//            return false;
//        }
//        return true;
//    }

//    private void requestPermission() {
//
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.CAMERA},
//                0);
//    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;

                        }

                    }
                    // Show permissionsDenied
                    //Toast.makeText(this, "Permissions denied by user", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
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



//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }

//    public String getRealPathFromURI(Uri uri) {
//        String path = "";
//        if (getContentResolver() != null) {
//            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//            if (cursor != null) {
//                cursor.moveToFirst();
//                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//                path = cursor.getString(idx);
//                cursor.close();
//            }
//        }
//        return path;
//    }


}