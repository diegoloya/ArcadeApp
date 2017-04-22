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


        mDatabase = FirebaseDatabase.getInstance().getReference();
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String friend = addUser.getText().toString();
                mDatabase.child("Uids").child(friend).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            String friendUID = dataSnapshot.getValue(String.class);
                            Toast.makeText(NumberPlayers.this, "Player invited. Wait for response"+friendUID, Toast.LENGTH_SHORT).show();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            mDatabase.child("Players").child(friendUID).child("challengeFlag").setValue(1);   //sents user's id to friends data

                            mDatabase.child("Players").child(friendUID).child("friend").setValue(user.getUid());
                        }
                        else
                            Toast.makeText(NumberPlayers.this, "Player "+friend+" does not exist. Try again.", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


}
}
