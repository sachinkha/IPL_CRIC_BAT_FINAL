package com.planet.iplcricbat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.planet.iplcricbat.Module.Offer_module;
import com.planet.iplcricbat.Payment;
import com.planet.iplcricbat.R;

import java.util.ArrayList;

public class Offer_Adapter extends RecyclerView.Adapter<Offer_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Offer_module> arrayList;

    public Offer_Adapter(Payment payment, ArrayList<Offer_module> arrayList) {
        this.context = payment;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_offer,parent,false);
        Offer_Adapter.MyViewHolder holder = new Offer_Adapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.custom_offer.setText(arrayList.get(position).getOffer_name()+" % Extra");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView custom_offer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            custom_offer = itemView.findViewById(R.id.custom_offer);
        }
    }
}
