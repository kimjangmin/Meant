package com.mean.demo.Write;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.nomad.instagramlogin.InstaLogin;
import com.nomad.instagramlogin.Keys;

import java.io.File;

/**
 * Created by sang on 2016-02-05.
 */
public class InstaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {


            super.onCreate(savedInstanceState);
            //  setContentView(R.layout.activity_real);
            InstaLogin instaLogin = new InstaLogin(InstaActivity.this,
                    "0f574d410bd64da2b87cc08878d3abdd",
                    "6ce98bbc38ea4b4997ffbef6ba87f1dc",
                    "https://123.com");
            instaLogin.login();

        }

    }

    protected  void onActivityResult(final int requestCode, final int resultCode,final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        /////////////////////////////////////////
        if (requestCode == Keys.LOGIN_REQ) {
            if (resultCode == RESULT_OK) {

                PackageManager manager = getBaseContext().getPackageManager();
                Intent i= manager.getLaunchIntentForPackage("com.instagram.android");

                if(i==null){
                    i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.instagram.android"));
                    startActivity(i);
                    return;
                }

                String type = "image/*";
                String mediaPath= Environment.getExternalStorageDirectory()+ "/meant/imsi.png";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType(type);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                File media = new File(mediaPath);
                if (media.isDirectory())
                {
                    Log.i("TEST", media.getAbsolutePath() + " dir exist.");
                }
                else
                {
                    Log.i("TEST", media.getAbsolutePath() + " dir not exist.");
                }
                Uri uri=Uri.fromFile(media);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setPackage("com.instagram.android");
                startActivity(shareIntent);
                finish();
            }
        }

        finish();
    }

}







