package com.txstate.gameapp;

public class Player {

    String username;
    int rpsHigh;
    int buttonHigh;
    int pianoHigh;
    int challengeFlag=0;
    String friend="";
    String uid;
    int rpsChoice=-1;

    public Player (String u, int r, int b, int p, int flag, String friend, String uid, int rpsChoice){
        this.username=u;
        this.rpsHigh=r;
        this.buttonHigh=b;
        this.pianoHigh=p;
        this.challengeFlag=flag;
        this.friend=friend;
        this.uid=uid;
        this.rpsChoice=rpsChoice;
    }


    public void setRpsChoice(int i){
        this.rpsChoice=i;
    }
    public void setChallengeFlag(int i){
        this.challengeFlag=i;
    }

    public int getRpsChoice(){
        return this.rpsChoice;
    }
}
