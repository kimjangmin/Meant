package com.mean.demo.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.mean.demo.R;

import java.util.ArrayList;

public class S_Help extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    SearchView view;
    public static Activity activity;

    ArrayList<String> arraylist;
    final String s1 = "Q. 회원가입은 어떻게 하나요? \n A. 잘하시면 됩니다 ^^";
    final String s2 = "Q. 회원 탈퇴를 하고 싶습니다 \n A. 못합니다";
    final String s3 = "Q. 비밀번호를 잃어버렸습니다 \n A. 찾을수가 없습니다";
    TextView tv;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__help);
        context = this.getApplicationContext();

        tv = (TextView)findViewById(R.id.textView8);
        arraylist = new ArrayList<String>();
        arraylist.add("회원가입은 어떻게 이루어 지나요?");
        arraylist.add("회원 탈퇴를 하고 싶습니다");
        arraylist.add("비밀번호를 잃어버렸습니다");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraylist);
        Spinner sp = (Spinner) this.findViewById(R.id.spinner2);
        sp.setPrompt("도움말"); // 스피너 제목
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int position2 = position;
        ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
        switch(position2)
        {
            case 0:
                tv.setText(s1);
                tv.setTextColor(Color.BLACK);
                break;
            case 1:
                tv.setText(s2);
                tv.setTextColor(Color.BLACK);
                break;
            case 2:
                tv.setText(s3);
                tv.setTextColor(Color.BLACK);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void push_back(View v)
    {
        finish();
    }

}