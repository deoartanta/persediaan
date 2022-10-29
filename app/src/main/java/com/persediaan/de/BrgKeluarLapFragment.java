package com.persediaan.de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.persediaan.de.adapter.AdapterLapTransfer;
import com.persediaan.de.adapter.AdapterTransferDetail;
import com.persediaan.de.api.ApiDaftarGudang;
import com.persediaan.de.api.ApiTransferDetail;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.api.data.ApiResponListener;
import com.persediaan.de.api.data.LoadDaftarGudang;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelLapTransfer;
import com.persediaan.de.model.ModelTransferDetail;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BrgKeluarLapFragment extends Fragment {
    RecyclerView recycler_lap_transfer;
    ArrayList<ModelTransferDetail> modelTransferDetails;
    ArrayList<ModelLapTransfer> modelLapTransfers;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    Retrofit retrofit;
    SessionManager sessionUser;
    HashMap<String,Integer> detailUserInt;
    LoadDaftarGudang loadDaftarGudang;

//    Adapter
    AdapterLapTransfer adapterLapTransfer;

    private int id_user;

    public BrgKeluarLapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SessionManager transtition;
        transtition = new SessionManager(requireContext(),"transtition");
//        transtition.setTranstition("laptransfer",true);
        View view = inflater.inflate(R.layout.fragment_brg_keluar_lap, container, false);

        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        sessionUser = new SessionManager(requireContext(),"login");
        detailUserInt = sessionUser.getUserDetailInt();
        id_user = detailUserInt.get(SessionManager.USER_ID);
        recycler_lap_transfer = view.findViewById(R.id.recycleLapTransfer);

        loadDaftarGudang = new LoadDaftarGudang(id_user,
                retrofit,jsonPlaceHolderApi);
        loadDaftarGudang.loadData();
        loadDaftarGudang.setApiResponListener(new ApiResponListener<ArrayList<ApiDaftarGudang>>() {
            @Override
            public void onResponse(boolean status, Response<ArrayList<ApiDaftarGudang>> body) {

            }

            @Override
            public void onResponse(boolean status, ArrayList<ApiDaftarGudang> body) {
                LoadCard1();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return view;
    }
    public void LoadCard1(){
        modelTransferDetails = new ArrayList<>();
        modelLapTransfers = new ArrayList<>();

        modelTransferDetails.add(new ModelTransferDetail(
                loadDaftarGudang.getNmArea(19),
                50,174,"Pack","Stop Map Folio Diamond 2006","Jakarta",
                "JKT-0005-22"));
        modelTransferDetails.add(new ModelTransferDetail(
                loadDaftarGudang.getNmArea(19),
                50,175,"Buah","LAKBAN COKLAT BESAR","Jakarta",
                "JKT-0005-22"));
        modelTransferDetails.add(new ModelTransferDetail(
                loadDaftarGudang.getNmArea(19),
                50,174,"Pack","Stop Map Folio Diamond","Jakarta",
                "JKT-0005-22"));

        modelLapTransfers.add(new ModelLapTransfer(
                "JKT-0005-22","Jakarta",
                loadDaftarGudang.getNmArea(19),modelTransferDetails.size(),10000000,
                1654102800));
        modelLapTransfers.add(new ModelLapTransfer(
                "JKT-0006-22","Jakarta",
                loadDaftarGudang.getNmArea(19),modelTransferDetails.size(),10000000,
                1654102800));
        modelLapTransfers.add(new ModelLapTransfer(
                "JKT-0007-22","Jakarta",
                loadDaftarGudang.getNmArea(19),modelTransferDetails.size(),10000000,
                1654102800));

//        Log.d("19201299", "LoadCard1[BrgKeluarLapFragment <96>]: "+loadDaftarGudang.toString());
        adapterLapTransfer = new AdapterLapTransfer(modelLapTransfers);
//                card_tranfer_detail.setVisibility(View.VISIBLE);
        recycler_lap_transfer.setVisibility(View.VISIBLE);
        recycler_lap_transfer.setAdapter(adapterLapTransfer);
        recycler_lap_transfer.setLayoutManager(new LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL,false));
        recycler_lap_transfer.setHasFixedSize(true);
        recycler_lap_transfer.setItemAnimator(new DefaultItemAnimator());
    }
//    public void LoadCard (){
//        recycler_lap_transfer.setAdapter(null);
//
//        modelTransferDetails = new ArrayList<>();
//        Call<ArrayList<ApiTransferDetail>> call = jsonPlaceHolderApi.getTransferCart(id_user);
//        call.enqueue(new Callback<ArrayList<ApiTransferDetail>>() {
//            @Override
//            public void onResponse(Call<ArrayList<ApiTransferDetail>> call, Response<ArrayList<ApiTransferDetail>> response) {
//                if (!response.isSuccessful()){
//                    Toast.makeText(requireContext(),
//                            "Terjadi error yang tidak diketahui["+response.code()+
//                                    "]",
//                            Toast.LENGTH_SHORT).show();
//                    return;
//                }
////                if(!session_manualBook.getManualBook(SessionManager.TRANSFER)){
////                    openManualBook();
////                }
//                for (ApiTransferDetail apiTransferDetail: response.body()) {
//                    modelTransferDetails.add(new ModelTransferDetail(
//                            loadDaftarGudang.getNmArea(apiTransferDetail.getGudang_tujuan()),
//                            apiTransferDetail.getQty_peng(),
//                            apiTransferDetail.getId_peng(),
//                            apiTransferDetail.getEceran(),
//                            apiTransferDetail.getNm_item(),
//                            apiTransferDetail.getNm_area(),
//                            apiTransferDetail.getDt_keluar()));
//                }
//
//                adapterLapTransfer = new AdapterLapTransfer(modelTransferDetails);
////                card_tranfer_detail.setVisibility(View.VISIBLE);
//                recycler_lap_transfer.setVisibility(View.VISIBLE);
//                recycler_lap_transfer.setAdapter(adapterLapTransfer);
//                recycler_lap_transfer.setLayoutManager(new LinearLayoutManager(requireContext(),
//                        LinearLayoutManager.VERTICAL,false));
//                recycler_lap_transfer.setHasFixedSize(true);
//                recycler_lap_transfer.setItemAnimator(new DefaultItemAnimator());
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<ApiTransferDetail>> call, Throwable t) {
//                Log.d("19201299",
//                        "onFailure(id User :"+id_user+"): BrgKeluarLapFragment[135]"+t.getMessage());
//                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}