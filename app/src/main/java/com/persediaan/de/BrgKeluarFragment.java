package com.persediaan.de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.tabs.TabLayout;
import com.persediaan.de.adapter.AdapterBrgKeluar;
import com.persediaan.de.adapter.RecyclerViewKonversiInterface;
import com.persediaan.de.data.SessionManager;

public class BrgKeluarFragment extends Fragment implements RecyclerViewKonversiInterface {
    TabLayout tabLayout;
    ViewPager2 pager2;
    AdapterBrgKeluar adapter;
    FragmentManager supportFragmentManager;
    MeowBottomNavigation BtnNavigation;
    FrameLayout frame_layout_manual_book;
    LinearLayout main_linearlayout;

    SessionManager transtition;


    public BrgKeluarFragment(FragmentManager supportFragmentManager, MeowBottomNavigation BtnNavigation) {
        this.supportFragmentManager = supportFragmentManager;
        this.BtnNavigation = BtnNavigation;
    }

    public BrgKeluarFragment(FragmentManager supportFragmentManager, MeowBottomNavigation BtnNavigation, FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout) {
        this.supportFragmentManager = supportFragmentManager;
        this.BtnNavigation = BtnNavigation;
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
    }

    public RecyclerViewKonversiInterface recyclerViewKonversiInterface(){
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_brg_keluar,container,false);

        transtition = new SessionManager(requireContext(),"transtition");

        tabLayout = view.findViewById(R.id.tab_pengeluaran);
        pager2 = view.findViewById(R.id.view_pager2);
        adapter = new AdapterBrgKeluar(supportFragmentManager, getLifecycle(),BtnNavigation,
                frame_layout_manual_book,main_linearlayout);
        pager2.setAdapter(adapter);

//        tabLayout.addTab(tabLayout.newTab().setText("Konversi"));
//        tabLayout.addTab(tabLayout.newTab().setText("Transfer"));

        if (transtition.getTranstition("transfer")){
            pager2.setCurrentItem(1);
            tabLayout.selectTab(tabLayout.getTabAt(1));
            transtition.setTranstition("transfer",false);
        }else if (transtition.getTranstition("laptransfer")){
            pager2.setCurrentItem(3);
            tabLayout.selectTab(tabLayout.getTabAt(2));
            transtition.setTranstition("laptransfer",false);
        }


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        return view;
    }

    @Override
    public void AddCartKonversi(View view, int Position) {

    }

    @Override
    public void DelCartKonversi(View view, int Position, int Id) {

    }
}