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
import android.widget.Button;
import android.widget.Toast;

import com.example.android.foodopdowner.Helper.Databasehelper;
import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.SignInResponse;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {
    TextInputEditText owneremail,ownerpassword;
    ConnectivityManager connectivityManager;
    Handler handle;
    ProgressDialog progressDialog;
    Button btn_login;
    Databasehelper db;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PPASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    User_Service apiinterface;
    List<SignInResponse> signInResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.activity_login);
        signInResponseList = new ArrayList<>();

        db = new Databasehelper(this);
        owneremail = findViewById(R.id.edit_mail);
        btn_login = findViewById(R.id.nextbutton);
        ownerpassword = findViewById(R.id.edit_pass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkInternetConenction();
                Intent intent=new Intent(login.this,Businesspage.class);
                Bundle bundle = new Bundle();
                bundle.putString("business_id","1567218728");
                bundle.putString("owner_id","33");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
        jsonObject.addProperty("email", owneremail.getText().toString());
        jsonObject.addProperty("password", ownerpassword.getText().toString());
        apiinterface = Api_Client.getClient().create(User_Service.class);
        Call<SignInResponse> call = apiinterface.ownerlogin(owneremail.getText().toString(), ownerpassword.getText().toString());
        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {

                if (response.isSuccessful()){
                    for(int i =0 ; i<signInResponseList.size();i++)
                    {
                        Toast.makeText(login.this, "login success", Toast.LENGTH_SHORT).show();
                        String buisness_id = response.body().getData().get(i).getBusinessId().toString();
                        String owner_id = response.body().getData().get(i).getOwnerId().toString();
                        Intent intent=new Intent(login.this,Businesspage.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("business_id",buisness_id);
                        bundle.putString("owner_id",owner_id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                }else {
                    Toast.makeText(login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {

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