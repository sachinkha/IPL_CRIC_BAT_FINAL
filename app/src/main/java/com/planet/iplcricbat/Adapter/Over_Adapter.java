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
import com.planet.iplcricbat.Module.Over_Module;
import com.planet.iplcricbat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Over_Adapter extends RecyclerView.Adapter<Over_Adapter.MyViewHolder> {

    Context context;
    ArrayList<Over_Module> arrayList;
    Over_Bat over_bat;

    public Over_Adapter(MainActivity mainActivity, ArrayList<Over_Module> arrayList, Over_Bat over_bat) {
        this.context = mainActivity;
        this.arrayList = arrayList;
        this.over_bat = over_bat;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_over, parent, false);
        Over_Adapter.MyViewHolder holder = new Over_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get().load(arrayList.get(position).getFirst_image()).into(holder.over_first);
        Picasso.get().load(arrayList.get(position).getSecond_image()).into(holder.over_second);

        holder.submit_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                over_bat.over_bat(arrayList.get(position).getId(),
                        arrayList.get(position).getFirst_image(),
                        arrayList.get(position).getSecond_image(),
                        arrayList.get(position).getFirst_team(),
                        arrayList.get(position).getSecond_team());
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView over_first, over_second;
        TextView submit_over;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            over_first = itemView.findViewById(R.id.over_first);
            over_second = itemView.findViewById(R.id.over_second);
            submit_over = itemView.findViewById(R.id.submit_over);
        }
    }

    public interface Over_Bat {
        public void over_bat(String id, String first_image, String second_image, String first_team, String second_team);
    }
}
