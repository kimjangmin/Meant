package com.mean.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
 * Created by CBR on 2016-01-10.
 */


public class TestRecyclerViewAdapter2  extends RecyclerView.Adapter<TestRecyclerViewAdapter2.CustomViewHolder> {
    //ViewHolder
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_age,tv_msg,tv_heart_num,tv_comment_num,tv_loc;
        ImageButton ib_menu;
        ImageView rank_back, img,ib_heart_toggle,post_grad,rank_color,ib_share;
        LinearLayout best_lay,best_heart_lay;
        public CustomViewHolder(View v)
        {
            super(v);
            this.tv_date = (TextView)v.findViewById(R.id.best_date);
            this.tv_heart_num = (TextView)v.findViewById(R.id.best_heart_num);
            this.tv_comment_num = (TextView)v.findViewById(R.id.best_comment_num);
            this.img = (ImageView)v.findViewById(R.id.best_image);
            this.rank_back = (ImageView)v.findViewById(R.id.best_ranking_back);
            this.rank_color = (ImageView)v.findViewById(R.id.best_ranking_color);
            this.best_lay = (LinearLayout)v.findViewById(R.id.best_lay);
            this.best_heart_lay = (LinearLayout)v.findViewById(R.id.best_heart_lay);
            this.ib_heart_toggle = (ImageView)v.findViewById(R.id.best_heart_toggle);
            this.ib_share = (ImageView)v.findViewById(R.id.w2_share);
        }
    }

    String comment_temp_num,comment_ret,heart_num,heart_toggle,heart_temp_num,user_id,heart_ret;
    String line2,req_id;
    List<FeedItem> ContentItems = new ArrayList<>();
    List<String> str = new ArrayList<>();
    List<String> postlist = new ArrayList<>();
    Context mContext;
    Typeface typeface;
    static final int TYPE_CELL = 1;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;

    //Constructor
    public TestRecyclerViewAdapter2(Context context,  OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        get_bestdata();
        get_best();
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
        switch (viewType) {
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_best, parent, false);
                CustomViewHolder viewHolder = new CustomViewHolder(view);
                return viewHolder;
             }
            }
        return null;
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Bitmap bmp;
        Log.e("r2_pos", Integer.toString(position));
        FeedItem model = ContentItems.get(position);
        String s = ContentItems.get(position).getId(), temp="";


        holder.tv_date.setText(model.getDate().toString()); //date

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

        model.setHeart_toggle(Integer.parseInt(heart_toggle));
        model.setUser_id(MainActivity.id);
        holder.img.bringToFront();
        holder.best_lay.bringToFront();
        holder.rank_back.bringToFront();
        holder.rank_color.bringToFront();
        holder.tv_date.bringToFront();

        ContentItems.set(position, model);
        holder.best_heart_lay.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
        holder.best_lay.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
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
    public void get_bestdata()
    {
        req_id = MainActivity.id;
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("request", "UTF-8")+"="+URLEncoder.encode("best","UTF-8");
                    data += "&" + URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(req_id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_best");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd2 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line2="";
                    while((line2 = rd2.readLine())!=null)
                    {
                        postlist.add(line2);
                    }
                    wr.close();
                    rd2.close();
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

    public void get_best()
    {
        req_id = MainActivity.id;
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("request", "UTF-8")+"="+URLEncoder.encode("best_data","UTF-8");
                    data += "&" + URLEncoder.encode("count","UTF-8")+"="+ URLEncoder.encode(Integer.toString(postlist.size()),"UTF-8");
                    if(postlist.size() >0)
                    {
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
                    }
                    else
                    {
                        data += "&" + URLEncoder.encode("no","UTF-8")+"="+ URLEncoder.encode("no","UTF-8");
                    }

                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_best");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd2 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line2 = "";
                    String imsi = "";
                    int check=0;
                    while((line2 = rd2.readLine())!=null)
                    {
                        if(check%13 != 7)
                        {
                            check++;
                            str.add(line2);
                        }
                        else
                        {
                            if(line2.charAt(line2.length()-2) != '@' && line2.charAt(line2.length()-1) != '^')
                            {
                                imsi += line2;
                            }
                            else
                            {
                                check++;
                                imsi += line2;
                                str.add(imsi);
                                imsi = "";
                            }
                        }
                    }
                    wr.close();
                    rd2.close();
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
                            i++;
                        }
                        else
                        {
                            heart_num = heart_ret;
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
        Log.e("r2_str.size()", Integer.toString(str.size()));
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


