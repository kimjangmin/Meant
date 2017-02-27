package com.mean.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mean.demo.all_listener.ImageClickListener;
import com.mean.demo.R;
import com.mean.demo.Write.RecycleUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBR on 2016-01-18.
 */

public class ImageGridAdapter extends BaseAdapter {

    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
    Context context = null;
    int[] imageIDs = null;
    ImageView iv;

    public ImageGridAdapter(Context context, int[] imageIDs)
    {
        this.context = context;
        this.imageIDs = imageIDs;
    }
    @Override
    public int getCount() {
        return (null != imageIDs) ? imageIDs.length : 0;
    }

    @Override
    public Object getItem(int position) {
        return (null != imageIDs) ? imageIDs[position] : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        iv = (ImageView)parent.findViewById(R.id.wr2_iv);

        if(null != convertView)
        {
            //imageView = (ImageView)convertView;

            try
            {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
                bmp = Bitmap.createScaledBitmap(bmp,320,240,false);

                imageView = (ImageView)convertView;
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(bmp);

                ImageClickListener imageViewClickListener = new ImageClickListener(context, imageIDs[position],position);
                imageView.setOnClickListener(imageViewClickListener);

            }
            catch(OutOfMemoryError e)
            {
                recycleHalf();
                System.gc();
                return getView(position,convertView,parent);
            }
        }
        else
        {
            try
            {
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imageIDs[position]);
                bmp = Bitmap.createScaledBitmap(bmp,320,240,false);

                imageView = new ImageView(context);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(bmp);

                ImageClickListener imageViewClickListener = new ImageClickListener(context, imageIDs[position],position);
                imageView.setOnClickListener(imageViewClickListener);

            }
            catch(OutOfMemoryError e)
            {
                recycleHalf();
                System.gc();
                return getView(position,convertView,parent);
            }
        }
        mRecycleList.add(new WeakReference<View>(imageView));
        return imageView;
    }

    public void recycleHalf()
    {
        int halfSize = mRecycleList.size()/2;
        List<WeakReference<View>> recycleHalfList = mRecycleList.subList(0, halfSize);
        RecycleUtils.recursiveRecycle(recycleHalfList);
        for(int i=0;i<halfSize;i++)
        {
            mRecycleList.remove(0);
        }
    }
    public void recycle()
    {
        RecycleUtils.recursiveRecycle(mRecycleList);
    }

}
