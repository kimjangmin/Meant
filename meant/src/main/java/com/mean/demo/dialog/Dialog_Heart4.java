package com.mean.demo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mean.demo.getset.FeedItem;
import com.mean.demo.MainActivity;
import com.mean.demo.R;
import com.mean.demo.adapter.TestRecyclerViewAdapter4;
import com.mean.demo.fragment.RecyclerViewFragment_4;

/**
 * Created by CBR on 2016-01-10.
 */
public class Dialog_Heart4 extends Dialog implements View.OnClickListener {
    Button bt1,bt2;
    Button b1,b2,b3,b4;
    Context act;
    View view;
    int position;
    TestRecyclerViewAdapter4 trva4;
    FeedItem fid;
    RecyclerView.Adapter mAdapter;
    public Dialog_Heart4(Context context,View view, int position, TestRecyclerViewAdapter4 trva4, FeedItem fid, RecyclerView.Adapter mAdapter,int flag)
    {
        super(context);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.view = view;
        this.position = position;
        this.trva4 = trva4;
        this.fid=fid;
        this.mAdapter = mAdapter;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_heart);
        if(flag == 0)
        {
            setContentView(R.layout.custom_dialog_heart);
            bt1 = (Button)findViewById(R.id.dia_heart_yes);
            bt2 = (Button)findViewById(R.id.dia_heart_no);
            bt1.setOnClickListener(this);
            bt2.setOnClickListener(this);
        }
        else
        {
            setContentView(R.layout.custom_dialog_menu);
            b1 = (Button)findViewById(R.id.menu_share);
            b2 = (Button)findViewById(R.id.menu_modify);
            b3 = (Button)findViewById(R.id.menu_delete);
            b4 = (Button)findViewById(R.id.menu_cancel);
            b1.setOnClickListener(this);
            b2.setOnClickListener(this);
            b3.setOnClickListener(this);
            b4.setOnClickListener(this);
        }
        act = context;
    }
    @Override
    public void onClick(View v) {
        if(v == bt1)
        {
            RecyclerViewFragment_4 r4 = new RecyclerViewFragment_4();
            r4.push_yes(view, position, trva4, fid, mAdapter);
            dismiss();
            Intent i = new Intent(act,MainActivity.class);
            act.startActivity(i);
            if(act instanceof Activity)
            {
                ((Activity)act).finish();
            }
        }
        else if(v == bt2)
        {
            dismiss();
        }
        else if(v == b1)
        {

        }
        else if(v == b2)
        {

        }
        else if(v == b3)
        {

        }
        else if(v == b4)
        {
            dismiss();
        }
    }
}
