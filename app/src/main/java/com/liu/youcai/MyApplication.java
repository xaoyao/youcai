package com.liu.youcai;

import android.app.Application;

import com.liu.youcai.bean.User;

/**
 * Created by liu on 2016/4/19 0019.
 */
public class MyApplication extends Application {
    private static User user=null;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        MyApplication.user = user;
    }
}
