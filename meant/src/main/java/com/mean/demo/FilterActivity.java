package com.mean.demo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    RadioGroup rg1;
    RadioButton rb1_1,rb1_2;
    String gender;
    Spinner spin_lang,spin_coun;
    private String[] lang_list= {"한국어","English","日本語"};
    private String[] coun_list= {"가나","동티모르","대한민국","미국","우즈베키스탄"};
    Context act;
    int coun,lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        act = getApplicationContext();
        rg1 = (RadioGroup)findViewById(R.id.filter_rg);
        rb1_1 = (RadioButton)findViewById(R.id.filter_rb1);
        rb1_2 = (RadioButton)findViewById(R.id.filter_rb2);
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

        spin_lang = (Spinner)findViewById(R.id.filter_lang);
        spin_coun = (Spinner)findViewById(R.id.filter_coun);

        spin_lang.setPrompt("Select Language");
        spin_coun.setPrompt("Select Country");
        ArrayAdapter<String> list_lang,list_coun;
        list_lang = new ArrayAdapter<String>(act,R.layout.spin,lang_list);
       // list_coun = new ArrayAdapter<String>(act,android.R.layout.simple_spinner_item,coun_list);
        list_coun = new ArrayAdapter<String>(act,R.layout.spin,coun_list);
        spin_lang.setAdapter(list_lang);
        spin_lang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.monsoon));
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
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.monsoon));
                coun = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void push_back(View v)
    {
        finish();
    }

}
