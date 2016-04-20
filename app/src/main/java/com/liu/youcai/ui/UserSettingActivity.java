package com.liu.youcai.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.R;

public class UserSettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private RelativeLayout mChangePassword;
    private Button mLoginOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ActivityCollector.addActivity(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitle("账号管理");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mChangePassword= (RelativeLayout) findViewById(R.id.changePassword);
        mLoginOut= (Button) findViewById(R.id.login_out);
        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserSettingActivity.this,ChangePasswordActivity.class));
            }
        });

        mLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(UserSettingActivity.this).edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(UserSettingActivity.this,LoginActivity.class));
                ActivityCollector.finishAll();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
