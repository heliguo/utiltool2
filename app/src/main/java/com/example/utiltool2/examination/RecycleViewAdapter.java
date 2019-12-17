package com.example.utiltool2.examination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utiltool2.R;

import java.util.List;

/**
 * @创建者 lgh
 * @创建时间 2019-08-15 15:52
 * @描述
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private Context      mContext;
    private List<String> mDatas;

    public RecycleViewAdapter(Context context, List<String> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.tv.setText(mDatas.get(i));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public ViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.tv_num);
        }
    }

}
