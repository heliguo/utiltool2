package com.example.library.manager;

import androidx.collection.SparseArrayCompat;

import com.example.library.model.RViewItem;

/**
 * author:lgh on 2019-11-14 16:15
 * 条目管理，配合Adaptec
 */
public class RViewItemManager<T> {

    private SparseArrayCompat<RViewItem<T>> styles = new SparseArrayCompat<>();

    public void addStyle(RViewItem<T> item) {
        if (item != null) {
            styles.put(styles.size(), item);
        }
    }

    public int getItemViewStylesCount() {
        return styles.size();
    }

    //获取集合中的key
    public int getItemViewType(T entity, int position) {
        for (int i = 0; i < styles.size(); i++) {

        }
        return 0;
    }
}
