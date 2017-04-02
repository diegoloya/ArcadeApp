package com.txstate.gameapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class MenuActivity extends AppCompatActivity {

    Button bSignOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
        bSignOut = (Button) findViewById(R.id.logout);

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

    }

    public void openRps(View view){
        Intent newRps = new Intent(this, RpsActivity.class);
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