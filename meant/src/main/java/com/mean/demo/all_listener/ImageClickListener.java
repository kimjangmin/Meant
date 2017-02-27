package com.mean.demo.all_listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mean.demo.R;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by CBR on 2016-01-18.
 */
public class ImageClickListener implements View.OnClickListener {
    Context context;
    int imageID;
    int res_index;
    int request=100;
    Uri img;
    Bitmap bitmap;
    public ImageClickListener(Context context, int imageID,int position)
    {
        this.context = context;
        this.imageID = imageID;
        this.res_index = position;
    }

    @Override
    public void onClick(View v) {
        ImageView iv = (ImageView)((Activity)context).findViewById(R.id.wr2_iv);

        if(res_index==0)
        {
            Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
            Intent i = new Intent(Intent.ACTION_PICK, uri);
            ((Activity) context).startActivityForResult(i, request);

        }
        else
        {
                iv.setTag(res_index);
                iv.setImageResource(imageID);
        }
    }
}
