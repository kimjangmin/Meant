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
import com.mean.demo.setting.ApplicationClass;
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
 * Created by florentchampigny on 24/04/15.
 */
public class TestRecyclerViewAdapter_Best extends RecyclerView.Adapter<TestRecyclerViewAdapter_Best.CustomViewHolder> {
    ApplicationClass applicationClass;
    Bitmap bitmap=null;
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_heart_num,tv_comment_num,tv_loc,tv_user,big_medal,big_comment,big_love;
        TextView b1,b2;
        ImageView img,ib_heart_toggle,profile_card,ib_share;
        LinearLayout post_layout,heart_layout;
        public CustomViewHolder(View v)
        {
            super(v);
            this.tv_date = (TextView)v.findViewById(R.id.post_date);
            this.tv_heart_num = (TextView)v.findViewById(R.id.post_heart_num);
            this.tv_comment_num = (TextView)v.findViewById(R.id.post_comment_num);
            this.ib_heart_toggle = (ImageView)v.findViewById(R.id.post_heart_toggle);
            this.tv_loc = (TextView)v.findViewById(R.id.post_loc);
            this.img = (ImageView)v.findViewById(R.id.post_image);
            this.b1 = (TextView)v.findViewById(R.id.btn1);
            this.b2 = (TextView)v.findViewById(R.id.btn2);
            this.profile_card= (ImageView)v.findViewById(R.id.profile);
            this.post_layout = (LinearLayout)v.findViewById(R.id.post_lay);
            this.heart_layout = (LinearLayout)v.findViewById(R.id.post_heart_lay);
            this.tv_user = (TextView)v.findViewById(R.id.user_big);
            this.ib_share = (ImageView)v.findViewById(R.id.w_share);
            this.big_medal = (TextView)v.findViewById(R.id.big_medal);
            this.big_comment = (TextView)v.findViewById(R.id.big_comment);
            this.big_love = (TextView)v.findViewById(R.id.big_love);
        }
    }
    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    static final int TYPE_SELECT = 2;
    Typeface typeface;
    String comment_temp_num,comment_ret,heart_num,heart_toggle,heart_temp_num,user_id,heart_ret;
    String line,req_id,postlist_data;
    List<FeedItem> ContentItems = new ArrayList<>();
    List<String> str = new ArrayList<>();
    List<String> postlist = new ArrayList<>();
    Context mContext;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public TestRecyclerViewAdapter_Best(Context context,  OnItemClickListener.OnItemClickCallback onItemClickCallback) {
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
            case 0:
                return TYPE_HEADER;
            case 1:
                return TYPE_SELECT;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return ContentItems.size()+2;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                CustomViewHolder viewHolder = new CustomViewHolder(view);
                return viewHolder;
            }
            case TYPE_SELECT: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_select, parent, false);
                CustomViewHolder viewHolder = new CustomViewHolder(view);
                return viewHolder;
            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_post, parent, false);
                CustomViewHolder viewHolder = new CustomViewHolder(view);
                return viewHolder;
            }
        }
        return null;
    }
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                String tsr3 = "";
                String tsr1="",tsr2="";
                tsr1 = MainActivity.user_nick;
                tsr2 = MainActivity.user_age;
                tsr3 = MainActivity.user_img;
                Picasso.with(mContext).load(tsr3).error(R.drawable.tutorial_1).placeholder(R.drawable.tutorial_1).into(holder.profile_card);
                tsr1 = tsr1 + "\nsince "+tsr2;
                holder.tv_user.setText(tsr1);
                break;
            case TYPE_SELECT:
                holder.b1.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
                holder.b2.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
                break;
            case TYPE_CELL:
                Bitmap bmp;
                position -= 2;
                Log.e("r1_pos", Integer.toString(position));
                FeedItem model = ContentItems.get(position);

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

                holder.tv_date.setText(model.getDate().toString()); //date
                holder.tv_loc.setText(model.getLocation().toString());  //location

                model.setHeart_num(Integer.parseInt(heart_num));
                model.setHeart_toggle(Integer.parseInt(heart_toggle));
                model.setUser_id(MainActivity.id);
                holder.img.bringToFront();
                holder.post_layout.bringToFront();

                ContentItems.set(position, model);

                holder.heart_layout.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
                holder.post_layout.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
                //       holder.ib_menu.setOnClickListener(new OnItemClickListener(position, onItemClickCallback, ContentItems));
                break;
        }
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
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line="";
                    while((line = rd.readLine())!=null)
                    {
                        postlist.add(line);
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

    public void get_best()
    {
        req_id = MainActivity.id;
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    Log.e("in get_data","in get data");
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

                    Log.e("data ",data);

                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/request_best");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line = "";
                    int check = 0;
                    String imsi="";
                    while((line = rd.readLine())!=null)
                    {
                        if(check%13 != 7)
                        {
                            check++;
                            str.add(line);
                        }
                        else
                        {
                            if(line.charAt(line.length()-2) != '@' && line.charAt(line.length()-1) != '^')
                            {
                                imsi += line;
                            }
                            else
                            {
                                check++;
                                imsi += line;
                                str.add(imsi);
                                imsi = "";
                            }
                        }
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
        for(int i=0;i<(str.size()/13);i++) {
            if(req_id.equals(str.get(13*i+1)))
            {
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
                it.setNick(str.get(13 * i + 11));
                ContentItems.add(it);
            }

        }
    }
    public void onChanged(int position,FeedItem f)
    {
        ContentItems.set(position, f);
        this.notifyDataSetChanged();
        Log.e("push", "button");
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}