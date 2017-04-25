package com.txstate.gameapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

    public class MyAdapter extends RecyclerView.Adapter<MyHolder> {


        Context context;
        ArrayList<RpsHighscoreDB> entries;
        DatabaseReference mDatabase;


        public MyAdapter(Context context, ArrayList<RpsHighscoreDB> entries) {
            this.context = context;
            this.entries = entries;
        }

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.user_row, parent,false);
            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, final int position) {
            holder.highscore.setText(entries.get(position).getScore());
            holder.username.setText(entries.get(position).getUsername());
            holder.place.setText(Integer.toString(position+1));

        }

        @Override
        public int getItemCount(){
            return entries.size();
        }


}
