package com.planet.iplcricbat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.planet.iplcricbat.MainActivity;
import com.planet.iplcricbat.R;

public class Amount_Adapter extends BaseAdapter {
    String[] amount_value;
    Context context;
    LayoutInflater inflater;
    public Amount_Adapter(MainActivity mainActivity, String[] amount) {

        this.context = mainActivity;
        this.amount_value = amount;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return amount_value.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.custom_spinner_amount,null);
        TextView names = view.findViewById(R.id.amount_text);
        names.setText(amount_value[i]);
        return view;
    }
}
