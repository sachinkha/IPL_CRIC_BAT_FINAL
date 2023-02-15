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
import com.planet.iplcricbat.Module.Team_Module;
import com.planet.iplcricbat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class All_Match_Adapter extends RecyclerView.Adapter<All_Match_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Team_Module> arrayList;
    All_Team all_team;
    public All_Match_Adapter(MainActivity  mainActivity, ArrayList<Team_Module> moduleArrayList, All_Team all_team) {
        this.context = mainActivity;
        this.arrayList = moduleArrayList;
        this.all_team = all_team;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_team,parent,false);
        All_Match_Adapter.MyViewHolder holder = new All_Match_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(arrayList.get(position).getFirst_image()).into(holder.match_first_image);
        Picasso.get().load(arrayList.get(position).getSecond_image()).into(holder.match_second_image);
        holder.match_first_team.setText(arrayList.get(position).getFirst_team());
        holder.match_second_team.setText(arrayList.get(position).getSecond_team());
        holder.submit_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all_team.all_team(arrayList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView match_first_image,match_second_image;
        TextView match_first_team,match_second_team;
        TextView submit_match;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            match_first_image = itemView.findViewById(R.id.match_first_image);
            match_second_image = itemView.findViewById(R.id.match_second_image);
            match_first_team = itemView.findViewById(R.id.match_first_team);
            match_second_team = itemView.findViewById(R.id.match_second_team);
            submit_match = itemView.findViewById(R.id.submit_match);
        }
    }
    public interface All_Team{
        public void all_team(String id);
    }
}
