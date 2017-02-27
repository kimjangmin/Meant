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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.mean.demo.IP_ADDR;
import com.mean.demo.ex_main.Login_dir;
import com.mean.demo.MainActivity;
import com.mean.demo.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class S_Secession extends AppCompatActivity {
    CheckBox cb;
    Button bt;
    int agree=0;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] navItems = {"프로필설정", "알림설정", "폰트설정","페이스북계정", "암호설정","서비스이용동의","기록삭제","버전정보","도움말","문의하기","약관 및 정책",
            "로그아웃","서비스 탈퇴"};
    private ListView lvNavList;
    String id;
    Context context;
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    Intent intent = new Intent(context, S_profile.class);
                    startActivity(intent);

                    break;
                case 1:
                    Intent intent1 = new Intent(context, S_Alarm.class);
                    startActivity(intent1);

                    break;
                case 2:
                    Intent intent2 = new Intent(context, S_Font.class);
                    startActivity(intent2);


                    break;
                case 3:
                    Intent intent3 = new Intent(context, S_Facebookid.class);
                    startActivity(intent3);

                    break;
                case 4:
                    Intent intent4= new Intent(context, S_Password.class);
                    startActivity(intent4);

                    break;

                case 5:
                    Intent intent5 = new Intent(context,S_Service.class);
                    startActivity(intent5);

                    break;
                case 6:
                    AlertDialog.Builder alt2 = new AlertDialog.Builder(S_Secession.this);
                    alt2.setMessage("기록을 전부 삭제합니다.\n삭제된 내용은\n다시 확인 할 수 없습니다").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            Delete_post.delete();


                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alt2.setTitle("기록 삭제");
                    alt2.show();

                    break;
                case 7:
                    Intent intent7 = new Intent(context, S_Version.class);
                    startActivity(intent7);

                    break;
                case 8:
                    Intent intent8 = new Intent(context,S_Help.class);
                    startActivity(intent8);

                    break;
                case 9:
                    Intent intent9 = new Intent(context, S_Question.class);
                    startActivity(intent9);

                    break;
                case 10:
                    Intent intent10 = new Intent(context, S_Policy.class);
                    startActivity(intent10);

                    break;
                case 11:
                    AlertDialog.Builder alt = new AlertDialog.Builder(S_Secession.this);
                    alt.setMessage("정말로 로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoginManager.getInstance().logOut();
                            Intent intent11 = new Intent(context, Login_dir.class);
                            startActivity(intent11);
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alt.setTitle("로그아웃");
                    alt.show();
                    break;
                case 12:
                    Intent intent12 = new Intent(context, S_Secession.class);
                    startActivity(intent12);

                    break;

            }
            mDrawer.closeDrawer(lvNavList);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s__secession);
        context = this.getApplicationContext();
        cb = (CheckBox)findViewById(R.id.checkBox2);
        bt = (Button)findViewById(R.id.button6);
        id = MainActivity.id;
        lvNavList = (ListView)findViewById(R.id.lv_activity_main_nav_list);
        lvNavList.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navItems));
        lvNavList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout2);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0) //토글때 사용
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };

        mDrawer.setDrawerListener(mDrawerToggle);

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (cb.isChecked() == true) {
                    agree = 1;
                } else {
                    agree = 0;
                }
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agree == 1) {
                    AlertDialog.Builder alt2 = new AlertDialog.Builder(S_Secession.this);
                    alt2.setMessage("정말로 탈퇴하시겠습니까?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            bye_user();
                            Toast.makeText(getApplicationContext(), "탈퇴되었습니다", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(context,Login_dir.class);
                            startActivity(in);
                            finish();

                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alt2.setTitle("회원탈퇴");
                    alt2.show();
                } else {
                    AlertDialog.Builder alt2 = new AlertDialog.Builder(S_Secession.this);
                    alt2.setMessage("동의를 하셔야지 탈퇴가 완료 됩니다").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                        }
                    });
                    alt2.setTitle("동의필요");
                    alt2.show();
                }
            }
        });
    }

    public void bye_user()
    {
        Thread thread = new Thread(new Runnable()
        {
            public void run()
            {
                try{
                    String data = URLEncoder.encode("id", "UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                    URL url = new URL("http://"+ IP_ADDR.get_ip()+":8888/bye_user");
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader rd3 = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    String line="";
                    while((line = rd3.readLine())!=null)
                    {
                    }
                    wr.close();
                    rd3.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}