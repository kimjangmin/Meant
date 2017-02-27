package com.mean.demo.all_listener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
 * Created by CBR on 2016-02-05.
 */
public class FireClickListener implements View.OnClickListener {
    Context context;
    String post_num,fire_num,my_id,bunryu,cate,fire;
    EditText et;
    public FireClickListener(Context con,int in)
    {
        this.context = con;
        this.post_num = Integer.toString(in);
    }

    @Override
    public void onClick(View vi) {
        fire_num = post_num;
        my_id = MainActivity.id;
        bunryu = "1";
        AlertDialog.Builder firedialog = new AlertDialog.Builder(context);
        firedialog.setTitle("신고하기");
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dialog_spinner,null);

        et= (EditText)v.findViewById(R.id.editText3);
        et.setHint("신고 내용");
        et.setHintTextColor(Color.DKGRAY);


        final Spinner spinner = (Spinner)v.findViewById(R.id.viewSpin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.option, android.R.layout.simple_spinner_item);
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
                            Log.e("post_finish", "post_finish");
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
                Toast.makeText(context.getApplicationContext(), "신고사유 :" + cate + "\n신고내용:" + fire + "\n신고가 접수되었습니다.", Toast.LENGTH_LONG).show();

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