package com.mean.demo.Write;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mean.demo.adapter.ImageGridAdapter;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.io.IOException;

public class Write_Activity2 extends AppCompatActivity {
    ImageView iv;
    int request=100;
    String get_msg,next_bitmap=null;
    int get_font;
    Uri img;
    static Bitmap bitmap=null;
    TextView tv1,tv2;
    ImageView iv3;
    public static int[] imageIds  = new int[]{R.drawable.ic_gallery_plus,R.drawable.back_2,R.drawable.back_3,R.drawable.back_4,R.drawable.back_5,R.drawable.back_6,R.drawable.back_7,R.drawable.back_8,R.drawable.back_9,R.drawable.back_10,R.drawable.back_11,R.drawable.back_12,R.drawable.back_13,R.drawable.back_14,R.drawable.back_15,R.drawable.back_16};
    ImageGridAdapter imageGridAdapter;
    Context con;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_2);
        con = this;
        mHandler = new Handler();

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                mProgressDialog = ProgressDialog.show(Write_Activity2.this,"","잠시만 기다려 주세요.",true);

                Intent get_int = getIntent();
                get_msg = get_int.getStringExtra("input_txt");
                get_font = get_int.getIntExtra("selected_che", 0);
                iv = (ImageView)findViewById(R.id.wr2_iv);
                tv1 = (TextView)findViewById(R.id.w2_cancel);
                tv2 = (TextView)findViewById(R.id.w2_next);
                iv3 = (ImageView)findViewById(R.id.w2_back);
                iv.setTag(0);
                GridView gridViewImages = (GridView)findViewById(R.id.gridViewImages);
                imageGridAdapter = new ImageGridAdapter(Write_Activity2.this,imageIds);
                gridViewImages.setAdapter(imageGridAdapter);
                tv1.bringToFront();
                tv2.bringToFront();
                iv3.bringToFront();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if(mProgressDialog!=null && mProgressDialog.isShowing())
                            {
                                mProgressDialog.dismiss();
                            }
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },2500);
            }
        });



    }

    public void push_exit(View v)
    {
        Intent i2 = new Intent(this,MainActivity.class);
        i2.putExtra("flag", 0);
        i2.putExtra("user_id", MainActivity.id);
        startActivity(i2);
        finish();
    }

    public void push_back(View v)
    {
        finish();
    }

    public void push_next(View v)
    {
        Intent i1 = new Intent(this,Write_Activity3.class);
        i1.putExtra("input_txt",get_msg);
        i1.putExtra("selected_che", get_font);
        int index = (int) iv.getTag();
        i1.putExtra("img_index",index);
        if(next_bitmap!=null)
        {
            i1.putExtra("bitmap", next_bitmap);
        }
        Log.e("index", Integer.toString(index));
        startActivity(i1);
    }

    @Override
    protected void onDestroy() {
        imageGridAdapter.recycle();
        super.onDestroy();
    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        if(requestCode == request)
        {

            img = data.getData();
            next_bitmap = String.valueOf(img);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img);
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}