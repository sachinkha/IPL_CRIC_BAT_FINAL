package com.planet.iplcricbat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.planet.iplcricbat.MainActivity;
import com.planet.iplcricbat.R;

public class Toss_Adapter extends BaseAdapter {
    String[] toss_value;
    Context context;
    LayoutInflater inflater;

    public Toss_Adapter(MainActivity mainActivity, String[] toss_value) {
        this.context = mainActivity;
        this.toss_value = toss_value;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return toss_value.length;
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
        view = inflater.inflate(R.layout.custom_spinner_toss,null);
        TextView names = view.findViewById(R.id.textView);
        names.setText(toss_value[i]);
        return view;
    }
}
