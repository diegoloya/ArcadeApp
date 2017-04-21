package com.txstate.gameapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NumberPlayers extends AppCompatActivity {

    Button onePlayer;
    Button twoPlayer;
    TextView addUser;
    Button invite;
    private DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_players);

        onePlayer = (Button) findViewById(R.id.onePlayer);
        twoPlayer = (Button) findViewById(R.id.twoPlayer);
        addUser = (TextView) findViewById(R.id.friendUID);
        invite = (Button) findViewById(R.id.invite);



        onePlayer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(NumberPlayers.this, RpsActivity.class));
            }

        });

        twoPlayer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                addUser.setVisibility(View.VISIBLE);
                invite.setVisibility(View.VISIBLE);
            }

        });


//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        invite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabase.child("Players").orderByChild("username").equalTo(addUser.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            mDatabase.child("Players").child(//trying to find player to send notification
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });


}
}
