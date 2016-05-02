package com.liu.youcai.ui.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.liu.youcai.MyApplication;
import com.liu.youcai.R;
import com.liu.youcai.adapter.TypeGridViewAdapter;
import com.liu.youcai.bean.Money;
import com.liu.youcai.bean.Type;
import com.liu.youcai.db.dao.MoneyDao;
import com.liu.youcai.db.dao.TypeDao;
import com.liu.youcai.ui.YouCaiActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    private Button mChoseTime;
    private View view;

    private ImageView mExpenseTypeIcon;
    private TextView mExpenseTypeName;
    private EditText mEtMoney;
    private EditText mEtOther;
    private Button mSave;

    private GridView mGridView;
    private List<Type> types;
    private Type type;

    private Money money;

    private String date;
    private double moneyNumber;


    public ExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_expense, container, false);

        mExpenseTypeIcon = (ImageView) view.findViewById(R.id.expense_type_icon);
        mExpenseTypeName = (TextView) view.findViewById(R.id.expense_type_text);
        mEtMoney = (EditText) view.findViewById(R.id.money);
        mEtOther = (EditText) view.findViewById(R.id.other);

        mSave = (Button) view.findViewById(R.id.sava);

        mChoseTime = (Button) view.findViewById(R.id.chose_time);

        mGridView = (GridView) view.findViewById(R.id.grid_view);

        initGridView();
        initData();

        mEtMoney.setText(moneyNumber + "");
        mChoseTime.setText(date);
        mChoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseTime();

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMoney();
            }

        });


        return view;
    }

    /**
     * 选择时间
     */
    private void choseTime() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = View.inflate(getContext(), R.layout.date_time_dialog, null);

        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);

        builder.setView(dialogView);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                date = String.format("%d-%02d-%02d %02d:%02d",
                        datePicker.getYear(),
                        datePicker.getMonth() + 1,
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                mChoseTime.setText(date);
                dialog.cancel();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    /**
     * 设置GridView
     */
    private void initGridView() {

        types = new TypeDao(getContext()).findAllExpenseType();
        TypeGridViewAdapter adapter = new TypeGridViewAdapter(getContext(), R.layout.type_item, types);
        mGridView.setAdapter(adapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = types.get(position);
                mExpenseTypeIcon.setBackgroundResource(type.getIcon());
                mExpenseTypeName.setText(type.getName());
            }
        });

    }

    private void saveMoney() {



        //编辑模式
        if (money != null) {

            money.setType(type);
            double m = Double.parseDouble(mEtMoney.getText().toString());
            money.setMoney(m);
            money.setDate(date);
            money.setOther(mEtOther.getText().toString());

            if (new MoneyDao(getContext()).update(money)) {
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "编辑失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            //添加模式
            money = new Money();
            money.setUserId(((MyApplication) getActivity().getApplication()).getUser().getId());
            money.setMoneyType(Money.EXPENSE);
            money.setType(type);

            double m = Double.parseDouble(mEtMoney.getText().toString());
            money.setMoney(m);
            money.setDate(date);
            money.setOther(mEtOther.getText().toString());
            if (new MoneyDao(getContext()).addMoney(money)) {
                startActivity(new Intent(getContext(), YouCaiActivity.class));
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            money = (Money) bundle.getSerializable("money");
            type = money.getType();
            date = money.getDate().substring(0, 16);
            moneyNumber = money.getMoney();

            //更新图标
            mExpenseTypeIcon.setBackgroundResource(type.getIcon());
            mExpenseTypeName.setText(type.getName());

            mEtOther.setText(money.getOther());

        } else {

            type = new TypeDao(getContext()).findEarningTypeByName("工资");
            Date time = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = format.format(time);
            moneyNumber = 0;
            money = null;
        }


    }

}
