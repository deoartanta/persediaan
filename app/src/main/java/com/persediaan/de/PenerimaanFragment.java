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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.persediaan.de.adapter.AdapterPenerimaan2;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelItemBrg;
import com.persediaan.de.model.ModelPenerimaan;

import java.util.ArrayList;
import java.util.HashMap;
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
    ShimmerFrameLayout shimmerFrameLayout;

    TextView tv_lblDtKosong;
    SessionManager sessionManagerUser;
    HashMap<String,Integer> detailUserInt;

    SessionManager sessionManualBook;
    FrameLayout frame_layout_manual_book;
    LinearLayout main_linearlayout;
    public PenerimaanFragment() {
        // Required empty public constructor
    }

    public PenerimaanFragment(FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout) {
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penerimaan,container,false);
        recyclerPenerimaan = view.findViewById(R.id.recyclerPenerimaan);
        tv_lblDtKosong = view.findViewById(R.id.tv_lbl_dtkosong);
//        Toast.makeText(getContext(), "Halaman Penerimaan", Toast.LENGTH_SHORT).show();
        shimmerFrameLayout = view.findViewById(R.id.shimerLayout);
        shimmerFrameLayout.startShimmer();
        recyclerPenerimaan.setVisibility(View.GONE);

        sessionManagerUser = new SessionManager(requireContext(),"login");
        sessionManualBook= new SessionManager(requireContext(),
                "manualbook");
        detailUserInt = sessionManagerUser.getUserDetailInt();
        loadCards(detailUserInt.get(SessionManager.USER_ID));
        return view;
    }

    @Override
    public void onItemClick(int position,View v) {
        TextView tv_idtrans=v.findViewById(R.id.tvResIdTrans);
        TextView tv_tgl_purchase=v.findViewById(R.id.tvResTGL);
        TextView tv_penyedia=v.findViewById(R.id.tvResSupplier);
        TextView tv_area=v.findViewById(R.id.tvResArea);
        TextView tv_sts=v.findViewById(R.id.tvResSTS);
        TextView tv_jumlah=v.findViewById(R.id.tvResJMLItem);
        TextView tv_total_hrg=v.findViewById(R.id.tvResTotalHrg);

        Intent i = new Intent(requireContext(),DetailPenerimaanActivity.class);

        String id_trans = (tv_idtrans!=null)?(String) tv_idtrans.getText():"null";
        String tgl_purchase = (tv_tgl_purchase!=null)?(String) tv_tgl_purchase.getText():"null";
        String penyedia = (tv_penyedia!=null)?(String) tv_penyedia.getText():"null";
        String area = (tv_area!=null)?(String) tv_area.getText():"null";
        String sts = (tv_sts!=null)?(String) tv_sts.getText():"null";
        String jumlah = (tv_jumlah!=null)?(String) tv_jumlah.getText():"null";
        String total_hrg = (tv_total_hrg!=null)?(String) tv_total_hrg.getText():"null";

        i.putExtra(DetailPenerimaanActivity.ID_TRANS,id_trans);
        i.putExtra(DetailPenerimaanActivity.TGL_PURCHASE,tgl_purchase);
        i.putExtra(DetailPenerimaanActivity.PENYEDIA,penyedia);
        i.putExtra(DetailPenerimaanActivity.AREA,area);
        i.putExtra(DetailPenerimaanActivity.STS,sts);
        i.putExtra(DetailPenerimaanActivity.JML_ITEM,jumlah);
        i.putExtra(DetailPenerimaanActivity.TOTAL_HRG,total_hrg);

        if (!sessionManualBook.getManualBook(SessionManager.DETAILPENER)){
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameManualBook,
                            new DetailPenerManualBookFragment(frame_layout_manual_book,
                                    main_linearlayout,i))
                    .commit();
            sessionManualBook.setManualBook(SessionManager.DETAILPENER,true);
        }else {
            startActivity(i);
        }
//        Toast.makeText(getContext(), "Position : "+position+"\n Nama Penyedia : "+nm_penyedia.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick1(int position, View view) {

    }

    private void loadCards(int id_user) {

        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        recyclerPenerimaan.setAdapter(null);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerPenerimaan.setVisibility(View.GONE);

        Call<List<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaanCart(id_user);
        call.enqueue(new Callback<List<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<List<ApiPenerimaan>> call, Response<List<ApiPenerimaan>> response) {

                List<ApiPenerimaan> apiPenerimaans = response.body();
                modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();
                if (!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                    tv_lblDtKosong.setVisibility(View.GONE);
                    return;
                }
                ArrayList<ModelItemBrg> modelItemBrgs;
                String id_purchase = null,
                        note = null,dt_purchase = null,nm_area = null,nm_suplier = null,
                        alasupplier = null, npwp = null,name_penyedia = null, status = null,
                        area = null, alamat = null, date,id_trans = null,nm_singkat = null;
                int admin = 0,id = 0,id_area = 0,id_supplier = 0,diterima = 0,harga_total=0,i=0,
                        jml_item=0,tgl_purchase = 0;

                modelItemBrgs = new ArrayList<ModelItemBrg>();
                for (ApiPenerimaan apiPenerimaan:apiPenerimaans){
                    tv_lblDtKosong.setVisibility(View.GONE);
                    modelItemBrgs.add(new ModelItemBrg(
                            apiPenerimaan.getId(), apiPenerimaan.getId_item(),
                            apiPenerimaan.getQty(),
                            apiPenerimaan.getId_satuan(),
                            apiPenerimaan.getHarga(),
                            apiPenerimaan.getJumlah(),
                            apiPenerimaan.getNm_item(),
                            apiPenerimaan.getEceran(),
                            apiPenerimaan.getNm_satuan()));
                    if (i==(apiPenerimaans.size()-1)){
                        nm_suplier = apiPenerimaan.getNm_suplier();
                        alasupplier = apiPenerimaan.getAlasuplier();
                    }
                    if (i==0){
                        id_purchase = apiPenerimaan.getId_purchase();
                        tgl_purchase = apiPenerimaan.getCreated();
                        note = apiPenerimaan.getNote();
                        dt_purchase =apiPenerimaan.getDt_purchase();
                        nm_area =apiPenerimaan.getNm_area();
                        nm_singkat =apiPenerimaan.getNm_singkat();

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
                    harga_total +=(apiPenerimaan.getHarga()*apiPenerimaan.getQty());
//                    (new SimpleDateFormat("dd MMM yyyy")
//                            .format(
//                                    new Date((Long.parseLong(penerimaanbrgApi.getCreated())*1000))
//                            )).toString()
                    i++;
                }
//                Log.d("19201299", "onResponse:Model Item BRGS: "+modelItemBrgs.toString());
                jml_item = i-1;
                if (diterima==0){
                    status = "Belum Diterima";
                }else if (diterima ==1){
                    status = "Belum Dikonversi";
                }
                    modelPenerimaanArrayList.add(new ModelPenerimaan(
                            id_purchase,tgl_purchase,note,dt_purchase,nm_area,nm_singkat,
                            nm_suplier,alasupplier,npwp,name_penyedia,status,area,
                            alamat,id_trans,jml_item,admin,id,id_area,id_supplier,diterima,
                            harga_total,modelItemBrgs
                    ));
                adapter = new AdapterPenerimaan2(modelPenerimaanArrayList,PenerimaanFragment.this);
                recyclerPenerimaan.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));
                recyclerPenerimaan.setHasFixedSize(true);
                recyclerPenerimaan.setItemAnimator(new DefaultItemAnimator());

                recyclerPenerimaan.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerPenerimaan.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<ApiPenerimaan>> call, Throwable t) {
                Call <ApiPenerimaan> call1 =
                        jsonPlaceHolderApi.getResponPenerimaanCartStatus(id_user);
                call1.enqueue(new Callback<ApiPenerimaan>() {
                    @Override
                    public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        tv_lblDtKosong.setVisibility(View.VISIBLE);
                        if (!response.isSuccessful()){
                            Toast.makeText(requireContext(), "Gagal terhubung ke server",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        tv_lblDtKosong.setText(""+(response.body().getMsg()).toUpperCase());
                    }

                    @Override
                    public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                        shimmerFrameLayout.setVisibility(View.GONE);
                        tv_lblDtKosong.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), "Gagal terhubung ke server",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCards(detailUserInt.get(SessionManager.USER_ID));
    }
}