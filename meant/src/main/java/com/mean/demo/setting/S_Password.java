package com.mean.demo.setting;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.mean.demo.R;

public class S_Password extends FragmentActivity {
    Switch aSwitch ;
    int fr1 = 0;
    int fr2 = 1;
    int index;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__password);
        context = this.getApplicationContext();
        index =fr1;
        aSwitch = (Switch)findViewById(R.id.switch2);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Fragment newFragment = new passwordfragment2(context);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id._fragment, newFragment);
                    transaction.commit();

                } else {
                    Fragment newFragment = new passwordfragment();
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    transaction.replace(R.id._fragment, newFragment);
                    transaction.commit();
                }
            }
        });

    }

    public void push_back(View v)
    {
        finish();
    }

}
