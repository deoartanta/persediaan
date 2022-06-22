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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.AdapterPenerimaan2;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.api.data.ApiResponListener;
import com.persediaan.de.api.data.LoadDaftarPenerimaan;
import com.persediaan.de.data.ListItem;
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

    private AdapterPenerimaan adapter2;
    private AdapterPenerimaan2 adapter;
    private String TAG = "19201299";
    boolean isDataPenerimaan = false;
    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    RecyclerView recyclerPenerimaan;
//    Load Daftar Penerimaan
    LoadDaftarPenerimaan loadDaftarPenerimaan;
    ArrayList<ModelPenerimaan> modelPenerimaanDetail;
    ArrayList<ModelPenerimaan> modelPenerimaanDetailK;
    ArrayList<ModelItemBrg> arrItemBrg;
    ArrayList<String> arrItemID;
    ArrayList<String> arrItemName;
    ArrayList<String> arrItemQty;
    ArrayList<String> arrItemStn;
    ArrayList<String> arrItemEcer;
    ArrayList<String> arrItemHarga;

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
        recyclerPenerimaan.setVisibility(View.GONE);

        sessionManagerUser = new SessionManager(requireContext(),"login");
        sessionManualBook= new SessionManager(requireContext(),
                "manualbook");
        detailUserInt = sessionManagerUser.getUserDetailInt();
        Log.d(TAG, "onCreateView: Start");
        loadCards(detailUserInt.get(SessionManager.USER_ID));
        return view;
    }

    @Override
    public void onItemClick(int position,View v) {
        TextView tv_idtrans=v.findViewById(R.id.tvResIdTrans);
        TextView tv_tgl_purchase=v.findViewById(R.id.tvTgl);
        TextView tv_penyedia=v.findViewById(R.id.tvNamePenyedia);
        TextView tv_ala_penyedia=v.findViewById(R.id.tvAlamat);
        TextView tv_area=v.findViewById(R.id.tvArea);
        TextView tv_sts=v.findViewById(R.id.tvResSTS);
        TextView tv_jumlah=v.findViewById(R.id.tvQTY);
        TextView tv_total_hrg=v.findViewById(R.id.tvHrgTotal);

        Intent i = new Intent(requireContext(),DetailPenerimaanActivity.class);

        String id_trans = (modelPenerimaanArrayList.get(position)!=null)?
                modelPenerimaanArrayList.get(position).getId_purchase():
                "null";
        String tgl_purchase = (tv_tgl_purchase!=null)?(String) tv_tgl_purchase.getText():"null";
        String penyedia = (tv_penyedia!=null)?(String) tv_penyedia.getText():"null";
        String ala_penyedia = (tv_ala_penyedia!=null)?(String) tv_ala_penyedia.getText():"null";
        String area = (tv_area!=null)?(String) tv_area.getText():"null";
        String sts = (tv_sts!=null)?(String) tv_sts.getText():"null";
        String jumlah = (tv_jumlah!=null)?(String) tv_jumlah.getText():"null";
        String total_hrg = (tv_total_hrg!=null)?(String) tv_total_hrg.getText():"null";
        int index = 0;
        arrItemID = new ArrayList<>();arrItemName = new ArrayList<>();arrItemStn = new ArrayList<>();
        arrItemEcer = new ArrayList<>();arrItemQty = new ArrayList<>();arrItemHarga = new ArrayList<>();
        for (ModelItemBrg modelItemBrg:modelPenerimaanDetail.get(position).getItemBrgs()){
            arrItemID.add(modelItemBrg.getId_item());
            arrItemName.add(modelItemBrg.getNm_item());
            arrItemStn.add(modelItemBrg.getNm_satuan());
            arrItemEcer.add(modelItemBrg.getNm_eceran());
            arrItemQty.add(String.valueOf(modelItemBrg.getQty()));
            arrItemHarga.add(String.valueOf(modelItemBrg.getHarga()));
        }


        i.putExtra(DetailPenerimaanActivity.ID_TRANS,id_trans);
        i.putExtra(DetailPenerimaanActivity.TGL_PURCHASE,tgl_purchase);
        i.putExtra(DetailPenerimaanActivity.PENYEDIA,penyedia);
        i.putExtra(DetailPenerimaanActivity.ALA_PENYEDIA,ala_penyedia);
        i.putExtra(DetailPenerimaanActivity.AREA,area);

        i.putExtra(DetailPenerimaanActivity.STS,sts);
        i.putExtra(DetailPenerimaanActivity.JML_ITEM,jumlah);
        i.putExtra(DetailPenerimaanActivity.TOTAL_HRG,total_hrg);
        i.putExtra("position",position);

        i.putStringArrayListExtra(ListItem.ID_ITEM,arrItemID);
        i.putStringArrayListExtra(ListItem.NM_ITEM,arrItemName);
        i.putStringArrayListExtra(ListItem.NM_SATUAN,arrItemStn);
        i.putStringArrayListExtra(ListItem.NM_ECER,arrItemEcer);
        i.putStringArrayListExtra(ListItem.QTY,arrItemQty);
        i.putStringArrayListExtra(ListItem.HARGA,arrItemHarga);


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
        Log.d(TAG, "onCreateView: Start ke 2");
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        recyclerPenerimaan.setAdapter(null);
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        recyclerPenerimaan.setVisibility(View.GONE);
//        Load Penerimaan
        {
            Log.d(TAG, "onCreateView: Start ke 3");
            Call<List<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaanCart(id_user);
            call.enqueue(new Callback<List<ApiPenerimaan>>() {
                @Override
                public void onResponse(Call<List<ApiPenerimaan>> call, Response<List<ApiPenerimaan>> response) {

                    List<ApiPenerimaan> apiPenerimaans = response.body();
                    modelPenerimaanArrayList = new ArrayList<ModelPenerimaan>();
                    Log.d(TAG, "onCreateView: Start ke 4");
                    if (!response.isSuccessful()) {
                        Toast.makeText(requireContext(), "Error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                        tv_lblDtKosong.setVisibility(View.GONE);
                        penerimaanKonversi(modelPenerimaanArrayList,false);
                        return;
                    }
                    {
                        ArrayList<ModelItemBrg> modelItemBrgs;
                        String id_purchase = null,
                                note = null, dt_purchase = null, nm_area = null, nm_suplier = null,
                                alasupplier = null, npwp = null, name_penyedia = null, status = null,
                                area = null, alamat = null, date, id_trans = null, nm_singkat = null;
                        int admin = 0, id = 0, id_area = 0, id_supplier = 0, diterima = 0, harga_total = 0, i = 0,
                                jml_item = 0, tgl_purchase = 0;

                        modelItemBrgs = new ArrayList<ModelItemBrg>();
                        Log.d(TAG, "onResponse: Belum Diisi");
                        for (ApiPenerimaan apiPenerimaan : apiPenerimaans) {
                            Log.d(TAG, "onResponse: "+new ModelItemBrg(
                                    apiPenerimaan.getId(), apiPenerimaan.getId_item(),
                                    apiPenerimaan.getQty(),
                                    apiPenerimaan.getId_satuan(),
                                    apiPenerimaan.getHarga(),
                                    apiPenerimaan.getJumlah(),
                                    apiPenerimaan.getNm_item(),
                                    apiPenerimaan.getEceran(),
                                    apiPenerimaan.getNm_satuan()).toString());
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
                            if (i == (apiPenerimaans.size() - 1)) {
                                nm_suplier = apiPenerimaan.getNm_suplier();
                                alasupplier = apiPenerimaan.getAlasuplier();
                            }
                            if (i == 0) {
                                id_purchase = apiPenerimaan.getId_purchase();
                                tgl_purchase = apiPenerimaan.getCreated();
                                note = apiPenerimaan.getNote();
                                dt_purchase = apiPenerimaan.getDt_purchase();
                                nm_area = apiPenerimaan.getNm_area();
                                nm_singkat = apiPenerimaan.getNm_singkat();

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
                            harga_total += (apiPenerimaan.getHarga() * apiPenerimaan.getQty());
//                    (new SimpleDateFormat("dd MMM yyyy")
//                            .format(
//                                    new Date((Long.parseLong(penerimaanbrgApi.getCreated())*1000))
//                            )).toString()
                            i++;
                        }
//                Log.d("19201299", "onResponse:Model Item BRGS: "+modelItemBrgs.toString());
                        jml_item = i - 1;
                        if (diterima == 0) {
                            status = "Belum Diterima";
                        } else if (diterima == 1) {
                            status = "Belum Dikonversi";
                        }
                        modelPenerimaanArrayList.add(new ModelPenerimaan(
                                id_purchase, tgl_purchase, note, dt_purchase, nm_area, nm_singkat,
                                nm_suplier, alasupplier, npwp, name_penyedia, status, area,
                                alamat, id_trans, jml_item, admin, id, id_area, id_supplier, diterima,
                                harga_total, modelItemBrgs
                        ));
                        Log.d(TAG, "onResponse: Udah Diisi");

                    }
                    if (modelPenerimaanArrayList != null) {
                        isDataPenerimaan = true;
                    }
                    penerimaanKonversi(modelPenerimaanArrayList,isDataPenerimaan);
                }

                @Override
                public void onFailure(Call<List<ApiPenerimaan>> call, Throwable t) {
                    Log.d(TAG, "onCreateView: Failure ke 1 "+t.getMessage());
                    Call<ApiPenerimaan> call1 =
                            jsonPlaceHolderApi.getResponPenerimaanCartStatus(id_user);
                    penerimaanKonversi(null,false);
                    call1.enqueue(new Callback<ApiPenerimaan>() {
                        @Override
                        public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                            shimmerFrameLayout.setVisibility(View.GONE);
                            if (!response.isSuccessful()) {
                                Toast.makeText(requireContext(), "Gagal terhubung ke server",
                                        Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d(TAG, "onCreateView: Failure kosong");
                            tv_lblDtKosong.setText("" + (response.body().getMsg()).toUpperCase());
                        }

                        @Override
                        public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                            Log.d(TAG, "onCreateView: Failure ke 2");
                            shimmerFrameLayout.setVisibility(View.GONE);
                            tv_lblDtKosong.setText("" + (t.getMessage()).toUpperCase());
                            Toast.makeText(requireContext(), "Gagal terhubung ke server",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }


        
    }
    public void penerimaanKonversi(ArrayList<ModelPenerimaan> modelPenerimaans, boolean b){
        modelPenerimaanArrayList = modelPenerimaans;
        boolean dataListPener = false;

        if (modelPenerimaanArrayList!=null){
            dataListPener = b;
        }
        isDataPenerimaan = dataListPener;
        {
            loadDaftarPenerimaan = new LoadDaftarPenerimaan(detailUserInt.get(SessionManager.USER_ID)
                    , retrofit,
                    jsonPlaceHolderApi,getContext());
            loadDaftarPenerimaan.LoadData();
            loadDaftarPenerimaan.setApiResponListener(new ApiResponListener<ArrayList<ApiPenerimaan>>() {
                @Override
                public void onResponse(boolean status, Response<ArrayList<ApiPenerimaan>> body) {

                }

                @Override
                public void onResponse(boolean status, ArrayList<ApiPenerimaan> body) {
                    HashMap<String,String> konversi = new HashMap<>();
                    HashMap<String,String> disKonversi = new HashMap<>();
                    ArrayList<ModelPenerimaan> modelKonversi = new ArrayList<>();
                    ArrayList<ModelPenerimaan> modelDisKonversi = new ArrayList<>();
                    modelPenerimaanDetail = new ArrayList<>();
                    modelPenerimaanDetailK = new ArrayList<>();
                    if (modelPenerimaanArrayList==null){
                        modelPenerimaanArrayList = new ArrayList<>();
                    }
                    String logKonversi = "";
                    String logDisKonversi = "";
                    int indexK=0,indexD = 0;
                    int ttlHargaKonversi = 0,ttlHargaDisKonversi = 0,jmlItemK = 0,jmlItemD = 0;
                    for (ApiPenerimaan apiPenerimaan :
                            body) {
//                        Log.d("19201299",
//                                "\n"+apiPenerimaan.getDikonversi()+":id Purchase"+
//                                        apiPenerimaan.getId_purchase()+"("+apiPenerimaan.getId_item()+
//                                        ")"+"=>"+apiPenerimaan.getQty()+"("+apiPenerimaan.getHarga()+")");
                        if (apiPenerimaan.getDikonversi()==1) {
                            loadDaftarPenerimaan.setDataKonversi(apiPenerimaan);
                            if (konversi.get(apiPenerimaan.getId_purchase()) == null) {
                                ttlHargaKonversi = 0;
                                jmlItemK = 0;
                                indexK = (indexK-1)+1;
                                konversi.put(apiPenerimaan.getId_purchase(),
                                        apiPenerimaan.getId_purchase());
                                ttlHargaKonversi =
                                        ttlHargaKonversi + (apiPenerimaan.getQty()*apiPenerimaan.getHarga());
                                jmlItemK ++;
                                ModelPenerimaan modelPenerimaanK =
                                        new ModelPenerimaan(apiPenerimaan.getId_trans(),
                                        apiPenerimaan.getId_detail(), apiPenerimaan.getAdmin(),
                                        apiPenerimaan.getNm_suplier(),apiPenerimaan.getAlasuplier(),
                                        apiPenerimaan.getNote(), apiPenerimaan.getTgl_purchase(),
                                        apiPenerimaan.getId(), apiPenerimaan.getId_purchase(),
                                        apiPenerimaan.getId_area(), apiPenerimaan.getId_item(),
                                        jmlItemD, apiPenerimaan.getId_satuan(),
                                        ttlHargaDisKonversi, apiPenerimaan.getDikonversi(),
                                        apiPenerimaan.getCreated(), apiPenerimaan.getUpdated())
                                        .setQty(apiPenerimaan.getQty()).setHarga(apiPenerimaan.getHarga());
                                modelKonversi.add(modelPenerimaanK);
                                arrItemBrg = new ArrayList<>();
                                arrItemBrg.add(new ModelItemBrg(0, apiPenerimaan.getId_item(), apiPenerimaan.getQty(),
                                        apiPenerimaan.getId_satuan(), apiPenerimaan.getHarga(), apiPenerimaan.getJumlah(),
                                        apiPenerimaan.getNm_item(), apiPenerimaan.getEceran(),apiPenerimaan.getNm_satuan()));
                                modelPenerimaanDetailK.add(modelPenerimaanK.setModelItemBrgs(arrItemBrg));
//                                Log.d("19201299",
//                                        "<<321>>Qty Variable: ("+apiPenerimaan.getId_purchase()+
//                                                ")"+jmlItemK);
//                                Log.d("19201299",
//                                        "<<323>>Qty Api Penerimaan: ("+apiPenerimaan.getId_purchase()+")"+apiPenerimaan.getQty());
//                                Log.d("19201299",
//                                        "<<325>>QTY Model: ("+modelKonversi.get(indexK).getId_purchase()+")"+modelKonversi.get(indexK).getQty());
                                logKonversi += apiPenerimaan.getId_purchase() + "=> id item " + apiPenerimaan.getId_item() + "\n";
                            }else{
                                arrItemBrg = new ArrayList<>();
                                arrItemBrg = modelPenerimaanDetailK.get(indexK).getModelItemBrgs();
                                arrItemBrg.add(new ModelItemBrg(0, apiPenerimaan.getId_item(), apiPenerimaan.getQty(),
                                        apiPenerimaan.getId_satuan(), apiPenerimaan.getHarga(), apiPenerimaan.getJumlah(),
                                        apiPenerimaan.getNm_item(), apiPenerimaan.getEceran(),apiPenerimaan.getNm_satuan()));
                                modelPenerimaanDetailK.set(indexK,
                                        modelPenerimaanDetailK.get(indexK).setModelItemBrgs(arrItemBrg));

                                ttlHargaKonversi =
                                        ttlHargaKonversi + (apiPenerimaan.getQty()*apiPenerimaan.getHarga());
                                jmlItemK++;
                                modelKonversi.set(indexK,modelKonversi.get(indexK)
                                        .setHarga_total(ttlHargaKonversi)
                                        .setTtlQty(jmlItemK)
                                );

                            }
                        }else{
                            loadDaftarPenerimaan.setDataDiskonversi(apiPenerimaan);
                            if (disKonversi.get(apiPenerimaan.getId_purchase()) == null) {
                                ttlHargaDisKonversi = 0;
                                jmlItemD = 0;
                                indexD = (indexD-1)+1;
                                disKonversi.put(apiPenerimaan.getId_purchase(),
                                        apiPenerimaan.getId_purchase());
                                ttlHargaDisKonversi =
                                        ttlHargaDisKonversi + (apiPenerimaan.getQty()*apiPenerimaan.getHarga());
                                jmlItemD++;
                                ModelPenerimaan modelPenerimaanD = new ModelPenerimaan(apiPenerimaan.getId_trans(),
                                        apiPenerimaan.getId_detail(), apiPenerimaan.getAdmin(),
                                        apiPenerimaan.getNm_suplier(),apiPenerimaan.getAlasuplier(),
                                        apiPenerimaan.getNote(), apiPenerimaan.getTgl_purchase(),
                                        apiPenerimaan.getId(), apiPenerimaan.getId_purchase(),
                                        apiPenerimaan.getId_area(), apiPenerimaan.getId_item(),
                                        jmlItemD, apiPenerimaan.getId_satuan(),
                                        ttlHargaDisKonversi, apiPenerimaan.getDikonversi(),
                                        apiPenerimaan.getCreated(), apiPenerimaan.getUpdated())
                                        .setQty(apiPenerimaan.getQty()).setHarga(apiPenerimaan.getHarga());
                                modelDisKonversi.add(modelPenerimaanD);
                                arrItemBrg = new ArrayList<ModelItemBrg>();
                                arrItemBrg.add(new ModelItemBrg(0, apiPenerimaan.getId_item(), apiPenerimaan.getQty(),
                                        apiPenerimaan.getId_satuan(), apiPenerimaan.getHarga(), apiPenerimaan.getJumlah(),
                                        apiPenerimaan.getNm_item(), apiPenerimaan.getEceran(),apiPenerimaan.getNm_satuan()));
                                modelPenerimaanDetail.add(modelPenerimaanD.setModelItemBrgs(arrItemBrg));
//                                Log.d("19201299",
//                                        "onResponse: ("+apiPenerimaan.getId_purchase()+")"+apiPenerimaan.getQty());
//                                Log.d("19201299",
//                                        "<<361>>onResponse: ("+modelDisKonversi.get(indexD).getId_purchase()+")"+modelDisKonversi.get(indexD).getQty());
                                logDisKonversi += apiPenerimaan.getId_purchase() + "=> id item " + apiPenerimaan.getId_item() + "\n";
                            }else{
                                arrItemBrg = new ArrayList<>();
                                arrItemBrg = modelPenerimaanDetail.get(indexD).getModelItemBrgs();
                                arrItemBrg.add(new ModelItemBrg(0, apiPenerimaan.getId_item(), apiPenerimaan.getQty(),
                                        apiPenerimaan.getId_satuan(), apiPenerimaan.getHarga(), apiPenerimaan.getJumlah(),
                                        apiPenerimaan.getNm_item(), apiPenerimaan.getEceran(),apiPenerimaan.getNm_satuan()));
                                modelPenerimaanDetail.set(indexD,
                                        modelPenerimaanDetail.get(indexD).setModelItemBrgs(arrItemBrg));

                                ttlHargaDisKonversi =
                                        ttlHargaDisKonversi + (apiPenerimaan.getQty()*apiPenerimaan.getHarga());
                                jmlItemD++;
                                modelDisKonversi.set(indexD,modelDisKonversi.get(indexD)
                                        .setHarga_total(ttlHargaDisKonversi)
                                        .setTtlQty(jmlItemD)
                                );

                            }
                        }
                    }
//                    Log.d("19201299", "\nKonversi("+modelKonversi.size()+"): "+logKonversi);
//                    Log.d("19201299","\nDisKonversi("+modelDisKonversi.size()+"): "+logDisKonversi);

                    loadDaftarPenerimaan.setSize(LoadDaftarPenerimaan.RECEIVE,(konversi.size()+disKonversi.size()));
                    loadDaftarPenerimaan.setSize(LoadDaftarPenerimaan.KONVERSI, konversi.size());
                    loadDaftarPenerimaan.setSize(LoadDaftarPenerimaan.DISKONVERSI, disKonversi.size());
                    for (ModelPenerimaan modelPenerimaan:modelKonversi){
                        modelDisKonversi.add(modelPenerimaan);
                    }
                    for (ModelPenerimaan modelPenerimaank:modelPenerimaanDetailK){
                        modelPenerimaanDetail.add(modelPenerimaank);
                    }

                    modelDisKonversi.add(null);
                    Log.d("19201299",
                            "<<444>>Jumlah Purchase yang sudah diterima("+modelDisKonversi.size()+
                                    ")");
//                    Log.d("19201299", "onResponse: "+modelDisKonversi.get(0).getTtlQty());
//                    Log.d("19201299", "onResponse: "+modelDisKonversi.get(0).getId_purchase());
//                    modelPenerimaanDetail.add(null);
                    for (ModelPenerimaan modelPenerimaan:modelDisKonversi){
                        modelPenerimaanArrayList.add(modelPenerimaan);
                    }
                    {
                        adapter = new AdapterPenerimaan2(modelPenerimaanArrayList, isDataPenerimaan,
                                PenerimaanFragment.this);
                        recyclerPenerimaan.setLayoutManager(new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL, false));
                        recyclerPenerimaan.setHasFixedSize(true);
                        recyclerPenerimaan.setItemAnimator(new DefaultItemAnimator());

                        recyclerPenerimaan.setAdapter(adapter);
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        recyclerPenerimaan.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
//                    tv_lblDtKosong.setVisibility(View.VISIBLE);
                    Toast.makeText(loadDaftarPenerimaan.getCtx(), "Gagal terhubung ke server",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        loadCards(detailUserInt.get(SessionManager.USER_ID));
    }
}