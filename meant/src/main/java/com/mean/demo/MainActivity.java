package com.mean.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.mean.demo.adapter.TabAdapter;
import com.mean.demo.dialog.Dialog_more;
import com.mean.demo.fragment.RecyclerViewFragment;
import com.mean.demo.fragment.RecyclerViewFragment_2;
import com.mean.demo.fragment.RecyclerViewFragment_3;
import com.mean.demo.fragment.RecyclerViewFragment_4;
import com.mean.demo.setting.SettingActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import io.fabric.sdk.android.Fabric;

import android.support.design.widget.TabLayout;  //추가




public class MainActivity extends AppCompatActivity{

    ViewPager pager;
    TabLayout tabLayout;

    String user_id, line,s1,s2,s3;
    RecyclerViewFragment r1;
    RecyclerViewFragment_2 r2;
    RecyclerViewFragment_3 r3;
    RecyclerViewFragment_4 r4;

    int flag, check_set;
    public static String user_age;
    public static String user_nick;
    public static String user_img;
    public static String id;
    public static String pwd;
    ImageView iv;
    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();


        iv = (ImageView)findViewById(R.id.main_set);
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        user_id = intent.getStringExtra("user_id");
        id = user_id;
        pwd = intent.getStringExtra("pwd");
        check_set = 0;
        check_info();
        get_age_nick();

        try{
            Thread.sleep(400);
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        r1 = new RecyclerViewFragment(flag);
        r2 = new RecyclerViewFragment_2();
        r3 = new RecyclerViewFragment_3();
        r4 = new RecyclerViewFragment_4();


        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                mProgressDialog = ProgressDialog.show(MainActivity.this,"","잠시만 기다려 주세요.",true);

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
                },1500);
            }
        });


        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        pager = (ViewPager)findViewById(R.id.viewpager);

        pager.setAdapter(new TabAdapter(this, getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position % 4) {
                    case 0:
                        return r1.newInstance(flag);
                    case 1:
                        return r2.newInstance();
                    case 2:
                        return r3.newInstance();
                    case 3:
                        return r4.newInstance();
                    default:
                        return r2.newInstance();
                }
            }
            @Override
            public int getCount() {
                return 4;
            }
        });

        tabLayout.setupWithViewPager(pager);
        tabLayout.getTabAt(0).setIcon(R.mipmap.tab_my_n);
        tabLayout.getTabAt(1).setIcon(R.mipmap.tab_best_n);
        tabLayout.getTabAt(2).setIcon(R.mipmap.tab_moment_n);
        tabLayout.getTabAt(3).setIcon(R.mipmap.tab_heart_n);


        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                String actionbar_title = null;
                switch (tab.getPosition()) {
                    case 0:
                        iv.setImageResource(R.mipmap.action_ic_settings);
                        tabLayout.getTabAt(0).setIcon(R.mipmap.tab_my_y);
                        tabLayout.getTabAt(1).setIcon(R.mipmap.tab_best_n);
                        tabLayout.getTabAt(2).setIcon(R.mipmap.tab_moment_n);
                        tabLayout.getTabAt(3).setIcon(R.mipmap.tab_heart_n);
                        check_set = 0;
                        break;
                    case 1:
                        iv.setImageResource(R.mipmap.ic_filter);
                        tabLayout.getTabAt(0).setIcon(R.mipmap.tab_my_n);
                        tabLayout.getTabAt(1).setIcon(R.mipmap.tab_best_y);
                        tabLayout.getTabAt(2).setIcon(R.mipmap.tab_moment_n);
                        tabLayout.getTabAt(3).setIcon(R.mipmap.tab_heart_n);
                        check_set=1;
                        break;
                    case 2:
                        iv.setImageResource(R.mipmap.ic_filter);
                        tabLayout.getTabAt(0).setIcon(R.mipmap.tab_my_n);
                        tabLayout.getTabAt(1).setIcon(R.mipmap.tab_best_n);
                        tabLayout.getTabAt(2).setIcon(R.mipmap.tab_moment_y);
                        tabLayout.getTabAt(3).setIcon(R.mipmap.tab_heart_n);
                        check_set=1;
                        break;
                    case 3:
                        iv.setImageResource(R.mipmap.ic_filter);
                        tabLayout.getTabAt(0).setIcon(R.mipmap.tab_my_n);
                        tabLayout.getTabAt(1).setIcon(R.mipmap.tab_best_n);
                        tabLayout.getTabAt(2).setIcon(R.mipmap.tab_moment_n);
                        tabLayout.getTabAt(3).setIcon(R.mipmap.tab_heart_y);
                        check_set=1;
                        break;
                }
            }
        });



    }
    public void push_set(View v)
    {
        Intent intent = null;
        if(check_set == 0)
        {
            intent = new Intent(this, SettingActivity.class);
        }
        else
        {
            intent = new Intent(this, FilterActivity.class);
        }
        startActivity(intent);
    }

    public void check_info()
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(MainActivity.id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/check_info");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    line = "";
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    while((line = rd.readLine())!=null)
                    {
                        if(line != null)
                        {
                            break;
                        }
                    }
                    wr.close();
                    rd.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
            if(line == null)
            {
                line = "1";
            }
            if(line.equals("1"))
            {
                Log.e("already have info", "already have info");
            }
            else
            {
                Log.e("Don't have info", "Don't have info");
                Dialog_more dialog = new Dialog_more(MainActivity.this,user_id);
                dialog.show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void get_age_nick()
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(MainActivity.id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/get_ager");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    line = "";
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    int i=0;
                    while((line = rd.readLine())!=null)
                    {
                        if(line != null)
                        {
                            if(i == 0)
                            {
                                s1 = line;
                                i++;
                            }
                            else if(i==1)
                            {
                                s2 = line;
                                i++;
                            }
                            else if(i==2)
                            {
                                s3 = line;
                                break;
                            }
                        }
                    }
                    wr.close();
                    rd.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
            user_age = s1;
            user_nick = s2;
            user_img = s3;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        get_age_nick();
        super.onResume();
    }

    @Override
    public void onBackPressed() {

    }
}
