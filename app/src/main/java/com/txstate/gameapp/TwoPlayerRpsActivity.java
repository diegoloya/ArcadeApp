package com.txstate.gameapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TwoPlayerRpsActivity extends AppCompatActivity {

    ImageView rockButton;
    ImageView paperButton;
    ImageView scissorButton;
//    ImageView friendChoice;
    ImageView myChoiceImage;
    ImageView friendChoiceImage;
    TextView wins;
    TextView losses;


    int winCount;
    int loseCount;

    private TextView results;
    private String list[] = {"Rock", "Paper", "Scissors"};
    private int images[] = {R.drawable.rock_button, R.drawable.paper_button, R.drawable.scissors_button};

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_player_rps);

        rockButton = (ImageView) findViewById(R.id.buttonRock);
        paperButton = (ImageView) findViewById(R.id.buttonPaper);
        scissorButton = (ImageView) findViewById(R.id.buttonScissors);
        myChoiceImage = (ImageView) findViewById(R.id.currentChoice);
        friendChoiceImage = (ImageView) findViewById(R.id.friendChoice);
        results = (TextView) findViewById(R.id.currentPlayer);
        wins = (TextView) findViewById(R.id.wins);
        losses = (TextView) findViewById(R.id.losses);
        winCount = 0;
        loseCount = 0;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myChoiceImage.setVisibility(View.INVISIBLE);
        friendChoiceImage.setVisibility(View.INVISIBLE);


        final Player player1 = new Player("",9,9,9,9,"","",-1);
        final Player player2 = new Player("",9,9,9,9,"","",-1);

        rockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.setRpsChoice(0);
                myChoiceImage.setImageResource(images[0]);
                myChoiceImage.setVisibility(View.VISIBLE);
            }
        });
        paperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.setRpsChoice(1);
                myChoiceImage.setImageResource(images[0]);
                myChoiceImage.setVisibility(View.VISIBLE);
            }
        });
        scissorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1.setRpsChoice(2);
                myChoiceImage.setImageResource(images[0]);
                myChoiceImage.setVisibility(View.VISIBLE);
            }
        });


        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friend = dataSnapshot.getValue(String.class);
                mDatabase.child("Players").child(friend).child("rpsChoice").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int friendChoice = dataSnapshot.getValue(int.class);
                        if (friendChoice!=-1){
                            player2.setRpsChoice(friendChoice);
                            Toast.makeText(TwoPlayerRpsActivity.this, "Friend has chosen", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        if (player1.getRpsChoice()==0){
            if (player2.getRpsChoice()==1){
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
            }
        }
        if (player1.getRpsChoice()==1){
            if (player2.getRpsChoice()==2){
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
            }
        }
        if (player1.getRpsChoice()==2){
            if (player2.getRpsChoice()==0){
                Toast.makeText(this, "You Lose!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "TIE!", Toast.LENGTH_SHORT).show();
        }

    }






    public void one(){                                                                                                                              //no one has chosen, will have chosen by exit
        Toast.makeText(this, "Im doing 1!!", Toast.LENGTH_SHORT).show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {                   //get friends uid
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String friendUID = dataSnapshot.getValue(String.class);

                mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {                     //check first if he has any input
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int choice = dataSnapshot.getValue(int.class);


                        if (choice == -1) {
                            mDatabase.child("Players").child(user.getUid()).child("score").setValue("1");
                            rockButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child("Players").child(user.getUid()).child("score").setValue(1);             //!!!!!!!!!!!!!

                                    //rock HAS BEEN SELECTED
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(0);
                                    myChoiceImage.setImageResource(images[0]);
                                    myChoiceImage.setVisibility(View.VISIBLE);

                                    //Wait for friends choice
                                    Toast.makeText(TwoPlayerRpsActivity.this, "here", Toast.LENGTH_SHORT).show();
                                }
                            });
                            paperButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child("Players").child(user.getUid()).child("score").setValue(1);             //!!!!!!!!!!!!!

                                    //paper HAS BEEN SELECTED
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(1);
                                    myChoiceImage.setImageResource(images[1]);
                                    myChoiceImage.setVisibility(View.VISIBLE);
                                    //Wait for friends choice


                                }
                            });
                            scissorButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child("Players").child(user.getUid()).child("score").setValue(1);             //!!!!!!!!!!!!!

                                    //scissors HAS BEEN SELECTED
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(2);
                                    myChoiceImage.setImageResource(images[2]);
                                    myChoiceImage.setVisibility(View.VISIBLE);
                                    //Wait for friends choice

                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public void two(){
        Toast.makeText(this, "Im doing 2!!", Toast.LENGTH_SHORT).show();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {                   //get friends uid
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String friendUID = dataSnapshot.getValue(String.class);

                mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {                     //check first if he has any input
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int choice = dataSnapshot.getValue(int.class);
                        if (choice != -1) {              //my turn he has selected

//                            mDatabase.child("Players").child(user.getUid()).child("score").setValue("2");
                            //Toast.makeText(TwoPlayerRpsActivity.this, "Friend has chosen. It's your turn!", Toast.LENGTH_SHORT).show();
                            mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                            rockButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child("Players").child(user.getUid()).child("threeFlag").setValue(1);             //!!!!!!!!!!!!!

                                    //rock HAS BEEN SELECTED
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(0);
                                    myChoiceImage.setImageResource(images[0]);
                                    myChoiceImage.setVisibility(View.VISIBLE);

                                    //Check friend's choice
                                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int friendsChoice = dataSnapshot.getValue(int.class);
                                            if (friendsChoice == 0) {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "TIE", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                                mDatabase.child("Players").child(friendUID).child("rpsChoice").setValue(-1);

                                            } else if (friendsChoice == 1) {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
                                                loseCount++;
                                                String temp = Integer.toString(loseCount);
                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                                mDatabase.child("Players").child(friendUID).child("rpsChoice").setValue(-1);


                                            } else {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
                                                winCount++;
                                                String temp = Integer.toString(winCount);
                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                                mDatabase.child("Players").child(friendUID).child("rpsChoice").setValue(-1);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            });

                            paperButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mDatabase.child("Players").child(user.getUid()).child("threeFlag").setValue(1);             //!!!!!!!!!!!!!

                                    //paper HAS BEEN SELECTED
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(1);
                                    myChoiceImage.setImageResource(images[1]);
                                    myChoiceImage.setVisibility(View.VISIBLE);
                                    //Check friend's choice
                                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int friendsChoice = dataSnapshot.getValue(int.class);
                                            if (friendsChoice == 1) {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "TIE", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                                            } else if (friendsChoice == 2) {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
                                                loseCount++;
                                                String temp = Integer.toString(loseCount);
                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                                            } else {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
                                                winCount++;
                                                String temp = Integer.toString(winCount);
                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });

                                }
                            });
                            scissorButton.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    mDatabase.child("Players").child(user.getUid()).child("threeFlag").setValue(1);             //!!!!!!!!!!!!!

                                    //scissors HAS BEEN SELECTED
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(2);
                                    myChoiceImage.setImageResource(images[2]);
                                    myChoiceImage.setVisibility(View.VISIBLE);
                                    //Check friend's choice
                                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int friendsChoice = dataSnapshot.getValue(int.class);
                                            if (friendsChoice == 2) {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "TIE", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                                            } else if (friendsChoice == 0) {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
                                                loseCount++;
                                                String temp = Integer.toString(loseCount);
                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                                            } else {
                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                                                friendChoiceImage.setImageResource(images[friendsChoice]);
                                                friendChoiceImage.setVisibility(View.VISIBLE);
                                                winCount++;
                                                String temp = Integer.toString(winCount);
                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void three(){

        Toast.makeText(this, "Im doing three!", Toast.LENGTH_SHORT).show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String friendUID = dataSnapshot.getValue(String.class);

                mDatabase.child("Players").child(friendUID).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    final int choice = dataSnapshot.getValue(int.class);
                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int myChoice = dataSnapshot.getValue(int.class);
                                            if (myChoice==choice){
                                                //tie
                                            }
                                            if (myChoice == 0){
                                                //i chose rock
                                                if (choice==1){
                                                    //i lose
                                                    Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
                                                    friendChoiceImage.setImageResource(images[choice]);
                                                    friendChoiceImage.setVisibility(View.VISIBLE);
                                                    loseCount++;
                                                    String temp = Integer.toString(loseCount);
                                                    wins.setText(temp);
                                                }
                                                else{
                                                    //i win
                                                    Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                                                    friendChoiceImage.setImageResource(images[choice]);
                                                    friendChoiceImage.setVisibility(View.VISIBLE);
                                                    winCount++;
                                                    String temp = Integer.toString(winCount);
                                                    wins.setText(temp);
                                                }
                                            }
                                            if (myChoice == 1){
                                                //paper
                                                if (choice==0){
                                                    //i win
                                                    Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                                                    friendChoiceImage.setImageResource(images[choice]);
                                                    friendChoiceImage.setVisibility(View.VISIBLE);
                                                    winCount++;
                                                    String temp = Integer.toString(winCount);
                                                    wins.setText(temp);

                                                }
                                                if (choice==2){
                                                    //i lose
                                                    Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
                                                    friendChoiceImage.setImageResource(images[choice]);
                                                    friendChoiceImage.setVisibility(View.VISIBLE);
                                                    loseCount++;
                                                    String temp = Integer.toString(loseCount);
                                                    wins.setText(temp);
                                                }
                                            }
                                            if (myChoice==2){
                                                //scissors
                                                if (choice==0){
                                                    //i lose
                                                    Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
                                                    friendChoiceImage.setImageResource(images[choice]);
                                                    friendChoiceImage.setVisibility(View.VISIBLE);
                                                    loseCount++;
                                                    String temp = Integer.toString(loseCount);
                                                    wins.setText(temp);
                                                }
                                                if (choice==1){
                                                    //i win
                                                    Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
                                                    friendChoiceImage.setImageResource(images[choice]);
                                                    friendChoiceImage.setVisibility(View.VISIBLE);
                                                    winCount++;
                                                    String temp = Integer.toString(winCount);
                                                    wins.setText(temp);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uid = dataSnapshot.getValue(String.class);
                mDatabase.child("Players").child(uid).child("score").setValue(0);
                mDatabase.child("Players").child(uid).child("rpsChoice").setValue(-1);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        mDatabase.child("Players").child(user.getUid()).child("score").setValue(0);
        mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
    }




    @Override
    public void onBackPressed(){
        super.onBackPressed();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String friend = dataSnapshot.getValue(String.class);
                //unfriend
                mDatabase.child("Players").child(friend).child("friend").setValue("");
                mDatabase.child("Players").child(friend).child("challengeFlag").setValue(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("Players").child(user.getUid()).child("friend").setValue("");
        mDatabase.child("Players").child(user.getUid()).child("challengeFlag").setValue(0);
        mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
    }
}


//        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                final String friendUID = dataSnapshot.getValue(String.class);
//
//                mDatabase.child("Players").child(friendUID).child("rpsChoice").addValueEventListener(new ValueEventListener() {    //check first if he has any input
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        int choice = dataSnapshot.getValue(int.class);
//
//
//                        if (choice==-1){                //no ones turn
//                            mDatabase.child("Players").child(user.getUid()).child("score").setValue("1");
//                            rockButton.setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v){
//                                    //rock HAS BEEN SELECTED
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(0);
//                                    myChoiceImage.setImageResource(images[0]);
//                                    myChoiceImage.setVisibility(View.VISIBLE);
//
//                                    //Wait for friends choice
//                                    Toast.makeText(TwoPlayerRpsActivity.this, "here", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            paperButton.setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v){
//                                    //paper HAS BEEN SELECTED
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(1);
//                                    myChoiceImage.setImageResource(images[1]);
//                                    myChoiceImage.setVisibility(View.VISIBLE);
//                                    //Wait for friends choice
//
//
//                                }
//                            });
//                            scissorButton.setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v){
//                                    //scissors HAS BEEN SELECTED
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(2);
//                                    myChoiceImage.setImageResource(images[2]);
//                                    myChoiceImage.setVisibility(View.VISIBLE);
//                                    //Wait for friends choice
//
//                                }
//                            });
//                        }
//
//
//                        else if (choice!=-1){              //my turn he has selected
//                            mDatabase.child("Players").child(user.getUid()).child("score").setValue("2");
//                            //Toast.makeText(TwoPlayerRpsActivity.this, "Friend has chosen. It's your turn!", Toast.LENGTH_SHORT).show();
//                            mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                            rockButton.setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v){
//                                    //rock HAS BEEN SELECTED
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(0);
//                                    myChoiceImage.setImageResource(images[0]);
//                                    myChoiceImage.setVisibility(View.VISIBLE);
//
//                                    //Check friend's choice
//                                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            int friendsChoice = dataSnapshot.getValue(int.class);
//                                            if (friendsChoice==0){
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "TIE", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                                mDatabase.child("Players").child(friendUID).child("rpsChoice").setValue(-1);
//
//                                            }
//                                            else if (friendsChoice==1){
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                loseCount++;
//                                                String temp = Integer.toString(loseCount);
//                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                                mDatabase.child("Players").child(friendUID).child("rpsChoice").setValue(-1);
//
//
//
//                                            }
//                                            else{
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                winCount++;
//                                                String temp = Integer.toString(winCount);
//                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                                mDatabase.child("Players").child(friendUID).child("rpsChoice").setValue(-1);
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//                                }
//                            });
//
//                            paperButton.setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v){
//                                    //paper HAS BEEN SELECTED
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(1);
//                                    myChoiceImage.setImageResource(images[1]);
//                                    myChoiceImage.setVisibility(View.VISIBLE);
//                                    //Check friend's choice
//                                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            int friendsChoice = dataSnapshot.getValue(int.class);
//                                            if (friendsChoice==1){
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "TIE", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                                            }
//                                            else if (friendsChoice==2){
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                loseCount++;
//                                                String temp = Integer.toString(loseCount);
//                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                                            }
//                                            else{
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                winCount++;
//                                                String temp = Integer.toString(winCount);
//                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//
//                                }
//                            });
//                            scissorButton.setOnClickListener(new View.OnClickListener(){
//                                @Override
//                                public void onClick(View v){
//                                    //scissors HAS BEEN SELECTED
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(2);
//                                    myChoiceImage.setImageResource(images[2]);
//                                    myChoiceImage.setVisibility(View.VISIBLE);
//                                    //Check friend's choice
//                                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            int friendsChoice = dataSnapshot.getValue(int.class);
//                                            if (friendsChoice==2){
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "TIE", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                                            }
//                                            else if (friendsChoice==0){
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU LOSE!", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                loseCount++;
//                                                String temp = Integer.toString(loseCount);
//                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                                            }
//                                            else{
//                                                Toast.makeText(TwoPlayerRpsActivity.this, "YOU WIN!", Toast.LENGTH_SHORT).show();
//                                                friendChoiceImage.setImageResource(images[friendsChoice]);
//                                                friendChoiceImage.setVisibility(View.VISIBLE);
//                                                winCount++;
//                                                String temp = Integer.toString(winCount);
//                                                wins.setText(temp);
//                                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//                                }
//                            });
//                        }
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//
//        Toast.makeText(this, "Outside", Toast.LENGTH_SHORT).show();
////
//
//        mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                public void onDataChange(DataSnapshot dataSnapshot){
//                    final String friendUID = dataSnapshot.getValue(String.class);
//
//                    mDatabase.child("Players").child(friendUID).child("rpsChoice").addValueEventListener()
//
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });






//        rockButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myChoiceImage.setImageResource(images[0]);
//                myChoiceImage.setVisibility(View.VISIBLE);
//
//                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(0);
//                mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {   //get friends uid
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String friendUID = dataSnapshot.getValue(String.class);
//                        mDatabase.child("Players").child(friendUID).child("rpsChoice").addValueEventListener(new ValueEventListener() {   //get friends uid
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                int choice = dataSnapshot.getValue(int.class);
//                                //wait
//                                if (choice == -1) {
//                                    Toast.makeText(TwoPlayerRpsActivity.this, "Wait for other player's choice", Toast.LENGTH_SHORT).show();
//                                }
//                                //tie
//                                if (choice == 0) {
//                                    //Display the correct image on the screen based on what the other player selected.
//                                    friendChoiceImage.setImageResource(images[0]);
//                                    results.setText(list[0] + "," + getString(R.string.tieText));
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//                                }
//                                //lose
//                                if (choice == 1) {
//                                    friendChoiceImage.setImageResource(images[1]);
//                                    results.setText(list[1] + "," + getString(R.string.loseText));
//                                    loseCount += 1;
//                                    String temp = Integer.toString(loseCount);
//                                    losses.setText(temp);
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//
//                                }
//                                //win
//                                if (choice == 2) {
//                                    friendChoiceImage.setImageResource(images[2]);
//                                    winCount += 1;
//                                    String temp = Integer.toString(winCount);
//                                    wins.setText(temp);
//                                    results.setText(list[2] + "," + getString(R.string.winText));
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//                                }
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//            }
//
//        });
//
//        paperButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myChoiceImage.setImageResource(images[1]);
//                myChoiceImage.setVisibility(View.VISIBLE);
//
//
//                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(1);
//                mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {   //get friends uid
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String friendUID = dataSnapshot.getValue(String.class);
//                        mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {   //get friends uid
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                int choice = dataSnapshot.getValue(int.class);
//                                if (choice == -1) {
//                                    Toast.makeText(TwoPlayerRpsActivity.this, "Wait for other player's choice", Toast.LENGTH_SHORT).show();
//                                }
//                                //tie
//                                if (choice == 1) {
//                                    //Display the correct image on the screen based on what the other player selected.
//                                    friendChoiceImage.setImageResource(images[1]);
//                                    results.setText(list[1] + "," + getString(R.string.tieText));
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//
//                                }
//                                //lose
//                                if (choice == 2) {
//                                    friendChoiceImage.setImageResource(images[2]);
//                                    results.setText(list[2] + "," + getString(R.string.loseText));
//                                    loseCount += 1;
//                                    String temp = Integer.toString(loseCount);
//                                    losses.setText(temp);
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//
//                                }
//                                if (choice == 0) {
//                                    friendChoiceImage.setImageResource(images[0]);
//                                    winCount += 1;
//                                    String temp = Integer.toString(winCount);
//                                    wins.setText(temp);
//                                    results.setText(list[0] + "," + getString(R.string.winText));
//                                }
//                                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                mDatabase = FirebaseDatabase.getInstance().getReference();
//                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                        String friend = dataSnapshot.getValue(String.class);
//                                        mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                    }
//                                    @Override
//                                    public void onCancelled(DatabaseError databaseError) {
//                                    }
//                                });
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
//
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//            }
//
//        });
//
//        scissorButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myChoiceImage.setImageResource(images[2]);
//                myChoiceImage.setVisibility(View.VISIBLE);
//
//
//                mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(2);
//                mDatabase.child("Players").child(user.getUid()).child("friend").addListenerForSingleValueEvent(new ValueEventListener() {   //get friends uid
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        String friendUID = dataSnapshot.getValue(String.class);
//                        mDatabase.child("Players").child(friendUID).child("rpsChoice").addListenerForSingleValueEvent(new ValueEventListener() {   //get friends uid
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                int choice = dataSnapshot.getValue(int.class);
//                                //wait
//                                if (choice == -1) {
//                                    Toast.makeText(TwoPlayerRpsActivity.this, "Wait for other player's choice", Toast.LENGTH_SHORT).show();
//                                }
//                                //tie
//                                if (choice == 2) {
//                                    //Display the correct image on the screen based on what the other player selected.
//                                    friendChoiceImage.setImageResource(images[2]);
//                                    results.setText(list[2] + "," + getString(R.string.tieText));
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//                                }
//                                //lose
//                                if (choice == 0) {
//                                    friendChoiceImage.setImageResource(images[0]);
//                                    results.setText(list[0] + "," + getString(R.string.loseText));
//                                    loseCount += 1;
//                                    String temp = Integer.toString(loseCount);
//                                    losses.setText(temp);
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//
//                                }
//                                //win
//                                if (choice == 1) {
//                                    friendChoiceImage.setImageResource(images[1]);
//                                    winCount += 1;
//                                    String temp = Integer.toString(winCount);
//                                    wins.setText(temp);
//                                    results.setText(list[1] + "," + getString(R.string.winText));
//                                    mDatabase.child("Players").child(user.getUid()).child("rpsChoice").setValue(-1);
//                                    myChoiceImage.setVisibility(View.INVISIBLE);
//
//                                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                                    mDatabase.child("Players").child(user.getUid()).child("friend").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            String friend = dataSnapshot.getValue(String.class);
//                                            mDatabase.child("Players").child(friend).child("rpsChoice").setValue(-1);
//                                        }
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//                                        }
//                                    });
//                                }
//                            }
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                            }
//                        });
//                    }
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                    }
//                });
//            }
//
//        });


//        paperButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //First is to create a random number between 0-2.
//                Random randNum = new Random();
//                int rand = randNum.nextInt(list.length);
//
//                //Display the correct image on the screen based on what the computer selected.
//                zombieChoice.setImageResource(images[rand]);
//
//                //Determines if the user won/lost/tied. Sets the test accordingly.
//                if(list[rand].equals("Rock")){
//                    //R.string.winText is the text set from the strings.xml file.
//                    winCount+=1;
//                    String temp = Integer.toString(winCount);
//                    wins.setText(temp);
//                    results.setText(list[rand] + "," + getString(R.string.winText));
//
//                }else if(list[rand].equals("Scissors")){
//                    loseCount+=1;
//                    String temp = Integer.toString(loseCount);
//                    losses.setText(temp);
//                    results.setText(list[rand] + "," + getString(R.string.loseText));
//                }else{
//                    results.setText(list[rand] + "," + getString(R.string.tieText));
//                }
//            }
//        });

//        scissorButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //First is to create a random number between 0-2.
//                Random randNum = new Random();
//                int rand = randNum.nextInt(list.length);
//
//                //Display the correct image on the screen based on what the computer selected.
//                zombieChoice.setImageResource(images[rand]);
//
//                //Determines if the user won/lost/tied. Sets the test accordingly.
//                if(list[rand].equals("Paper")){
//                    winCount+=1;
//                    String temp = Integer.toString(winCount);
//                    wins.setText(temp);
//                    //R.string.winText is the text set from the strings.xml file.
//                    results.setText(list[rand] + "," + getString(R.string.winText));
//                }else if(list[rand].equals("Rock")){
//                    loseCount+=1;
//                    String temp = Integer.toString(loseCount);
//                    losses.setText(temp);
//                    results.setText(list[rand] + "," + getString(R.string.loseText));
//                }else{
//                    results.setText(list[rand] + "," + getString(R.string.tieText));
//                }
//            }
//        });

