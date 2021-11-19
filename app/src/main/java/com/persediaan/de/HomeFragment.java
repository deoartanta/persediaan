package com.persediaan.de;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.adapter.myAdapter;
import com.persediaan.de.model.ModelPenerimaan;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ActionBar actionBar;
    private ArrayList<ModelPenerimaan> modelPenerimaanArrayList;

    private myAdapter adapter;
    private AdapterPenerimaan adapterPenerimaan;

    ViewPager viewPager;
    ViewPager2 viewPager2;
    RecyclerView home_penerimaan,home_brgout;
    ScrollView svHome;

    MeowBottomNavigation bottomNavigation;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment(MeowBottomNavigation bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2, MeowBottomNavigation btnNavBottom) {
        HomeFragment fragment = new HomeFragment(btnNavBottom);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        home_penerimaan = view.findViewById(R.id.recyclerHomePenerimaan);
        home_brgout = view.findViewById(R.id.recyclerHomeBrgKeluar);
//        loadCards();
        return view;
    }

    private void loadCards() {
        modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();

//        modelPenerimaanArrayList.add(new ModelPenerimaan(
//                "Deo Artanta","Belum","Surabaya",
//                "Jl. Cempaka no.27",10,200000,
//                "25 September 2021","001","sby-002-200",0,
//                getResources().getColor(R.color.white),
//                R.drawable.ic_bubble_chart_24,
//                R.drawable.ic_bg_label_red_1,false));

         adapter = new myAdapter(getContext(),modelPenerimaanArrayList);
         adapterPenerimaan = new AdapterPenerimaan(modelPenerimaanArrayList,this);

         home_brgout.setLayoutManager(new LinearLayoutManager(getContext(),
                 LinearLayoutManager.HORIZONTAL, false));
         home_brgout.setItemAnimator(new DefaultItemAnimator());
         home_brgout.setAdapter(adapterPenerimaan);

         home_penerimaan.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
//        home_penerimaan.setHasFixedSize(true);
        home_penerimaan.setItemAnimator(new DefaultItemAnimator());

        home_penerimaan.setAdapter(adapterPenerimaan);

    }

    @Override
    public void onItemClick(int position, View view) {
        TextView tv_idtrans = view.findViewById(R.id.tvIdTrans);
        Intent i = new Intent(requireContext(),DetailPenerimaanActivity.class);

        String id_trans = (String) tv_idtrans.getText();
        i.putExtra(DetailPenerimaanActivity.ID_TRANS,id_trans);
        startActivity(i);
    }
}