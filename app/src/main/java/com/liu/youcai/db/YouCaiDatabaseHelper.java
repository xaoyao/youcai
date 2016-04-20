package com.liu.youcai.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liu on 2016/4/19 0019.
 */
public class YouCaiDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER = "create table user(" +
            "user_id integer primary key autoincrement," +
            "username text," +
            "password text)";

    public YouCaiDatabaseHelper(Context context) {
        super(context, "YouCai.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
