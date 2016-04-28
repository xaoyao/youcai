package com.liu.youcai.bean;

/**
 * Created by liu on 2016/4/28 0028.
 */
public class Money {

    public static final int EARNING=0;
    public static final int EXPENSE=1;

    private int id;
    private int userId;
    private int moneyType;
    private Type type;
    private double money;
    private String date;
    private String other;

    public Money() {
    }

    public Money(int id, int userId, int moneyType, Type type, double money, String date, String other) {
        this.id = id;
        this.userId = userId;
        this.moneyType = moneyType;
        this.type = type;
        this.money = money;
        this.date = date;
        this.other = other;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(int moneyType) {
        this.moneyType = moneyType;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
