package com.txstate.gameapp;

/**
 * Created by diego on 4/24/17.
 */

public class RpsHighscoreDB {

    String score;
    String username;
    String flag;

    public RpsHighscoreDB(String s, String u, String f){
        this.score=s;
        this.username=u;
        this.flag=f;
    }

    public String getUsername() {
        return username;
    }

    String getScore(){
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}
