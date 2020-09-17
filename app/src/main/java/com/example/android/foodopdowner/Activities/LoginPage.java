package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class LoginPage extends AppCompatActivity {
    TextInputEditText edmail,edpassword;
/*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        edmail=findViewById(R.id.edit_email);
        edpassword=findViewById(R.id.edit_password);
    }

    public void Signin(View view) {
        if(edmail.getText().toString().length()==0)
        {
            edmail.setError("Email is Mandatory");
            edmail.requestFocus();
        }

        else if(edpassword.getText().toString().length()==0)
        {
            edpassword.setError("Please Enter Your Password");
            edmail.requestFocus();

        }
        else {
            startActivity(new Intent(getApplicationContext(), Businesspage.class));
        }
    }
*/

}
