package com.mean.demo.ex_main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.mean.demo.IP_ADDR;
import com.mean.demo.R;
import com.mean.demo.adapter.CustomViewAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Login_dir extends AppCompatActivity {

    public com.facebook.login.widget.LoginButton loginButton;
    public CallbackManager callbackManager;
    public AccessTokenTracker accessTokenTracker;
    String res, id;
    Context con;
    GraphRequest request;
    List<String> str = new ArrayList<String>();
    ViewPager page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        setContentView(R.layout.activity_login_dir);
        con = this.getApplicationContext();

        page = (ViewPager)findViewById(R.id.pager);
        CustomViewAdapter adapter = new CustomViewAdapter(getLayoutInflater());
        page.setAdapter(adapter);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult result) {

                //   AccessToken tok = result.getAccessToken();
                Profile pro = Profile.getCurrentProfile();
                id = pro.getId();
                check_face();
                if (str.get(0).equals("1")) {
                    Intent intent = new Intent(con, LoginActivity.class);
                    intent.putExtra("face_flag", "1");
                    intent.putExtra("mail_id", str.get(1));
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(con, Make_Face.class);
                    intent.putExtra("face_id", id);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(FacebookException error) {
            }

            @Override
            public void onCancel() {
                finish();
            }
        });
    }
    public void check_face()    //Get data from comment DB
    {
        Log.e("check face","cf");
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+id;
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/check_face");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    res = null;
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    while((res = rd.readLine())!=null)
                    {
                        str.add(res);
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void go_direct(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("face_flag","0");
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}