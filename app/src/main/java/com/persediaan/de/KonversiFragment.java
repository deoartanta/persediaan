package com.persediaan.de;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.textfield.TextInputEditText;
import com.persediaan.de.adapter.AdapterCartKonversi;
import com.persediaan.de.adapter.AdapterItemsKonversi;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.adapter.RecyclerViewKonversiInterface;
import com.persediaan.de.api.ApiKonversi;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelCartKonv;
import com.persediaan.de.model.ModelItemsKonv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonversiFragment extends Fragment implements RecyclerViewClickInterface, RecyclerViewKonversiInterface {
    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    SessionManager sessionManagerProfil;
    HashMap<String, Integer> detailUser;
    TextView tv_no_konversi, tvTglBarang, tvNmItem, tvNmSatuan, tvQty, tvNmSatuanEdt, tvNmEceranEdt;
    TextInputEditText edtQtySatu, edtQtyEcer;
    RecyclerView recycleKonversi, recycleHasilKonversi;
    AutoCompleteTextView autoCompleteNoReceipt;
    CardView crdListBarang, crdHasilKonversi;
    ArrayAdapter<String> adapterItems;
    ArrayList<ModelItemsKonv> listItems;
    ArrayList<ModelCartKonv> listCart;
    LinearLayout footBtn, lyt_qty, lyt_sisa;
    ImageButton btnEdtKonversi, btnAddKonversi;
    Button btnCancelKonversi, btnSimpanKonversi;
    MeowBottomNavigation meowBottomNavigation;

    public KonversiFragment(MeowBottomNavigation meowBottomNavigation) {
        this.meowBottomNavigation = meowBottomNavigation;
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
        footBtn = view.findViewById(R.id.footBtn);
        btnEdtKonversi = view.findViewById(R.id.btnEdtKonversi);
        btnAddKonversi = view.findViewById(R.id.btnAddKonversi);
        crdListBarang = view.findViewById(R.id.crdListBarang);
        crdHasilKonversi = view.findViewById(R.id.cardTransferDetail);
        autoCompleteNoReceipt = view.findViewById(R.id.autoCompleteNo_receipt);
        tvTglBarang = view.findViewById(R.id.tvTglBarang);
        tvNmItem = view.findViewById(R.id.tvNmItem);
        tvNmSatuan = view.findViewById(R.id.tvNmSatuan);
        tvQty = view.findViewById(R.id.tvQty);
        recycleKonversi = view.findViewById(R.id.recycleKonversi);
        recycleHasilKonversi = view.findViewById(R.id.recyclerTransferDetail);
        btnCancelKonversi = view.findViewById(R.id.btnCancelKonversi);
        btnSimpanKonversi = view.findViewById(R.id.btnSimpanKonversi);

        cek_cart();

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
                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<ApiKonversi>> callNoReceipt = jsonPlaceHolderApi.getResponNoReceipt(detailUser.get(SessionManager.USER_ID));
        callNoReceipt.enqueue(new Callback<List<ApiKonversi>>() {
            @Override
            public void onResponse(Call<List<ApiKonversi>> call, Response<List<ApiKonversi>> response) {
                List<ApiKonversi> noReceipts = response.body();
                ArrayList<String> MdlNoReceipt = new ArrayList<>();
                for (ApiKonversi arr:noReceipts){
                    MdlNoReceipt.add(arr.getId_trans());
                }
                if(noReceipts.get(0).getMsg() != null){
                    autoCompleteNoReceipt.setText("Tidak Ada No Receipt");
                } else {
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
                                        listItems = new ArrayList<>();
                                        int ii= 1;
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
                                        }
                                        AdapterItemsKonversi cycleItems;
                                        for(int i = 0; i < listItems.size(); i++)
                                        {
                                            String date = String.valueOf(listItems.get(0).getCreated());
                                            Date expiry = new Date(Long.parseLong(date) * 1000);
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                                            tvTglBarang.setText(sdf.format(expiry));
                                        }
                                        cycleItems = new AdapterItemsKonversi(requireContext(), listItems, KonversiFragment.this);
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
                                        Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<ApiKonversi>> call, Throwable t) {
                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancelKonversi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> cancelKonv = jsonPlaceHolderApi.getCancelKonversi(String.valueOf(detailUser.get(SessionManager.USER_ID)));
                cancelKonv.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String dt_konv = response.body();
                        if(dt_konv != null){
                            Toast.makeText(getContext(), "Cart Konversi dihapus", Toast.LENGTH_SHORT).show();
                            meowBottomNavigation.show(4,false);
                        }
                        if(!response.isSuccessful()){
                            Toast.makeText(getContext(), "Gagal hapus cart Konversi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSimpanKonversi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ApiKonversi> saveKonv = jsonPlaceHolderApi.getSimpanKonversi(String.valueOf(detailUser.get(SessionManager.USER_ID)));
                saveKonv.enqueue(new Callback<ApiKonversi>() {
                    @Override
                    public void onResponse(Call<ApiKonversi> call, Response<ApiKonversi> response) {
                        ApiKonversi tersimpan = response.body();
                        if(tersimpan != null){
                            Toast.makeText(getContext(), "Berhasil simpan data", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    meowBottomNavigation.show(4,false);
                                }
                            }, 500);
                        }else {
                            Toast.makeText(getContext(), "Gagal simpan data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiKonversi> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void AddCartKonversi(View view, int Position) {
        edtQtySatu = view.findViewById(R.id.edtQtySatu);
        edtQtyEcer = view.findViewById(R.id.edtQtyEcer);
        lyt_qty = view.findViewById(R.id.lytQty);
        lyt_sisa = view.findViewById(R.id.lytSisa);
        btnEdtKonversi = view.findViewById(R.id.btnEdtKonversi);
        btnAddKonversi = view.findViewById(R.id.btnAddKonversi);
        int id_user = detailUser.get(SessionManager.USER_ID);
        int qty = Integer.valueOf(String.valueOf(edtQtySatu.getText()));
        int sisa;
        if(edtQtyEcer.getText().toString().isEmpty()) {
            sisa = 0;
        } else {
            sisa = Integer.valueOf(String.valueOf(edtQtyEcer.getText()));
        }
        Call<ApiKonversi> addItems = jsonPlaceHolderApi.getAddCartKonversi(
                id_user,
                listItems.get(Position).getId(),
                qty,
                sisa

        );
        addItems.enqueue(new Callback<ApiKonversi>() {
            @Override
            public void onResponse(Call<ApiKonversi> call, Response<ApiKonversi> response) {
                ApiKonversi Items = response.body();
                if(Items != null){
                    lyt_qty.setVisibility(View.GONE);
                    lyt_sisa.setVisibility(View.GONE);
                    btnAddKonversi.setVisibility(View.GONE);
                    btnEdtKonversi.setVisibility(View.VISIBLE);
                    cek_cart();
                }
            }

            @Override
            public void onFailure(Call<ApiKonversi> call, Throwable t) {
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void DelCartKonversi(View view, int Position, int Id) {
        Call<Integer> delCart = jsonPlaceHolderApi.getDeleteCartKonversi(Id);
        delCart.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int delItm = response.body();
                if(delItm != 0){
                    Toast.makeText(getContext(), "Item berhasil dihapus", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            meowBottomNavigation.show(4,false);
                        }
                    }, 500);
                } else {
                    Toast.makeText(getContext(), "Gagal hapus item", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cek_cart(){
        Call<List<ApiKonversi>> cartKonversi = jsonPlaceHolderApi.getShowCartKonversi(detailUser.get(SessionManager.USER_ID));
        cartKonversi.enqueue(new Callback<List<ApiKonversi>>() {
            @Override
            public void onResponse(Call<List<ApiKonversi>> call, Response<List<ApiKonversi>> response) {
                List<ApiKonversi> Cart = response.body();
                if(Cart.get(0).getMsg() == null){
                    crdHasilKonversi.setVisibility(View.VISIBLE);
                    footBtn.setVisibility(View.VISIBLE);
                    listCart = new ArrayList<>();
                    for(ApiKonversi arr:Cart){
                        listCart.add(new ModelCartKonv(
                                arr.getId(),
                                arr.getDt_conv(),
                                arr.getId_area(),
                                arr.getId_receipt(),
                                arr.getId_item(),
                                arr.getQty_asal(),
                                arr.getQty_conv(),
                                arr.getId_satuan(),
                                arr.getHarga(),
                                arr.getUpdated(),
                                arr.getNm_item(),
                                arr.getNm_area(),
                                arr.getNm_singkat(),
                                arr.getNm_satuan(),
                                arr.getJumlah(),
                                arr.getEceran(),
                                arr.getId_trans(),
                                arr.getAdmin(),
                                arr.getNote(),
                                arr.getTgl_trans()));
                    }

                    if(listCart.get(0).getId_receipt() != null){
                        crdListBarang.setVisibility(View.VISIBLE);
                        autoCompleteNoReceipt.setText(listCart.get(0).getId_receipt());
                        Call<List<ApiKonversi>> callItems = jsonPlaceHolderApi.getResponItems(listCart.get(0).getId_receipt());
                        callItems.enqueue(new Callback<List<ApiKonversi>>() {
                            @Override
                            public void onResponse(Call<List<ApiKonversi>> call, Response<List<ApiKonversi>> response) {
                                List<ApiKonversi> Items = response.body();
                                listItems = new ArrayList<>();
                                int ii= 1;
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
                                }
                                AdapterItemsKonversi cycleItems;
                                for(int i = 0; i < listItems.size(); i++)
                                {
                                    String date = String.valueOf(listItems.get(0).getCreated());
                                    Date expiry = new Date(Long.parseLong(date) * 1000);
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                                    tvTglBarang.setText(sdf.format(expiry));
                                }
                                cycleItems = new AdapterItemsKonversi(requireContext(), listItems, KonversiFragment.this);
                                recycleKonversi.setLayoutManager(new LinearLayoutManager(getContext(),
                                        LinearLayoutManager.VERTICAL, false));
                                recycleKonversi.setHasFixedSize(true);
                                recycleKonversi.setItemAnimator(new DefaultItemAnimator());
                                recycleKonversi.setAdapter(cycleItems);
                            }

                            @Override
                            public void onFailure(Call<List<ApiKonversi>> call, Throwable t) {

                            }
                        });
                    }

                    AdapterCartKonversi cycleItems;
                    cycleItems = new AdapterCartKonversi(requireContext(), listCart, KonversiFragment.this);
                    recycleHasilKonversi.setLayoutManager(new LinearLayoutManager(getContext(),
                            LinearLayoutManager.VERTICAL, false));
                    recycleHasilKonversi.setHasFixedSize(true);
                    recycleHasilKonversi.setItemAnimator(new DefaultItemAnimator());
                    recycleHasilKonversi.setAdapter(cycleItems);
                } else {
                    Toast.makeText(getContext(), "Cart Kosong", Toast.LENGTH_SHORT).show();
                    crdHasilKonversi.setVisibility(View.GONE);
                    footBtn.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ApiKonversi>> call, Throwable t) {
//                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onItemClick1(int position, View view) {

    }
}