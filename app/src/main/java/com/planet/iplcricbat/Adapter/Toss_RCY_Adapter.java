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
import com.planet.iplcricbat.Module.Toss_Module;
import com.planet.iplcricbat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Toss_RCY_Adapter extends RecyclerView.Adapter<Toss_RCY_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Toss_Module> arrayList;
    Toss_bet toss_bet;
    public Toss_RCY_Adapter(MainActivity mainActivity, ArrayList<Toss_Module> arrayList,Toss_bet toss_bet) {
        this.context = mainActivity;
        this.arrayList = arrayList;
        this.toss_bet = toss_bet;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_toss,parent,false);
        Toss_RCY_Adapter.MyViewHolder holder = new Toss_RCY_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(arrayList.get(position).getFirst_image()).into(holder.first_image);
        Picasso.get().load(arrayList.get(position).getSecond_image()).into(holder.second_image);
        holder.first_team.setText(arrayList.get(position).getFirst_team());
        holder.second_team.setText(arrayList.get(position).getSecond_team());
        holder.percentage1.setText(arrayList.get(position).getPercentage1());
        holder.percentage2.setText(arrayList.get(position).getPercentage2());
       if (arrayList.get(position).getWinner_toss().equalsIgnoreCase("")){

       }else {
           holder.winner_toss.setText("Win Toss:- "+arrayList.get(position).getWinner_toss());
           holder.submit_toss.setVisibility(View.GONE);
       }
        holder.submit_toss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toss_bet.toss_bet(arrayList.get(position).getId(),
                        arrayList.get(position).getFirst_team(),arrayList.get(position).getSecond_team());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView first_image,second_image;
        TextView first_team,second_team,percentage1,percentage2,winner_toss;
        TextView submit_toss;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            first_image = itemView.findViewById(R.id.first_image);
            second_image = itemView.findViewById(R.id.second_image);
            first_team = itemView.findViewById(R.id.first_team);
            second_team = itemView.findViewById(R.id.second_team);
            submit_toss = itemView.findViewById(R.id.submit_toss);
            percentage1 = itemView.findViewById(R.id.percentage1);
            percentage2 = itemView.findViewById(R.id.percentage2);
            winner_toss = itemView.findViewById(R.id.winner_toss);
        }
    }
    public interface Toss_bet{
        public void toss_bet(String id, String first_team, String second_team);
    }
}
