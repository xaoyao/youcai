package com.liu.youcai.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liu.youcai.R;
import com.liu.youcai.adapter.StatisticsListViewAdapter;
import com.liu.youcai.bean.Money;
import com.liu.youcai.bean.Statistics;
import com.liu.youcai.db.dao.MoneyDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends Fragment {
    public static final String TYPE="type";
    public static final int EARNING_TYPE=0;
    public static final int EXPENSE_TYPE=1;

    private ImageView mImageViewLeft;
    private ImageView mImageViewRight;
    private TextView mShowMonth;
    private TextView mMonthMoney;
    private ListView mStatisticsListView;

    private List<Statistics> statisticses;
    private int year;
    private int month;

    private int moneyType;
    private StatisticsListViewAdapter adapter;


    public StatisticsFragment() {

    }

    public static StatisticsFragment newInstanse(int type){

        StatisticsFragment fragment=new StatisticsFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.fragment_statistics, container, false);

        initView(view);
        initData();
        initStatisticses();


        mShowMonth.setText(year+"年 "+month+"月");
        updateMonthMoney();

        mStatisticsListView.setEmptyView(view.findViewById(R.id.empty_view));
        updateListView();




        mImageViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month==1){
                    month=12;
                    year-=1;
                    mShowMonth.setText(year+"年 "+month+"月");
                    initStatisticses();
                    updateListView();
                    updateMonthMoney();
                }else {
                    month-=1;
                    mShowMonth.setText(year+"年 "+month+"月");
                    initStatisticses();
                    updateListView();
                    updateMonthMoney();
                }
            }
        });

        mImageViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(month==12){
                    month=1;
                    year+=1;
                    mShowMonth.setText(year+"年 "+month+"月");
                    initStatisticses();
                    updateListView();
                    updateMonthMoney();
                }else {
                    month+=1;
                    mShowMonth.setText(year+"年 "+month+"月");
                    initStatisticses();
                    updateListView();
                    updateMonthMoney();
                }

            }
        });

        return view;
    }

    private void initView(View view){

        mImageViewLeft= (ImageView) view.findViewById(R.id.left_iv);
        mImageViewRight= (ImageView) view.findViewById(R.id.right_iv);
        mShowMonth= (TextView) view.findViewById(R.id.statistics_month);
        mMonthMoney= (TextView) view.findViewById(R.id.statistics_money);
        mStatisticsListView= (ListView) view.findViewById(R.id.statistics_view);
    }

    private void initData(){

        moneyType=getArguments().getInt(TYPE);

        Date date=new Date();
        SimpleDateFormat formatYear=new SimpleDateFormat("yyyy");
        SimpleDateFormat formatMonth=new SimpleDateFormat("MM");
        year=Integer.parseInt(formatYear.format(date));
        month=Integer.parseInt(formatMonth.format(date));
    }

    /**
     * 获取收支统计数据
     */
    private void initStatisticses(){

        if(moneyType==EARNING_TYPE){
            statisticses=new MoneyDao(getContext()).statisticsByMonth(Money.EARNING,
                    String.format("%d-%02d",year,month));
        }else {
            statisticses=new MoneyDao(getContext()).statisticsByMonth(Money.EXPENSE,
                    String.format("%d-%02d",year,month));
        }

    }

    /**
     * 更新显示总计金额
     */
    private void updateMonthMoney(){
        double allSum=0;
        for(Statistics s:statisticses){
            allSum+=s.getSum();
        }
        mMonthMoney.setText(allSum+"");
    }

    /**
     * 更新Listview
     */
    private void updateListView(){

        adapter = new StatisticsListViewAdapter(getContext(),
                R.layout.statistics_item,statisticses);

        mStatisticsListView.setAdapter(adapter);

    }

}
