package com.persediaan.de.adapter;

import android.view.View;

public interface RecyclerViewClickExpendInterface {
    void onItemClick(int position, View view);
    void onItemExpendClick(int position, View view,boolean isEnable);
}
