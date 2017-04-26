package com.txstate.gameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ButtonMashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_mash);
    }

    public void onePlayer(View view){
        Intent newOnePlayer = new Intent(this, OnePlayer.class);
        startActivity(newOnePlayer);
    }

    public void twoPlayer(View view){
        Intent newTwoPlayer = new Intent(this, TwoPlayer.class);
        startActivity(newTwoPlayer);
    }

}