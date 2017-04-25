package com.txstate.gameapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyHolder extends RecyclerView.ViewHolder {

    TextView username;
    TextView highscore;
    TextView place;

    public MyHolder(View itemView) {
        super(itemView);
        username=(TextView) itemView.findViewById(R.id.tvUsername);
        highscore=(TextView) itemView.findViewById(R.id.tvScore);
        place = (TextView) itemView.findViewById(R.id.place);

    }
}
