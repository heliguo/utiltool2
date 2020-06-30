package com.example.utiltool2.ui.ScrollTable.recyclerview;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.example.rvlibrary.holder.RViewHolder;
import com.example.rvlibrary.model.RViewItem;
import com.example.utiltool2.R;
import com.example.utiltool2.ui.ScrollTable.listview.HScrollView;
import com.example.utiltool2.ui.ScrollTable.listview.ProductData;

/**
 * author:lgh on 2020/6/11 11:35
 */
public class Item implements RViewItem<ProductData> {

    @Override
    public int getItemLayout() {
        return R.layout.item_hscroll_view;
    }

    @Override
    public boolean openClick() {
        return true;
    }

    @Override
    public boolean isItemView(ProductData entity, int position) {
        return entity.getType() == 0;
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    public void convert(RViewHolder holder, ProductData entity, int position) {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
        TextView tv7;
        HScrollView hScrollView;

        tv1 = holder.itemView.findViewById(R.id.textview1);
        tv2 = holder.itemView.findViewById(R.id.textview2);
        tv3 = holder.itemView.findViewById(R.id.textview3);
        tv4 = holder.itemView.findViewById(R.id.textview4);
        tv5 = holder.itemView.findViewById(R.id.textview5);
        tv6 = holder.itemView.findViewById(R.id.textview6);
        tv7 = holder.itemView.findViewById(R.id.textview7);
        hScrollView = holder.itemView.findViewById(R.id.hscrollview);

        tv1.setText(entity.getStr1());
        tv2.setText(entity.getStr1() + entity.getStr2());
        tv3.setText(entity.getStr3());
        tv4.setText(entity.getStr4());
        tv5.setText(entity.getStr5());
        tv6.setText(entity.getStr6());
        tv7.setText(entity.getStr7());


    }
}
