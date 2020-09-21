package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodopdowner.Adapters.DataAdapter;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.Food_Items;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    FloatingActionButton btn_food_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuitems);
        recyclerView = findViewById(R.id.recyclerView);
        btn_food_menu = findViewById(R.id.add_food);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });

        btn_food_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //before inflating the custom alert dialog layout, we will get the current activity viewgroup
                ViewGroup viewGroup = findViewById(android.R.id.content);

                //then we will inflate the custom alert dialog xml that we created
                View dialogView = LayoutInflater.from(Menuitems.this).inflate(R.layout.activity_menu_details, viewGroup, false);


                //Now we need an AlertDialog.Builder object
                AlertDialog.Builder builder = new AlertDialog.Builder(Menuitems.this);

                EditText et_name = (dialogView.findViewById(R.id.edit_itemname));
                ImageView imageitem1 = (dialogView.findViewById(R.id.imageitem1));
                EditText et_price = (dialogView.findViewById(R.id.edit_price));
                final CheckBox check_veg = (dialogView.findViewById(R.id.check_veg));
                final CheckBox check_non_veg = (dialogView.findViewById(R.id.check_non_veg));
                TextView tv_save = (dialogView.findViewById(R.id.saveitem));


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
                        Toast.makeText(Menuitems.this, "Item saved", Toast.LENGTH_SHORT).show();
                    }
                });


                check_veg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(check_veg.isChecked())
                        {
                            Toast.makeText(Menuitems.this, "veg", Toast.LENGTH_SHORT).show();
                            check_non_veg.setChecked(false);
                        }
                        else
                        {
                            Toast.makeText(Menuitems.this, "non veg", Toast.LENGTH_SHORT).show();
                            check_non_veg.setChecked(true);
                        }
                    }
                });

                //setting the view of the builder to our custom view that we already inflated
                builder.setView(dialogView);

                //finally creating the alert dialog and displaying it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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
                if(response!=null)
                {
                    //Food_Items userlist=response.body();
                   // String text=userlist.status;
                   // ArrayList<Food_Items.Datum>datumList=userlist.data;
                    //dataAdapter=new DataAdapter(Menuitems.this,datumList);
                   // recyclerView.setAdapter(dataAdapter);

                }

                else
                {
                    Toast.makeText(Menuitems.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Food_Items> call, Throwable t) {
                Toast.makeText(Menuitems.this,"Response is Fail",Toast.LENGTH_SHORT).show();

            }
        });

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

     startActivity(new Intent(getApplicationContext(),Businesspage.class));

    }
}