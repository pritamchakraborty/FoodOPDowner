package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class Banksetup extends AppCompatActivity {
    EditText edbankname,edaccountno,edroutingno,edcity;
    Toolbar toolbar;
    TextView tv_pay,setPay;
    User_Service apiInterface;
    String owner_id = "";
    String buisness_id = "";
    String buisnessname ="";
    String firstaddress ="";
    String phoneno ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksetup);
        edbankname=findViewById(R.id.edit_bankname);
        tv_pay = (findViewById(R.id.tv_pay));
        edaccountno=findViewById(R.id.edit_accountnumber);
        edroutingno=findViewById(R.id.edit_routingnumber);
        edcity=findViewById(R.id.edit_city);
        setPay=findViewById(R.id.setPay);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Setup2.class));
            }
        });

        Bundle bundle = getIntent().getExtras();
        owner_id = bundle.getString("owner_id");
        buisness_id = bundle.getString("business_id");
        buisnessname = bundle.getString("buisness_name");
        firstaddress = bundle.getString("address");
        phoneno = bundle.getString("phone_no");

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(Banksetup.this);
                builder1.setMessage("Thank you for your payment");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                // bankDetails();
                AlertDialog alert11 = builder1.create();
                alert11.show();
                bankDetails();
            }
        });




    }
    private  void bankDetails()
    {
        JsonObject js = new JsonObject();
        js.addProperty("owner_id", "142");
        js.addProperty("business_id", "1224965296");
        js.addProperty("bank_name", edbankname.getText().toString());
        js.addProperty("acc_number", edaccountno.getText().toString());
        js.addProperty("routing_number", edroutingno.getText().toString());
        js.addProperty("city", edcity.getText().toString());
        js.addProperty("setup_fee",setPay.getText().toString());
        js.addProperty("txn_id","SJFDKFKJGI12344");
        js.addProperty("payment_gross", "500");
        js.addProperty("currency_code","USD");
        js.addProperty("onwer_email","shivangirenukappa@gmail.com");
        js.addProperty("payment_status", "success");

        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<JsonObject> call = apiInterface.getBank("142", "1224965296",edbankname.getText().toString(),
                edaccountno.getText().toString(),edroutingno.getText().toString(), edcity.getText().toString(), setPay.getText().toString(),"SJFDKFKJGI12344",
                "500","USD","shivangirenukappa@gmail.com","success");

        call.enqueue(new retrofit2.Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("atag" + response.body());

                JsonObject js = response.body();
                if (js != null) {
                    if (js.get("status").getAsString().equalsIgnoreCase("success")) {
                        Toast.makeText(Banksetup.this, "Thank you for payment", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Banksetup.this,SplashScreenActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("business_id",buisness_id);
                        bundle.putString("owner_id",owner_id);
                        bundle.putString("buisness_name",buisnessname);
                        bundle.putString("address",firstaddress);
                        bundle.putString("phone_no",phoneno);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
                        Toast.makeText(Banksetup.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Banksetup.this, "Failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Banksetup.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
    /*public void Payment(View view)
    {
        startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
    }*/
}
