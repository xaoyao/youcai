package com.liu.youcai.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.liu.youcai.ActivityCollector;
import com.liu.youcai.R;
import com.liu.youcai.adapter.DetailRecyclerViewAdapter;
import com.liu.youcai.bean.Money;
import com.liu.youcai.db.dao.MoneyDao;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;

    private TextView mAllEarning;
    private TextView mAllExpense;

    private List<Money> moneys;
    private DetailRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ActivityCollector.addActivity(this);

        initToolbar();

        initMoneys();

        mAllEarning= (TextView) findViewById(R.id.all_earning);
        mAllExpense= (TextView) findViewById(R.id.all_expense);
        updateAllOfEarningAndExpanse();


        mRecyclerView= (RecyclerView) findViewById(R.id.rec_view);
        adapter = new DetailRecyclerViewAdapter(DetailActivity.this,
                R.layout.detail_item,moneys);


        adapter.setDeleteOrEditOnClickListener(new DetailRecyclerViewAdapter.DeleteOrEditOnClickListener() {
            @Override
            public void deleteUpdate() {
                updateAllOfEarningAndExpanse();
            }

            @Override
            public void onEditClick(Money money) {

                //以编辑模式启动添加数据界面
                Intent i=new Intent(DetailActivity.this,AddActivity.class);
                i.putExtra(AddActivity.MODE,AddActivity.MODE_EDIT);
                i.putExtra("money",money);
                startActivity(i);

            }
        });

        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
    }


    @Override
    protected void onResume() {
        super.onResume();
        initMoneys();
        adapter.setMoneys(moneys);
        adapter.notifyDataSetChanged();
        updateAllOfEarningAndExpanse();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void initToolbar(){
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("明细查看");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initMoneys(){
        moneys= new MoneyDao(DetailActivity.this).findAll();
    }


    /**
     * 更新上方显示的收入支出总额
     */
    private void updateAllOfEarningAndExpanse(){
        double sumEarning=0;
        double sumExpense=0;
        for(Money money :moneys){
            if(money.getMoneyType()==Money.EARNING){
                sumEarning+=money.getMoney();
            }else {
                sumExpense+=money.getMoney();
            }
        }

        mAllEarning.setText(sumEarning+"");
        mAllExpense.setText(sumExpense+"");
    }
}
