package com.persediaan.de.adapter;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.BrgKeluarLapFragment;
import com.persediaan.de.KonversiFragment;
import com.persediaan.de.TransferFragment;

public class AdapterBrgKeluar extends FragmentStateAdapter {
    MeowBottomNavigation meowBottomNavigation;
    FrameLayout frame_layout_manual_book;
    LinearLayout main_linearlayout;
    public AdapterBrgKeluar(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MeowBottomNavigation meowBottomNavigation,
                            FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout) {
        super(fragmentManager, lifecycle);
        this.meowBottomNavigation = meowBottomNavigation;
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1 :
                return new TransferFragment(frame_layout_manual_book);
            case 2:
                return new BrgKeluarLapFragment();
        }
        return new KonversiFragment(meowBottomNavigation,frame_layout_manual_book);

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
