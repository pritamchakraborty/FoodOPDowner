package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodopdowner.Adapters.CurrentOrdersAdapter;
import com.example.android.foodopdowner.Adapters.Drinks_DataAdapter;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.CurrentOrdersModel;
import com.example.android.foodopdowner.model.FoodItem;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;

public class Orderspage extends AppCompatActivity {
    private static final String TAG = "this";
    TextView txcurrenttime;
    Button deliveryboy;
    Toolbar toolbar;
    CardView view_order;
    List<CurrentOrdersModel>currentOrdersModels = new ArrayList<>();
    LinearLayoutManager mLayoutManager;
    RecyclerView rv_current_orders;
    CurrentOrdersAdapter currentOrdersAdapter;
    User_Service apiInterface;
    String buisness_id = "";
    String owner_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderspage);
        view_order = findViewById(R.id.view_order);
        txcurrenttime = findViewById(R.id.curenttime);
        deliveryboy = findViewById(R.id.btn_delivery_boy);
        rv_current_orders = findViewById(R.id.rv_current_orders);

        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txcurrenttime.setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);


        owner_id = getIntent().getStringExtra("owner_id");
        buisness_id = getIntent().getStringExtra("business_id");

        rv_current_orders = (RecyclerView) findViewById(R.id.rv_current_orders);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_current_orders.setLayoutManager(layoutManager);

        getOrders();

    }

    private void getOrders() {
        JsonObject js = new JsonObject();
        js.addProperty("business_id", buisness_id);
        js.addProperty("owner_id", owner_id);

        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<CurrentOrdersModel> call = apiInterface.currentOrder(buisness_id, owner_id);

        call.enqueue(new retrofit2.Callback<CurrentOrdersModel>() {

            @Override
            public void onResponse(Call<CurrentOrdersModel> call, Response<CurrentOrdersModel> response) {
                    CurrentOrdersModel currentOrdersModel = response.body();
                    Toast.makeText(Orderspage.this, "Order updated", Toast.LENGTH_SHORT).show();
                    currentOrdersAdapter = new CurrentOrdersAdapter(getApplicationContext(), currentOrdersModels);
                    rv_current_orders.setAdapter(currentOrdersAdapter);

            }

        @Override
        public void onFailure (Call <CurrentOrdersModel> call, Throwable t){
            Toast.makeText(Orderspage.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

        }
    });
}


    public void Admin(View view) {
        startActivity(new Intent(getApplicationContext(), Admin.class));
    }

    public void Deliverytrack(View view) {
        startActivity(new Intent(getApplicationContext(), Deliverytrack.class));

    }

}
