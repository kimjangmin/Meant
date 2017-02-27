package com.mean.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mean.demo.IP_ADDR;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by CBR on 2016-01-25.
 */
public class Dialog_more extends Dialog implements AdapterView.OnClickListener {
    TextView show,bt1,nick_check,nick_valid;
    RadioGroup rg1;
    RadioButton rb1_1,rb1_2;
    Spinner spin,spin_lang,spin_coun;
    Context act;
    String mail,gender,nick_value,check;
    EditText nick;
    int age,lang,coun,nick_yn;
    private String[] age_list= new String[101];
    private String[] lang_list= {"한국어","English","日本語"};
    private String[] coun_list= {"가나","동티모르","대한민국","미국","우즈베키스탄"};
    int selected_che;

    public Dialog_more(Context context,String mail)
    {
        super(context);
        for(int i=0;i<101;i++)
        {
            age_list[i] = Integer.toString(1916+i);
        }
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_more);
        this.mail = mail;
        nick_yn = 0;
        gender = "";
        check="";
        nick = (EditText)findViewById(R.id.nick_input);
        nick_check = (TextView)findViewById(R.id.nick_check);
        nick_valid = (TextView)findViewById(R.id.nick_valid);

        bt1 = (TextView)findViewById(R.id.more_more);
        bt1.setOnClickListener(this);
        nick_check.setOnClickListener(this);

        act = context;
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

        spin = (Spinner)findViewById(R.id.more_spin);
        spin_lang = (Spinner)findViewById(R.id.lang_spin);
        spin_coun = (Spinner)findViewById(R.id.coun_spin);
        spin.setPrompt("Select Age");
        spin_lang.setPrompt("Select Lang");
        spin_coun.setPrompt("Select Coun");
        ArrayAdapter<String> list,list_lang,list_coun;
        list = new ArrayAdapter<String>(act,R.layout.spin,age_list);
        list_lang = new ArrayAdapter<String>(act,R.layout.spin,lang_list);
        list_coun = new ArrayAdapter<String>(act,R.layout.spin,coun_list);
        spin.setAdapter(list);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(act.getResources().getColor(R.color.monsoon));
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
                ((TextView)parent.getChildAt(0)).setTextColor(act.getResources().getColor(R.color.monsoon));
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
                ((TextView)parent.getChildAt(0)).setTextColor(act.getResources().getColor(R.color.monsoon));
                coun = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == bt1)
        {
            if((rb1_1.isChecked()||rb1_2.isChecked()) && age >= 0 && lang >= 0 && coun >= 0 && nick_yn == 1) {
                apply_more();
                dismiss();
            }
            else
            {
                Toast.makeText(act,"항목을 체크하여 주십시오.",Toast.LENGTH_SHORT).show();
            }
        }
        else if(v == nick_check)
        {
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

    }
    public void apply_more()
    {
        Thread thread = new Thread(new Runnable()
    {
        public void run()
        {
            try{
                String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(mail,"UTF-8");
                data += "&" + URLEncoder.encode("gender","UTF-8")+"="+ URLEncoder.encode(gender,"UTF-8");
                data += "&" + URLEncoder.encode("age","UTF-8")+"="+ URLEncoder.encode(age_list[age],"UTF-8");
                data += "&" + URLEncoder.encode("lang","UTF-8")+"="+ URLEncoder.encode(lang_list[lang],"UTF-8");
                data += "&" + URLEncoder.encode("coun","UTF-8")+"="+ URLEncoder.encode(coun_list[coun],"UTF-8");
                data += "&" + URLEncoder.encode("nick","UTF-8")+"="+ URLEncoder.encode(nick_value,"UTF-8");

                URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/apply_more");
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                String line = "";
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                while((line = rd.readLine())!=null)
                {
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



}

