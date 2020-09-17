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

import com.example.android.foodopdowner.R;

public class Banksetup extends AppCompatActivity {
EditText edbankname,edaccountno,edroutingno,edcity;
    Toolbar toolbar;
    TextView tv_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banksetup);
        edbankname=findViewById(R.id.edit_bankname);
        tv_pay = (findViewById(R.id.tv_pay));
        edaccountno=findViewById(R.id.edit_accountnumber);
        edroutingno=findViewById(R.id.edit_routingnumber);
        edcity=findViewById(R.id.edit_city);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Setup2.class));
            }
        });

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
                AlertDialog alert11 = builder1.create();
                alert11.show();
                startActivity(new Intent(getApplicationContext(),Businesspage.class));
            }
        });
    }
    public void Payment(View view)
    {
        startActivity(new Intent(getApplicationContext(),Drinkmenu.class));
    }
}
