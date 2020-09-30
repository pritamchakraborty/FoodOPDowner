package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;


public class Deliveryboy extends AppCompatActivity {

    TextView txcurrenttime,Back_owner;
    Toolbar toolbar;
    RadioGroup rg_delivery_choice;
    RadioButton rb_yes, rb_no;
    User_Service apiInterface;
    EditText edit_fstname,edit_lstname,edit_delvierymobile,edit_pinid,edit_reenterpin;
    Button nextbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliveryboy);
        Back_owner=findViewById(R.id.backtoowner);
        txcurrenttime = findViewById(R.id.curenttime);
        rg_delivery_choice = findViewById(R.id.rg_delivery_choice);
        rb_yes = findViewById(R.id.rb_yes);
        rb_no = findViewById(R.id.rb_no);
        toolbar=findViewById(R.id.toolbar);
        edit_fstname=findViewById(R.id.edit_fstname);
        edit_lstname = findViewById(R.id.edit_lstname);
        edit_delvierymobile = findViewById(R.id.edit_delvierymobile);
        edit_pinid=findViewById(R.id.edit_pinid);
        edit_reenterpin=findViewById(R.id.edit_reenterpin);
        nextbutton=findViewById(R.id.nextbutton);

        isDeliveryChecked();
       nextbutton.setOnClickListener(new View.OnClickListener() {
         @Override
            public void onClick(View v) {
              deliveryBoy();
            }
       });


        //for getting system real time
        final Handler someHandler = new Handler(getMainLooper());
        someHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                txcurrenttime.setText(new SimpleDateFormat("HH:mm:ss", Locale.US).format(new Date()));
                someHandler.postDelayed(this, 1000);
            }
        }, 10);




        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });

    }


    public void Deliveryview(View view) {

        try {


            String fstname = edit_fstname.getText().toString();
            String lstname = edit_lstname.getText().toString();
            String mob = edit_delvierymobile.getText().toString();
            String pin = edit_pinid.getText().toString();
            if (edit_fstname.getText().toString().length() == 0) {
                edit_fstname.setError("First Name is Mandatory");
                edit_fstname.requestFocus();
            } else if (edit_lstname.getText().toString().length() == 0) {
                edit_lstname.setError("Last Name is Mandatory");
                edit_lstname.requestFocus();
            } else if (edit_delvierymobile.getText().toString().length() == 0) {
                edit_delvierymobile.setError("Mobile Number is Mandatory");
                edit_delvierymobile.requestFocus();
            } else if (edit_pinid.getText().toString().length() == 4) {
                edit_pinid.setError("Please Enter Your Pin");
                edit_pinid.requestFocus();

            } else if (edit_reenterpin.getText().toString().isEmpty()) {
                // Toast.makeText(getApplicationContext(),"enter Password ",Toast.LENGTH_SHORT).show();
                edit_reenterpin.setError("Please Re-enter Pin");
            }  else {
                checkInternetConenction();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

        public void deliveryBoy() {
            JsonObject js = new JsonObject();
            js.addProperty("owner_id", "51");
            js.addProperty("business_id", "1234567890");
            js.addProperty("first_name", edit_fstname.getText().toString());
            js.addProperty("last_name", edit_lstname.getText().toString());
            js.addProperty("phone_number", edit_delvierymobile.getText().toString());
            js.addProperty("delivery_boy_status", "yes");
            js.addProperty("delivary_boy_pin", edit_pinid.getText().toString());

            apiInterface = Api_Client.getClient().create(User_Service.class);
            Call<JsonObject> call = apiInterface.deliveryBoy("51", "1234567890", edit_fstname.getText().toString(),
                    edit_lstname.getText().toString(), edit_delvierymobile.getText().toString(), "yes", edit_pinid.getText().toString());

            call.enqueue(new retrofit2.Callback<JsonObject>() {

                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    System.out.println("atag" + response.body());

                    JsonObject js = response.body();
                    if (js != null) {
                        if (js.get("status").getAsString().equalsIgnoreCase("success")) {
                            Toast.makeText(Deliveryboy.this, "Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Deliveryboy.this, Deliveryaddress.class);
                            startActivity(intent);

                        } else if (js.get("status").getAsString().equalsIgnoreCase("exists")) {
                            Toast.makeText(Deliveryboy.this, "Exist", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Deliveryboy.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Deliveryboy.this, "Failed", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(Deliveryboy.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }

        public void isDeliveryChecked () {
            rg_delivery_choice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (rb_yes.isChecked()) {
                        Back_owner.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
                        Back_owner.invalidate();


                    } else if (rb_no.isChecked()) {
                        Back_owner.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                        Back_owner.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Deliveryboy.this, Businesspage.class);
                                startActivity(intent);
                            }
                        });
                    }
                }
            });
        }
    public boolean checkInternetConenction() {

        ConnectivityManager connec
                = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);

        // Check for network connections
        if (connec.getNetworkInfo(0).getState() ==
                NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() ==
                        NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            final ProgressDialog progressDialog = new ProgressDialog(Deliveryboy.this);
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
                    deliveryBoy();
                    isDeliveryChecked();
                }

            }).start();

        } else if (
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(Deliveryboy.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
        /*public void Deliveryview (View view){
            startActivity(new Intent(getApplicationContext(), Deliveryaddress.class));

        }*/
    }

}
