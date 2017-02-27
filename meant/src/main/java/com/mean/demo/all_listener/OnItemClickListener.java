package com.mean.demo.all_listener;

import android.view.View;

import com.mean.demo.getset.FeedItem;

import java.util.List;

/**
 * Created by CBR on 2016-01-14.
 */
public class OnItemClickListener implements View.OnClickListener {

    private int position;
    private OnItemClickCallback onItemClickCallback;
    private List<FeedItem> itemset;
    public OnItemClickListener(int position, OnItemClickCallback onItemClickCallback, List<FeedItem> it)
    {
        this.position = position;
        this.onItemClickCallback = onItemClickCallback;
        this.itemset = it;
    }

    public OnItemClickListener() {

    }

    @Override
    public void onClick(View v) {
        onItemClickCallback.onItemClicked(v,position, itemset);
    }
    public interface OnItemClickCallback
    {
        void onItemClicked(View view, int position, List<FeedItem> it);
    }
}
