package com.persediaan.de.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.persediaan.de.KonversiFragment;
import com.persediaan.de.TransferFragment;

public class AdapterBrgKeluar extends FragmentStateAdapter {
    public AdapterBrgKeluar(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1 :
                return new TransferFragment();
        }
        return new KonversiFragment();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
