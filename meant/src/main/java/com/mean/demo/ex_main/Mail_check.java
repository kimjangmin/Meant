package com.mean.demo.ex_main;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.mean.demo.GMailSender;
import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;
import com.mean.demo.R;
import com.mean.demo.dialog.Dialog_Cong;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Mail_check extends Activity {
    String mail_addr, password,number, nansu="";
    String num,line,phone,name,year,month,day,sex; //난수저장 및 DB저장 column
    EditText et;
    TextView show,warn;
    Context act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_check);
        Intent intent = getIntent();
        mail_addr = intent.getStringExtra("mail");
        password = intent.getStringExtra("pwd");
        show = (TextView)findViewById(R.id.show_mail);
        et = (EditText)findViewById(R.id.check_num);
        warn = (TextView)findViewById(R.id.warn_msg);
        show.setText(mail_addr);
        line= "";
        phone=null;
        name=null;
        year=null;
        month=null;
        day=null;
        sex=null;
        act = this.getApplicationContext();
        int i = randomRange(1000,9999);//난수생성
        num = Integer.toString(i);

        // email 발송
        send_mail();
    }

    public void send_mail()
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                GMailSender sender = new GMailSender("js204703","ptxehvuubbedzzje");
                try{
                    sender.sendMail("EMPATHY 인증번호입니다.", "당신의 인증번호는 " + num + "입니다.", "js204703@gmail.com", mail_addr);
                }catch(Exception e)
                {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }
        });
        thread.start();
        try
        {
            thread.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void push_ok(View v)
    {
        number = et.getText().toString();   //입력된 난수
        if(number.equals(num))  //입력된 숫자와 발생한 난수가 같으면
        {
            Thread thread = new Thread(new Runnable()
            {
                public void run()
                {
                    try{
                        String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(mail_addr,"UTF-8");
                        data += "&" + URLEncoder.encode("pwd","UTF-8")+"="+ URLEncoder.encode(password, "UTF-8");
                        URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/regist");
                        URLConnection conn = url.openConnection();
                        conn.setDoOutput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        wr.write(data);
                        wr.flush();

                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                        while((line = rd.readLine())!=null)
                        {
                            System.out.println(line);
                            Log.e("read", line);
                            break;
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
                if(line.equals("1"))
                {
                    Log.e("success", "login success");
                    Dialog_Cong dialog = new Dialog_Cong(this,mail_addr);
                    dialog.show();
                }
                else
                {
                    Log.e("failed", "login failed");
                    Toast.makeText(act, "login failed", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        {
            warn.setText("인증번호가 틀렸습니다.");   //색깔 빨강으로 변경
        }
    }
    public static int randomRange(int n1, int n2)
    {
        return (int)(Math.random() * (n2 - n1 + 1)) + n1;
    }

    public void re_send(View v)
    {
        send_mail();
    }

    public void re_set(View v)
    {
        Intent j = new Intent(this, SignupActivity.class);
        startActivity(j);
        finish();
    }

    public void go_back(View v)
    {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        Intent j = new Intent(this, MainActivity.class);
        startActivity(j);
        finish();
    }

}

