package com.persediaan.de.adapter;

import android.view.View;

public interface RecyclerViewClickExpendInterface {
    void onItemExpendClick(int id,int position, View view,boolean isEnable);
    void onItemClickExpandable(int position, View view, boolean expandable);
}
