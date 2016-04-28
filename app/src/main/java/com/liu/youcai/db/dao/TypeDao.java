package com.liu.youcai.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liu.youcai.bean.Money;
import com.liu.youcai.bean.Type;
import com.liu.youcai.db.YouCaiDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2016/4/25 0025.
 */
public class TypeDao {
    private Context context;
    private YouCaiDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public TypeDao(Context context){
        this.context=context;
        dbHelper=new YouCaiDatabaseHelper(context);
    }

    /**
     * 查询所有的收入类型
     * @return
     */
    public List<Type> findAllEarningType(){
        List<Type> types=new ArrayList<>();
        Type type=null;
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from earning_type",new String[]{});

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("type_id"));
                String name=cursor.getString(cursor.getColumnIndex("type_name"));
                int icon=cursor.getInt(cursor.getColumnIndex("type_icon"));
                type=new Type(id,name,icon);
                types.add(type);
            }while (cursor.moveToNext());
        }
        db.close();
        return types;
    }

    /**
     * 查询所有的支出类型
     * @return
     */
    public List<Type> findAllExpenseType(){
        List<Type> types=new ArrayList<>();
        Type type=null;
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from expense_type",new String[]{});

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("type_id"));
                String name=cursor.getString(cursor.getColumnIndex("type_name"));
                int icon=cursor.getInt(cursor.getColumnIndex("type_icon"));
                type=new Type(id,name,icon);
                types.add(type);
            }while (cursor.moveToNext());
        }
        db.close();
        return types;
    }

    /**
     * 通过name查询Type
     * @param name
     * @return
     */
    public Type findEarningTypeByName(String name){
        Type type=null;
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from earning_type where type_name=?",new String[]{name});
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("type_id"));
                String type_name=cursor.getString(cursor.getColumnIndex("type_name"));
                int icon=cursor.getInt(cursor.getColumnIndex("type_icon"));
                type=new Type(id,type_name,icon);
            }while (cursor.moveToNext());
        }
        db.close();
        return type;
    }

    /**
     * 通过name查询Type
     * @param name
     * @return
     */
    public Type findExpenseTypeByName(String name){
        Type type=null;
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from expense_type where type_name=?",new String[]{name});
        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("type_id"));
                String type_name=cursor.getString(cursor.getColumnIndex("type_name"));
                int icon=cursor.getInt(cursor.getColumnIndex("type_icon"));
                type=new Type(id,type_name,icon);
            }while (cursor.moveToNext());
        }
        db.close();
        return type;
    }

    /**
     * 通过Id查询类型
     * @return
     */
    public Type findTypeById(int moneyType,int typeId){

        db=dbHelper.getWritableDatabase();
        Cursor cursor;
        Type type=null;
        switch (moneyType){
            case Money.EARNING:
                cursor=db.rawQuery("select * from earning_type where type_id=?",new String[]{typeId+""});
                if(cursor.moveToFirst()){
                    int id=cursor.getInt(cursor.getColumnIndex("type_id"));
                    String type_name=cursor.getString(cursor.getColumnIndex("type_name"));
                    int icon=cursor.getInt(cursor.getColumnIndex("type_icon"));
                    type=new Type(id,type_name,icon);
                }
                break;
            case Money.EXPENSE:
                cursor=db.rawQuery("select * from expense_type where type_id=?",new String[]{typeId+""});
                if(cursor.moveToFirst()){
                    int id=cursor.getInt(cursor.getColumnIndex("type_id"));
                    String type_name=cursor.getString(cursor.getColumnIndex("type_name"));
                    int icon=cursor.getInt(cursor.getColumnIndex("type_icon"));
                    type=new Type(id,type_name,icon);
                }
                break;
        }

        db.close();
        return type;
    }


}
