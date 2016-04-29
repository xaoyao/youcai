package com.liu.youcai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liu.youcai.R;
import com.liu.youcai.bean.Money;
import com.liu.youcai.db.dao.MoneyDao;

import java.util.List;

/**
 * Created by liu on 2016/4/29 0029.
 */
public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder>{

    private Context mContext;
    private int resourceId;
    private List<Money> moneys;

    private DeleteOrEditOnClickListener mListener;

    public void setDeleteOrEditOnClickListener(DeleteOrEditOnClickListener Listener) {
        this.mListener = Listener;
    }

    public void setMoneys(List<Money> moneys) {
        this.moneys = moneys;
    }

    public DetailRecyclerViewAdapter(Context context, int resource, List<Money> moneys){
        this.mContext=context;
        this.resourceId=resource;
        this.moneys=moneys;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resourceId,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Money money=moneys.get(position);

        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) holder.line.getLayoutParams();

        //第一条数据，一定显示日期
        if(position==0){
            holder.title.setVisibility(View.VISIBLE);
            holder.date.setText(money.getDate().substring(0,10));
            if(money.getMoneyType()==Money.EARNING){
                holder.earning.setVisibility(View.VISIBLE);
                holder.expense.setVisibility(View.GONE);
                holder.earning.setText(money.getMoney()+"  "+money.getType().getName());
                holder.typeIcon.setImageResource(money.getType().getIcon());
            }else {
                holder.expense.setVisibility(View.VISIBLE);
                holder.earning.setVisibility(View.GONE);
                holder.expense.setText(money.getType().getName()+"  "+money.getMoney());
                holder.typeIcon.setImageResource(money.getType().getIcon());
            }

            //中间的竖线
            params.addRule(RelativeLayout.ALIGN_TOP,R.id.rl_title);
            params.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.rl_content);


        }else {
            //不是第一条数据
            //如果和上一条数据时间相同，不显示时间标题
            if(money.getDate().substring(0,10).equals(moneys.get(position-1).getDate().substring(0,10))){
                holder.title.setVisibility(View.GONE);
                if(money.getMoneyType()==Money.EARNING){
                    holder.earning.setVisibility(View.VISIBLE);
                    holder.expense.setVisibility(View.GONE);
                    holder.earning.setText(money.getMoney()+"  "+money.getType().getName());
                    holder.typeIcon.setImageResource(money.getType().getIcon());
                }else {
                    holder.expense.setVisibility(View.VISIBLE);
                    holder.earning.setVisibility(View.GONE);
                    holder.expense.setText(money.getType().getName()+"  "+money.getMoney());
                    holder.typeIcon.setImageResource(money.getType().getIcon());
                }

                //中间的竖线
                params.addRule(RelativeLayout.ALIGN_TOP,R.id.rl_content);
                params.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.rl_content);

            }else {
                //和上一条数据时间不同，显示时间标题
                holder.title.setVisibility(View.VISIBLE);
                holder.date.setText(money.getDate().substring(0,10));
                if(money.getMoneyType()==Money.EARNING){
                    holder.earning.setVisibility(View.VISIBLE);
                    holder.expense.setVisibility(View.GONE);
                    holder.earning.setText(money.getMoney()+"  "+money.getType().getName());
                    holder.typeIcon.setImageResource(money.getType().getIcon());
                }else {
                    holder.expense.setVisibility(View.VISIBLE);
                    holder.earning.setVisibility(View.GONE);
                    holder.expense.setText(money.getType().getName()+"  "+money.getMoney());
                    holder.typeIcon.setImageResource(money.getType().getIcon());
                }

                //中间的竖线
                params.addRule(RelativeLayout.ALIGN_TOP,R.id.rl_title);
                params.addRule(RelativeLayout.ALIGN_BOTTOM,R.id.rl_content);
            }
        }

        holder.line.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return moneys.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout title;
        TextView date;
//        TextView dayExpense;

        ImageView delete;
        ImageView edit;

        ImageView typeIcon;

        TextView earning;
        TextView expense;
        View line;

        private boolean isClicked=false;


        public ViewHolder(View itemView) {
            super(itemView);

            initView();

            initClick();
        }

        private void initView(){
            title= (RelativeLayout) itemView.findViewById(R.id.rl_title);
            date= (TextView) itemView.findViewById(R.id.tv_date);

//            dayExpense= (TextView) itemView.findViewById(R.id.tv_everyday_expense);

            delete= (ImageView) itemView.findViewById(R.id.delete);
            edit= (ImageView) itemView.findViewById(R.id.edit);
            typeIcon= (ImageView) itemView.findViewById(R.id.money_type_icon);
            earning= (TextView) itemView.findViewById(R.id.earning_money);
            expense= (TextView) itemView.findViewById(R.id.expense_money);

            line=itemView.findViewById(R.id.line);
        }

        private void initClick(){

            //删除数据
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getLayoutPosition();
                    if(new MoneyDao(mContext).deleteById(moneys.get(position).getId())){

                        moneys.remove(position);

                        delete.animate().translationX(0).setDuration(500);
                        edit.animate().translationX(0).setDuration(500);

                        notifyDataSetChanged();
                        //删除时更新收入支出总额
                        if(mListener!=null){
                            mListener.deleteUpdate();
                        }

                        isClicked=false;

                    }else {
                        Toast.makeText(mContext,"删除失败",Toast.LENGTH_SHORT).show();
                    }




                }
            });

            //编辑数据
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){

                        delete.animate().translationX(0).setDuration(500);
                        edit.animate().translationX(0).setDuration(500);
                        isClicked=false;

                        mListener.onEditClick(moneys.get(getLayoutPosition()));

                    }
                }
            });

            //点击图标，显示删除，编辑
            typeIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(isClicked){
                        delete.animate().translationX(0).setDuration(500);
                        edit.animate().translationX(0).setDuration(500);
                        isClicked=false;

                    }else {
                        delete.animate().translationX(-300).setDuration(500);
                        edit.animate().translationX(300).setDuration(500);
                        isClicked=true;
                    }

                }
            });

        }


    }

    /**
     * 编辑按钮的点击监听事件
     */
    public interface DeleteOrEditOnClickListener{
        /**
         * 删除时更新收入支出总额
         */
        void deleteUpdate();

        /**
         * edit点击事件
         * @param money
         */
        void onEditClick(Money money);
    }
}
