package com.mean.demo.setting;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mean.demo.IP_ADDR;
import com.mean.demo.ex_main.LoginActivity;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016-01-11.
 */
public class S_profile extends AppCompatActivity {

    private ApplicationClass applicationClass;
    private ImageView tviv;
    ProgressDialog dialog = null;
    TextView show,bt1,nick_check,nick_valid;
    RadioGroup rg1;
    RadioButton rb1_1,rb1_2;
    Spinner spin,spin_lang,spin_coun;
    String mail,gender,nick_value,check,file_name,sourceFileUri, mapping,req_id;
    EditText nick;
    int age,lang,coun,nick_yn;
    private String[] age_list= new String[101];
    private String[] lang_list= {"한국어","English","日本語"};
    private String[] coun_list= {"가나","동티모르","대한민국","미국","우즈베키스탄"};
    Context act;
    int request =100;
    int serverResponseCode =0;
    Uri img;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_profile);
        act = this.getApplicationContext();
        tviv = (ImageView)this.findViewById(R.id.imageView);
        bt1 = (TextView)findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((rb1_1.isChecked() || rb1_2.isChecked()) && age >= 0 && lang >= 0 && coun >= 0 && nick_yn == 1) {
                    push_change();
                    Intent j = new Intent(act,MainActivity.class);
                    j.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    j.putExtra("flag", 0);
                    j.putExtra("user_id", MainActivity.id);
                    j.putExtra("pwd", MainActivity.pwd);
                    startActivity(j);
                    finish();
                } else {
                    Toast.makeText(act, "항목을 체크하여 주십시오.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nick = (EditText)findViewById(R.id.nick_input);
        nick_check = (TextView)findViewById(R.id.nick_check);
        nick_valid = (TextView)findViewById(R.id.nick_valid);


        rg1 = (RadioGroup)findViewById(R.id.more_gender_rg);
        rb1_1 = (RadioButton)findViewById(R.id.rb1_1);
        rb1_2 = (RadioButton)findViewById(R.id.rb1_2);
        RadioButton.OnClickListener optionOnClickListener = new RadioButton.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.rb1_1)
                {
                    gender = "1";
                }else if(v.getId() == R.id.rb1_2)
                {
                    gender = "0";
                }
            }
        };
        rb1_1.setOnClickListener(optionOnClickListener);
        rb1_2.setOnClickListener(optionOnClickListener);


        for(int i=0;i<101;i++)
        {
            age_list[i] = Integer.toString(1916+i);
        }

        spin = (Spinner)findViewById(R.id.more_spin);
        spin_lang = (Spinner)findViewById(R.id.lang_spin);
        spin_coun = (Spinner)findViewById(R.id.coun_spin);
        spin.setPrompt("Select Age");
        spin_lang.setPrompt("Select Lang");
        spin_coun.setPrompt("Select Coun");
        ArrayAdapter<String> list,list_lang,list_coun;
        list = new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,age_list);
        list_lang = new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,lang_list);
        list_coun = new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,coun_list);
        spin.setAdapter(list);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.monsoon));
                age = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_lang.setAdapter(list_lang);
        spin_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.monsoon));
                lang = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spin_coun.setAdapter(list_coun);
        spin_coun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.monsoon));
                coun = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nick_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nick_value = nick.getText().toString();
                Thread thread = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try{
                            String data = URLEncoder.encode("nick", "UTF-8")+"="+URLEncoder.encode(nick_value,"UTF-8");
                            URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/unique_nick");
                            URLConnection conn = url.openConnection();
                            conn.setDoOutput(true);
                            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                            wr.write(data);
                            wr.flush();
                            String line = null;
                            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                            while((line = rd.readLine())!=null)
                            {
                                if(line != null)
                                {
                                    check = line;
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
                    if(check.equals("1"))
                    {
                        nick_yn = 1;
                        nick_valid.setText("사용가능");
                        nick_valid.setTextColor(act.getResources().getColor(R.color.book_loading_book));
                    }
                    else
                    {
                        nick_yn = 0;
                        nick_valid.setText("사용불가");
                        nick_valid.setTextColor(act.getResources().getColor(R.color.error_color));

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

//////////////프로필 사진부분
        applicationClass = (ApplicationClass)getApplicationContext();
        applicationClass.setting = getSharedPreferences("setting", 0);
        String bitmap2 = applicationClass.setting.getString("profile", String.valueOf(0));
        img = Uri.parse(bitmap2);

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(bitmap2!=null)
        {

            tviv.setImageBitmap(bitmap);
        }
        else {
            tviv.setImageResource(R.drawable.dal);
        }

//////view 클릭리스너
        tviv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI;
                Intent i = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(i, request);
            }
        });

        tviv.post(new Runnable() {
            public void run() {
                int width_container = tviv.getWidth();
                int height_container = tviv.getHeight();

                tviv.setDrawingCacheEnabled(true);
                tviv.buildDrawingCache(true);

                Bitmap capture = Bitmap.createBitmap(tviv.getMeasuredWidth(), tviv.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                Log.e("size : ", Integer.toString(tviv.getMeasuredWidth()) + " , " + Integer.toString(tviv.getMeasuredHeight()));
                Canvas can = new Canvas(capture);
                tviv.draw(can);
                FileOutputStream fos;
                Intent it3 = getIntent();
                String str_name = it3.getStringExtra("it3_name");
                File fileRoute = null;
                fileRoute = Environment.getExternalStorageDirectory();

                try {
                    File path = new File(fileRoute, "Meant");
                    if (!path.exists()) {
                        path.mkdirs();
                    }

                    file_name = "imsi.png";
                    fos = new FileOutputStream(fileRoute + "/Meant/" + file_name);
                    Log.e("Screen Shot", " : " + tviv.getDrawingCache());
                    capture.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    tviv.setDrawingCacheEnabled(false);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });


    }
    /////////////////s_profile 끝
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==request) {

            img = data.getData();

            String bitmap2 = String.valueOf(img);
            applicationClass.setting = getSharedPreferences("setting", 0);
            applicationClass.editor = applicationClass.setting.edit();
            applicationClass.editor.putString("profile", bitmap2);
            applicationClass.editor.apply();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), img);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent i = new Intent(this,S_profile.class);
        startActivity(i);
        finish();
    }

    public void push_change()
    {
        try {
            Thread.sleep(500);
            save_img();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void save_img()
    {
        dialog = ProgressDialog.show(S_profile.this, "", "Uploading file...", true);

        Thread thr = new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.e("uploading started..", "uploading started..");
                    }
                });
                sourceFileUri = Environment.getExternalStorageDirectory()+"/Meant/"+file_name;
                uploadFile(sourceFileUri);
            }
        });
        thr.start();
        try{
            thr.join();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public int uploadFile(String str) {
        String fileName = str;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(str);

        if (!sourceFile.isFile()) {
            dialog.dismiss();
            Log.e("uploadFile", "Source File not exist :" + fileName);
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(str);
                URL url = new URL("http://" + IP_ADDR.get_ip() + ":8181/api/photo");

                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e("byteRead - ", Integer.toString(bytesRead));
                }

                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.e("uploadFile", "Http Response is :" + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    Log.e("200", "server res : 200 OK");
                    BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()),8192);
                    final StringBuilder response = new StringBuilder();
                    String strLine = null;
                    while((strLine = input.readLine()) != null)
                    {
                        Log.e("get msg",strLine);
                        response.append(strLine);
                        mapping = response.toString();
                    }
                    input.close();

                    runOnUiThread(new Runnable(){
                        public void run(){
                            String msg = "File upload Completed";
                            Log.e("msg",msg);
                        }
                    });
                }
                fileInputStream.close();
                dos.flush();
                dos.close();
                set_prof();
            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.e("MalformedURLException", "exception");
                    }
                });
            } catch (Exception e) {
                dialog.dismiss();
                e.printStackTrace();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

    }

    public void set_prof()
    {
        req_id = LoginActivity.id;
        for(int i=0;i<mapping.length();i++)
        {
            if(mapping.charAt(i) == '\\')
            {
                mapping = mapping.substring(i+1);
            }
        }
        mapping = "http://"+ IP_ADDR.get_ip()+":8181/uploads/"+ mapping;
        Log.e("mapping : ",mapping);
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(req_id,"UTF-8");
                    data += "&" + URLEncoder.encode("gender","UTF-8")+"="+ URLEncoder.encode(gender,"UTF-8");
                    data += "&" + URLEncoder.encode("age","UTF-8")+"="+ URLEncoder.encode(age_list[age],"UTF-8");
                    data += "&" + URLEncoder.encode("lang","UTF-8")+"="+ URLEncoder.encode(lang_list[lang],"UTF-8");
                    data += "&" + URLEncoder.encode("coun","UTF-8")+"="+ URLEncoder.encode(coun_list[coun],"UTF-8");
                    data += "&" + URLEncoder.encode("nick","UTF-8")+"="+ URLEncoder.encode(nick_value,"UTF-8");
                    data += "&" + URLEncoder.encode("url","UTF-8")+"="+ URLEncoder.encode(mapping,"UTF-8");

                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/chg_prof");
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
    public void push_back(View v)
    {
        finish();
    }
}