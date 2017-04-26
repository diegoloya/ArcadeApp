package com.txstate.gameapp;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class TwoPlayer extends AppCompatActivity {

    TextView showValue;
    TextView showValue2;
    TextView showTime;
    int counter = 0;
    int counter2 = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player);

        showValue = (TextView) findViewById(R.id.counterValue);
        showValue2 = (TextView) findViewById(R.id.counterValue2);
        showTime = (TextView) findViewById(R.id.timeD);

        final Button btn = (Button) findViewById(R.id.button4);
        final Button btn2 = (Button) findViewById(R.id.button5);

        timer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTime.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                showTime.setText("Out of Time!");
                btn.setClickable(false);
                btn2.setClickable(false);
            }
        }.start();
    }

    public void oneScore(View view){
        counter++;
        showValue.setText(Integer.toString(counter));
    }

    public void twoScore(View v){
        counter2++;
        showValue2.setText(Integer.toString(counter2));
    }
}

