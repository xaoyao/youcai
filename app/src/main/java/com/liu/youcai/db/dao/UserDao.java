package com.liu.youcai.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.renderscript.Sampler;

import com.liu.youcai.bean.User;
import com.liu.youcai.db.YouCaiDatabaseHelper;

/**
 * Created by liu on 2016/4/19 0019.
 */
public class UserDao {
    private Context context;
    private YouCaiDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserDao(Context context) {
        this.context = context;
        dbHelper=new YouCaiDatabaseHelper(context);
    }


    /**
     * 通过id查询用户
     * @param userId
     * @return
     */
    public User findUserById(int userId){
        db = dbHelper.getWritableDatabase();
        User user=null;

        Cursor cursor= db.rawQuery("select * from user where user_id=?",new String[]{userId+""});
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("user_id"));
                String username=cursor.getString(cursor.getColumnIndex("username"));
                String password=cursor.getString(cursor.getColumnIndex("password"));

                user=new User(id,username,password);

            }while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return user;
    }

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username){
        db = dbHelper.getWritableDatabase();
        User user=null;
        Cursor cursor=db.rawQuery("select * from user where username=?",new String[]{username});
        if(cursor.moveToFirst()){
            do {
                int id=cursor.getInt(cursor.getColumnIndex("user_id"));
                String name=cursor.getString(cursor.getColumnIndex("username"));
                String password=cursor.getString(cursor.getColumnIndex("password"));

                user=new User(id,name,password);

            }while (cursor.moveToNext());

            cursor.close();
            db.close();
        }
        return user;
    }

    public User addUser(String username,String password){
        db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("username",username);
        values.put("password",password);
        long id=db.insert("user",null,values);
        if(id==-1){
            return null;
        }
        User user=findUserById((int) id);
        db.close();
        return user;

    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public User login(String username,String password){
        User user=findUserByUsername(username);
        if(user==null){
            return null;
        }
        if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
            return user;
        }

        return null;
    }

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    public User register(String username,String password){
        if(findUserByUsername(username)!=null){
            return null;
        }

        User user=addUser(username,password);
        return user;
    }

    /**
     * 更改密码
     * @param oldUser
     * @return
     */
    public User changePassword(User oldUser,String newPassword){
        db = dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",newPassword);
        int change=db.update("user",values,"user_id=?",new String[]{oldUser.getId()+""});
        if(change<1){
            return null;
        }
        db.close();
        return findUserById(oldUser.getId());
    }
}
