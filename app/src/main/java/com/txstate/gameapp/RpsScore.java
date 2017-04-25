package com.txstate.gameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static java.lang.Integer.valueOf;

public class RpsScore extends AppCompatActivity {

    Button bHighscore;
    Button playAgain;
    TextView newHighscore;
    TextView score;
    DatabaseReference mDatabase;
    ArrayList<RpsHighscoreDB> entries = new ArrayList<>();
    String scoreStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps_score);

        Bundle bundle = getIntent().getExtras();
        scoreStr = bundle.getString("score");       //save data from previous activity

        score=(TextView) findViewById(R.id.score);
        bHighscore= (Button) findViewById(R.id.bhighscore);
        playAgain=(Button) findViewById(R.id.playAgain);
        newHighscore=(TextView) findViewById(R.id.highscore);

        bHighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RpsScore.this, RpsHighscores.class));
            }
        });

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RpsScore.this, RpsActivity.class));
            }
        });
        String scoreStr2 = "Score: "+ scoreStr;
        score.setText(scoreStr2);                    //set it to the TextView

        getUsers(valueOf(scoreStr));

        //Toast.makeText(this, entries.get(1).getUsername(), Toast.LENGTH_SHORT).show();
        //getPlace(valueOf(scoreStr),entries);
    }

    private void getUsers(int score2) {
        final int score=score2;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String hs = "hs";
        mDatabase.child("Highscores").orderByChild("flag").equalTo(hs).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        entries.clear();
                       // Toast.makeText(RpsScore.this, "Diego"+dataSnapshot, Toast.LENGTH_LONG).show();


                        while (items.hasNext()) {
                            DataSnapshot item = items.next();
                            String username, flag;
                            String score;
                            username = item.child("username").getValue(String.class);
                            score = item.child("score").getValue().toString();
                            flag = item.child("flag").getValue().toString();
                            RpsHighscoreDB entry = new RpsHighscoreDB(score, username, flag);
                            entries.add(entry);
                        }
                        Collections.sort(entries, new Comparator<RpsHighscoreDB>() {

                            @Override
                            public int compare(RpsHighscoreDB a, RpsHighscoreDB b) {
                                if (valueOf(a.getScore()) > valueOf(b.getScore()))
                                    return -1;
                                if (valueOf(a.getScore()) < valueOf(b.getScore()))
                                    return 1;
                                return 0;
                            }
                        });
                        getPlace(score,entries);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }

    private void getPlace(int score, ArrayList<RpsHighscoreDB> arrayList){

        ArrayList<RpsHighscoreDB> testList = new ArrayList<>();
        for (int i=0;i<10;i++){
            testList.add(arrayList.get(i));
        }
        if (valueOf(testList.get(9).getScore())>score){
            //did not get highscore
            Toast.makeText(this, "Did not set a highscore. Try again!", Toast.LENGTH_SHORT).show();
        }
        else{
            newHighscore.setVisibility(View.VISIBLE);
            //new highscore
            int position=0;
            while(valueOf(testList.get(position).getScore())>score){
                position++;
            }
            final int tempPosition=position;
            final int tempScore=score;
            mDatabase = FirebaseDatabase.getInstance().getReference();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            for (int i = tempPosition;i<=9;i++){

                mDatabase.child("Highscores").child(Integer.toString(i+2)).child("score").setValue(testList.get(i).getScore());
                mDatabase.child("Highscores").child(Integer.toString(i+2)).child("username").setValue(arrayList.get(i).getUsername());

            }
            mDatabase.child("Players").child(user.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.getValue(String.class);

                    mDatabase.child("Highscores").child(Integer.toString(tempPosition+1)).child("score").setValue(tempScore);
                    mDatabase.child("Highscores").child(Integer.toString(tempPosition+1)).child("username").setValue(username);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }





}
