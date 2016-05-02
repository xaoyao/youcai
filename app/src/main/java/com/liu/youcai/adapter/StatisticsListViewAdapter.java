package com.liu.youcai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liu.youcai.R;
import com.liu.youcai.bean.Statistics;

import java.util.List;

/**
 * Created by liu on 2016/5/2 0002.
 */
public class StatisticsListViewAdapter extends ArrayAdapter<Statistics> {
    private int resourceId;

    public StatisticsListViewAdapter(Context context, int resource, List<Statistics> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Statistics statistics=getItem(position);

        View view;
        ViewHolder holder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder(view);
            view.setTag(holder);

        }else {
            view=convertView;
            holder= (ViewHolder) view.getTag();
        }


        holder.typeIcon.setImageResource(statistics.getType().getIcon());
        holder.typeTittle.setText(statistics.getType().getName());
        holder.sum.setText(statistics.getSum()+"");
        return view;

    }

    class ViewHolder{
        ImageView typeIcon;
        TextView typeTittle;
        TextView sum;
        public ViewHolder(View v){
            typeIcon= (ImageView) v.findViewById(R.id.type_icon);
            typeTittle= (TextView) v.findViewById(R.id.type_tittle);
            sum= (TextView) v.findViewById(R.id.sum_money);
        }
    }
}
