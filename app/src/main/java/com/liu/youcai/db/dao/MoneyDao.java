package com.liu.youcai.db.dao;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liu.youcai.MyApplication;
import com.liu.youcai.bean.Money;
import com.liu.youcai.bean.Statistics;
import com.liu.youcai.bean.Type;
import com.liu.youcai.bean.User;
import com.liu.youcai.db.YouCaiDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2016/4/28 0028.
 */
public class MoneyDao {
    private Context context;
    private YouCaiDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private User user;

    public MoneyDao(Context context){
        this.context=context;
        dbHelper=new YouCaiDatabaseHelper(context);
        user=((MyApplication)context.getApplicationContext()).getUser();
    }


    /**
     * 向数据库插入记录
     * @param money
     * @return
     */
    public boolean addMoney(Money money){

        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("user_id",money.getUserId());
        values.put("money_type",money.getMoneyType());
        values.put("type_id",money.getType().getId());
        values.put("money",money.getMoney());
        values.put("date",money.getDate());
        values.put("other",money.getOther());
        long id=db.insert("money",null,values);
        db.close();

        if(id==-1){
            return false;
        }
        return true;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<Money> findAll(){
        List<Money> moneys=new ArrayList<>();
        Money money=null;

        db=dbHelper.getWritableDatabase();

        Cursor cursor=db.rawQuery("select * from money where user_id=? order by date desc",new String[]{user.getId()+""});

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("money_id"));
                int userId=cursor.getInt(cursor.getColumnIndex("user_id"));
                int moneyType=cursor.getInt(cursor.getColumnIndex("money_type"));
                int typeId=cursor.getInt(cursor.getColumnIndex("type_id"));
                double m=cursor.getDouble(cursor.getColumnIndex("money"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String other=cursor.getString(cursor.getColumnIndex("other"));

                Type type=new TypeDao(context).findTypeById(moneyType,typeId);

                money=new Money(id,userId,moneyType,type,m,date,other);
                moneys.add(money);

            }while (cursor.moveToNext());
        }
        db.close();

        return moneys;
    }

    /**
     * 通过时间查找记录
     * @param datetime
     * @return
     */
    public List<Money> findMoneyByTime(String datetime){

        List<Money> moneys=new ArrayList<>();
        Money money=null;

        db=dbHelper.getWritableDatabase();

        Cursor cursor=db.rawQuery("select * from money where user_id=? and date like ?",new String[]{user.getId()+"",datetime+"%"});

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("money_id"));
                int userId=cursor.getInt(cursor.getColumnIndex("user_id"));
                int moneyType=cursor.getInt(cursor.getColumnIndex("money_type"));
                int typeId=cursor.getInt(cursor.getColumnIndex("type_id"));
                double m=cursor.getDouble(cursor.getColumnIndex("money"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String other=cursor.getString(cursor.getColumnIndex("other"));

                Type type=new TypeDao(context).findTypeById(moneyType,typeId);

                money=new Money(id,userId,moneyType,type,m,date,other);
                moneys.add(money);

            }while (cursor.moveToNext());
        }
        db.close();

        return moneys;

    }


    /**
     * 通过时间查找收入记录
     * @param datetime
     * @return
     */
    public List<Money> findEarningMoneyByTime(String datetime){

        List<Money> moneys=new ArrayList<>();
        Money money=null;

        db=dbHelper.getWritableDatabase();

        Cursor cursor=db.rawQuery("select * from money where user_id=? and money_type=? and date like ?",new String[]{user.getId()+"",Money.EARNING+"",datetime+"%"});

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("money_id"));
                int userId=cursor.getInt(cursor.getColumnIndex("user_id"));
                int moneyType=cursor.getInt(cursor.getColumnIndex("money_type"));
                int typeId=cursor.getInt(cursor.getColumnIndex("type_id"));
                double m=cursor.getDouble(cursor.getColumnIndex("money"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String other=cursor.getString(cursor.getColumnIndex("other"));

                Type type=new TypeDao(context).findTypeById(moneyType,typeId);

                money=new Money(id,userId,moneyType,type,m,date,other);
                moneys.add(money);

            }while (cursor.moveToNext());
        }
        db.close();

        return moneys;

    }

    /**
     * 通过时间查找支出记录
     * @param datetime
     * @return
     */
    public List<Money> findExpenseMoneyByTime(String datetime){

        List<Money> moneys=new ArrayList<>();
        Money money=null;

        db=dbHelper.getWritableDatabase();

        Cursor cursor=db.rawQuery("select * from money where user_id=? and money_type=? and date like ?",new String[]{user.getId()+"",Money.EXPENSE+"",datetime+"%"});

        if(cursor.moveToFirst()){
            do{
                int id=cursor.getInt(cursor.getColumnIndex("money_id"));
                int userId=cursor.getInt(cursor.getColumnIndex("user_id"));
                int moneyType=cursor.getInt(cursor.getColumnIndex("money_type"));
                int typeId=cursor.getInt(cursor.getColumnIndex("type_id"));
                double m=cursor.getDouble(cursor.getColumnIndex("money"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                String other=cursor.getString(cursor.getColumnIndex("other"));

                Type type=new TypeDao(context).findTypeById(moneyType,typeId);

                money=new Money(id,userId,moneyType,type,m,date,other);
                moneys.add(money);

            }while (cursor.moveToNext());
        }
        db.close();

        return moneys;

    }


    /**
     * 通过id删除记录
     * @param id
     * @return
     */
    public boolean deleteById(int id){

        db=dbHelper.getWritableDatabase();
        int code=db.delete("money","money_id=?",new String[]{id+""});
        if(code==0){
            return false;
        }
        db.close();
        return true;
    }

    /**
     * 修改记录
     * @param money
     * @return
     */
    public boolean update(Money money){
        db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("type_id",money.getType().getId());
        values.put("money",money.getMoney());
        values.put("date",money.getDate());
        values.put("other",money.getOther());
        int code=db.update("money",values,"money_id=?",new String[]{money.getId()+""});
        db.close();

        if(code<1){
            return false;
        }
        return true;
    }

    /**
     * 查询每月的收支统计
     * @return
     */
    public List<Statistics> statisticsByMonth(int moneyType , String time){
        List<Statistics> statisticses=new ArrayList<>();
        db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select type_id , sum(money) from money where user_id=? and date like ? and money_type=? group by type_id",
                new String[]{user.getId()+"",time+"%",moneyType+""});
        if(cursor.moveToFirst()){
            do{
                int typeId=cursor.getInt(cursor.getColumnIndex("type_id"));
                double sum=cursor.getDouble(cursor.getColumnIndex("sum(money)"));

                Type type=new TypeDao(context).findTypeById(moneyType,typeId);

                Statistics statistics=new Statistics(type,sum);
                statisticses.add(statistics);

            }while (cursor.moveToNext());
        }
        db.close();

        return statisticses;
    }





}
