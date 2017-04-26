package com.txstate.gameapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class OnePlayer extends AppCompatActivity {

    TextView showValue;
    TextView showTime;
    int counter = 0;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_player);

        showValue = (TextView) findViewById(R.id.counterValue);
        showTime = (TextView) findViewById(R.id.timeD);

        final Button btn = (Button) findViewById(R.id.button3);

        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTime.setText("seconds remaining: " + millisUntilFinished / 1000);

            }


            @Override
            public void onFinish() {
                showTime.setText("Out of Time!");
                btn.setClickable(false);
            }
        }.start();


    }

    public void counterP1(View view)
    {
        counter++;
        showValue.setText(Integer.toString(counter));
    }

}

