package com.mean.demo.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mean.demo.GMailSender;
import com.mean.demo.R;

public class S_Question extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView btn;
    Spinner spinner;
    String mail2;
    EditText t1,t2;
    String text, sub;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__question);
        context = this.getApplicationContext();
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adp1 = ArrayAdapter.createFromResource(this,R.array.ques,android.R.layout.simple_spinner_item);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        spinner.setOnItemSelectedListener(this);

        t1 = (EditText)findViewById(R.id.editText2);
        t2 = (EditText)findViewById(R.id.editText);
        t2.setHint(" 아래 내용을 함꼐 보내주시면 더욱 빠른 처리가 가능합니다.\n"+
                " -문제가 발생한 시간대\n"+
                " -문의내용");

        btn=(TextView)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mail2= String.valueOf(t1.getText());
                text= String.valueOf(t2.getText());
                Thread thread = new Thread(new Runnable()
                {
                    public void run()
                    {
                        GMailSender sender = new GMailSender("js204703","ptxehvuubbedzzje");
                        try{
                            sender.sendMail("[" + sub + "]" + "관련문의 도착 " + "[" + mail2 + "]", text, "js204703@gmail.com", mail2);


                        }catch(Exception e)
                        {
                            Log.e("SendMail", e.getMessage(), e);
                        }
                    }
                });
                thread.start();
                try
                {
                    thread.join();
                    AlertDialog.Builder alt = new AlertDialog.Builder(S_Question.this);
                    alt.setMessage("문의사항이 등록되었습니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    alt.show();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }


            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner sp1 = (Spinner)findViewById(R.id.spinner);
        sub = (String)sp1.getAdapter().getItem(sp1.getSelectedItemPosition());
        ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void push_back(View v)
    {
        finish();
    }
}