package com.persediaan.de;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.AdapterPenerimaan2;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.model.ModelPenerimaan;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PenerimaanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PenerimaanFragment extends Fragment implements RecyclerViewClickInterface {

    private AdapterPenerimaan2 adapter;
    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;
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
//        Toast.makeText(getContext(), "Halaman Penerimaan", Toast.LENGTH_SHORT).show();
        adapter = loadCards();
        recyclerPenerimaan.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerPenerimaan.setHasFixedSize(true);
        recyclerPenerimaan.setItemAnimator(new DefaultItemAnimator());

        recyclerPenerimaan.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemClick(int position,View v) {
        TextView tv_idtrans=v.findViewById(R.id.tvResIdTrans);
        TextView tv_admin=v.findViewById(R.id.tvResAdmin);
        Intent i = new Intent(requireContext(),DetailPenerimaanActivity.class);

        String id_trans = (tv_idtrans!=null)?(String) tv_idtrans.getText():"null"+tv_admin.getText();
        i.putExtra(DetailPenerimaanActivity.ID_TRANS,id_trans);
        startActivity(i);
//        Toast.makeText(getContext(), "Position : "+position+"\n Nama Penyedia : "+nm_penyedia.getText(), Toast.LENGTH_SHORT).show();
    }

    private AdapterPenerimaan2 loadCards() {
        modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();

        modelPenerimaanArrayList.add(new ModelPenerimaan(
                "Deo Artanta","Belum","Surabaya/perikanan",
                "Jl. Cempaka no.27",5,200000,
                "25 September 2021","001","sby-002-200",0,
                getResources().getColor(R.color.white),
                R.drawable.ic_bubble_chart_24,
                R.drawable.ic_bg_label_red_1,false));

        modelPenerimaanArrayList.add(new ModelPenerimaan(
                "Totok Risqy","Sudah","Jember",
                "Jl. Sumbersari no.35",4,300000,
                "19 September2021","002","sby-003-200",0,
                getResources().getColor(R.color.colorBgGreen),
                R.drawable.ic_bubble_chart_24,
                R.drawable.ic_bg_label_green,false));
        modelPenerimaanArrayList.add(new ModelPenerimaan(
                "Shohib Habibullah","Sudah","20 Item",
                "Jl. Dermaga IV no.50",6,1000000,
                "20 September 2021","003","sby-004-200",0,
                getResources().getColor(R.color.colorBgGreen),
                R.drawable.ic_bubble_chart_24,
                R.drawable.ic_bg_label_green,
                true));
        return new AdapterPenerimaan2(modelPenerimaanArrayList,PenerimaanFragment.this);
    }
}