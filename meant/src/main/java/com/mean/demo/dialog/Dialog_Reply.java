package com.mean.demo.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mean.demo.CommentActivity;
import com.mean.demo.CommoreActivity;
import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CBR on 2016-01-27.
 */
public class Dialog_Reply extends Dialog implements View.OnClickListener {

    EditText et;
    String str,get_y,get_m,get_d,id,com_num;
    Button bt1,bt2;
    Context act;
    java.util.Calendar cal = java.util.Calendar.getInstance();
    public Dialog_Reply(Context context, String com_num)
    {
        super(context);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_reply);

        this.com_num = com_num;
        et = (EditText)findViewById(R.id.reply_et);
        bt1 = (Button)findViewById(R.id.reply_yes);
        bt2 = (Button)findViewById(R.id.reply_no);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        act = context;
        id = MainActivity.id;
        get_y = Integer.toString(cal.get(cal.YEAR));
        get_m = Integer.toString(cal.get(cal.MONTH) + 1);
        get_d = Integer.toString(cal.get(cal.DATE));
    }

    @Override
    public void onClick(View v) {
        if(v == bt1)
        {
            input_reply();
            Intent intent = new Intent(act,CommentActivity.class);
            List<String> l = CommoreActivity.ls;
            List<Integer> i = CommoreActivity.li;
            intent.putStringArrayListExtra("StringSet", (ArrayList<String>) l);
            intent.putIntegerArrayListExtra("IntSet", (ArrayList<Integer>) i);
            intent.putStringArrayListExtra("data", (ArrayList<String>) CommoreActivity.datalist);
            intent.putExtra("post_num", CommoreActivity.post_num);
            act.startActivity(intent);
            if(act instanceof Activity)
            {
                ((Activity)act).finish();
            }
            dismiss();
        }
        else if(v == bt2)
        {
            dismiss();
        }
    }

    public void input_reply()
    {
        str = et.getText().toString();

        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("post_num", "UTF-8")+"="+URLEncoder.encode(Integer.toString(CommoreActivity.post_num),"UTF-8");
                    data += "&"+URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(MainActivity.id,"UTF-8");
                    data += "&"+URLEncoder.encode("year", "UTF-8")+"="+URLEncoder.encode(get_y,"UTF-8");
                    data += "&"+ URLEncoder.encode("month", "UTF-8")+"="+URLEncoder.encode(get_m,"UTF-8");
                    data += "&"+ URLEncoder.encode("day", "UTF-8")+"="+URLEncoder.encode(get_d,"UTF-8");
                    data += "&"+URLEncoder.encode("msg", "UTF-8")+"="+URLEncoder.encode(str,"UTF-8");
                    data += "&"+URLEncoder.encode("is_reple", "UTF-8")+"="+URLEncoder.encode("1","UTF-8");
                    data += "&"+URLEncoder.encode("com_num", "UTF-8")+"="+URLEncoder.encode(com_num,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/push_comment");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    String line = "";
                    while((line = rd.readLine())!=null)
                    {
                        Log.e("reply data", line);
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

}

