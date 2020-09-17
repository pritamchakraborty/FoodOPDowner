package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.SignupModel;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class Setup3 extends AppCompatActivity {

    Switch franchise_switch;
    EditText franchise_name, franchise_email, franchise_phone, franchise_contact_person, store_id, sales_tax, surcharge;
    CheckBox cb_transaction;
    TextView tv_transaction_fee;
    Button next_btn;
    ProgressBar progressBar;
    User_Service apiInterface;
    String owner_id = "";
    String buisness_id = "";
    String franchise_yes = "";
    String transaction_fee = "10";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);

        franchise_switch = findViewById(R.id.simpleSwitch);
        franchise_name = findViewById(R.id.edit_franchise_name);
        tv_transaction_fee = findViewById(R.id.tv_transaction_fee);
        franchise_email = findViewById(R.id.edit_franchise_email);
        franchise_contact_person = findViewById(R.id.edit_francise_contact_person);
        franchise_phone = findViewById(R.id.edit_franchise_phone);
        store_id = findViewById(R.id.edit_store_id);
        sales_tax = findViewById(R.id.edit_sales_tax);
        surcharge = findViewById(R.id.edit_surcharge);
        cb_transaction = findViewById(R.id.cb_transaction);
        next_btn = findViewById(R.id.nextbutton);

        cb_transaction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                tv_transaction_fee.setVisibility(View.VISIBLE);
                tv_transaction_fee.setText("Currently" + transaction_fee + "%");
            }
        });

        checkFranchiseStatus();
        checkInternetConenction();


    }

    private void checkFranchiseStatus() {
        if(franchise_switch.isChecked())
        {
            franchise_yes = "1";
        }
        else
        {
            franchise_yes = "0";
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
            final ProgressDialog progressDialog = new ProgressDialog(Setup3.this);
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
                    SaveFranchiseData();
                }

            }).start();

        }else if (
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(Setup3.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;

    }

    private void SaveFranchiseData() {

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            owner_id = bundle.getString("owner_id");
            buisness_id = bundle.getString("buisness_id");

        }
        JsonObject js = new JsonObject();
        js.addProperty("owner_id", owner_id);
        js.addProperty("business_id", buisness_id);
        js.addProperty("franchise_status", franchise_yes);
        js.addProperty("franchise_name", franchise_name.getText().toString());
        js.addProperty("franchise_email", franchise_email.getText().toString());
        js.addProperty("store_id", store_id.getText().toString());
        js.addProperty("franchise_contact_person", franchise_contact_person.getText().toString());
        js.addProperty("franchise_phone", franchise_phone.getText().toString());
        js.addProperty("sales_tax", sales_tax.getText().toString());
        js.addProperty("surcharge", surcharge.getText().toString());
        js.addProperty("transaction_fee", transaction_fee);


        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<JsonObject> call = apiInterface.sendFranchisedetail(owner_id,buisness_id,franchise_yes,franchise_name.getText().toString()
        ,franchise_email.getText().toString(),store_id.getText().toString(),franchise_contact_person.getText().toString()
        ,franchise_phone.getText().toString(),sales_tax.getText().toString(),surcharge.getText().toString(),transaction_fee);

        call.enqueue(new retrofit2.Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("atag" + response.body());

                if(response.isSuccessful())
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("buisness_id",buisness_id);
                    bundle.putString("owner_id",owner_id);
                    Intent intent=new Intent(Setup3.this,Banksetup.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Setup3.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}