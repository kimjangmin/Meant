package com.mean.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mean.demo.R;

/**
 * Created by CBR on 2016-01-10.
 */
public class Dialog_EX extends Dialog {

    Context act;
    public Dialog_EX(Context context,Bitmap bit)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_cong);
        ImageView iv = (ImageView)findViewById(R.id.ex_iv);
        iv.setImageBitmap(bit);
        act = context;
    }


}

