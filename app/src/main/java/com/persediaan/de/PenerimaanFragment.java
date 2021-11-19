package com.persediaan.de;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.AdapterPenerimaan2;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiLogin;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelItemBrg;
import com.persediaan.de.model.ModelPenerimaan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PenerimaanFragment extends Fragment implements RecyclerViewClickInterface {

    private AdapterPenerimaan2 adapter;
    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    RecyclerView recyclerPenerimaan;
    ProgressBar loading;

    public PenerimaanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penerimaan,container,false);
        recyclerPenerimaan = view.findViewById(R.id.recyclerPenerimaan);
        loading = view.findViewById(R.id.progressBarPenerimaan);
//        Toast.makeText(getContext(), "Halaman Penerimaan", Toast.LENGTH_SHORT).show();
        loadCards();
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

    private void loadCards() {

        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaan("0009-14-11");
        call.enqueue(new Callback<List<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<List<ApiPenerimaan>> call, Response<List<ApiPenerimaan>> response) {
                loading.setVisibility(View.GONE);
                List<ApiPenerimaan> apiPenerimaans = response.body();
                modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();
                if (!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<ModelItemBrg> modelItemBrgs;
                String id_purchase = null,
                        note = null,dt_purchase = null,nm_area = null,nm_suplier = null,
                        alasuplier = null, npwp = null,name_penyedia = null, status = null,
                        area = null, alamat = null, date,id_trans = null,nm_singkat = null;
                int admin = 0,id = 0,id_area = 0,id_supplier = 0,diterima = 0,harga_total=0,i=0,
                        jml_item=0,tgl_purchase = 0;

                modelItemBrgs = new ArrayList<ModelItemBrg>();
                for (ApiPenerimaan apiPenerimaan:apiPenerimaans){

                    modelItemBrgs.add(new ModelItemBrg(
                            apiPenerimaan.getId_item(),
                            apiPenerimaan.getQty(),
                            apiPenerimaan.getId_satuan(),
                            apiPenerimaan.getHarga(),
                            apiPenerimaan.getJumlah(),
                            apiPenerimaan.getNm_item(),
                            apiPenerimaan.getEceran(),
                            apiPenerimaan.getNm_satuan()));
                    if (i==0){
                        id_purchase = apiPenerimaan.getId_purchase();
                        tgl_purchase = apiPenerimaan.getTgl_purchase();
                        note = apiPenerimaan.getNote();
                        dt_purchase =apiPenerimaan.getDt_purchase();
                        nm_area =apiPenerimaan.getNm_area();
                        nm_singkat =apiPenerimaan.getNm_singkat();

                        nm_suplier = apiPenerimaan.getNm_suplier();
                        alasuplier = apiPenerimaan.getAlasuplier();
                        npwp = apiPenerimaan.getNpwp();
                        name_penyedia = apiPenerimaan.getName_penyedia();
                        area = apiPenerimaan.getArea();
                        alamat = apiPenerimaan.getAlamat();
                        id_trans = apiPenerimaan.getId_trans();

                        admin = apiPenerimaan.getAdmin();
                        id = apiPenerimaan.getId();
                        id_area = apiPenerimaan.getId_area();
                        id_supplier = apiPenerimaan.getId_suplier();
                        diterima = apiPenerimaan.getDiterima();
                    }
                    harga_total +=apiPenerimaan.getHarga();
//                    (new SimpleDateFormat("dd MMM yyyy")
//                            .format(
//                                    new Date((Long.parseLong(penerimaanbrgApi.getCreated())*1000))
//                            )).toString()
                    i++;
                }
                Log.d("19201299", "onResponse:Model Item BRGS: "+modelItemBrgs.toString());
                jml_item = i;
                if (diterima==0){
                    status = "Belum Diterima";
                }else if (diterima ==1){
                    status = "Belum Dikonversi";
                }
                    modelPenerimaanArrayList.add(new ModelPenerimaan(
                            id_purchase,tgl_purchase,note,dt_purchase,nm_area,nm_singkat,
                            nm_suplier,alasuplier,npwp,name_penyedia,status,area,
                            alamat,id_trans,jml_item,admin,id,id_area,id_supplier,diterima,
                            harga_total,modelItemBrgs
                    ));
                adapter = new AdapterPenerimaan2(modelPenerimaanArrayList,PenerimaanFragment.this);
                recyclerPenerimaan.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));
                recyclerPenerimaan.setHasFixedSize(true);
                recyclerPenerimaan.setItemAnimator(new DefaultItemAnimator());

                recyclerPenerimaan.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ApiPenerimaan>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
//        modelPenerimaanArrayList.add(new ModelPenerimaan(
//                "Deo Artanta","Belum","Surabaya/perikanan",
//                "Jl. Cempaka no.27",5,200000,
//                "25 September 2021","001","sby-002-200",0,
//                getResources().getColor(R.color.white),
//                R.drawable.ic_bubble_chart_24,
//                R.drawable.ic_bg_label_red_1,false));
        
    }
}