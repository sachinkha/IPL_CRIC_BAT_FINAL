package com.planet.iplcricbat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.planet.iplcricbat.Payment;
import com.planet.iplcricbat.R;

public class Amount_Adapter_Design extends RecyclerView.Adapter<Amount_Adapter_Design.MyViewHolder> {

    Context context ;
    String[] value;
    public Amount_Adapter_Design(Payment payment, String[] value) {
        this.value = value;
        this.context = payment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_amount,parent,false);

        Amount_Adapter_Design.MyViewHolder holder = new Amount_Adapter_Design.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return value.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
