package com.planet.iplcricbat.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.planet.iplcricbat.Module.Bowling_Module;
import com.planet.iplcricbat.R;

import java.util.ArrayList;

public class Bowling_Adapter extends RecyclerView.Adapter<Bowling_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Bowling_Module> arrayList;
    Bowling_Set bowling_set;
    public Bowling_Adapter(FragmentActivity activity, ArrayList<Bowling_Module> arrayList,Bowling_Set bowling_set) {
        this.context = activity;
        this.arrayList = arrayList;
        this.bowling_set = bowling_set;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_bowling,parent,false);
        Bowling_Adapter.MyViewHolder holder = new Bowling_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bowling_player.setText(arrayList.get(position).getPlayers_name());
        holder.bowling_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bowling_set.bowling_set(arrayList.get(position).getId(),arrayList.get(position).getMatch_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView bowling_player,bowling_set;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bowling_player = itemView.findViewById(R.id.bowling_player);
            bowling_set = itemView.findViewById(R.id.bowling_set);
        }
    }
    public interface Bowling_Set{
        public void bowling_set(String id, String match_id);
    }
}
