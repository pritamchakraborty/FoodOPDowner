package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodopdowner.Adapters.DataAdapter;
import com.example.android.foodopdowner.Adapters.Drinks_DataAdapter;
import com.example.android.foodopdowner.Helper.ImageChooser;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.DrinkItem;
import com.example.android.foodopdowner.model.FoodItem;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Drinkmenu extends AppCompatActivity implements ImageChooser {
    Toolbar toolbar;
    User_Service apiInterface;
    RecyclerView recyclerView;
    GridLayoutManager mLayoutManager;
    Drinks_DataAdapter dataAdaapter;
    private ArrayList<DrinkItem> data;
    EditText et_name, et_price;
    ImageView imageitem1;
    String owner_id = "";
    String buisness_id = "";
    byte[] imagearr;
    private Uri mCropImageUri;
    ProgressBar progressBar;
    Uri imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinkmenu);
        recyclerView = findViewById(R.id.drinks_recyclerView);
        toolbar = findViewById(R.id.toolbar);
        data = drinkItems();
        //Bundle bundle = getIntent().getExtras();
        //owner_id = bundle.getString("owner_id");
        //buisness_id = bundle.getString("business_id");

        //checking the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Businesspage.class));
            }
        });
        dataAdaapter = new Drinks_DataAdapter(Drinkmenu.this, data, this);
        mLayoutManager = new GridLayoutManager(Drinkmenu.this, 2);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(dataAdaapter);
        apiInterface = Api_Client.getClient().create(User_Service.class);

    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

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
                imageitem1.setImageURI(result.getUri());
                imageUrl = result.getUri();
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void chooseImage(int pos) {
    }

    @Override
    public void showAlertDialog(final int pos) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(Drinkmenu.this, R.style.CustomDialogTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_drink_menu_details, null);
        et_name = (view.findViewById(R.id.edit_itemname));
        imageitem1 = (view.findViewById(R.id.imageitem1));
        et_price = (view.findViewById(R.id.edit_price));
        final CheckBox check_veg = (view.findViewById(R.id.check_veg));
        final CheckBox check_non_veg = (view.findViewById(R.id.check_non_veg));
        TextView tv_save = (view.findViewById(R.id.saveitem));


        imageitem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(Drinkmenu.this);
            }
        });


        et_name.getText().toString();
        et_price.getText().toString();

        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.get(pos).setItem_image(imageUrl);
                data.get(pos).setItem_name(et_name.getText().toString());
                data.get(pos).setOriginal_price(et_price.getText().toString());
                addFoodToMenu();
                dataAdaapter.notifyDataSetChanged();

            }
        });


        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private ArrayList<DrinkItem> drinkItems() {
        ArrayList<DrinkItem> datumArrayList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            DrinkItem drinkItems = new DrinkItem();
            drinkItems.setId("" + i);
            datumArrayList.add(drinkItems);
        }
        return datumArrayList;
    }

    private void addFoodToMenu() {
        JsonObject js = new JsonObject();
        js.addProperty("menu_type", "1");
        js.addProperty("drink", "1");
        js.addProperty("business_id", "1567218728");
        js.addProperty("owner_id", "33");
        js.addProperty("menu_name", et_name.getText().toString());
        js.addProperty("price", et_price.getText().toString());
        js.addProperty("pic", "");
        js.addProperty("discount", "5");


        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<Food_Items> call = apiInterface.saveFoodMenu("1", "1", "1567218728", "33", et_name.getText().toString(), et_price.getText().toString(), "", "5");

        call.enqueue(new retrofit2.Callback<Food_Items>() {

            @Override
            public void onResponse(Call<Food_Items> call, Response<Food_Items> response) {
                System.out.println("atag" + response.body());
                if (response != null) {
                    Food_Items userlist = response.body();
                    if (userlist.getStatus().equals("success")) {
                        Toast.makeText(Drinkmenu.this, "Added to menu", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<Food_Items> call, Throwable t) {
                Toast.makeText(Drinkmenu.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }


    public void MenuDetails(View view){
        Intent intent=new Intent(getApplicationContext(),DrinkMenuDetails.class);
        startActivity(intent);
    }

    public void Foodmenu(View view) {
        startActivity(new Intent(getApplicationContext(), FoodMenu.class));
    }
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(),Businesspage.class));

    }

}
