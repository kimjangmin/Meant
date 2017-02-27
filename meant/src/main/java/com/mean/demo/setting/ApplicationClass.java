package com.mean.demo.setting;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;

/**
 * Created by CBR on 2016-01-26.
 */
public class ApplicationClass extends Application {


    static SharedPreferences setting;
    static SharedPreferences.Editor editor;
    int agreeTok =0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}