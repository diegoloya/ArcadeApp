package com.txstate.gameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openRps(View view){
        Intent newRps = new Intent(this, RpsActivity.class);
        startActivity(newRps);
    }

    public void openButtonMash(View view){
        Intent newButtonMash = new Intent(this, ButtonMashActivity.class);
        startActivity(newButtonMash);
    }

    public void openPong(View view){
        Intent newPong = new Intent(this, PongActivity.class);
        startActivity(newPong);
    }

}