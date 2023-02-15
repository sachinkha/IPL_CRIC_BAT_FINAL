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

import com.planet.iplcricbat.Module.Player_Module;
import com.planet.iplcricbat.R;

import java.util.ArrayList;

public class Batting_Adapter extends RecyclerView.Adapter<Batting_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Player_Module> arrayList;
    Batting_set batting_set;
    public Batting_Adapter(FragmentActivity activity, ArrayList<Player_Module> arrayList,Batting_set batting_set) {
        this.context = activity;
        this.arrayList = arrayList;
        this.batting_set = batting_set;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_batting,parent,false);
        Batting_Adapter.MyViewHolder holder = new Batting_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.batting_player.setText(arrayList.get(position).getPlayers_name());
        holder.batting_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batting_set.batting_set(arrayList.get(position).getId(),arrayList.get(position).getMatch_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView batting_player,batting_set;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            batting_player = itemView.findViewById(R.id.batting_player);
            batting_set = itemView.findViewById(R.id.batting_set);
        }
    }
    public interface Batting_set {
        public void batting_set(String id, String match_id);
    }
}
