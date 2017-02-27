package com.mean.demo.ex_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.mean.demo.IP_ADDR;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Make_Face extends AppCompatActivity {

    EditText et;
    String id,face_id,res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__face);
        et = (EditText)findViewById(R.id.face_email);
        Intent inte = getIntent();
        face_id = inte.getStringExtra("face_id");
    }
    public void go_login(View v)
    {
        id = et.getText().toString();
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                    data += "&" + URLEncoder.encode("face_id","UTF-8")+"="+face_id;
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/apply_face");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    res = null;
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    while((res = rd.readLine())!=null)
                    {
                        if(res != null)
                        {
                            break;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent inten = new Intent(this, LoginActivity.class);
        inten.putExtra("face_flag","1");
        inten.putExtra("mail_id",id);
        startActivity(inten);
        finish();
    }
}
