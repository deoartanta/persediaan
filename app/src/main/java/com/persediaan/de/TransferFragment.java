package com.persediaan.de;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.persediaan.de.adapter.AdapterItemGudang;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiDaftarGudang;
import com.persediaan.de.api.ApiItemGudang;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelItemGudang;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferFragment extends Fragment implements RecyclerViewClickInterface {

    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionUser;
    HashMap<String,Integer> detailUserInt;

    AdapterItemGudang adapterItemGudang;

    TextView tv_no_pengeluaran;
    TextView tv_nm_item;
    AutoCompleteTextView  daftar_gudang;

    AlertDialog.Builder dialogBrg;
    AlertDialog dialog;

    ArrayAdapter<String> listGudangAdapter;
    HashMap<String,Integer> modelDaftarGudang;

    DialogInterface dialogInterface;

    Button btn_pilih_item;

    EditText edt_qty_satu,edt_qty_ecer;

    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_transfer, container, false);
        tv_no_pengeluaran = view.findViewById(R.id.tvNoPengeluaran);
        tv_nm_item = view.findViewById(R.id.tvNmItem);
        edt_qty_ecer = view.findViewById(R.id.edtQtyEcer);
        edt_qty_satu = view.findViewById(R.id.edtQtySatu);

        daftar_gudang = view.findViewById(R.id.autoCompleteG_tujuan);

        btn_pilih_item = view.findViewById(R.id.btnPilihBarang);
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        sessionUser = new SessionManager(requireContext(),"login");

        detailUserInt = sessionUser.getUserDetailInt();

        loadDaftarGudang(detailUserInt.get(SessionManager.USER_ID),daftar_gudang);

        Call<String> call =
                jsonPlaceHolderApi.getResponNoTransfer(detailUserInt.get(SessionManager.USER_ID));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv_no_pengeluaran.setText(response.body());

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        btn_pilih_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater viewInflater = getLayoutInflater();
                View itemGudangView = viewInflater.inflate(R.layout.recycle_item_gudang,
                        null);
                RecyclerView recyclerViewItemGudang =
                        itemGudangView.findViewById(R.id.recycleItemGudang);
                Call<ArrayList<ApiItemGudang>> call =
                        jsonPlaceHolderApi.getItemGudang(detailUserInt.get(SessionManager.USER_ID));
                call.enqueue(new Callback<ArrayList<ApiItemGudang>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ApiItemGudang>> call, Response<ArrayList<ApiItemGudang>> response) {
                        ArrayList<ModelItemGudang> modelItemGudangs = new ArrayList<>();
                        for (ApiItemGudang ApiItemGudang:response.body()){
                            modelItemGudangs.add(new ModelItemGudang(ApiItemGudang.getNm_item(),
                                    ApiItemGudang.getSisa(),ApiItemGudang.getNm_area()));
                        }
                        adapterItemGudang = new AdapterItemGudang(modelItemGudangs,
                                TransferFragment.this);
                        recyclerViewItemGudang.setAdapter(adapterItemGudang);
                        recyclerViewItemGudang.setLayoutManager( new LinearLayoutManager(getContext(),
                                LinearLayoutManager.VERTICAL,false));
                        recyclerViewItemGudang.setHasFixedSize(true);
                        recyclerViewItemGudang.setItemAnimator(new DefaultItemAnimator());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ApiItemGudang>> call, Throwable t) {

                    }
                });

                dialogBrg = new AlertDialog.Builder(requireContext())
                        .setTitle("Pilih Barang ")
                        .setView(itemGudangView)
                        .setCancelable(false)
//                        .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                dialog = dialogBrg.create();
                dialog.show();
            }
        });
        return view;
    }

    public void loadDaftarGudang(int id_user, AutoCompleteTextView autoCompleteTextView){
        Call<ArrayList<ApiDaftarGudang>> call=jsonPlaceHolderApi.getDaftarGudang(id_user);
        call.enqueue(new Callback<ArrayList<ApiDaftarGudang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarGudang>> call, Response<ArrayList<ApiDaftarGudang>> response) {
                ArrayList<String> arrayListGudang = new ArrayList<>();
                for (ApiDaftarGudang apiDaftarGudang:response.body()){
                    arrayListGudang.add(apiDaftarGudang.getNm_area());
                }
                listGudangAdapter = new ArrayAdapter<>(getActivity(),R.layout.tv_daftar_item,
                        arrayListGudang);
                autoCompleteTextView.setAdapter(listGudangAdapter);
                autoCompleteTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarGudang>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        TextView tv_nm_item =view.findViewById(R.id.tvResNmItem);
        TextView tv_stock =view.findViewById(R.id.tvResStock);
        TextView tv_area =view.findViewById(R.id.tvArea);

        this.tv_nm_item.setText(tv_nm_item.getText());
        dialog.dismiss();

    }

    @Override
    public void onItemClick1(int position, View view) {

    }
}