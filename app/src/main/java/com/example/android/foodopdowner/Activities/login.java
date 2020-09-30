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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
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
    private ArrayList<SignInResponse>signInResponses;
    Databasehelper db;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String PPASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
    User_Service apiinterface;
    List<SignInResponse> signInResponseList;
    String business_id = "";
    String owner_id = "";


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
                checkInternetConenction();
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

                    Toast.makeText(login.this, "Login Success", Toast.LENGTH_SHORT).show();
                    SignInResponse signInResponse = response.body();
                    if(!signInResponse.getData().isEmpty())
                    {
                        business_id = signInResponse.getData().get(0).getBusinessId();
                        owner_id = signInResponse.getData().get(0).getOwnerId();
                        Intent intent = new Intent(login.this, Businesspage.class);
                        intent.putExtra("business_id",business_id);
                        intent.putExtra("owner_id",owner_id);
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