package com.txstate.gameapp;

public class Player {

    String username;
    int rpsHigh;
    int buttonHigh;
    int pianoHigh;
    String rpsMessage="";
    String friend="";

    public Player (String u, int r, int b, int p, String message, String friend){
        this.username=u;
        this.rpsHigh=r;
        this.buttonHigh=b;
        this.pianoHigh=p;
        this.rpsMessage=message;
        this.friend=friend;
    }
}
