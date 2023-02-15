package com.planet.iplcricbat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.planet.iplcricbat.MainActivity;
import com.planet.iplcricbat.Module.Match_Module;
import com.planet.iplcricbat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Match_RCY_Adapter extends RecyclerView.Adapter<Match_RCY_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Match_Module> arrayList;
    Match_bet match_bet;

    public Match_RCY_Adapter(MainActivity mainActivity, ArrayList<Match_Module> arrayList,Match_bet match_bet) {
        this.context = mainActivity;
        this.arrayList = arrayList;
        this.match_bet = match_bet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_match,parent,false);
        Match_RCY_Adapter.MyViewHolder holder = new Match_RCY_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(arrayList.get(position).getFirst_image()).into(holder.match_first_image);
        Picasso.get().load(arrayList.get(position).getSecond_image()).into(holder.match_second_image);
        holder.match_first_team.setText(arrayList.get(position).getFirst_team());
        holder.match_second_team.setText(arrayList.get(position).getSecond_team());
        holder.match_percentage1.setText(arrayList.get(position).getPercentage1());
        holder.match_percentage2.setText(arrayList.get(position).getPercentage2());
        if (arrayList.get(position).getWinner_match().equalsIgnoreCase("")){

        }else {
            holder.winner_match.setText("Win Match:- "+arrayList.get(position).getWinner_match());
            holder.submit_match.setVisibility(View.GONE);
        }

        holder.submit_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                match_bet.match_bet(arrayList.get(position).getId(),
                        arrayList.get(position).getFirst_team(),arrayList.get(position).getSecond_team());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView match_first_image,match_second_image;
        TextView match_first_team,match_second_team,match_percentage1,match_percentage2,winner_match;
        TextView submit_match;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            match_first_image = itemView.findViewById(R.id.match_first_image);
            match_second_image = itemView.findViewById(R.id.match_second_image);
            match_first_team = itemView.findViewById(R.id.match_first_team);
            match_second_team = itemView.findViewById(R.id.match_second_team);
            match_percentage1 = itemView.findViewById(R.id.match_percentage1);
            match_percentage2 = itemView.findViewById(R.id.match_percentage2);
            winner_match = itemView.findViewById(R.id.winner_match);

            submit_match = itemView.findViewById(R.id.submit_match);
        }
    }
    public interface Match_bet{
        public void match_bet(String id, String first_team, String second_team);
    }
}
