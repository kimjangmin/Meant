package com.mean.demo.setting;

import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by CBR on 2016-01-21.
 */
public class Delete_post {
    static String req_id = "";
    public static void delete()
    {
        req_id = MainActivity.id;
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(req_id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/delete_post");
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

    }
}
