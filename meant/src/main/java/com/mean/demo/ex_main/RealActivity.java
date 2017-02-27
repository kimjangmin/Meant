package com.mean.demo.ex_main;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.mean.demo.R;
import com.victor.loading.newton.NewtonCradleLoading;

public class RealActivity extends AppCompatActivity{
private NewtonCradleLoading newtonCradleLoading;
private Button button;
    Context context;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        FacebookSdk.sdkInitialize(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real);
        context = this.getApplicationContext();
        LoginManager.getInstance().logOut();
       intent=new Intent(this, Login_dir.class);
        new CountDownTimer(2*1000,900){
                public void onTick(long millisUnitilFinished){
                }
                public void onFinish(){

                    startActivity(intent);
                    finish();
                }
        }.start();
   }
}