package com.mean.demo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mean.demo.R;

/**
 * Created by CBR on 2016-02-03.
 */
public class CustomViewAdapter extends PagerAdapter {
    LayoutInflater inflater;
    public CustomViewAdapter(LayoutInflater inflater)
    {
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return 4;
    }

    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = null;
        view = inflater.inflate(R.layout.view_pager,null);
        ImageView img = (ImageView)view.findViewById(R.id.page);
        switch (position)
        {
            case 0:
                img.setImageResource(R.drawable.tutorial_1);
                break;
            case 1:
                img.setImageResource(R.drawable.tutorial_1);
                break;
            case 2:
                img.setImageResource(R.drawable.tutorial_1);
                break;
            case 3:
                img.setImageResource(R.drawable.tutorial_1);
        }
        container.addView(view);
        return view;

    }
    @Override

    public void destroyItem(ViewGroup container, int position, Object object) {


        container.removeView((View) object);

    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}
