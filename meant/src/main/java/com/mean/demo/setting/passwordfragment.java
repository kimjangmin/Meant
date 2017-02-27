package com.mean.demo.setting;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mean.demo.R;

/**
 * Created by CBR on 2016-01-21.
 */
public class passwordfragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.password_one, container, false);


        return v;
    }
}
