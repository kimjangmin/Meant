package com.mean.demo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.mean.demo.CommentActivity;
import com.mean.demo.getset.FeedItem;
import com.mean.demo.IP_ADDR;
import com.mean.demo.Write.InstaActivity;
import com.mean.demo.all_listener.OnItemClickListener;
import com.mean.demo.R;
import com.mean.demo.adapter.TestRecyclerViewAdapter4;
import com.mean.demo.Write.Write_Activity1;
import com.melnykov.fab.FloatingActionButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-01-12.
 */
public class RecyclerViewFragment_4 extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    Context context;
    private static final int ITEM_COUNT = 2;
    public List<FeedItem> ContentItems = new ArrayList<>();
    public String line;//user;
    static String user;
    ImageView ib;
    TextView tv;
    FeedItem fid;
    String chg_heart_id,chg_heart_post,command,share_url;
    TestRecyclerViewAdapter4 trva4;
    Bitmap bit;
    HttpGet request;
    HttpResponse reponse;
    Bitmap bitmap2;

    private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback()
    {
        @Override
        public void onItemClicked(View view, int position, List<FeedItem> it) {
            fid = it.get(position);
            if(view.getId() == R.id.ht_heart_lay)
            {
                fid = it.get(position);
                Log.e("heart numberber : ",Integer.toString(fid.getHeart_num()));
                if(it.get(position).getHeart_toggle() == 1)
                {
                    ib = (ImageView)view.findViewById(R.id.ht_heart_toggle);
                    tv = (TextView)view.findViewById(R.id.ht_heart_num);
                    tv.setText(Integer.toString(fid.getHeart_num() - 1));
                    ib.setImageResource(R.mipmap.ic_like);
                    change_heartdata(fid, 1);
                    fid.setHeart_toggle(0);
                    fid.setHeart_num(fid.getHeart_num() - 1);
                    trva4.onChanged(position, fid);
                    mAdapter.notifyDataSetChanged();
                }
                else
                {
                    ib = (ImageView)view.findViewById(R.id.ht_heart_toggle);
                    tv = (TextView)view.findViewById(R.id.ht_heart_num);
                    tv.setText(Integer.toString(fid.getHeart_num() + 1));
                    ib.setImageResource(R.mipmap.ic_like_y);
                    change_heartdata(fid, 0);
                    fid.setHeart_toggle(1);
                    fid.setHeart_num(fid.getHeart_num() + 1);
                    trva4.onChanged(position, fid);
                    mAdapter.notifyDataSetChanged();
                }
            }
            else if(view.getId() == R.id.ht_lay)
            {
                fid = it.get(position);
                Intent intent = new Intent(context,CommentActivity.class);
                List<String> l = new ArrayList<>();
                List<Integer> i = new ArrayList<>();
                l.add(fid.getId());
                l.add(fid.getDate());
                l.add(fid.getAge());
                l.add(fid.getUser_id());
                l.add(fid.getMsg());
                l.add(fid.getImg_url());
                l.add(fid.getLocation());
                l.add(fid.getNick());
                i.add(fid.getpost_num());
                i.add(fid.getHeart_num());
                i.add(fid.getHeart_toggle());
                i.add(fid.getComment_num());
                i.add(Integer.parseInt(fid.getEffect()));
                i.add(Integer.parseInt(fid.getFont()));
                i.add(Integer.parseInt(fid.getLux()));
                intent.putStringArrayListExtra("StringSet", (ArrayList<String>) l);
                intent.putIntegerArrayListExtra("IntSet", (ArrayList<Integer>) i);
                startActivity(intent);
            }
            else if(view.getId() == R.id.w4_share)
            {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String url = fid.getImg_url();
                        try {
                            URL url2 = new URL(url);
                            request = new HttpGet(url2.toURI());
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        HttpClient client = new DefaultHttpClient();
                        try {
                            reponse = (HttpResponse)client.execute(request);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        HttpEntity entity = reponse.getEntity();
                        try {
                            BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
                            InputStream instream = bufferedHttpEntity.getContent();
                            bitmap2 = BitmapFactory.decodeStream(instream);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                try{
                    thread.join();
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                fid = it.get(position);


                share_dialog(bitmap2);

            }
        }
    };
    public boolean islog()
    {

        return AccessToken.getCurrentAccessToken()!=null;
    }

    public void share_dialog(final Bitmap bitmap2)
    {
        final ListView listView = new ListView(context);

        String[] items = {"페이스북", "인스타그램", "트위터"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_dialog, R.id.listView2, items);
        listView.setAdapter(adapter);

        AlertDialog.Builder sharedialog = new AlertDialog.Builder(context).setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        sharedialog.setView(listView);
        sharedialog.setTitle("공유하기");
        sharedialog.setCancelable(true);

        sharedialog.show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {
                    case 0:        //페이스북
                        boolean log = islog();
                        if (log) {
                            final ShareDialog dialog = new ShareDialog((Activity) context);
                            SharePhoto photo = new SharePhoto.Builder()
                                    .setBitmap(bitmap2)
                                    .build();
                            final SharePhotoContent content = new SharePhotoContent.Builder()
                                    .addPhoto(photo)
                                    .build();
                            dialog.show((Activity) context, content);

                        } else {

                        }
                        break;
                    case 1:       //인스타그램
                        Intent insta = new Intent(view.getContext(), InstaActivity.class);
                        insta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        view.getContext().startActivity(insta);

                        break;
                    case 2:        //트위터


                        break;
                }

            }
        });
    }
    public void push_yes(View y_view,int y_position, TestRecyclerViewAdapter4 y_trva4, FeedItem y_fid, RecyclerView.Adapter y_mAdapter)
    {
      //  ib = (ImageView)y_view.findViewById(R.id.post_heart_btn);
        tv = (TextView)y_view.findViewById(R.id.post_heart_num);
        tv.setText(Integer.toString(y_fid.getHeart_num()-1)+"개");
        ib.setImageResource(R.drawable.heart_bin);
        change_heartdata(y_fid, 1);
        y_fid.setHeart_toggle(0);
        y_fid.setHeart_num(y_fid.getHeart_num() - 1);
        y_trva4.onChanged(y_position, y_fid);
        y_mAdapter.notifyDataSetChanged();
    }
    public RecyclerViewFragment_4()
    {
        this.line = "";
        this.user="";
    }
    public RecyclerViewFragment_4 newInstance() {
        return new RecyclerViewFragment_4();
        // return this;
    }
    public void change_heartdata(FeedItem fi,int toggle)
    {
        Log.e("change_heart",Integer.toString(toggle));
        chg_heart_id = fi.getUser_id();
        if(toggle == 1)
        {
            chg_heart_post = Integer.toString(fi.getpost_num());
            command = "delete";
        }
        else
        {
            chg_heart_post = Integer.toString(fi.getpost_num());
            command = "insert";
        }
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("this is", "UTF-8")+"="+URLEncoder.encode(command,"UTF-8");
                    data += "&" + URLEncoder.encode("post_num", "UTF-8")+"="+URLEncoder.encode(chg_heart_post,"UTF-8");
                    data += "&" + URLEncoder.encode("id","UTF-8")+"="+ URLEncoder.encode(chg_heart_id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/change_heart");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd4 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    line="";
                    while((line = rd4.readLine())!=null)
                    {
                        Log.e("change heart success",line);
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
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview_4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = this.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView4);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        //get_data();
        trva4 = new TestRecyclerViewAdapter4(context,onItemClickCallback);
        mAdapter = new RecyclerViewMaterialAdapter(trva4);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(context, Write_Activity1.class);
                startActivity(in);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        {
            mAdapter.notifyDataSetChanged();
        }
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
