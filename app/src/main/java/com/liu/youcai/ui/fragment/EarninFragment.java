package com.liu.youcai.ui.fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.liu.youcai.R;
import com.liu.youcai.bean.Type;
import com.liu.youcai.db.dao.TypeDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarninFragment extends Fragment {
    private Button mChoseTime;
    private View view;
    private ImageView mEarningTypeIcon;
    private GridView mGridView;


    public EarninFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_earnin, container, false);

        mEarningTypeIcon= (ImageView) view.findViewById(R.id.earning_type_icon);

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

//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(System.currentTimeMillis());
//        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
//
//        timePicker.setIs24HourView(true);
//        timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
//        timePicker.setCurrentMinute(Calendar.MINUTE);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                StringBuffer sb = new StringBuffer();
////                sb.append(String.format("%d-%02d-%02d",
////                        datePicker.getYear(),
////                        datePicker.getMonth() + 1,
////                        datePicker.getDayOfMonth()));
////                sb.append(" ");
////                sb.append(String.format("%02d:%02d",
////                        timePicker.getCurrentHour()
////                        ,timePicker.getCurrentMinute()));

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
