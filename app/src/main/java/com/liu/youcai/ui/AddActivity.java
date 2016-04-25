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
import com.liu.youcai.ui.fragment.EarninFragment;
import com.liu.youcai.ui.fragment.ExpenseFragment;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private ViewPager mAddViewPager;
    private List<Fragment> mAddFragments;

    private TextView mEarningTextView;
    private TextView mExpenseTextView;
    private AddViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ActivityCollector.addActivity(this);

        initToolBar();

        initAddFragments();

        initViewPager();

        mEarningTextView= (TextView) findViewById(R.id.earning_tv);
        mExpenseTextView= (TextView) findViewById(R.id.expense_tv);
        changeTextColor();

        mEarningTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddViewPager.setCurrentItem(0);
            }
        });
        mExpenseTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddViewPager.setCurrentItem(1);
            }
        });



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    /**
     * 初始化要显示的fragment
     */
    private void  initAddFragments(){
        mAddFragments=new ArrayList<>();
        EarninFragment earninFragment=new EarninFragment();
        ExpenseFragment expenseFragment=new ExpenseFragment();
        mAddFragments.add(earninFragment);
        mAddFragments.add(expenseFragment);
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("添加记录");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViewPager(){
        mAddViewPager= (ViewPager) findViewById(R.id.add_vp);

        mPagerAdapter = new AddViewPagerAdapter(getSupportFragmentManager(),mAddFragments);

        mAddViewPager.setAdapter(mPagerAdapter);

        mAddViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        int item=mAddViewPager.getCurrentItem();
        mEarningTextView.setTextColor(item==0?Color.BLUE:Color.BLACK);
        mExpenseTextView.setTextColor(item==1?Color.BLUE:Color.BLACK);
    }
}
