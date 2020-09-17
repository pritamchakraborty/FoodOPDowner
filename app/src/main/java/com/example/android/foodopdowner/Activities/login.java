package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.android.foodopdowner.Helper.Databasehelper;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    TextInputEditText owneremail,ownerpassword;
    ConnectivityManager connectivityManager;
    Handler handle;
    ProgressDialog progressDialog;
    Databasehelper db;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PPASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    User_Service apiinterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.activity_login);

        db = new Databasehelper(this);
        owneremail = findViewById(R.id.edit_mail);
        ownerpassword = findViewById(R.id.edit_pass);
    }

    public void Signin(View view) {
        String emailid = owneremail.getText().toString().trim();

        if (owneremail.getText().toString().isEmpty()) {
            owneremail.setError("Email is Mandatory");
            owneremail.requestFocus();
        } else if (!owneremail.getText().toString().trim().matches(emailpattern)) {
            owneremail.setError("Invalid email");
            owneremail.requestFocus();
        }else if (ownerpassword.getText().toString().isEmpty()) {
            ownerpassword.setError("Please Enter Your Password");
            ownerpassword.requestFocus();

        } else if (!ownerpassword.getText().toString().trim().matches(PPASSWORD_PATTERN)) {
            ownerpassword.setError("Please Enter Correct Password");
            ownerpassword.requestFocus();
        }else {

            checkInternetConenction();
        }
    }

    public void callLogin() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ownemail", owneremail.getText().toString());
        jsonObject.addProperty("ownpassword", ownerpassword.getText().toString());
        apiinterface = Api_Client.getClient().create(User_Service.class);
        Call<JsonObject> call = apiinterface.ownerlogin(owneremail.getText().toString(), ownerpassword.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject js=response.body();
                if (js!=null){

                    if (js.get("status").getAsString().equalsIgnoreCase("success")){
                        Toast.makeText(login.this,"Login Success",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(login.this,Businesspage.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(login.this, "An Error Occured", Toast.LENGTH_SHORT).show();

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
            final ProgressDialog progressDialog = new ProgressDialog(login.this);
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
                   /* String s1 = edmail.getText().toString();
                    String s2 = edpassword.getText().toString();*/

                    // startActivity(new Intent(getApplicationContext(), Businesspage.class));
                    callLogin();
                }

            }).start();


        } else if (
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                NetworkInfo.State.DISCONNECTED) {
            Toast.makeText(login.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;


    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}