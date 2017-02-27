
package com.mean.demo.ex_main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.mean.demo.R;
import com.mean.demo.dialog.Dialog_checkmail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText _emailText, _passwordText, _passwordText2;
 //   @Bind(R.id.input_email) EditText _emailText;
 //   @Bind(R.id.input_password) EditText _passwordText;
 //   @Bind(R.id.check_password) EditText _passwordText2;
 //   @Bind(R.id.btn_signup) Button _signupButton;
 //   @Bind(R.id.link_login) TextView _loginLink;
    private static final String TAG = "SignupActivity";
    Pattern pattern;
    Matcher matcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _passwordText2 = (EditText)findViewById(R.id.check_password);
        //ButterKnife.bind(this);
    }
    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String password2 = _passwordText2.getText().toString();
/*

        if ( ( password2.isEmpty() || password2.length() < 8 || password2.length() > 16 )  )
        {
            _passwordText2.setError("enter a valid password");
            valid = false;
        } else
        {
            _passwordText2.setError(null);
        }

        if ( ( password.isEmpty() || password.length() < 8 || password.length() > 16 )  )
        {
            _passwordText.setError("enter a valid password");
            valid = false;
        } else
        {
            _passwordText.setError(null);
        }
        */
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if(! Passwrodvalidate(password))
        {
            _passwordText.setError("8~16자리 영문,숫자,특수문자의 조합으로 입력해주세요.");
            valid = false;
        }
        else
        {
            _passwordText.setError(null);
        }

        if(! Passwrodvalidate(password2) )
        {
            _passwordText2.setError("8~16자리 영문,숫자,특수문자의 조합으로 입력해주세요.");
        }
        else
        {
            _passwordText.setError(null);
        }

        if ( ! password.equals(password2)  )
        {
            _passwordText.setError("not the same password");
            _passwordText2.setError("not the same password");
            valid = false;
        } else
        {
            _passwordText.setError(null);
        }
        return valid;
    }

    private static final String Passwrod_PATTERN ="(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$";// "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{8,16}$";
    public boolean Passwrodvalidate(final String hex) {
        pattern = Pattern.compile(Passwrod_PATTERN);
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    public void go_next(View v) {

        if(validate()) {
            Dialog_checkmail dialog = new Dialog_checkmail(this, _emailText.getText().toString(), _passwordText.getText().toString());
            dialog.show();
        }

    }

    public void go_back(View v)
    {
        finish();
    }

}