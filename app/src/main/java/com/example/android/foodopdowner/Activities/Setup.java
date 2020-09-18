package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.foodopdowner.R;
import com.example.android.foodopdowner.model.Data;
import com.example.android.foodopdowner.model.EmailData;
import com.example.android.foodopdowner.model.SignupData;
import com.example.android.foodopdowner.model.SignupModel;
import com.example.android.foodopdowner.model.User;
import com.example.android.foodopdowner.rest.Api_Client;
import com.example.android.foodopdowner.rest.User_Service;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Setup extends AppCompatActivity {
    private static final String TAG = "this";
    User_Service apiInterface;
    private static final int CAMERA_REQUEST = 1888;
    EditText edfirstname,edlastname,edmobileno,edmailid,edmailcode,edpassword,edit_emailverification,edit_confirm_password;
    String emailpattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /*private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";*/
    private static final String PPASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

    boolean isPasswordValid;

    CircleImageView circleImageView;
    ProgressBar progressBar;
    Button btn_verify_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        edfirstname = findViewById(R.id.edit_firstname);
        progressBar = findViewById(R.id.pbHeaderProgress);
        btn_verify_email = findViewById(R.id.btn_verify_email);
        edit_emailverification = findViewById(R.id.edit_emailverification);
        edlastname = findViewById(R.id.edit_lastname);
        edmobileno = findViewById(R.id.edit_mobilenumber);
        edmailid = findViewById(R.id.edit_emailid);
        edmailcode = findViewById(R.id.edit_emailverification);
        edpassword = findViewById(R.id.edit_password);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        edit_emailverification.setVisibility(View.GONE);



        edmobileno.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btn_verify_email.setVisibility(View.VISIBLE);
            }
        });

        btn_verify_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        progressBar.setVisibility(View.VISIBLE);
        final JsonObject js = new JsonObject();
        js.addProperty("email", edmailid.getText().toString());

        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<EmailData> call = apiInterface.getEmail(edmailid.getText().toString());

        call.enqueue(new retrofit2.Callback<EmailData>() {

            @Override
            public void onResponse(Call<EmailData> call, Response<EmailData> response) {

                if(response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    edit_emailverification.setVisibility(View.VISIBLE);
                    String otp = response.body().getData().getOtp().toString();
                    edit_emailverification.setText(otp);
                    Toast.makeText(Setup.this, "Email verification code sent", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(Setup.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onFailure(Call<EmailData> call, Throwable t) {
                Toast.makeText(Setup.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }


    public void Next(View view) {

        try {
            String emailid = edmailid.getText().toString().trim();

            String s1 = edmailid.getText().toString();
            String s2 = edpassword.getText().toString().trim();

            String fstname = edfirstname.getText().toString();
            String lstname = edlastname.getText().toString();
            String mob = edmobileno.getText().toString();
            String mai = edmailid.getText().toString();
            if (edfirstname.getText().toString().length() == 0) {
                edfirstname.setError("First Name is Mandatory");
                edfirstname.requestFocus();
            } else if (edlastname.getText().toString().length() == 0) {
                edlastname.setError("Last Name is Mandatory");
                edlastname.requestFocus();
            } else if (edmobileno.getText().toString().length() == 0) {
                edmobileno.setError("Mobile Number is Mandatory");
                edmobileno.requestFocus();
            } else if (edmailid.getText().toString().length() == 0 || !emailid.matches(emailpattern)) {
                edmailid.setError("Email Id is Mandatory");
                edmailid.requestFocus();
            } else if (edmailcode.getText().toString().length() == 0) {
                edmailcode.setError("Please Enter Your Verification Code");
                edmailcode.requestFocus();

            } else if (edpassword.getText().toString().isEmpty()) {
                // Toast.makeText(getApplicationContext(),"enter Password ",Toast.LENGTH_SHORT).show();
                edpassword.setError("Enter Password");
            } else if (!edpassword.getText().toString().trim().matches(PPASSWORD_PATTERN)) {
                // Toast.makeText(getApplicationContext(),"Invalid password ",Toast.LENGTH_SHORT).show();
                edpassword.setError("Invalid Password");

            } else {
                checkInternetConenction();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
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
            final ProgressDialog progressDialog = new ProgressDialog(Setup.this);
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
                    signUpNewUser();
                }

            }).start();

        }else if (
                connec.getNetworkInfo(0).getState() ==
                        NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() ==
                                NetworkInfo.State.DISCONNECTED  ) {
            Toast.makeText(Setup.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;

    }


    public void signUpNewUser() {
        JsonObject js = new JsonObject();
        js.addProperty("user_category", 2);
        js.addProperty("first_name", edfirstname.getText().toString());
        js.addProperty("last_name", edlastname.getText().toString());
        js.addProperty("email", edmailid.getText().toString());
        js.addProperty("password", edpassword.getText().toString());
        js.addProperty("phone_no", edmobileno.getText().toString());


        apiInterface = Api_Client.getClient().create(User_Service.class);
        Call<SignupData> call = apiInterface.signUp("3", edfirstname.getText().toString(),
                edlastname.getText().toString(),edmailid.getText().toString(), edpassword.getText().toString(), edmobileno.getText().toString());

        call.enqueue(new retrofit2.Callback<SignupData>() {

            @Override
            public void onResponse(Call<SignupData> call, Response<SignupData> response) {
                System.out.println("atag" + response.body());

                if(response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    String buisness_id = response.body().getData().getBusinessId().toString();
                    String owner_id = response.body().getData().getOwnerId().toString();
                    Intent intent=new Intent(Setup.this,Setup2.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("buisness_id",buisness_id);
                    bundle.putString("owner_id",owner_id);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }



            }

            @Override
            public void onFailure(Call<SignupData> call, Throwable t) {
                Toast.makeText(Setup.this, "Request Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
