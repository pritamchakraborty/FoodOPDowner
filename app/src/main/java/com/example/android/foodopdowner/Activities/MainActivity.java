package com.example.android.foodopdowner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.example.android.foodopdowner.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//    private void launchApp() {
//        PackageManager pm = this.getPackageManager();
//        boolean isInstalled = isPackageInstalled("com.example.android.foodopdowner", pm);
//        if(!isInstalled)
//        {
//            startActivity(new Intent(getApplicationContext(),SplashScreenActivity.class));
//
//        }
//        else
//        {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//
//        }
//    }

//    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
//        try {
//            packageManager.getPackageInfo(packageName, 0);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }

    public void Setup(View view) {
        startActivity(new Intent(getApplicationContext(),Setup.class));
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(),login.class));

    }
}
