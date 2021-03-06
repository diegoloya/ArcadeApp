package com.txstate.gameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class RpsActivity extends AppCompatActivity {

    ImageView rockButton;
    ImageView paperButton;
    ImageView scissorButton;
    ImageView cpuChoice;
    ImageView myChoice;
    ImageView strike1;
    ImageView strike2;
    ImageView strike3;

    TextView wins;
    TextView losses;


    private TextView results;
    private String list[] = {"Rock", "Paper", "Scissors"};
    private int images[] = {R.drawable.rock_button, R.drawable.paper_button, R.drawable.scissors_button};
    int winCount;
    int loseCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps);

        strike1=(ImageView) findViewById(R.id.x1);
        strike2=(ImageView) findViewById(R.id.x2);
        strike3=(ImageView) findViewById(R.id.x3);
        rockButton = (ImageView) findViewById(R.id.buttonRock);
        paperButton = (ImageView) findViewById(R.id.buttonPaper);
        scissorButton = (ImageView) findViewById(R.id.buttonScissors);
        myChoice = (ImageView) findViewById(R.id.myChoice);
        cpuChoice = (ImageView) findViewById(R.id.computerChoice);
        results = (TextView) findViewById(R.id.currentPlayer);
        wins = (TextView) findViewById(R.id.wins);
        losses = (TextView) findViewById(R.id.losses);
        winCount=0;
        loseCount=0;

        play();
    }

    public int play(){
        myChoice.setVisibility(View.INVISIBLE);
        cpuChoice.setVisibility(View.INVISIBLE);



        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myChoice.setImageResource(images[0]);
                myChoice.setVisibility(View.VISIBLE);

                //First is to create a random number between 0-2.
                Random randNum = new Random();
                int rand = randNum.nextInt(list.length);

                cpuChoice.setImageResource(images[rand]);
                cpuChoice.setVisibility(View.VISIBLE);


                //Determines if the user won/lost/tied. Sets the test accordingly.
                if (list[rand].equals("Scissors")) {
                    //R.string.winText is the text set from the strings.xml file.
                    winCount += 1;
                    String temp = Integer.toString(winCount);
                    wins.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.winText));
                } else if (list[rand].equals("Paper")) {
                    results.setText(list[rand] + "," + getString(R.string.loseText));
                    loseCount += 1;
                    String temp = Integer.toString(loseCount);
//                    losses.setText(temp);
                    if (loseCount==1)
                        strike1.setVisibility(View.VISIBLE);
                    if (loseCount==2)
                        strike2.setVisibility(View.VISIBLE);
                    if(loseCount==3){
                        strike3.setVisibility(View.VISIBLE);
                        Intent i = new Intent(RpsActivity.this, RpsScore.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("score", Integer.toString(winCount));
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                } else {
                    results.setText(list[rand] + "," + getString(R.string.tieText));
                }
            }
        });

        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myChoice.setImageResource(images[1]);
                myChoice.setVisibility(View.VISIBLE);

                //First is to create a random number between 0-2.
                Random randNum = new Random();
                int rand = randNum.nextInt(list.length);
                cpuChoice.setImageResource(images[rand]);
                cpuChoice.setVisibility(View.VISIBLE);



                //Determines if the user won/lost/tied. Sets the test accordingly.
                if (list[rand].equals("Rock")) {
                    //R.string.winText is the text set from the strings.xml file.
                    winCount += 1;
                    String temp = Integer.toString(winCount);
                    wins.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.winText));

                } else if (list[rand].equals("Scissors")) {
                    loseCount += 1;
                    String temp = Integer.toString(loseCount);
//                    losses.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.loseText));
                    if (loseCount==1)
                        strike1.setVisibility(View.VISIBLE);
                    if (loseCount==2)
                        strike2.setVisibility(View.VISIBLE);
                    if(loseCount==3){
                        strike3.setVisibility(View.VISIBLE);
                        Intent i = new Intent(RpsActivity.this, RpsScore.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("score", Integer.toString(winCount));
                        i.putExtras(bundle);
                        startActivity(i);
                    }

                } else {
                    results.setText(list[rand] + "," + getString(R.string.tieText));
                }
            }
        });

        scissorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myChoice.setImageResource(images[2]);
                myChoice.setVisibility(View.VISIBLE);
                //First is to create a random number between 0-2.
                Random randNum = new Random();
                int rand = randNum.nextInt(list.length);
                cpuChoice.setImageResource(images[rand]);
                cpuChoice.setVisibility(View.VISIBLE);



                //Determines if the user won/lost/tied. Sets the test accordingly.
                if (list[rand].equals("Paper")) {
                    winCount += 1;
                    String temp = Integer.toString(winCount);
                    wins.setText(temp);
                    //R.string.winText is the text set from the strings.xml file.
                    results.setText(list[rand] + "," + getString(R.string.winText));
                } else if (list[rand].equals("Rock")) {
                    loseCount += 1;
                    String temp = Integer.toString(loseCount);
//                    losses.setText(temp);
                    results.setText(list[rand] + "," + getString(R.string.loseText));
                    if (loseCount==1)
                        strike1.setVisibility(View.VISIBLE);
                    if (loseCount==2)
                        strike2.setVisibility(View.VISIBLE);
                    if(loseCount==3){
                        strike3.setVisibility(View.VISIBLE);
                        Intent i = new Intent(RpsActivity.this, RpsScore.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("score", Integer.toString(winCount));
                        i.putExtras(bundle);
                        startActivity(i);
                    }

                } else {
                    results.setText(list[rand] + "," + getString(R.string.tieText));
                }
            }
        });
        return loseCount;
    }

}
