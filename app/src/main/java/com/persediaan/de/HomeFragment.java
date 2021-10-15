package com.persediaan.de;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.persediaan.de.adapter.myAdapter;
import com.persediaan.de.model.myModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ActionBar actionBar;
    private ArrayList<myModel> modelArrayList;

    private myAdapter adapter;

    ViewPager viewPager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
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
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        viewPager = view.findViewById(R.id.vp2Home);
        loadCards();
        return view;
    }

    private void loadCards() {
         modelArrayList = new ArrayList<myModel>();

         modelArrayList.add(new myModel(
                 "Deo Artanta",
                 "Belum",
                 "3 Item",
                 "Jl. Cempaka no.27",
                 "Rp. 200.000",
                 "25 September 2021",
                 R.drawable.ic_bubble_chart_24,
                 R.drawable.ic_bg_label_red_1));
         modelArrayList.add(new myModel(
                 "Totok Risqy",
                 "Sudah",
                 "10 Item",
                 "Jl. Sumbersari no.35",
                 "Rp. 300.000",
                 "19 September2021",
                 R.drawable.ic_bubble_chart_24,
                 R.drawable.ic_bg_label_green));
         modelArrayList.add(new myModel(
                 "Shohib Habibullah",
                 "Sudah",
                 "20 Item",
                 "Jl. Dermaga IV no.50",
                 "Rp. 1.000.000",
                 "20 September 2021",
                 R.drawable.ic_bubble_chart_24,
                 R.drawable.ic_bg_label_green));
         adapter = new myAdapter(getContext(),modelArrayList);
         viewPager.setAdapter(adapter);
         viewPager.setPadding(0,0,10,0);
    }
}