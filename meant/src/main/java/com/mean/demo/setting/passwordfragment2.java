package com.mean.demo.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by CBR on 2016-01-21.
 */
public class passwordfragment2 extends Fragment {

    EditText et3;
    EditText et4;
    EditText et5;
    Button bt7;
    String now_pwd,check_pwd,check_pwd2,password,req_id,line;
    Context context;
    public passwordfragment2()
    {

    }

    @SuppressLint("ValidFragment")
    public passwordfragment2(Context con)
    {
        this.context = con;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.password2, container, false);
        et3 = (EditText)v.findViewById(R.id.now_pwd);
        et4 = (EditText)v.findViewById(R.id.check_pwd);
        et5 = (EditText)v.findViewById(R.id.check_pwd2);
        bt7 = (Button)v.findViewById(R.id.button7);
        password = MainActivity.pwd;
        bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                now_pwd = et3.getText().toString();
                check_pwd2 = et5.getText().toString();
                check_pwd = et4.getText().toString();
                if(password.equals(now_pwd)) {
                    if (!(now_pwd.equals(check_pwd))) {
                        if (check_pwd.equals(check_pwd2)) {
                            change_pwd();
                            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                            ab.setMessage("비밀번호가 변경 되었습니다").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    et3.setText("");
                                    et4.setText("");
                                    et5.setText("");
                                    Intent j = new Intent(context,MainActivity.class);
                                    j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    j.putExtra("flag", 0);
                                    j.putExtra("user_id", MainActivity.id);
                                    j.putExtra("pwd", MainActivity.pwd);
                                    startActivity(j);
                                }
                            });
                            ab.setTitle("비밀번호 변경");
                            ab.show();
                        } else {
                            AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                            ab.setMessage("비밀번호를 다시 확인하여 주세요").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            ab.setTitle("재확인");
                            ab.show();

                        }
                    } else {
                        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                        ab.setMessage("이전 비밀번호와 같습니다").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        ab.show();
                    }
                }
                else
                {
                    AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
                    ab.setMessage("예전 비밀번호와 일치 하지 않습니다").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ab.show();
                }

            }
        });
        return v;
    }


    public void change_pwd()
    {
        req_id = MainActivity.id;
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(req_id,"UTF-8");
                    data += "&" + URLEncoder.encode("check_pwd","UTF-8")+"="+ URLEncoder.encode(check_pwd,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/change_pwd");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd3 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line="";
                    while((line = rd3.readLine())!=null)
                    {
                    }
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
}
