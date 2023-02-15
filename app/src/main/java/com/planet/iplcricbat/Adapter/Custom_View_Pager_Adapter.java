package com.planet.iplcricbat.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.planet.iplcricbat.MainActivity;
import com.planet.iplcricbat.Module.ViewPagerData;
import com.planet.iplcricbat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Custom_View_Pager_Adapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<ViewPagerData> images = new ArrayList<ViewPagerData>();

    public Custom_View_Pager_Adapter(MainActivity mainActivity, ArrayList<ViewPagerData> listImage) {
        this.context = mainActivity;
        this.images = listImage;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        Log.e("Image Array size", ">>>" + images.size());
        return images.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.custom_view_pager_adapter, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewimg);
        LinearLayout llImageClick=(LinearLayout)itemView.findViewById(R.id.llImageClick);

        final ViewPagerData imageShow = images.get(position);
        Log.e("Get Image", ">>>" + imageShow.getImage());



        Picasso.get().load(imageShow.getImage()).into(imageView);
        container.addView(itemView);

        //listening to image click
        llImageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

