package com.txstate.gameapp;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button bRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference databaseReference;
    private DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        etUsername = (EditText) findViewById(R.id.RegisterName);
        etPassword = (EditText) findViewById(R.id.RegisterPassword);
        bRegister = (Button) findViewById(R.id.bRegister);


        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        };
        bRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                registerUser();
            }

        });

    }



    private void saveUserInformation(){

        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String username = etUsername.getText().toString();
        int rpsHigh = 0;
        int buttonHigh=0;
        int pianoHigh=0;


        Player student = new Player(username,rpsHigh, buttonHigh, pianoHigh, "", "");
        databaseReference.child("Players").child(user.getUid()).setValue(student);


        Toast.makeText(this,"Information Saved...",Toast.LENGTH_LONG).show();


    }

    private void registerUser(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        username = username + "@email.com";

        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                    saveUserInformation();
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Problem with registration. Try again.", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });
    }

}
