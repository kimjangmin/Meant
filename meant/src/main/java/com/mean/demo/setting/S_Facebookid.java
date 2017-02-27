package com.mean.demo.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.mean.demo.R;

public class S_Facebookid extends AppCompatActivity {

    static String id;
    ImageView view;
    TextView tvtv,textview6;
    ToggleButton toggle;

    private CallbackManager callbackManager;
    boolean log;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_s__facebookid);
        context = this.getApplicationContext();

        view = (ImageView)findViewById(R.id.imageView2);
        tvtv = (TextView)findViewById(R.id.tvtv);
        textview6 = (TextView)findViewById(R.id.textView6);
        toggle = (ToggleButton)findViewById(R.id.toggleButton);

        log = islog();
        if(log)  //연동중인 상태라면
        {
            toggle.setChecked(true);
            tvtv.setText("페이스북 계정이 연동되어 있습니다");
            Profile pro = Profile.getCurrentProfile();
            String name = pro.getName();
            textview6.setText(name);
        }
        else //연동중이지 않다면
        {
            toggle.setChecked(false);
            tvtv.setText("페이스북 계정이 연동되있지 않습니다");
            textview6.setText("");

        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b) // 킨거
                {
                    LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {

                            Intent i = new Intent(S_Facebookid.this, S_Facebookid.class);
                            startActivity(i);
                            finish();
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });

                }
                else //끈거
                {
                    LoginManager.getInstance().logOut();
                    Intent i = new Intent(S_Facebookid.this, S_Facebookid.class);
                    startActivity(i);
                    finish();

                }

            }
        });

    }
    public boolean islog()
    {
        return AccessToken.getCurrentAccessToken() != null;

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void push_back(View v)
    {
        finish();
    }

}