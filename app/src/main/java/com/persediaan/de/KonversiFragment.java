package com.persediaan.de;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.adapter.AdapterItemsKonversi;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiKonversi;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelItemsKonv;
import com.persediaan.de.model.ModelKonversi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonversiFragment extends Fragment implements RecyclerViewClickInterface {
    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    SessionManager sessionManagerProfil;
    HashMap<String, Integer> detailUser;
    TextView tv_no_konversi, tvTglBarang, tvNmItem, tvNmSatuan, tvQty;
    RecyclerView recycleKonversi;
    AutoCompleteTextView autoCompleteNoReceipt;
    CardView crdListBarang;
    ArrayAdapter<String> adapterItems;

    public KonversiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_konversi, container, false);
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        sessionManagerProfil = new SessionManager(requireContext(),"login");
        detailUser = sessionManagerProfil.getUserDetailInt();

        tv_no_konversi = view.findViewById(R.id.tvno_konversi);
        Call<String> callNoKonversi = jsonPlaceHolderApi.getResponNoKonversi(String.valueOf(detailUser.get(SessionManager.USER_ID)));
        callNoKonversi.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String no_konversi = response.body();
                tv_no_konversi.setText(no_konversi);

                if (!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        crdListBarang = view.findViewById(R.id.crdListBarang);
        autoCompleteNoReceipt = view.findViewById(R.id.autoCompleteNo_receipt);
        tvTglBarang = view.findViewById(R.id.tvTglBarang);
        tvNmItem = view.findViewById(R.id.tvNmItem);
        tvNmSatuan = view.findViewById(R.id.tvNmSatuan);
        tvQty = view.findViewById(R.id.tvQty);
        recycleKonversi = view.findViewById(R.id.recycleKonversi);
        Call<List<ApiKonversi>> callNoReceipt = jsonPlaceHolderApi.getResponNoReceipt(detailUser.get(SessionManager.USER_ID));
        callNoReceipt.enqueue(new Callback<List<ApiKonversi>>() {
            @Override
            public void onResponse(Call<List<ApiKonversi>> call, Response<List<ApiKonversi>> response) {
                List<ApiKonversi> noReceipts = response.body();
                ArrayList<String> MdlNoReceipt = new ArrayList<>();
                for (ApiKonversi arr:noReceipts){
                    MdlNoReceipt.add(arr.getId_trans());
                }
                adapterItems = new ArrayAdapter<String>(getActivity(),R.layout.list_item, MdlNoReceipt);
                autoCompleteNoReceipt.setAdapter(adapterItems);
                autoCompleteNoReceipt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String id_trans = parent.getItemAtPosition(position).toString();
                        if(id_trans != null) {
                            crdListBarang.setVisibility(View.VISIBLE);
                            Call<List<ApiKonversi>> callItems = jsonPlaceHolderApi.getResponItems(id_trans);
                            callItems.enqueue(new Callback<List<ApiKonversi>>() {
                                @Override
                                public void onResponse(Call<List<ApiKonversi>> call, Response<List<ApiKonversi>> response) {
                                    List<ApiKonversi> Items = response.body();
                                    ArrayList<ModelItemsKonv> listItems = new ArrayList<>();
                                    for(ApiKonversi arr:Items){
                                        listItems.add(new ModelItemsKonv(
                                            arr.getId_detail(),
                                            arr.getId_purchase(),
                                            arr.getNm_item(),
                                            arr.getNm_area(),
                                            arr.getNm_singkat(),
                                            arr.getNm_satuan(),
                                            arr.getEceran(),
                                            arr.getId(),
                                            arr.getId_area(),
                                            arr.getId_item(),
                                            arr.getQty(),
                                            arr.getId_satuan(),
                                            arr.getHarga(),
                                            arr.getDikonversi(),
                                            arr.getCreated(),
                                            arr.getUpdated(),
                                            arr.getJumlah()
                                        ));
                                        listItems.add(new ModelItemsKonv(
                                                arr.getId_detail(),
                                                arr.getId_purchase(),
                                                arr.getNm_item(),
                                                arr.getNm_area(),
                                                arr.getNm_singkat(),
                                                arr.getNm_satuan(),
                                                arr.getEceran(),
                                                arr.getId(),
                                                arr.getId_area(),
                                                arr.getId_item(),
                                                arr.getQty(),
                                                arr.getId_satuan(),
                                                arr.getHarga(),
                                                arr.getDikonversi(),
                                                arr.getCreated(),
                                                arr.getUpdated(),
                                                arr.getJumlah()
                                        ));
                                        listItems.add(new ModelItemsKonv(
                                                arr.getId_detail(),
                                                arr.getId_purchase(),
                                                arr.getNm_item(),
                                                arr.getNm_area(),
                                                arr.getNm_singkat(),
                                                arr.getNm_satuan(),
                                                arr.getEceran(),
                                                arr.getId(),
                                                arr.getId_area(),
                                                arr.getId_item(),
                                                arr.getQty(),
                                                arr.getId_satuan(),
                                                arr.getHarga(),
                                                arr.getDikonversi(),
                                                arr.getCreated(),
                                                arr.getUpdated(),
                                                arr.getJumlah()
                                        ));
                                        listItems.add(new ModelItemsKonv(
                                                arr.getId_detail(),
                                                arr.getId_purchase(),
                                                arr.getNm_item(),
                                                arr.getNm_area(),
                                                arr.getNm_singkat(),
                                                arr.getNm_satuan(),
                                                arr.getEceran(),
                                                arr.getId(),
                                                arr.getId_area(),
                                                arr.getId_item(),
                                                arr.getQty(),
                                                arr.getId_satuan(),
                                                arr.getHarga(),
                                                arr.getDikonversi(),
                                                arr.getCreated(),
                                                arr.getUpdated(),
                                                arr.getJumlah()
                                        ));
                                        listItems.add(new ModelItemsKonv(
                                                arr.getId_detail(),
                                                arr.getId_purchase(),
                                                arr.getNm_item(),
                                                arr.getNm_area(),
                                                arr.getNm_singkat(),
                                                arr.getNm_satuan(),
                                                arr.getEceran(),
                                                arr.getId(),
                                                arr.getId_area(),
                                                arr.getId_item(),
                                                arr.getQty(),
                                                arr.getId_satuan(),
                                                arr.getHarga(),
                                                arr.getDikonversi(),
                                                arr.getCreated(),
                                                arr.getUpdated(),
                                                arr.getJumlah()
                                        ));
                                        listItems.add(new ModelItemsKonv(
                                                arr.getId_detail(),
                                                arr.getId_purchase(),
                                                arr.getNm_item(),
                                                arr.getNm_area(),
                                                arr.getNm_singkat(),
                                                arr.getNm_satuan(),
                                                arr.getEceran(),
                                                arr.getId(),
                                                arr.getId_area(),
                                                arr.getId_item(),
                                                arr.getQty(),
                                                arr.getId_satuan(),
                                                arr.getHarga(),
                                                arr.getDikonversi(),
                                                arr.getCreated(),
                                                arr.getUpdated(),
                                                arr.getJumlah()
                                        ));
                                    }
                                    AdapterItemsKonversi cycleItems;
                                    for(int i = 0; i < listItems.size(); i++)
                                    {
                                        String date = String.valueOf(listItems.get(0).getCreated());
                                        Date expiry = new Date(Long.parseLong(date) * 1000);
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                                        tvTglBarang.setText(sdf.format(expiry));
                                    }
                                    cycleItems = new AdapterItemsKonversi(listItems, KonversiFragment.this);
                                    recycleKonversi.setLayoutManager(new LinearLayoutManager(getContext(),
                                            LinearLayoutManager.VERTICAL, false));
                                    recycleKonversi.setHasFixedSize(true);
                                    recycleKonversi.setItemAnimator(new DefaultItemAnimator());
                                    recycleKonversi.setAdapter(cycleItems);

                                    if (!response.isSuccessful()){
                                        Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ApiKonversi>> call, Throwable t) {
                                    Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

                if (!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<ApiKonversi>> call, Throwable t) {
                Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onItemClick(int position, View view) {

    }
}