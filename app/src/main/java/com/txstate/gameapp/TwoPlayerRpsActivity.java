package com.txstate.gameapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class TwoPlayerRpsActivity extends AppCompatActivity {

    ImageView rockButton;
    ImageView paperButton;
    ImageView scissorButton;
    ImageView zombieChoice;
    TextView wins;
    TextView losses;

    int winCount;
    int loseCount;

    private TextView results;
    private String list[] = {"Rock", "Paper", "Scissors"};
    private int images[] = {R.drawable.rock_button, R.drawable.paper_button, R.drawable.scissors_button};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_rps);

        rockButton = (ImageView) findViewById(R.id.buttonRock);
        paperButton = (ImageView) findViewById(R.id.buttonPaper);
        scissorButton = (ImageView) findViewById(R.id.buttonScissors);
        zombieChoice = (ImageView) findViewById(R.id.titleId);
        results = (TextView) findViewById(R.id.textViewId);
        wins = (TextView) findViewById(R.id.wins);
        losses = (TextView) findViewById(R.id.losses);
        winCount=0;
        loseCount=0;




        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //First is to create a random number between 0-2.
                Random randNum = new Random();
                int rand = randNum.nextInt(list.length);

                //Display the correct image on the screen based on what the other player selected.
                zombieChoice.setImageResource(images[rand]);

                //Determines if the user won/lost/tied. Sets the test accordingly.
                if(list[rand].equals("Scissors")){
                    //R.string.winText is the text set from the strings.xml file.
                    winCount+=1;
                    String temp = Integer.toString(winCount);
                    wins.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.winText));
                }else if(list[rand].equals("Paper")){
                    results.setText(list[rand] + "," + getString(R.string.loseText));
                    loseCount+=1;
                    String temp = Integer.toString(loseCount);
                    losses.setText(temp);
                }else{
                    results.setText(list[rand] + "," + getString(R.string.tieText));
                }
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //First is to create a random number between 0-2.
                Random randNum = new Random();
                int rand = randNum.nextInt(list.length);

                //Display the correct image on the screen based on what the computer selected.
                zombieChoice.setImageResource(images[rand]);

                //Determines if the user won/lost/tied. Sets the test accordingly.
                if(list[rand].equals("Rock")){
                    //R.string.winText is the text set from the strings.xml file.
                    winCount+=1;
                    String temp = Integer.toString(winCount);
                    wins.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.winText));

                }else if(list[rand].equals("Scissors")){
                    loseCount+=1;
                    String temp = Integer.toString(loseCount);
                    losses.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.loseText));
                }else{
                    results.setText(list[rand] + "," + getString(R.string.tieText));
                }
            }
        });

        scissorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //First is to create a random number between 0-2.
                Random randNum = new Random();
                int rand = randNum.nextInt(list.length);

                //Display the correct image on the screen based on what the computer selected.
                zombieChoice.setImageResource(images[rand]);

                //Determines if the user won/lost/tied. Sets the test accordingly.
                if(list[rand].equals("Paper")){
                    winCount+=1;
                    String temp = Integer.toString(winCount);
                    wins.setText(temp);
                    //R.string.winText is the text set from the strings.xml file.
                    results.setText(list[rand] + "," + getString(R.string.winText));
                }else if(list[rand].equals("Rock")){
                    loseCount+=1;
                    String temp = Integer.toString(loseCount);
                    losses.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.loseText));
                }else{
                    results.setText(list[rand] + "," + getString(R.string.tieText));
                }
            }
        });

    }
}
