package com.liu.youcai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liu.youcai.R;

/**
 * Created by liu on 2016/4/19 0019.
 */
public class YouCaiDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_USER = "create table user(" +
            "user_id integer primary key autoincrement," +
            "username text," +
            "password text)";

    private static final String CREATE_EXPENSE_TYPE="create table expense_type(" +
            "type_id integer primary key autoincrement," +
            "type_name text," +
            "type_icon integer)";
    private static final String CREATE_EARNING_TYPE="create table earning_type(" +
            "type_id integer primary key autoincrement," +
            "type_name text," +
            "type_icon integer)";
    private static final String CREATE_MONEY="create table money(" +
            "money_id integer primary key autoincrement," +
            "user_id integer," +
            "money_type integer," +
            "type_id integer," +
            "money real," +
            "date datetime," +
            "other text)";

    public YouCaiDatabaseHelper(Context context) {
        super(context, "YouCai.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_EARNING_TYPE);
        db.execSQL(CREATE_EXPENSE_TYPE);
        db.execSQL(CREATE_MONEY);

        initEarningType(db);
        initExpenseType(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initExpenseType(SQLiteDatabase db){
        ContentValues values=new ContentValues();
        values.put("type_name","一般");
        values.put("type_icon", R.drawable.icon_zhichu_type_yiban);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","餐饮");
        values.put("type_icon", R.drawable.icon_zhichu_type_canyin);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","交通");
        values.put("type_icon", R.drawable.icon_zhichu_type_jiaotong);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","酒水饮料");
        values.put("type_icon", R.drawable.icon_zhichu_type_yanjiuyinliao);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","水果");
        values.put("type_icon", R.drawable.icon_zhichu_type_shuiguolingshi);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","零食");
        values.put("type_icon", R.drawable.icon_zhichu_type_lingshi);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","买菜");
        values.put("type_icon", R.drawable.icon_zhichu_type_maicai);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","衣服鞋帽");
        values.put("type_icon", R.drawable.icon_zhichu_type_yifu);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","生活用品");
        values.put("type_icon", R.drawable.icon_zhichu_type_richangyongpin);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","话费");
        values.put("type_icon", R.drawable.icon_zhichu_type_shoujitongxun);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","护肤彩妆");
        values.put("type_icon", R.drawable.icon_zhichu_type_huazhuangpin);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","房租");
        values.put("type_icon", R.drawable.icon_zhichu_type_fangzu);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","电影");
        values.put("type_icon", R.drawable.icon_zhichu_type_dianying);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","淘宝");
        values.put("type_icon", R.drawable.icon_zhichu_type_taobao);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","还钱");
        values.put("type_icon", R.drawable.icon_zhichu_type_jiechu);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","红包");
        values.put("type_icon", R.drawable.icon_shouru_type_hongbao);
        db.insert("expense_type",null,values);

        values.clear();
        values.put("type_name","药品");
        values.put("type_icon", R.drawable.icon_zhichu_type_yaopinfei);
        db.insert("expense_type",null,values);
    }
    private void initEarningType(SQLiteDatabase db){
        ContentValues values=new ContentValues();

        values.put("type_name","工资");
        values.put("type_icon",R.drawable.icon_shouru_type_gongzi);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","生活费");
        values.put("type_icon",R.drawable.icon_shouru_type_shenghuofei);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","红包");
        values.put("type_icon",R.drawable.icon_shouru_type_hongbao);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","零花钱");
        values.put("type_icon",R.drawable.icon_shouru_type_linghuaqian);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","外快兼职");
        values.put("type_icon",R.drawable.icon_shouru_type_jianzhiwaikuai);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","投资收入");
        values.put("type_icon",R.drawable.icon_shouru_type_touzishouru);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","借入");
        values.put("type_icon",R.drawable.icon_shouru_type_jieru);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","奖金");
        values.put("type_icon",R.drawable.icon_shouru_type_jiangjin);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","还款");
        values.put("type_icon",R.drawable.icon_shouru_type_huankuan);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","报销");
        values.put("type_icon",R.drawable.icon_shouru_type_baoxiao);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","现金");
        values.put("type_icon",R.drawable.icon_shouru_type_xianjin);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","退款");
        values.put("type_icon",R.drawable.icon_shouru_type_tuikuan);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","支付宝");
        values.put("type_icon",R.drawable.icon_shouru_type_zhifubao);
        db.insert("earning_type",null,values);

        values.clear();
        values.put("type_name","其他");
        values.put("type_icon",R.drawable.icon_shouru_type_qita);
        db.insert("earning_type",null,values);

    }
}
