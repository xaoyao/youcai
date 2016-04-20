package com.liu.youcai.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.MyApplication;
import com.liu.youcai.R;
import com.liu.youcai.bean.User;
import com.liu.youcai.db.dao.UserDao;

public class RegisterActivity extends AppCompatActivity {

    private Button mRegister;
    private EditText mETUsername;
    private EditText mETPassword;
    private EditText mETAgainPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollector.addActivity(this);

        setTranslucent();
        mRegister = (Button) findViewById(R.id.register);

        mETUsername = (EditText) findViewById(R.id.username);
        mETPassword = (EditText) findViewById(R.id.password);
        mETAgainPassword = (EditText) findViewById(R.id.again_password);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mETUsername.getText().toString().trim();
                String password = mETPassword.getText().toString().trim();
                String againPassword = mETAgainPassword.getText().toString().trim();

                if (username == null || "".equals(username) || password == null || "".equals(password)) {

                    Toast.makeText(RegisterActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();

                } else if (!(password.equals(againPassword))) {

                    Toast.makeText(RegisterActivity.this, "密码不一致！", Toast.LENGTH_SHORT).show();

                } else {
                    //注册
                    User user = new UserDao(RegisterActivity.this).register(username, password);
                    if (user == null) {
                        //注册失败
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    } else {

                        ((MyApplication) getApplication()).setUser(user);
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this, YouCaiActivity.class));
                        finish();
                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 使状态栏透明
     */
    public void setTranslucent() {

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
