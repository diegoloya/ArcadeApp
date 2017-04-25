package com.txstate.gameapp;

import android.content.Intent;
import android.support.annotation.NonNull;
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


public class MenuActivity extends AppCompatActivity {

    Button bSignOut;
    Button bChallengeRPS;
    TextView currentUser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    int message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        bSignOut = (Button) findViewById(R.id.logout);
        bChallengeRPS = (Button) findViewById(R.id.bChallengeRPS);
        currentUser = (TextView) findViewById(R.id.current);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("Players").child(user.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String current = dataSnapshot.getValue(String.class);
                currentUser.setText(current);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                }
            }
        };

        bSignOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                signOut();
            }
        });


        mDatabase.child("Players").child(user.getUid()).child("challengeFlag").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                message = dataSnapshot.getValue(int.class);
                if (message==1){
                    bChallengeRPS.setVisibility(View.VISIBLE);
                    bChallengeRPS.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v){
                            final FirebaseUser user = mAuth.getCurrentUser();
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String friend = dataSnapshot.getValue(String.class);
                                    mDatabase.child("Players").child(friend).child("challengeFlag").setValue(2);
                                    mDatabase.child("Players").child(friend).child("friend").setValue(user.getUid());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            startActivity(new Intent(MenuActivity.this, TwoPlayerRpsActivity.class));
                        }
                    });
                }
                else if (message==2){
                    Toast.makeText(MenuActivity.this, "Friend accepted challenge", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MenuActivity.this, TwoPlayerRpsActivity.class));
                }
                else
                    bChallengeRPS.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }

    public void openRps(View view){
        Intent newRps = new Intent(this, NumberPlayers.class);
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

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
    }


}