package com.persediaan.de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.persediaan.de.adapter.AdapterAkunSetting;
import com.persediaan.de.adapter.RecyclerViewClickExpendInterface;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.ModelProfileRowExpand;
import com.persediaan.de.model.ModelProfileRowItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements RecyclerViewClickExpendInterface,RecyclerViewClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SessionManager sessionManagerProfil;
    HashMap<String,String> DetailProfile;

    private AdapterAkunSetting adapterProfile;
//    private AdapterAkunSetting adapter;

    ArrayList<ModelProfileRowExpand>arrayList_profileRowExpand;

    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;

    RecyclerView recyclerView_profileSetting;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        recyclerView_profileSetting = view.findViewById(R.id.reyclerProfileSetting);
        sessionManagerProfil = new SessionManager(requireContext(),"login");
        DetailProfile = sessionManagerProfil.getUserDetail();
        loadSettingProfile();
        return view;
    }
    public ArrayList<ModelProfileRowItem> createRowItem(String[]item){
        ArrayList<ModelProfileRowItem>itemArray=new ArrayList<ModelProfileRowItem>();
        for (String itemLopp:item){
            itemArray.add(new ModelProfileRowItem(itemLopp,
                    R.drawable.ic_baseline_keyboard_arrow_right_24));
        }
        return itemArray;
    };
    public void loadSettingProfile(){
        arrayList_profileRowExpand = new ArrayList<ModelProfileRowExpand>();

        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                "Akun",R.drawable.ic_person_edit,
                createRowItem(new String[]{"Nama","Username","Password","Alamat"}), true));
        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                "Logout",R.drawable.ic_baseline_power_settings_new_24,
                null, false));

//        arrayList_settingAkun.add(
//                new ModelProfileRowItem("Logout",
//                        R.drawable.ic_baseline_power_settings_new_24));
//        adapter = loadCards();
        adapterProfile = new AdapterAkunSetting(
                arrayList_profileRowExpand,ProfileFragment.this);

        recyclerView_profileSetting.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView_profileSetting.setHasFixedSize(true);
        recyclerView_profileSetting.setItemAnimator(new DefaultItemAnimator());

        recyclerView_profileSetting.setAdapter(adapterProfile);


    }

//    private AdapterAkunSetting loadCards() {
//        modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();
//        arrayList_profileRowExpand = new ArrayList<ModelProfileRowExpand>();
//
//        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
//                "Akun",R.drawable.ic_baseline_keyboard_arrow_right_24,
//                createRowItem(new String[]{"Nama","Username","Password"}), true));
//
//        modelPenerimaanArrayList.add(new ModelPenerimaan(
//                "Deo Artanta",
//                "Belum",
//                "3 Item",
//                "Jl. Cempaka no.27",
//                200000,
//                "25 September 2021",
//                "001",
//                0,
//                getResources().getColor(R.color.white),
//                R.drawable.ic_bubble_chart_24,
//                R.drawable.ic_bg_label_red_1));
//        modelPenerimaanArrayList.add(new ModelPenerimaan(
//                "Totok Risqy",
//                "Sudah",
//                "10 Item",
//                "Jl. Sumbersari no.35",
//                300000,
//                "19 September2021",
//                "002",
//                0,
//                getResources().getColor(R.color.colorBgGreen),
//                R.drawable.ic_bubble_chart_24,
//                R.drawable.ic_bg_label_green));
//        modelPenerimaanArrayList.add(new ModelPenerimaan(
//                "Shohib Habibullah",
//                "Sudah",
//                "20 Item",
//                "Jl. Dermaga IV no.50",
//                1000000,
//                "20 September 2021",
//                "003",
//                0,
//                getResources().getColor(R.color.colorBgGreen),
//                R.drawable.ic_bubble_chart_24,
//                R.drawable.ic_bg_label_green,
//                120));
//        return new AdapterAkunSetting(modelPenerimaanArrayList, ProfileFragment.this);
//    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(requireContext(), "Position "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemExpendClick(int position, View view,boolean isEnable) {
        if (isEnable){

        }else{
            switch (position){
                case 1:
                    sessionManagerProfil.logout();
                    break;
            }
        }
    }
}