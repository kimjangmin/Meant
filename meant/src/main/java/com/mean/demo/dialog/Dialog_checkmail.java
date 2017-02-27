package com.mean.demo.dialog;

/**
 * Created by CBR on 2016-01-10.
 */
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mean.demo.IP_ADDR;
import com.mean.demo.ex_main.Mail_check;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Dialog_checkmail extends Dialog implements View.OnClickListener {
    TextView et1;
    TextView bt1,bt2;
    String id,password;
    String line;
    Context act;
    public Dialog_checkmail(Context context,String mail,String pwd)
    {
        super(context);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_check);
        et1 = (TextView)findViewById(R.id.custom1_input);
        bt1 = (TextView)findViewById(R.id.custom1_btn1);
        bt2 = (TextView)findViewById(R.id.custom1_btn2);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        et1.setText(mail);
        this.password = pwd;
        act = context;
        line = "";
    }

    public void onClick(View v)
    {
        id = et1.getText().toString();
        if(v == bt1)
        {
            Log.e("ID", id);
            Thread thread = new Thread(new Runnable()
            {
                public void run()
                {
                    try
                    {
                        String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                        URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/check_mail");
                        URLConnection conn = url.openConnection();
                        conn.setDoOutput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                        wr.write(data);
                        wr.flush();
                        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while((line = rd.readLine()) != null)
                        {
                            Log.e("read",line);
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
            try
            {
                thread.join();
                if(line.equals("1"))    //이미 존재하는 ID -> 이미 사용중인 계정 dialog
                {
                    dismiss();
                    Dialog_Used dialog = new Dialog_Used(act,et1.getText().toString());
                    dialog.show();
                }
                else    //이메일 인증 액티비티로 이동
                {
                    dismiss();
                    Intent j = new Intent(act,Mail_check.class);
                    j.putExtra("mail",id);
                    j.putExtra("pwd",password);
                    act.startActivity(j);
                }

            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            dismiss();
        }

    }

}