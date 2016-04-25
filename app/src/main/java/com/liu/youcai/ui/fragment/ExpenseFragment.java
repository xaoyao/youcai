package com.liu.youcai.ui.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.liu.youcai.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    private Button mChoseTime;
    private View view;


    public ExpenseFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_expense, container, false);

        mChoseTime= (Button) view.findViewById(R.id.chose_time);
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String t=format.format(date);
        mChoseTime.setText(t);
        mChoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseTime();

            }
        });


        return view;
    }

    /**
     * 选择时间
     */
    private void choseTime(){

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        View dialogView=View.inflate(getContext(),R.layout.date_time_dialog,null);

        final DatePicker datePicker= (DatePicker) dialogView.findViewById(R.id.date_picker);
        final TimePicker timePicker= (TimePicker) dialogView.findViewById(R.id.time_picker);

        timePicker.setIs24HourView(true);

        builder.setView(dialogView);


        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String time=String.format("%d-%02d-%02d %02d:%02d",
                        datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());

                mChoseTime.setText(time);
                dialog.cancel();
            }
        });

        Dialog dialog=builder.create();
        dialog.show();
    }

}
