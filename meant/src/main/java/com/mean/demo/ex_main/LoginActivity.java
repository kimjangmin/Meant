package com.mean.demo.ex_main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.mean.demo.IP_ADDR;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import butterknife.ButterKnife;
import butterknife.Bind;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    String line;
    public static String id;
    static String pwd;
    String flag,mail_id;
    Context con;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    @Bind(R.id.link_signup)
    TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        con = this.getApplicationContext();
        Intent in = getIntent();
        flag = in.getStringExtra("face_flag");
        id = in.getStringExtra("mail_id");
        String temp="";
        pwd = "";
        if(flag.equals("1"))
        {
        for(int i=0;i<id.length();i++)
        {
            if(id.charAt(i) == '%')
            {
                temp += "@";
                i += 2;
            }
            else
            {
                temp += id.charAt(i);
            }
        }
            id = temp;
            Log.e("in here","in here");
            Intent ie = new Intent(con,MainActivity.class);
            ie.putExtra("user_id",id);
            ie.putExtra("flag",0);
            ie.putExtra("pwd",pwd);
            startActivity(ie);
            finish();
        }
        ButterKnife.bind(this);
        line = "";

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        // TODO: Implement your own authentication logic here.

        id = _emailText.getText().toString();
        pwd = _passwordText.getText().toString();
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                    data += "&" + URLEncoder.encode("pwd","UTF-8")+"="+ URLEncoder.encode(pwd, "UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();

                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    while((line = rd.readLine())!=null)
                    {
                        System.out.println(line);
                        Log.e("read", line);
                        break;
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
            if(line.equals("1"))
            {
                Toast.makeText(this, "login ", Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(con,MainActivity.class);
                inte.putExtra("user_id",id);
                inte.putExtra("flag",0);
                inte.putExtra("pwd",pwd);
                startActivity(inte);
                finish();
            }
            else
            {
                Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }



    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() > 17 || password.length() < 7) {
            _passwordText.setError("enter a valid password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }


        return valid;
    }
    public void go_sign(View v) {
        Intent i = new Intent(this, SignupActivity.class);
        startActivity(i);
    }

    public void go_back(View v) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        LoginManager.getInstance().logOut();
        Intent j = new Intent(this,Login_dir.class);
        startActivity(j);
        finish();
    }

}