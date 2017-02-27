package com.mean.demo.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.mean.demo.ex_main.Login_dir;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.util.ArrayList;
import java.util.HashMap;

public class S_Service extends AppCompatActivity {

    ApplicationClass appli;
    ExpandableListView view;
    private ArrayList<String> arrayGroup = new ArrayList<String>();
    private HashMap<String, ArrayList<String>> arrayChild = new HashMap<String,ArrayList<String>>();
    TextView confirm;
    CheckBox check;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__service);
        context = this.getApplicationContext();
        appli = new ApplicationClass();
        view = (ExpandableListView)findViewById(R.id.expandableListView2);
        view.setSelector(new PaintDrawable(0x9e9e9e));
        check = (CheckBox)findViewById(R.id.checkBox);
        setArrayData();
        view.setAdapter(new Adp(this, arrayGroup, arrayChild));
        confirm = (TextView)findViewById(R.id.button5);

        if(appli.agreeTok==1)
        {
            check.setChecked(true);
        }
        else
        {
            check.setChecked(false);
        }

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.getId() == R.id.checkBox) {
                    if (isChecked) {
                        appli.agreeTok = 1;

                    } else {
                        appli.agreeTok = 0;

                    }

                }

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (appli.agreeTok == 1) {
                    AlertDialog.Builder alt = new AlertDialog.Builder(S_Service.this);
                    alt.setMessage("약관에 동의 하셨습니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent11 = new Intent(context, MainActivity.class);
                            startActivity(intent11);
                        }
                    });
                    alt.setTitle("동의 확인");
                    alt.show();

                } else {
                    AlertDialog.Builder alt = new AlertDialog.Builder(S_Service.this);
                    alt.setMessage("동의를 하지 않으면 서비스를 이용할수 없습니다.\n 정말 동의하지 않습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginManager.getInstance().logOut();
                            Intent intent11 = new Intent(context, Login_dir.class);
                            startActivity(intent11);
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alt.setTitle("동의확인");
                    alt.show();
                }
            }
        });
    }
    private void setArrayData() {
        arrayGroup.add("위치정보 이용 동의");
        arrayGroup.add("프로필 정보 수집 동의");
        arrayGroup.add("이벤트 및 광고성 정보 수신 동의");


        ArrayList<String> use = new ArrayList<String>();
        use.add("현재 자신의 위치정보를 제공하는 것에 동의합니다");
        ArrayList<String> info = new ArrayList<String>();
        info.add("자신의 프로필 정보를 수집하는것을 동의 합니다");
        ArrayList<String> pol = new ArrayList<String>();
        pol.add("이벤트 및 광고성 정보를 수신받는것에 동의합니다");


        arrayChild.put(arrayGroup.get(0), use);
        arrayChild.put(arrayGroup.get(1), info);
        arrayChild.put(arrayGroup.get(2), pol);

    }
    public void push_back(View v)
    {
       finish();
    }

}