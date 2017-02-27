package com.mean.demo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mean.demo.adapter.Comment_Adapter;
import com.mean.demo.getset.Comment_value;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    private ListView listView;
    private Comment_Adapter adapter;
    String line,line2,send_msg,get_y,get_m,get_d,is_re;
    String cate,fire,bunryu,fire_num,my_id;
    int post_num;
    Context mContext,fContext;
    List<String> ls,datalist,com_id,y,m,d,msg,comment_num,is_reple,comment_nick;
    List<Integer> li;
    ImageView iv,iv_toggle;
    TextView tv_nick,tv_loc,ment,tv_heart_num,tv_com_num;
    EditText et;
    java.util.Calendar cal = java.util.Calendar.getInstance();
    Typeface typeface;
    Bitmap bmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        adapter = new Comment_Adapter();
        listView = (ListView)findViewById(R.id.comment_list);
        iv = (ImageView)findViewById(R.id.com_post_image);
        et = (EditText)findViewById(R.id.editText);
        tv_loc = (TextView)findViewById(R.id.comment_location);
        tv_nick = (TextView)findViewById(R.id.post_nick);
        iv_toggle = (ImageView)findViewById(R.id.com_toggle);
        tv_heart_num = (TextView)findViewById(R.id.com_heart);
        tv_com_num = (TextView)findViewById(R.id.com_more);
        ment = (TextView)findViewById(R.id.com_more);

        mContext = getApplicationContext();
        fContext = CommentActivity.this;

        Intent intent = getIntent();
        ls = intent.getStringArrayListExtra("StringSet");
        li = intent.getIntegerArrayListExtra("IntSet");
        post_num = li.get(0);
        tv_loc.setText(ls.get(6));
        tv_nick.setText(ls.get(7));

        if(li.get(2) == 0)
        {
            iv_toggle.setImageResource(R.mipmap.ic_like);
        }
        else
        {
            iv_toggle.setImageResource(R.mipmap.ic_like_y);
        }
        tv_com_num.setText(Integer.toString(li.get(3)));
        tv_heart_num.setText(Integer.toString(li.get(1)));

        Picasso.with(mContext).load(ls.get(5)).error(R.drawable.back_1).placeholder(R.drawable.back_1).into(iv);
        // 넘겨온 글의 이미지와 메시지 정보 등록
        get_y = Integer.toString(cal.get(cal.YEAR));
        get_m = Integer.toString(cal.get(cal.MONTH)+1);
        get_d = Integer.toString(cal.get(cal.DATE));
        is_re = "0";

        get_data();
        ment.setText(Integer.toString(datalist.size()/8));
        for(int i=0;i<(datalist.size()/8);i++)
        {
            Comment_value cv = new Comment_value();
            cv.setId(com_id.get(i));
            cv.setYear(y.get(i));
            cv.setMonth(m.get(i));
            cv.setDay(d.get(i));
            cv.setMsg(msg.get(i));
            cv.setcomment_num(comment_num.get(i));
            cv.setis_reple(is_reple.get(i));
            cv.setNick(comment_nick.get(i));
            adapter.add(cv);
        }
        listView.setAdapter(adapter);


    }
    public CommentActivity()
    {
        ls = new ArrayList<>();
        li = new ArrayList<>();
        datalist = new ArrayList<>();
        y = new ArrayList<>();
        m = new ArrayList<>();
        d = new ArrayList<>();
        com_id = new ArrayList<>();
        msg = new ArrayList<>();
        comment_num = new ArrayList<>();
        is_reple = new ArrayList<>();
        comment_nick = new ArrayList<>();
    }

    public void input_comment(View v)
    {
        send_msg = et.getText().toString();
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("post_num", "UTF-8")+"="+URLEncoder.encode(Integer.toString(li.get(0)),"UTF-8");
                    data += "&"+URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(MainActivity.id,"UTF-8");
                    data += "&"+URLEncoder.encode("year", "UTF-8")+"="+URLEncoder.encode(get_y,"UTF-8");
                    data += "&"+ URLEncoder.encode("month", "UTF-8")+"="+URLEncoder.encode(get_m,"UTF-8");
                    data += "&"+ URLEncoder.encode("day", "UTF-8")+"="+URLEncoder.encode(get_d,"UTF-8");
                    data += "&"+URLEncoder.encode("msg", "UTF-8")+"="+URLEncoder.encode(send_msg,"UTF-8");
                    data += "&"+URLEncoder.encode("is_reple", "UTF-8")+"="+URLEncoder.encode(is_re,"UTF-8");
                    data += "&"+URLEncoder.encode("com_nick", "UTF-8")+"="+URLEncoder.encode(MainActivity.user_nick,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/push_comment");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line2 = "";
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

            Intent intent = new Intent(mContext,CommentActivity.class);
            List<String> l = new ArrayList<>();
            List<Integer> i = new ArrayList<>();
            for(int q=0;q<7;q++)
            {
                l.add(ls.get(q));
                i.add(li.get(q));
            }
            l.add(ls.get(7));
            intent.putStringArrayListExtra("StringSet", (ArrayList<String>) l);
            intent.putIntegerArrayListExtra("IntSet", (ArrayList<Integer>) i);
            startActivity(intent);
            adapter.notifyDataSetChanged();
            finish();
            // parseResult(response.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void get_data()
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("post_num", "UTF-8")+"="+URLEncoder.encode(Integer.toString(li.get(0)),"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_comment");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line = "";
                    while((line = rd4.readLine())!=null)
                    {
                        Log.e("comment_line data",line);
                        datalist.add(line);
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
            // parseResult(response.toString());
            parseString(datalist);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void parseString(List<String> s)
    {
        for(int i=0;i<(s.size()/8);i++)
        {
            com_id.add(i, s.get(8 * i + 0));
            Log.e("com_id", com_id.get(i));
            y.add(i, s.get(8 * i + 1));
            m.add(i, s.get(8*i+2));
            d.add(i, s.get(8*i+3));
            msg.add(i, s.get(8*i+4));
            comment_num.add(i, s.get(8*i+5));
            is_reple.add(i, s.get(8 * i + 6));
            comment_nick.add(i, s.get(8 * i + 7));
        }
    }

    public void comment_more(View v)
    {
        Intent inte = new Intent(mContext, CommoreActivity.class);
        inte.putStringArrayListExtra("data", (ArrayList<String>) datalist);
        inte.putExtra("post_num",li.get(0));
        inte.putExtra("StringSet", (ArrayList<String>) ls);
        inte.putExtra("IntSet", (ArrayList<Integer>) li);
        startActivity(inte);
        finish();
    }

    public void push_back(View v)
    {
        this.finish();
    }

    public void go_fire(View vie)
    {
        fire_num = Integer.toString(post_num);
        my_id = MainActivity.id;
        bunryu = "0";
        AlertDialog.Builder firedialog = new AlertDialog.Builder(fContext);
        firedialog.setTitle("신고하기");
        LayoutInflater inflater = (LayoutInflater)fContext.getSystemService(fContext.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_spinner,null);

        et= (EditText)v.findViewById(R.id.editText3);
        et.setHint("신고 내용");
        et.setHintTextColor(Color.DKGRAY);


        final Spinner spinner = (Spinner)v.findViewById(R.id.viewSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(fContext, R.array.option, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cate = (String) spinner.getAdapter().getItem(spinner.getSelectedItemPosition());

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firedialog.setView(v);
        firedialog.setPositiveButton("신고", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fire = String.valueOf(et.getText());
                Thread thread = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try{
                            String data = URLEncoder.encode(my_id, "UTF-8")+"="+URLEncoder.encode(fire_num,"UTF-8");
                            data += "="+URLEncoder.encode(cate, "UTF-8")+"="+URLEncoder.encode(fire,"UTF-8");
                            data += "="+URLEncoder.encode(bunryu, "UTF-8");
                            URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/add_fire");
                            URLConnection conn = url.openConnection();
                            conn.setDoOutput(true);
                            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                            wr.write(data);
                            wr.flush();
                            BufferedReader rd3 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                            String line="";
                            while((line = rd3.readLine())!=null)
                            {
                            }
                            Log.e("post_finish","post_finish");
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
                Toast.makeText(fContext.getApplicationContext(), "신고사유 :" + cate + "\n신고내용:" + fire + "\n신고가 접수되었습니다.", Toast.LENGTH_LONG).show();

            }
        });
        firedialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        Log.e("here","you're");
        firedialog.show();
    }

}
