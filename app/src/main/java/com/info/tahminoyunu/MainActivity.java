package com.info.tahminoyunu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void btnNormalOyun(View v) {
        startActivity(new Intent(MainActivity.this, NormalOyunActivity.class));
    }

    public void btnSureliOyun(View v) {
        startActivity(new Intent(MainActivity.this, SureliOyunActivity.class));
    }

    public void btnCikis(View v) {
        cikisYap();
    }

    @Override
    public void onBackPressed() {
        cikisYap();
    }

    public void cikisYap() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}