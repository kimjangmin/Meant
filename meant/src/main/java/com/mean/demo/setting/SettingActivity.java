package com.mean.demo.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mean.demo.R;

public class SettingActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this.getApplicationContext();
    }
    public void push_back(View v)
    {
        this.finish();
    }

    public void push_profile(View v)
    {
        Intent i = new Intent(context, S_profile.class);
        startActivity(i);
    }

    public void push_alarm(View v)
    {
        this.finish();
    }

    public void push_font(View v)
    {
        Intent i = new Intent(context, S_Font.class);
        startActivity(i);
    }

    public void push_face(View v)
    {
        Intent i = new Intent(context,S_Facebookid.class);
        startActivity(i);
    }
    public void push_pwd(View v)
    {
        Intent i = new Intent(context,S_Password.class);
        startActivity(i);
    }

    public void push_agree(View v)
    {
        Intent i = new Intent(context,S_Service.class);
        startActivity(i);
    }

    public void push_delete(View v)
    {
        AlertDialog.Builder alt2 = new AlertDialog.Builder(SettingActivity.this);
        alt2.setMessage("기록을 전부 삭제합니다.\n삭제된 내용은\n다시 확인 할 수 없습니다").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete_post.delete();
            }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alt2.setTitle("기록 삭제");
        alt2.show();
    }

    public void push_version(View v)
    {
        Intent i = new Intent(context,S_Version.class);
        startActivity(i);
    }

    public void push_help(View v)
    {
        Intent i = new Intent(context,S_Help.class);
        startActivity(i);
    }

    public void push_ques(View v)
    {
        Intent i = new Intent(context,S_Question.class);
        startActivity(i);
    }
}
