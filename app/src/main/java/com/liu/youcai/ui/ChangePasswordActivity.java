package com.liu.youcai.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.MyApplication;
import com.liu.youcai.R;
import com.liu.youcai.bean.User;
import com.liu.youcai.db.dao.UserDao;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private EditText mPassword;
    private EditText mNewPassword;
    private EditText mAgainPassword;
    private Button mCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ActivityCollector.addActivity(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        String username = ((MyApplication) getApplication()).getUser().getUsername();
        mToolBar.setTitle(username);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPassword = (EditText) findViewById(R.id.password);
        mNewPassword = (EditText) findViewById(R.id.new_password);
        mAgainPassword = (EditText) findViewById(R.id.again_new_password);
        mCommit = (Button) findViewById(R.id.commit);

        mCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString().trim();
                String newPassword = mNewPassword.getText().toString().trim();
                String againPassword = mAgainPassword.getText().toString().trim();

                User oldUser = ((MyApplication) getApplication()).getUser();

                if (!oldUser.getPassword().equals(password)) {
                    Toast.makeText(ChangePasswordActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                } else {

                    if (!newPassword.equals(againPassword)) {
                        Toast.makeText(ChangePasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    } else {

                        User newUser = new UserDao(ChangePasswordActivity.this).changePassword(oldUser, newPassword);

                        if (newUser == null) {
                            Toast.makeText(ChangePasswordActivity.this, "更改密码错误", Toast.LENGTH_SHORT).show();
                        } else {
                            ((MyApplication) getApplication()).setUser(newUser);
                            Toast.makeText(ChangePasswordActivity.this, "更改密码成功", Toast.LENGTH_SHORT).show();

                            //修改自动登录密码
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(ChangePasswordActivity.this).edit();
                            editor.putString("password", newUser.getPassword());
                            editor.commit();

                            startActivity(new Intent(ChangePasswordActivity.this, YouCaiActivity.class));
                            finish();

                        }
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
}
