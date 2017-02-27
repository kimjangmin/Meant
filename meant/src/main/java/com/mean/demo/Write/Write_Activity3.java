package com.mean.demo.Write;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.mean.demo.all_listener.ImageClickListener2;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.io.IOException;
import java.util.Random;

public class Write_Activity3 extends AppCompatActivity {
    int[] imag = new int[]{R.id.wr3_img0,R.id.wr3_img1,R.id.wr3_img2,R.id.wr3_img3,R.id.wr3_img4,R.id.wr3_img5,R.id.wr3_img6,R.id.wr3_img7,R.id.wr3_img8,R.id.wr3_img9,R.id.wr3_img10,R.id.wr3_img11};
    Bitmap[] res_bmp = new Bitmap[12];
    Uri img;
    Bitmap bitmap=null;
    ImageView[] image_list = new ImageView[12];
    String get_msg,recv_bitmap=null,next_bitmap=null;
    int get_font,index,get_lux;
    ImageView iv;
    Bitmap bmp;
    SeekBar seekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_3);
        get_lux = 100;
        Intent get_int = getIntent();
        recv_bitmap= get_int.getStringExtra("bitmap");
        if(recv_bitmap!=null)
        {
            img = Uri.parse(recv_bitmap);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        get_msg = get_int.getStringExtra("input_txt");
        get_font = get_int.getIntExtra("selected_che", 0);
        index = get_int.getIntExtra("img_index", 0);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        iv = (ImageView)findViewById(R.id.wr3_iv);
        iv.setTag(0);

        if(bitmap!=null)
        {
            iv.setImageBitmap(bitmap);
            bmp = bitmap;
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);

        }
        else {
            iv.setImageResource(Write_Activity2.imageIds[index]);
            bmp = BitmapFactory.decodeResource(this.getResources(), Write_Activity2.imageIds[index]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);

        }

        for(int i=0;i<12;i++)
        {
            res_bmp[0] = bmp;
            image_list[i] = (ImageView)findViewById(imag[i]);

            if(i==0){image_list[i].setImageBitmap(bmp);}
            else if(i==1){
                res_bmp[i] = doInvert(bmp);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i == 2){
                res_bmp[i] = doGreyscale(bmp);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==3){
                res_bmp[i] = createContrast(bmp, 50);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==4){
                res_bmp[i] = applyGaussianBlur(bmp);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i ==5){
                res_bmp[i] = emboss(bmp);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==6){
                res_bmp[i] = engrave(bmp);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==7){
                res_bmp[i] = applySnowEffect(bmp);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==8){
                res_bmp[i] = boost(bmp, 1, 150);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==9){
                res_bmp[i] = boost(bmp, 2, 50);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==10){
                res_bmp[i] = boost(bmp, 3, 67);
                image_list[i].setImageBitmap(res_bmp[i]);}
            else if(i==11){
                res_bmp[i] = filter(bmp, 50, 50, 50);
                image_list[i].setImageBitmap(res_bmp[i]);}
            ImageClickListener2 imageViewClickListener = new ImageClickListener2(this, res_bmp[i],i);
            image_list[i].setOnClickListener(imageViewClickListener);
        }
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int get_eff = (int) iv.getTag();
                Bitmap bt = doBrightness(res_bmp[get_eff],progress-100);
                get_lux = progress;
                iv.setImageBitmap(bt);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    protected void onDestroy()
    {
        bmp.recycle();
        bmp = null;

        super.onDestroy();
    }

    public void push_exit(View v)
    {
        Intent i3 = new Intent(this,MainActivity.class);
        i3.putExtra("flag", 0);
        i3.putExtra("user_id", MainActivity.id);
        startActivity(i3);
        finish();
    }
    public void push_back(View v)
    {
        finish();
    }
    public void push_next(View v)
    {
        Intent i1 = new Intent(this,Write_Activity4.class);
        i1.putExtra("input_txt",get_msg);
        i1.putExtra("selected_che", get_font);

        if(recv_bitmap!=null)
        {
            next_bitmap =   recv_bitmap;
            i1.putExtra("bitmap",next_bitmap);
        }
        else
        {
            i1.putExtra("img_index", index);
        }
        i1.putExtra("selected_effect",(int)iv.getTag());
        i1.putExtra("lux",get_lux);
        i1.putExtra("name","");
        startActivity(i1);
    }

    public static Bitmap doInvert(Bitmap src)
    {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(),src.getHeight(), src.getConfig());
        int A,R,G,B;
        int pixelColor;
        int height=src.getHeight();
        int width=src.getWidth();

        for(int y=0;y<height;y++)
        {
            for(int x=0;x<width;x++)
            {
                pixelColor = src.getPixel(x,y);
                A = Color.alpha(pixelColor);
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                bmOut.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

    public static Bitmap doGreyscale(Bitmap src)
    {
        final double GS_RED = 0.299;
        final double GS_GREEN = 0.587;
        final double GS_BLUE = 0.114;
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(),src.getHeight(), src.getConfig());
        int A,R,G,B;
        int pixel;
        int height=src.getHeight();
        int width=src.getWidth();

        for(int x=0;x<width;++x)
        {
            for(int y = 0; y < height;++y)
            {
                pixel = src.getPixel(x,y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                R = G = B = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);
                bmOut.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

    public static Bitmap createContrast(Bitmap src, double value)
    {
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(),src.getHeight(), src.getConfig());
        int A,R,G,B;
        int pixel;
        int height = src.getHeight();
        int width=src.getWidth();
        double contrast = Math.pow((100 + value) / 100, 2);

        for(int x=0;x<width;++x)
        {
            for(int y=0;y<height;++y)
            {
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                R = (int)(((((R/255.0)-0.5)*contrast)+0.5)*255.0);
                if(R<0){R=0;}
                else if(R>255){R=255;}

                G = Color.green(pixel);
                G = (int)(((((G/255.0)-0.5)*contrast) +0.5)*255.0);
                if(G<0){G=0;}
                else if(G>255){G=255;}

                B = Color.blue(pixel);
                B = (int)(((((B/255.0)-0.5)*contrast)+0.5)*255.0);
                if(B<0){B=0;}
                else if(B>255){B=255;}

                bmOut.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

    public static Bitmap applyGaussianBlur(Bitmap src)
    {
        double[][] GaussianBlurConfig = new double[][]{
                {1,2,1},{2,4,2},{1,2,1}
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(GaussianBlurConfig);
        convMatrix.Factor = 16;
        convMatrix.Offset = 0;
        return ConvolutionMatrix.computeConvolution3x3(src,convMatrix);
    }

    public static Bitmap emboss(Bitmap src)
    {
        double[][] EmbossConfig = new double[][]{
                {-1,0,-1},{0,4,0},{-1,0,-1}
        };
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.applyConfig(EmbossConfig);
        convMatrix.Factor = 1;
        convMatrix.Offset = 127;
        return ConvolutionMatrix.computeConvolution3x3(src,convMatrix);
    }
    public static Bitmap engrave(Bitmap src)
    {
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.setAll(0);
        convMatrix.Matrix[0][0] = -2;
        convMatrix.Matrix[1][1] = 2;
        convMatrix.Factor = 1;
        convMatrix.Offset = 95;
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
    }

    public static Bitmap applySnowEffect(Bitmap src)
    {
        int width = src.getWidth();
        int height = src.getHeight();
        int[] pixels = new int[width * height];
        src.getPixels(pixels, 0, width, 0, 0, width, height);
        Random random = new Random();

        int R,G,B, index=0, thresHold = 50;
        for(int y=0;y<height;++y)
        {
            for(int x=0;x<width;++x)
            {
                index = y * width + x;
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                thresHold = random.nextInt(0xFF);
                if(R>thresHold && G>thresHold && B>thresHold)
                {
                    pixels[index] = Color.rgb(0xFF,0xFF,0xFF);
                }
            }
        }
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }
    public static Bitmap boost(Bitmap src, int type, float percent)
    {
        int width = src.getWidth();
        int height=  src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A,R,G,B;
        int pixel;

        for(int x=0;x<width; x++)
        {
            for(int y = 0; y < height;y++)
            {
                pixel = src.getPixel(x,y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                if(type == 1)
                {
                    R = (int)(R * (1 + percent));
                    if(R > 255) R=255;
                }
                else if(type == 2)
                {
                    G = (int)(G * (1+percent));
                    if(G>255)G=255;
                }
                else if(type == 3)
                {
                    B = (int)(B * (1+percent));
                    if(B>255)B=255;
                }
                bmOut.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

    public static Bitmap filter(Bitmap src, double red, double green, double blue)
    {
        int width = src.getWidth();
        int height=  src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        int A,R,G,B;
        int pixel;

        for(int x=0;x<width; x++)
        {
            for(int y = 0; y < height;y++)
            {
                pixel = src.getPixel(x,y);
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * red);
                G = (int)(Color.green(pixel)* green);
                B = (int)(Color.blue(pixel) * blue);
                bmOut.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

    public static Bitmap doBrightness(Bitmap src, int value)
    {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        int A,R,G,B;
        int pixel;

        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                R += value;
                if(R>255){R=255;}
                else if(R<0){R=0;}

                G += value;
                if(G>255){G=255;}
                else if(G<0){G=0;}

                B += value;
                if(B>255){B=255;}
                else if(B<0){B=0;}
                bmOut.setPixel(x,y,Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }

}
