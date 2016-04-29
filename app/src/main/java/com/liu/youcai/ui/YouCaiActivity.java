package com.liu.youcai.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.MyApplication;
import com.liu.youcai.R;
import com.liu.youcai.bean.Money;
import com.liu.youcai.db.dao.MoneyDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class YouCaiActivity extends AppCompatActivity {
    private Button btnAdd;
    private DrawerLayout mDrawerLayout;
    private TextView todayExpense;

    private LinearLayout mToDetail;
    private LinearLayout mTodayToDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_cai);
        ActivityCollector.addActivity(this);
        setTranslucent();

        initDrawerLayout();

        todayExpense= (TextView) findViewById(R.id.today_expense);
        mToDetail= (LinearLayout) findViewById(R.id.to_detail);
        mTodayToDetail= (LinearLayout) findViewById(R.id.today_to_detail);


        btnAdd= (Button) findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouCaiActivity.this,AddActivity.class));
            }
        });

        mToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouCaiActivity.this,DetailActivity.class));
            }
        });
        mTodayToDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouCaiActivity.this,DetailActivity.class));
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTodayExpense();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 初始化侧滑菜单
     */
    private void initDrawerLayout(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);

        //侧滑菜单Header用户管理点击
        View headView=navigationView.getHeaderView(0);

        TextView usernameTV= (TextView) headView.findViewById(R.id.username);
        usernameTV.setText(((MyApplication)getApplication()).getUser().getUsername());

        LinearLayout userLayout= (LinearLayout) headView.findViewById(R.id.user_Layout);
        userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YouCaiActivity.this,UserSettingActivity.class));
                mDrawerLayout.closeDrawers();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Toast.makeText(YouCaiActivity.this,item.getTitle(),Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 使状态栏透明
     */
    public void setTranslucent() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 更新显示今日支出
     */
    private void updateTodayExpense(){
        Date date =new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        String time=format.format(date);
        List<Money> todayMoneys=new MoneyDao(YouCaiActivity.this).findExpenseMoneyByTime(time);
        Double sum=0.00;
        for(Money m:todayMoneys){
            sum+=m.getMoney();
        }
        todayExpense.setText(todayMoneys.size()+"笔 "+sum);
    }


    //双击退出
    private boolean is2CallBack=false;
    @Override
    public void onBackPressed() {

        if(!is2CallBack){
            is2CallBack=true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    is2CallBack=false;
                }
            },2000);
        }else {
            ActivityCollector.finishAll();
        }
    }
}
