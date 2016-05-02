package com.liu.youcai.bean;

/**
 * Created by liu on 2016/5/2 0002.
 */
public class Statistics {
    private Type type;
    private double sum;

    public Statistics() {
    }

    public Statistics(Type type, double sum) {
        this.type = type;
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
