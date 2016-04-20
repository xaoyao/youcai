package com.liu.youcai;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity容器
 * 退出程序
 *
 * Created by liu on 2016/4/19 0019.
 */
public class ActivityCollector {

    public static List<Activity> activities=new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            activity.finish();
        }
    }

}
