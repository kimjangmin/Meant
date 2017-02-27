package com.mean.demo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.mean.demo.ex_main.LoginActivity;
import com.mean.demo.R;

/**
 * Created by CBR on 2016-01-10.
 */
public class Dialog_Cong extends Dialog implements View.OnClickListener {

    TextView show;
    TextView bt1;
    Context act;
    public Dialog_Cong(Context context,String mail)
    {
        super(context);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_cong);
        show = (TextView)findViewById(R.id.cong_mail);
        show.setText(mail);
        bt1 = (TextView)findViewById(R.id.cong_cong);
        bt1.setOnClickListener(this);
        act = context;
    }

    @Override
    public void onClick(View v) {
        if(v == bt1)
        {
            dismiss();
            Intent i = new Intent(act,LoginActivity.class);
            i.putExtra("face_flag","0");
            act.startActivity(i);
        }
    }
}
