package com.txstate.gameapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static java.lang.Integer.valueOf;

public class RpsHighscores extends AppCompatActivity {

    DatabaseReference mDatabase;
    ArrayList<RpsHighscoreDB> entries = new ArrayList<>();
    RecyclerView recyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rps_highscores);

        recyclerview=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getUsers();

    }

    private void getUsers() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String hs = "hs";
        mDatabase.child("Highscores").orderByChild("flag").equalTo(hs).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                        entries.clear();
                        while (items.hasNext()) {

                            DataSnapshot item = items.next();
                            String username,flag;
                            String score;
                            username = item.child("username").getValue(String.class);
                            score = item.child("score").getValue().toString();
                            flag = item.child("flag").getValue().toString();
                            RpsHighscoreDB entry = new RpsHighscoreDB(score, username,flag);
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
                        MyAdapter adapter=new MyAdapter(RpsHighscores.this,entries);
                        recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }
}