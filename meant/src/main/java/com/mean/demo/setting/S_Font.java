package com.mean.demo.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mean.demo.R;

public class S_Font extends AppCompatActivity {

    RadioGroup grp;
    TextView textView3;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__font);

        final SpannableStringBuilder sps = new SpannableStringBuilder();
        grp = (RadioGroup)findViewById(R.id.radioGroup);
        grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:

                        break;
                    case R.id.radioButton2:
                        Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_LONG);
                        break;
                    case R.id.radioButton3:
                        Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG);
                        break;
                }

            }
        });

        context = this.getApplicationContext();

    }

    public void push_back(View v)
    {
        finish();
    }
}
