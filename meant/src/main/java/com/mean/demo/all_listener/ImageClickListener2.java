package com.mean.demo.all_listener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.mean.demo.R;

/**
 * Created by CBR on 2016-01-18.
 */
public class ImageClickListener2 implements View.OnClickListener {
    Context context;
    Bitmap bitID;
    int res_index;
    public ImageClickListener2(Context context, Bitmap imageID,int position)
    {
        this.context = context;
        this.bitID = imageID;
        this.res_index = position;
    }

    @Override
    public void onClick(View v) {
        ImageView iv = (ImageView)((Activity)context).findViewById(R.id.wr3_iv);
        iv.setTag(res_index);
        iv.setImageBitmap(bitID);
    }
}
