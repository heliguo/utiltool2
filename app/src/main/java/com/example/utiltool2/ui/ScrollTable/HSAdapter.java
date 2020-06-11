package com.example.utiltool2.ui.ScrollTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.utiltool2.R;

import java.util.List;

/**
 * @创建者 lgh
 * @创建时间 2019-08-20 17:11
 * @描述 listview适配器
 */
public class HSAdapter extends BaseAdapter {

    private Context           mContext;
    private HScrollView       mHScrollView;//listview 每个item的布局方式
    private List<ProductData> mProductDataList;//数据传入
    private int               mScrollViewId;//布局传入

    HSAdapter(Context context, HScrollView hScrollView, List<ProductData> productDataList,
              int scrollViewId) {
        mContext = context;
        mHScrollView = hScrollView;
        mProductDataList = productDataList;
        mScrollViewId = scrollViewId;

    }


    @Override
    public int getCount() {
        return mProductDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            //listview子项布局加载
            convertView = LayoutInflater.from(mContext).inflate(mScrollViewId, null);
            //获取当前条目中的右侧滑动控件
            holder.hScrollView = convertView.findViewById(R.id.hscrollview);
            //这里需要从传入的列表头拿到里面的右侧滑动控件
            //将当前条目的右侧滑动控件添加到头部滑动控件的滑动观察者集合中
            mHScrollView.addOnScrollChangedListener(new HScrollViewImp(holder.hScrollView));
            //进行holder的初始化操作
            holder.tv1 = convertView.findViewById(R.id.textview1);
            holder.tv2 = convertView.findViewById(R.id.textview2);
            holder.tv3 = convertView.findViewById(R.id.textview3);
            holder.tv4 = convertView.findViewById(R.id.textview4);
            holder.tv5 = convertView.findViewById(R.id.textview5);
            holder.tv6 = convertView.findViewById(R.id.textview6);
            holder.tv7 = convertView.findViewById(R.id.textview7);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv1.setText(mProductDataList.get(position).getStr1());
        holder.tv2.setText(mProductDataList.get(position).getStr1() + mProductDataList.get(position).getStr2());
        holder.tv3.setText(mProductDataList.get(position).getStr3());
        holder.tv4.setText(mProductDataList.get(position).getStr4());
        holder.tv5.setText(mProductDataList.get(position).getStr5());
        holder.tv6.setText(mProductDataList.get(position).getStr6());
        holder.tv7.setText(mProductDataList.get(position).getStr7());

        return convertView;
    }


    class ViewHolder {
        TextView    tv1;
        TextView    tv2;
        TextView    tv3;
        TextView    tv4;
        TextView    tv5;
        TextView    tv6;
        TextView    tv7;
        HScrollView hScrollView;
    }

    /**
     * HScrollView的对象包裹进来，
     * 实现接口OnScrollChangedListener中onScrollChanged的方法
     */
    public class HScrollViewImp implements HScrollView.OnScrollChangedListener {

        HScrollView mScrollView;

        HScrollViewImp(HScrollView scrollView) {
            mScrollView = scrollView;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollView.smoothScrollTo(l, t);
        }
    }



}
