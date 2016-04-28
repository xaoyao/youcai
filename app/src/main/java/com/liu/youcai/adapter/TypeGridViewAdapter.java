package com.liu.youcai.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.liu.youcai.R;
import com.liu.youcai.bean.Type;

import java.util.List;

/**
 * Created by liu on 2016/4/26 0026.
 */
public class TypeGridViewAdapter extends ArrayAdapter<Type>{
    private int resource;

    public TypeGridViewAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.resource=resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Type type= getItem(position);

        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view=View.inflate(getContext(),resource,null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) view.findViewById(R.id.type_item_imageView);
            viewHolder.textView= (TextView) view.findViewById(R.id.type_item_textview);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.imageView.setBackgroundResource(type.getIcon());
        viewHolder.textView.setText(type.getName());

        return view;

    }

    class ViewHolder{

        ImageView imageView;
        TextView textView;
    }

}
