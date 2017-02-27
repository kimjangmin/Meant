package com.mean.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mean.demo.getset.FeedItem;
import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;
import com.mean.demo.all_listener.OnItemClickListener;
import com.mean.demo.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-12.
 */
public class TestRecyclerViewAdapter4 extends RecyclerView.Adapter<TestRecyclerViewAdapter4.CustomViewHolder> {
//public class TestRecyclerViewAdapter2  extends RecyclerView.Adapter<TestRecyclerViewAdapter2.CustomViewHolder> {
    //ViewHolder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView ht_loc,tv_date,ht_name,tv_heart_num,tv_comment_num;
        LinearLayout ht_heart_lay,ht_lay;
        ImageView ib_heart_toggle,img,ht_name_back,ib_share;
        public CustomViewHolder(View v)
        {
            super(v);
            this.tv_heart_num = (TextView)v.findViewById(R.id.ht_heart_num);
            this.tv_comment_num = (TextView)v.findViewById(R.id.ht_comment_num);
            this.img = (ImageView)v.findViewById(R.id.ht_image);
            this.ht_heart_lay = (LinearLayout)v.findViewById(R.id.ht_heart_lay);
            this.ht_lay = (LinearLayout)v.findViewById(R.id.ht_lay);
            this.ht_loc = (TextView)v.findViewById(R.id.ht_loc);
            this.ht_name = (TextView)v.findViewById(R.id.ht_name);
            this.ht_name_back = (ImageView)v.findViewById(R.id.ht_name_back);
            this.ib_heart_toggle = (ImageView)v.findViewById(R.id.ht_heart_toggle);
            this.ib_share = (ImageView)v.findViewById(R.id.w4_share);
        }
    }
    String comment_temp_num,comment_ret,heart_num,heart_toggle,heart_temp_num,user_id,heart_ret;
    // get heart,comment
    String line4,req_id,postlist_data;
    Typeface typeface;
    List<FeedItem> ContentItems = new ArrayList<>();
    List<String> str = new ArrayList<>();
    List<String> postlist = new ArrayList<>();
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    //in get data
    Context mContext;
    static final int TYPE_CELL = 1;

    //Constructor
    public TestRecyclerViewAdapter4(Context context, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        get_heartdata();
        if(postlist.size()>0)
        {
            get_data();
        }
        this.mContext = context;
        this.comment_ret = "0";
        this.comment_temp_num = "0";
        this.heart_ret = "0";
        this.heart_num="0";
        this.heart_toggle="0";
        this.heart_temp_num = "0";
        this.user_id = "";
        this.onItemClickCallback = onItemClickCallback;
    }
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
                return TYPE_CELL;
        }
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_heart, parent, false);
                CustomViewHolder viewHolder = new CustomViewHolder(view);
                return viewHolder;
       // return null;
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Bitmap bmp;
        Log.e("r4_pos", Integer.toString(position));
        FeedItem model = ContentItems.get(position);
        //holder.tv_id.setText(model.getId(position).toString());
        String s = ContentItems.get(position).getId(), temp="";
        temp = ContentItems.get(position).getNick();
        holder.ht_name.setText(temp);     //id

        try{
            Picasso.with(mContext).load(model.getImg_url().toString()).error(R.drawable.tutorial_1).placeholder(R.drawable.tutorial_1).into(holder.img);
        }
        catch(IllegalArgumentException iee)
        {
            holder.img.setImageResource(R.drawable.tutorial_1);
        }

        comment_getnum(model.getpost_num());
        holder.tv_comment_num.setText(comment_ret);   //comment num
        model.setComment_num(Integer.parseInt(comment_ret));
        heart_getdata(model.getpost_num()); //heart data

        if(heart_toggle.equals("1"))
        {holder.ib_heart_toggle.setImageResource(R.mipmap.ic_like_y);}
        else
        {holder.ib_heart_toggle.setImageResource(R.mipmap.ic_like);}

        holder.tv_heart_num.setText(heart_num);
        model.setHeart_num(Integer.parseInt(heart_num));
     //   model.setHeart_toggle(Integer.parseInt(heart_toggle));
        model.setUser_id(MainActivity.id);
        holder.img.bringToFront();
        holder.ht_lay.bringToFront();
        holder.ht_name_back.bringToFront();
        holder.ht_name.bringToFront();

        ContentItems.set(position, model);
        holder.ht_heart_lay.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
        holder.ht_lay.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
        holder.ib_share.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
    }
    @Override
    public int getItemCount() {
        return ContentItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void get_heartdata()
    {
        req_id = MainActivity.id;

        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("request", "UTF-8")+"="+URLEncoder.encode("myheart","UTF-8");
                    data += "&" + URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(req_id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_myheart");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line4="";
                    while((line4 = rd4.readLine())!=null)
                    {
                        Log.e("r4_line postlistdata",line4);
                        postlist.add(line4);
                    }
                    Log.e("heart_get_finish","heart_get_finish");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void get_data()
    {
        postlist_data = "";
        for(int i=0;i<postlist.size();i++)
        {
            if(i ==0)
            {
                postlist_data += postlist.get(i);

            }
            else {
                postlist_data += ("=" + postlist.get(i));
            }
        }
        req_id = MainActivity.id;
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    Log.e("in get_data","in get data");
                    String data = URLEncoder.encode("request", "UTF-8")+"="+URLEncoder.encode("myheart_data","UTF-8");
                    data += "&" + URLEncoder.encode("count","UTF-8")+"="+ URLEncoder.encode(Integer.toString(postlist.size()),"UTF-8");

                    for(int i=0;i<postlist.size();i++)
                    {
                        if(i==0)
                        {
                            data += "&" + URLEncoder.encode(postlist.get(i),"UTF-8");
                        }
                        else
                        {
                            data += "="+URLEncoder.encode(postlist.get(i),"UTF-8");
                        }
                    }
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_myheart");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line4 = "";
                    int check = 0;
                    String imsi = "";
                    while((line4 = rd4.readLine())!=null)
                    {
                        if(check%13 != 7)
                        {
                            check++;
                            str.add(line4);
                        }
                        else
                        {
                            if(line4.charAt(line4.length()-2) != '@' && line4.charAt(line4.length()-1) != '^')
                            {
                                imsi += line4;
                            }
                            else
                            {
                                check++;
                                imsi += line4;
                                str.add(imsi);
                                imsi = "";
                            }
                        }
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
            parseString(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void heart_getdata(int post_num) //Get data from heart DB
    {
        String ret;
        user_id = MainActivity.id;
        heart_temp_num = Integer.toString(post_num);
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("request", "UTF-8")+"="+URLEncoder.encode("moment_heart","UTF-8");
                    data += "&" + URLEncoder.encode("post_num","UTF-8")+"="+ URLEncoder.encode(heart_temp_num,"UTF-8");
                    data += "&" + URLEncoder.encode("my_id","UTF-8")+"="+ URLEncoder.encode(user_id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_data");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));

                    int i=0;
                    while((heart_ret = rd4.readLine())!=null)
                    {
                        if(i ==0)
                        {
                            heart_toggle = heart_ret;
                            Log.e("heart_toggle", heart_toggle);
                            i++;
                        }
                        else
                        {
                            heart_num = heart_ret;
                            Log.e("heart_num",heart_num);
                        }
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void comment_getnum(int post_num)    //Get data from comment DB
    {
        String ret;
        comment_temp_num = Integer.toString(post_num);
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("request", "UTF-8")+"="+URLEncoder.encode("moment_comment","UTF-8");
                    data += "&" + URLEncoder.encode("post_num","UTF-8")+"="+ URLEncoder.encode(comment_temp_num,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_data");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    while((comment_ret = rd4.readLine())!=null)
                    {
                        if(comment_ret != null)
                        {
                            break;
                        }
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void parseString(List<String> res)
    {
        this.str = res;
        Log.e("r4_str.size()", Integer.toString(str.size()));
        for(int i=0;i<(str.size()/13);i++) {
            FeedItem it = new FeedItem();
            it.setpost_num(Integer.parseInt(str.get(13 * i + 0)));
            it.setId(str.get(13 * i + 1));
            it.setDate(str.get(13 * i + 2) + "-" + str.get(13 * i + 3) + "-" + str.get(13 * i + 4));
            it.setAge(str.get(13 * i + 5));
            it.setImg_url(str.get(13 * i + 6));
            it.setMsg(str.get(13 * i + 7));
            it.setFont(str.get(13 * i + 8));
            it.setEffect(str.get(13 * i + 9));
            it.setLocation(str.get(13 * i + 10));
            it.setLux(str.get(13 * i + 11));
            it.setNick(str.get(13 * i + 12));
            ContentItems.add(it);
        }
    }
    public void onChanged(int position,FeedItem f)
    {
        ContentItems.set(position, f);
        this.notifyDataSetChanged();
        Log.e("push","button");
    }


}


