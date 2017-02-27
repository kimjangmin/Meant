package com.mean.demo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.mean.demo.adapter.Comment_Adapter;
import com.mean.demo.getset.Comment_value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CommoreActivity extends AppCompatActivity {
    public static List<String> datalist;
    public static List<String> ls;
    public static List<Integer> li;
    ListView list;
    private Comment_Adapter adapter;
    Context mContext;
    String send_msg, get_y, get_m, get_d;
    EditText et;
    java.util.Calendar cal  = java.util.Calendar.getInstance();
    public static int post_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commore);
        Intent intent = getIntent();
        datalist = new ArrayList<>();
        ls = new ArrayList<>();
        li = new ArrayList<>();
        list = (ListView)findViewById(R.id.comore_list);
        et = (EditText)findViewById(R.id.comore_editText);
        datalist = intent.getStringArrayListExtra("data");
        post_num = intent.getIntExtra("post_num", 0);
        ls = intent.getStringArrayListExtra("StringSet");
        li = intent.getIntegerArrayListExtra("IntSet");
        mContext=this.getApplicationContext();

        adapter = new Comment_Adapter();
        get_y = Integer.toString(cal.get(cal.YEAR));
        get_m = Integer.toString(cal.get(cal.MONTH) + 1);
        get_d = Integer.toString(cal.get(cal.DATE));

        for(int i=0;i<(datalist.size()/7);i++)
        {
            Comment_value cv = new Comment_value();
            cv.setId(datalist.get(7 * i + 0));
            cv.setYear(datalist.get(7 * i + 1));
            cv.setMonth(datalist.get(7 * i + 2));
            cv.setDay(datalist.get(7 * i + 3));
            cv.setMsg(datalist.get(7 * i + 4));
            cv.setcomment_num(datalist.get(7 * i + 5));
            cv.setis_reple(datalist.get(7 * i + 6));
            adapter.add(cv);
        }
        list.setAdapter(adapter);
    }

    public void input_comment(View v)
    {
        send_msg = et.getText().toString();
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("post_num", "UTF-8")+"="+URLEncoder.encode(Integer.toString(post_num),"UTF-8");
                    data += "&"+URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(MainActivity.id,"UTF-8");
                    data += "&"+URLEncoder.encode("year", "UTF-8")+"="+URLEncoder.encode(get_y,"UTF-8");
                    data += "&"+ URLEncoder.encode("month", "UTF-8")+"="+URLEncoder.encode(get_m,"UTF-8");
                    data += "&"+ URLEncoder.encode("day", "UTF-8")+"="+URLEncoder.encode(get_d,"UTF-8");
                    data += "&"+URLEncoder.encode("msg", "UTF-8")+"="+URLEncoder.encode(send_msg,"UTF-8");
                    data += "&"+URLEncoder.encode("is_reple", "UTF-8")+"="+URLEncoder.encode("0","UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/push_comment");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    String line2 = "";
                    while((line2 = rd4.readLine())!=null)
                    {
                        Log.e("push_comment data", line2);
                    }
                    wr.close();
                    rd4.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
            Intent inte = new Intent(this,CommoreActivity.class);
            datalist.add(MainActivity.id);
            datalist.add(get_y);
            datalist.add(get_m);
            datalist.add(get_d);
            datalist.add(send_msg);
            datalist.add("0");
            inte.putStringArrayListExtra("data", (ArrayList<String>) datalist);
            inte.putExtra("post_num",post_num);
            startActivity(inte);
            finish();
            // parseResult(response.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void push_back(View v)
    {
        Intent intent = new Intent(mContext,CommentActivity.class);
        intent.putStringArrayListExtra("StringSet", (ArrayList<String>) ls);
        intent.putIntegerArrayListExtra("IntSet", (ArrayList<Integer>) li);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(mContext,CommentActivity.class);
        intent.putStringArrayListExtra("StringSet", (ArrayList<String>) ls);
        intent.putIntegerArrayListExtra("IntSet", (ArrayList<Integer>) li);
        startActivity(intent);
        finish();

    }
}
