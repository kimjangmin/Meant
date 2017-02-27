package com.mean.demo.Write;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;
import com.mean.demo.R;
import com.nomad.instagramlogin.InstaLogin;
import com.nomad.instagramlogin.Keys;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

public class Write_Activity4 extends AppCompatActivity {
    private CallbackManager callbackManager;
    ImageView[] image_list = new ImageView[12];
    String get_msg,get_loc,req_id, file_name;
    int get_font,index,effect,get_lux;
    int add_y,add_m,add_d,add_age;
    int add_h,add_mi,add_s,add_mis;
    String sourceFileUri, mapping, img_path,recv_bitmap;
    Uri img;
    Bitmap bitmap=null;

    CheckBox cb3,cb4;
    boolean log;
    boolean c_f,c_t,c_i;
    ProgressDialog dialog = null;
    int serverResponseCode =0;
    String upLoadServerUri = null;

    Context context;
    ImageView iv,iv_face,iv_twitter,iv_insta;
    FrameLayout tviv;
    TextView tv,loctv,tv_face,tv_twitter,tv_insta;
    Bitmap bmp;
    Typeface typeface;
    java.util.Calendar cal;

    /////////////////////////////////////
    TextView stat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_4);
        context = this.getApplicationContext();
        mapping = "";
        Intent get_int = getIntent();
        get_msg = get_int.getStringExtra("input_txt");
        get_font = get_int.getIntExtra("selected_che", 0);
        recv_bitmap = get_int.getStringExtra("bitmap");
        if(recv_bitmap!=null)
        {
            img = Uri.parse(recv_bitmap);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        index = get_int.getIntExtra("img_index", 0);
        effect = get_int.getIntExtra("selected_effect", 0);
        get_lux = get_int.getIntExtra("lux", 0);
        get_loc = get_int.getStringExtra("name");

        cal = java.util.Calendar.getInstance();
        iv = (ImageView)findViewById(R.id.wr4_iv);
        tv = (TextView)findViewById(R.id.wr4_tv);
        loctv = (TextView)findViewById(R.id.wr4_loctv);
        tviv = (FrameLayout)findViewById(R.id.wr4_capture);
        //loctv.setText(get_loc);
        tv.setText(get_msg);

        tv_face = (TextView)findViewById(R.id.w4tv_face);
        tv_insta = (TextView)findViewById(R.id.w4tv_insta);
        tv_twitter = (TextView)findViewById(R.id.w4tv_twitter);
        iv_face = (ImageView)findViewById(R.id.wr4_face);
        iv_twitter = (ImageView)findViewById(R.id.wr4_twitter);
        iv_insta = (ImageView)findViewById(R.id.wr4_insta);

        c_f=false;
        c_i=false;
        c_t=false;
        if(bitmap!=null)
        {
            bmp = bitmap;
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);
            bmp = change_effect(bmp, effect);
            iv.setImageBitmap(bmp);
        }
        else {
            bmp = BitmapFactory.decodeResource(this.getResources(), Write_Activity2.imageIds[index]);
            bmp = Bitmap.createScaledBitmap(bmp, 320, 240, false);
            bmp = change_effect(bmp, effect);
            iv.setImageBitmap(bmp);
        }
        ImageView iv2 = (ImageView)findViewById(R.id.wr4_gr);
        iv2.bringToFront();
        loctv.bringToFront();
        tv.bringToFront();
        typeface = change_font(get_font, context);
        tv.setTypeface(typeface);

        tviv.post(new Runnable() {
            public void run() {
                int width_container = tviv.getWidth();
                int height_container = tviv.getHeight();

                tviv.setDrawingCacheEnabled(true);
                tviv.buildDrawingCache(true);

                Bitmap capture = Bitmap.createBitmap(tviv.getMeasuredWidth(), tviv.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Log.e("size : ", Integer.toString(tviv.getMeasuredWidth())+" , "+Integer.toString(tviv.getMeasuredHeight()));
                Canvas can = new Canvas(capture);
                tviv.draw(can);
                FileOutputStream fos;
                Intent it3 = getIntent();
                String str_name = it3.getStringExtra("it3_name");
                File fileRoute = null;
                fileRoute = Environment.getExternalStorageDirectory();

                try {
                    File path = new File(fileRoute, "Meant");
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    add_y = cal.get(cal.YEAR);
                    add_m = cal.get(cal.MONTH) + 1;
                    add_d = (cal.get(cal.DATE));
                    add_h = cal.get(cal.HOUR_OF_DAY);
                    add_mi = cal.get(cal.MINUTE);
                    add_s = (cal.get(cal.SECOND));
                    add_mis = (cal.get(cal.MILLISECOND));
                    file_name = Integer.toString(add_y) + Integer.toString(add_m) + Integer.toString(add_d) + "_";
                    file_name += Integer.toString(add_h) + Integer.toString(add_mi) + Integer.toString(add_s);
                    file_name += Integer.toString(add_mis) + ".png";
                    file_name = "imsi.png";
                    fos = new FileOutputStream(fileRoute + "/Meant/" + file_name);
                    Log.e("Screen Shot", " : " + tviv.getDrawingCache());
                    capture.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    tviv.setDrawingCacheEnabled(false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
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

    public void add_place(View v)
    {
        Intent in = new Intent(this, PlacesAutoCompleteActivity.class);
        in.putExtra("input_txt", get_msg);
        in.putExtra("selected_che", get_font);
        in.putExtra("img_index", index);
        in.putExtra("selected_effect", effect);
        in.putExtra("lux", get_lux);
        startActivity(in);
    }

    public void push_share(View v)
    {
        try {
            Thread.sleep(500);
            save_img();
            post_facebook();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void save_img() {

        dialog = ProgressDialog.show(Write_Activity4.this, "", "Uploading file...", true);

        Thread thr = new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.e("uploading started..", "uploading started..");
                    }
                });
                sourceFileUri = Environment.getExternalStorageDirectory()+"/Meant/"+file_name;
                uploadFile(sourceFileUri);
            }
        });
        thr.start();
        try{
            thr.join();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public int uploadFile(String str) {
        String fileName = str;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(str);

        Log.e("File Path",str);

        if (!sourceFile.isFile()) {
            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :" + fileName);
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                URL url = new URL("http://" + IP_ADDR.get_ip() + ":8181/api/photo");


                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                Log.e("buffsize",Integer.toString(bufferSize));

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e("byteRead - ", Integer.toString(bytesRead));
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.e("uploadFile", "Http Response is :" + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    Log.e("200", "server res : 200 OK");
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()),8192);
                    final StringBuilder response = new StringBuilder();
                    String strLine = null;
                    while((strLine = input.readLine()) != null)
                    {
                        Log.e("get msg",strLine);
                        response.append(strLine);
                        mapping = response.toString();
                    }
                    input.close();

                    runOnUiThread(new Runnable(){
                        public void run(){
                            String msg = "File upload Completed";
                            Log.e("msg",msg);
                        }
                    });
                }
                fileInputStream.close();
                dos.flush();
                dos.close();
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        add_post();
                    }
                });
                th.start();
                try{
                    th.join();
                }catch(Exception ea)
                {
                    ea.printStackTrace();
                }

            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.e("MalformedURLException", "exception");
                    }
                });
            } catch (Exception e) {
                dialog.dismiss();
                e.printStackTrace();
            }
            dialog.dismiss();
            return serverResponseCode;
        }
    }

    public void add_post()
    {
        req_id = MainActivity.id;
        int user_year=0;
        user_year = Integer.parseInt(MainActivity.user_age);
        user_year = (add_y - user_year)-(add_y - user_year)%10;
        add_age = user_year;
        img_path = "";
        for(int i=0;i<mapping.length();i++)
        {
            if(mapping.charAt(i) == '\\')
            {
                mapping = mapping.substring(i+1);
            }
        }
        mapping = "http://"+ IP_ADDR.get_ip()+":8181/uploads/"+ mapping;
        Log.e("mapping : ",mapping);
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode(req_id, "UTF-8")+"="+URLEncoder.encode(Integer.toString(add_y),"UTF-8");
                    data += "=" + URLEncoder.encode(Integer.toString(add_m),"UTF-8")+"="+ URLEncoder.encode(Integer.toString(add_d),"UTF-8");
                    data += "=" + URLEncoder.encode(Integer.toString(add_age),"UTF-8")+"="+ URLEncoder.encode(mapping,"UTF-8");
                    data += "=" + URLEncoder.encode(get_msg,"UTF-8")+"="+ URLEncoder.encode(Integer.toString(get_font),"UTF-8");
                    data += "=" + URLEncoder.encode(Integer.toString(effect),"UTF-8")+"="+ URLEncoder.encode(get_loc,"UTF-8");
                    data += "=" + URLEncoder.encode(Integer.toString(get_lux),"UTF-8")+"="+ URLEncoder.encode(Integer.toString(cal.get(cal.HOUR_OF_DAY)),"UTF-8");
                    data += "=" + URLEncoder.encode(Integer.toString(cal.get(cal.MINUTE)),"UTF-8")+"="+ URLEncoder.encode(Integer.toString(cal.get(cal.SECOND)),"UTF-8");
                    data += "=" + URLEncoder.encode(MainActivity.user_nick,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/add_post");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd3 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    String line="";
                    while((line = rd3.readLine())!=null)
                    {
                    }
                    Log.e("post_finish","post_finish");
                    wr.close();
                    rd3.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Typeface change_font(int index, Context context)
    {
        if(index == 0)
        {
            return Typeface.createFromAsset(context.getAssets(), "fonts/twopm.ttf");
        }
        else if(index == 1)
        {
            return Typeface.createFromAsset(context.getAssets(), "fonts/myche.ttf");
        }
        else if(index == 2)
        {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Ddalgi.ttf");
        }
        else if(index == 3)
        {
            return Typeface.createFromAsset(context.getAssets(), "fonts/hulim.ttf");
        }
        else
        {
            return Typeface.createFromAsset(context.getAssets(), "fonts/Hongcha.ttf");
        }
    }

    public static Bitmap change_effect(Bitmap src, int effect)
    {
        if(effect == 0)
        {
            return src;
        }
        else if(effect == 1)
        {
            return Write_Activity3.doInvert(src);
        }
        else if(effect == 2)
        {
            return Write_Activity3.doGreyscale(src);
        }
        else if(effect == 3)
        {
            return Write_Activity3.createContrast(src, 50);
        }
        else if(effect == 4)
        {
            return Write_Activity3.applyGaussianBlur(src);
        }
        else if(effect == 5)
        {
            return Write_Activity3.emboss(src);
        }
        else if(effect == 6)
        {
            return Write_Activity3.engrave(src);
        }
        else if(effect == 7)
        {
            return Write_Activity3.applySnowEffect(src);
        }
        else if(effect == 8)
        {
            return Write_Activity3.boost(src, 1, 150);
        }
        else if(effect == 9)
        {
            return Write_Activity3.boost(src,2,50);
        }
        else if(effect == 10)
        {
            return Write_Activity3.boost(src,3,67);
        }
        else
        {
            return Write_Activity3.filter(src,50,50,50);
        }
    }
    public boolean islog()
    {

        return AccessToken.getCurrentAccessToken()!=null;
    }

    public void post_facebook()
    {
        boolean ischeck1 = c_f;
        boolean ischeck2 = c_i;


        if(ischeck1)
        {
            Log.e("check1","check1");
            log= islog();
            if(log)
            {
                final ShareDialog dialog = new ShareDialog(Write_Activity4.this);
                File fileRoute = null;
                fileRoute = Environment.getExternalStorageDirectory();
                Bitmap bit = BitmapFactory.decodeFile(fileRoute + "/Meant/" + file_name);
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bit)
                        .build();
                final SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                Log.e("check2", "check2");

                Log.e("check3", "check3");
                TimerTask myTask = new TimerTask() {
                    public void run() {
                        dialog.show(Write_Activity4.this, content);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(myTask, 5000);
            }
            else
            {}

        }

        if(ischeck2)
        {
            //////////////////////////////
            InstaLogin instaLogin = new InstaLogin(Write_Activity4.this,
                    "0f574d410bd64da2b87cc08878d3abdd",
                    "6ce98bbc38ea4b4997ffbef6ba87f1dc",
                    "https://123.com");
            instaLogin.login();
        }
        else
        {
            Intent in = new Intent(Write_Activity4.this, MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            in.putExtra("flag", 0);
            in.putExtra("user_id", MainActivity.id);
            in.putExtra("pwd", MainActivity.pwd);
            startActivity(in);
            finish();
        }
    }


    protected  void onActivityResult(final int requestCode, final int resultCode,final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode!=0) {
            Intent in = new Intent(Write_Activity4.this, MainActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            in.putExtra("flag", 0);
            in.putExtra("user_id", MainActivity.id);
            in.putExtra("pwd", MainActivity.pwd);
            startActivity(in);
            finish();
        }
        else if(resultCode==0)
        {

        }


        /////////////////////////////////////////
        if (requestCode == Keys.LOGIN_REQ) {
            if (resultCode == RESULT_OK) {

                PackageManager manager = getBaseContext().getPackageManager();
                Intent i= manager.getLaunchIntentForPackage("com.instagram.android");

                if(i==null){
                    i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.instagram.android"));
                    startActivity(i);
                    return;
                }

                String type = "image/*";
                String mediaPath= Environment.getExternalStorageDirectory()+ "/meant/imsi.png";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType(type);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                File media = new File(mediaPath);
                if (media.isDirectory())
                {
                    Log.i("TEST", media.getAbsolutePath() + " dir exist.");
                }
                else
                {
                    Log.i("TEST", media.getAbsolutePath() + " dir not exist.");
                }
                Uri uri=Uri.fromFile(media);
                shareIntent.putExtra(Intent.EXTRA_STREAM,uri);
                shareIntent.setPackage("com.instagram.android");
                startActivity(shareIntent);
            }
        }
    }

    public void check_eve(View v)
    {
        if(v == iv_face)
        {
            if(c_f)
            {
                c_f = false;
                iv_face.setImageResource(R.mipmap.ic_share_facebook);
                tv_face.setTextColor(getResources().getColor(R.color.iron));
            }
            else
            {
                c_f = true;
                iv_face.setImageResource(R.mipmap.ic_share_facebook_select);
                tv_face.setTextColor(getResources().getColor(R.color.white));
            }
        }
        else if(v == iv_insta)
        {
            if(c_i)
            {
                c_i = false;
                iv_insta.setImageResource(R.mipmap.ic_share_instagram);
                tv_insta.setTextColor(getResources().getColor(R.color.iron));
            }
            else
            {
                c_i = true;
                iv_insta.setImageResource(R.mipmap.ic_share_instagram_select);
                tv_insta.setTextColor(getResources().getColor(R.color.white));
            }
        }
        else if(v == iv_twitter)
        {
            if(c_t)
            {
                c_t = false;
                iv_twitter.setImageResource(R.mipmap.ic_share_twitter);
                tv_twitter.setTextColor(getResources().getColor(R.color.iron));
            }
            else
            {
                c_t = true;
                iv_twitter.setImageResource(R.mipmap.ic_share_twitter_select);
                tv_twitter.setTextColor(getResources().getColor(R.color.white));
            }
        }
    }



}