package com.mean.demo.Write;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mean.demo.R;

public class Write_Activity1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] che = {"오후2시체","내가쓴글씨체","딸기마카롱체","흘림체","홍차체"};
    String input_txt;
    int selected_che;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_1);
        Spinner sp1 = (Spinner)findViewById(R.id.wr1_spin);
        sp1.setPrompt("Select Font");
        ArrayAdapter<String> list;
        list = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,che);
        sp1.setAdapter(list);
        sp1.setOnItemSelectedListener(this);
    }

    public void push_back(View v)
    {
        finish();
    }
    public void push_next(View v)
    {
        Intent in = new Intent(this,Write_Activity2.class);
        int take = selected_che;
        EditText et = (EditText)findViewById(R.id.wr1_edit);
        String s = et.getText().toString();
        in.putExtra("input_txt",s);
        in.putExtra("selected_che",take);
        startActivity(in);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        this.selected_che = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
