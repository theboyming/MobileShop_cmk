package com.example.jxxy.cmk.adapter;

import android.app.Activity;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jxxy.cmk.http.entity.CategoryEntity;
import com.example.jxxy.cmk.R;

import java.util.List;
import java.util.Locale;

public class CategroyLeftAdapter extends RecyclerView.Adapter<CategroyLeftAdapter.LeftViewHolder>implements View.OnClickListener {

    private final List<CategoryEntity> datas;
    private final Activity mContext;
    private OnItemClickListener onItemClickListener;
    private int select = 0;

    public CategroyLeftAdapter(Activity activity, List<CategoryEntity> data) {
        this.datas = data;
        this.mContext = activity;
    }//定义类方法，以便调用

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int position = (int) v.getTag();
            CategoryEntity entity = datas.get(position);
            onItemClickListener.onItemClick(v, position, entity);
        }
    }//针对RecycleView执行项目条例点击设置监听

    public void setOnItemClickListener(OnItemClickListener l) {
        this.onItemClickListener = l;
    }//点击方式还有以下几种
    //OnItemClickListener                适配器item 点击事件 也就是item 根布局的点击事件
    //OnItemLongClickListener          适配器item 长按事件 也就是item 根布局的长按事件
    //OnItemChildClickListener          适配器item child 点击事件 是item布局中子view的点击事件
    //OnItemChildLongClickListener      适配器item child 长按事件 是item布局中子view的长按事件

    @NonNull
    @Override
    public LeftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_categroy_left, parent, false);
        view.setOnClickListener(this);
        return new LeftViewHolder(view);
    }//引入布局并将其inflate加载成View并返回

    @Override
    public void onBindViewHolder(@NonNull LeftViewHolder holder, int position) {
        if (select == position) {
            holder.ll_item.setBackgroundResource(android.R.color.white);
        } else {
            holder.ll_item.setBackgroundColor(Color.parseColor("#fff3f4f6"));
        }
        CategoryEntity entity = datas.get(position);
        holder.itemView.setTag(position);
        holder.tv_name.setText(entity.getName());
    }
    //onBindeViewHolder方法的调用时机是item出现（或将要出现）在屏幕上时，这时需要向传入的viewHolder中填充数据等操作。
    //onCreateViewHolder的目的是创建viewHolder。而viewHolder作为recyclerView缓存管理的对象是可以在列表中复用的。
    //当屏幕上下滑动，子项移除屏幕viewHolder就会被回收，子项复用时会从缓存池中判断item type再次调用onBindViewHolder方法。



    @Override
    public int getItemCount() {
        if (datas != null) {
            return datas.size();
        }
        return 0;
    }

    public void setSelect(int select) {
        this.select = select;
        notifyDataSetChanged();//notifyDataSetChanged方法是通过一个外部的方法控制，如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
    }


    public class LeftViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_name;
        public final LinearLayout ll_item;

        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            tv_name = itemView.findViewById(R.id.tv_name);
        }//定位这里的布局控件id以便调用
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, CategoryEntity entity);
    }//条目点击监听接口
}