package com.liu.youcai.ui;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.R;
import com.liu.youcai.adapter.AddViewPagerAdapter;
import com.liu.youcai.ui.fragment.StatisticsFragment;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextView mEarningTextView;
    private TextView mExpenseTextView;
    private AddViewPagerAdapter mPagerAdapter;

    private ViewPager mStatisticsViewPager;
    private List<Fragment> mStatisticsFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        ActivityCollector.addActivity(this);

        mEarningTextView= (TextView) findViewById(R.id.earning_tv);
        mExpenseTextView= (TextView) findViewById(R.id.expense_tv);

        initToolbar();

        initStatisticsFragments();
        initViewPager();
        changeTextColor();

        mEarningTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatisticsViewPager.setCurrentItem(0);
            }
        });
        mExpenseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatisticsViewPager.setCurrentItem(1);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void initToolbar(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("收支统计");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initStatisticsFragments(){

        StatisticsFragment Earningfragment=StatisticsFragment
                .newInstanse(StatisticsFragment.EARNING_TYPE);
        StatisticsFragment ExpenseFragment=StatisticsFragment
                .newInstanse(StatisticsFragment.EXPENSE_TYPE);
        mStatisticsFragments=new ArrayList<>();
        mStatisticsFragments.add(Earningfragment);
        mStatisticsFragments.add(ExpenseFragment);

    }

    private void initViewPager(){
        mStatisticsViewPager= (ViewPager) findViewById(R.id.statistics_vp);

        mPagerAdapter = new AddViewPagerAdapter(getSupportFragmentManager(),mStatisticsFragments);

        mStatisticsViewPager.setAdapter(mPagerAdapter);

        mStatisticsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeTextColor();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * 改变选中选项卡的颜色
     */
    private void changeTextColor(){
        int item=mStatisticsViewPager.getCurrentItem();
        mEarningTextView.setTextColor(item==0?Color.BLUE:Color.BLACK);
        mExpenseTextView.setTextColor(item==1?Color.BLUE:Color.BLACK);
    }
}
