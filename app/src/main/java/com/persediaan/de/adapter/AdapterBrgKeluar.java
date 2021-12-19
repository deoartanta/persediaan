package com.persediaan.de.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.KonversiFragment;
import com.persediaan.de.TransferFragment;

public class AdapterBrgKeluar extends FragmentStateAdapter {
    MeowBottomNavigation meowBottomNavigation;
    public AdapterBrgKeluar(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, MeowBottomNavigation meowBottomNavigation) {
        super(fragmentManager, lifecycle);
        this.meowBottomNavigation = meowBottomNavigation;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1 :
                return new TransferFragment();
        }
        return new KonversiFragment(meowBottomNavigation);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
