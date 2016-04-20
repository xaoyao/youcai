package com.liu.youcai.ui;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.MyApplication;
import com.liu.youcai.R;
import com.liu.youcai.bean.User;
import com.liu.youcai.db.dao.UserDao;

public class LoginActivity extends AppCompatActivity {

    private EditText mETPassword;
    private EditText mETUserName;
    private Button mLogin;
    private Button mRegister;
    private CheckBox mAutoLogin;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActivityCollector.addActivity(this);
        setTranslucent();

        initView();

        //自动登录
        pref=PreferenceManager.getDefaultSharedPreferences(this);
        boolean autoLogin=pref.getBoolean("autoLogin",false);
        if(autoLogin){
            String username=pref.getString("username","");
            String password=pref.getString("password","");
            login(username,password);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 初始化控件
     */
    private void initView(){

        mAutoLogin = (CheckBox) findViewById(R.id.autoLogin);
        mETUserName = (EditText) findViewById(R.id.username);
        mETPassword = (EditText) findViewById(R.id.password);

        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mETUserName.getText().toString().trim();
                String password = mETPassword.getText().toString().trim();
                login(username,password);

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    private void login(String username,String password){
        User user = new UserDao(LoginActivity.this).login(username, password);
        if (user == null) {
            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        } else {

            //自动登录,保存用户名和密码
            if(mAutoLogin.isChecked()){
                editor=pref.edit();
                editor.putBoolean("autoLogin",true);
                editor.putString("username",username);
                editor.putString("password",password);
                editor.commit();
            }

            ((MyApplication)getApplication()).setUser(user);

            startActivity(new Intent(LoginActivity.this, YouCaiActivity.class));
            finish();
        }
    }

    /**
     * 使状态栏透明
     */
    private void setTranslucent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
