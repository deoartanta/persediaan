package com.persediaan.de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.adapter.myAdapter;
import com.persediaan.de.data.dataDummy;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.myModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PenerimaanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PenerimaanFragment extends Fragment implements RecyclerViewClickInterface {

    private AdapterPenerimaan adapter;
    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;
    dataDummy dataDummyn;
    RecyclerView recyclerPenerimaan;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public PenerimaanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PenerimaanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PenerimaanFragment newInstance(String param1, String param2) {
        PenerimaanFragment fragment = new PenerimaanFragment();
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
        View view = inflater.inflate(R.layout.fragment_penerimaan,container,false);
        recyclerPenerimaan = view.findViewById(R.id.recyclerPenerimaan);


        modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();

        dataDummyn = new dataDummy();
        ArrayList<ModelPenerimaan> listPenerimaan = dataDummyn.getPenerimaan();
        for (ModelPenerimaan modelPenerimaan :listPenerimaan){
            modelPenerimaanArrayList.add(new ModelPenerimaan(
                    modelPenerimaan.getName_penyedia(),
                    modelPenerimaan.getStatus(),
                    modelPenerimaan.getArea(),
                    modelPenerimaan.getAlamat(),
                    modelPenerimaan.getHarga_total(),
                    modelPenerimaan.getTgl(),
                    modelPenerimaan.getImg_bg_card(),
                    modelPenerimaan.getNo_penerimaan()
            ));
        }
        adapter= new AdapterPenerimaan(modelPenerimaanArrayList,
                PenerimaanFragment.this);
        recyclerPenerimaan.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(int position,View v) {

    }

    private void loadCards() {
        modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();

        modelPenerimaanArrayList.add(new ModelPenerimaan(
                "Deo Artanta",
                "Belum",
                "3 Item",
                "Jl. Cempaka no.27",
                200000,
                "25 September 2021",
                R.drawable.ic_bubble_chart_24,"001"));
        modelPenerimaanArrayList.add(new ModelPenerimaan(
                "Totok Risqy",
                "Sudah",
                "10 Item",
                "Jl. Sumbersari no.35",
                300000,
                "19 September2021",
                R.drawable.ic_bubble_chart_24,
                "002"));
        modelPenerimaanArrayList.add(new myModel(
                "Shohib Habibullah",
                "Sudah",
                "20 Item",
                "Jl. Dermaga IV no.50",
                "Rp. 1.000.000",
                "20 September 2021",
                R.drawable.ic_bubble_chart_24,
                R.drawable.ic_bg_label_green));
        adapter = new myAdapter(getContext(),modelPenerimaanArrayList);
    }
}