package com.mean.demo.adapter;

/**
 * Created by Administrator on 2016-02-01.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mean.demo.R;
import com.mean.demo.fragment.RecyclerViewFragment;
import com.mean.demo.fragment.RecyclerViewFragment_2;
import com.mean.demo.fragment.RecyclerViewFragment_3;
import com.mean.demo.fragment.RecyclerViewFragment_4;

/**
 * Created by CBR on 2016-02-01.
 */
public class TabAdapter extends FragmentPagerAdapter {
    RecyclerViewFragment r1;
    RecyclerViewFragment_2 r2;
    RecyclerViewFragment_3 r3;
    RecyclerViewFragment_4 r4;
    private int[] imageResId = {
            R.drawable.tab_friend_icon_n,
            R.drawable.tab_chatting_icon_n,
            R.drawable.tab_channel_icon_n,
            R.drawable.tab_more_icon_n
    };

    Context context;
    public TabAdapter(Context context,FragmentManager manager){
        super(manager);
        this.context = context;
        r1 = new RecyclerViewFragment(0);
        r2 = new RecyclerViewFragment_2();
        r3 = new RecyclerViewFragment_3();
        r4 = new RecyclerViewFragment_4();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return r1;
            case 1:
                return r2;
            case 2:
                return r3;
            case 3:
                return r4;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}