package com.persediaan.de;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.persediaan.de.adapter.AdapterItemClickListener;
import com.persediaan.de.adapter.AdapterItemGudang;
import com.persediaan.de.adapter.AdapterTransferDetail;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiDaftarGudang;
import com.persediaan.de.api.ApiItemGudang;
import com.persediaan.de.api.ApiTransferDetail;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.api.data.ApiResponListener;
import com.persediaan.de.api.data.LoadDaftarGudang;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelItemGudang;
import com.persediaan.de.model.ModelTransferDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransferFragment extends Fragment implements RecyclerViewClickInterface {

    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    SessionManager sessionUser,session_manualBook;
    HashMap<String,Integer> detailUserInt;
//  Adapter
    AdapterItemGudang adapterItemGudang;
    AdapterTransferDetail adapterTransferDetail;
//    RecycleView
    RecyclerView recycler_transfer_detail;

    TextView tv_no_pengeluaran;
    TextView tv_nm_item;
    TextView tv_satuan;
    TextView tv_eceran;
    AutoCompleteTextView  daftar_gudang;

    AlertDialog.Builder dialogBrg;
    AlertDialog dialog;

    ArrayAdapter<String> listGudangAdapter;
    HashMap<String,Integer> modelDaftarGudang;
    ArrayList<ModelItemGudang> modelItemGudangs;

    DialogInterface dialogInterface;
//    Button
    Button btn_pilih_item, btn_tambah,btn_cancel,btn_simpan;

    EditText edt_qty_satu,edt_qty_ecer;

    CardView cardViewTransfer,
            card_tranfer_detail;
//    Model
    ModelTransferDetail modelTransferDetail;
    ArrayList<ModelTransferDetail> modelTransferDetails;

//    LinearLayout
    LinearLayout linear_btn_transfer;

//Data
    LoadDaftarGudang loadDaftarGudang;

//    Shimmer
    ShimmerFrameLayout shimmer_tab_transfer;
    ScrollView scroll_tab_transfer;
    FrameLayout frame_layout_manual_book;

    public TransferFragment() {
        // Required empty public constructor
    }

    public TransferFragment(FrameLayout frame_layout_manual_book) {
        this.frame_layout_manual_book = frame_layout_manual_book;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_transfer, container, false);
        tv_no_pengeluaran = view.findViewById(R.id.tvNoPengeluaran);
        tv_nm_item = view.findViewById(R.id.tvNmItem);
        tv_satuan = view.findViewById(R.id.tvSatuan);
        tv_eceran = view.findViewById(R.id.tvEceran);

        edt_qty_satu = view.findViewById(R.id.edtQtySatu);
        edt_qty_ecer = view.findViewById(R.id.edtQtyEcer);

        daftar_gudang = view.findViewById(R.id.autoCompleteG_tujuan);
//        Button
        btn_pilih_item = view.findViewById(R.id.btnPilihBarang);
        btn_cancel = view.findViewById(R.id.btnTransferCancel);
        btn_simpan = view.findViewById(R.id.btnTransferSimpan);

//        LinearLayout
        linear_btn_transfer = view.findViewById(R.id.linearBtnTransfer);
        linear_btn_transfer.setVisibility(View.GONE);

//        RecycleView
        recycler_transfer_detail = view.findViewById(R.id.recyclerTransferDetail);

//      Card View
        cardViewTransfer = view.findViewById(R.id.cardTransfer);
        card_tranfer_detail = view.findViewById(R.id.cardTransferDetail);
        card_tranfer_detail.setVisibility(View.GONE);
        cardViewTransfer.setVisibility(View.GONE);

//        Shimmer
        shimmer_tab_transfer = view.findViewById(R.id.shimmerTabTransfer);
        scroll_tab_transfer = view.findViewById(R.id.scrollTabTransfer);
        shimmer_tab_transfer.setVisibility(View.VISIBLE);
        scroll_tab_transfer.setVisibility(View.GONE);

        btn_tambah = view.findViewById(R.id.btnTransferTambah);
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        sessionUser = new SessionManager(requireContext(),"login");
        session_manualBook = new SessionManager(requireContext(),SessionManager.MANUAL_BOOK);

        detailUserInt = sessionUser.getUserDetailInt();

        loadDaftarGudang = new LoadDaftarGudang(detailUserInt.get(SessionManager.USER_ID),
                retrofit,jsonPlaceHolderApi);
        loadDaftarGudang.loadData();


//      Function&Method
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
        btn_pilih_item.setEnabled(false);

//        Listener
        btn_pilih_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater viewInflater = getLayoutInflater();
                ShimmerFrameLayout shimmer_modal_item;
                View itemGudangView = viewInflater.inflate(R.layout.recycle_item_gudang,
                        null);
                shimmer_modal_item = itemGudangView.findViewById(R.id.shimmerMdlItemGudang);
                shimmer_modal_item.setVisibility(View.VISIBLE);
                RecyclerView recyclerViewItemGudang =
                        itemGudangView.findViewById(R.id.recycleItemGudang);
                recyclerViewItemGudang.setVisibility(View.GONE);
                Call<ArrayList<ApiItemGudang>> call =
                        jsonPlaceHolderApi.getItemGudang(detailUserInt.get(SessionManager.USER_ID));
                call.enqueue(new Callback<ArrayList<ApiItemGudang>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ApiItemGudang>> call, Response<ArrayList<ApiItemGudang>> response) {
                        modelItemGudangs = new ArrayList<>();
                        if (response.body().size()<1){
                            System.out.println("Item gudang kosong");
                            Toast.makeText(getContext(), "Item gudang kosong", Toast.LENGTH_SHORT).show();
                        }
                        for (ApiItemGudang ApiItemGudang:response.body()){
                            ModelItemGudang modelItemGudang =
                                    new ModelItemGudang(ApiItemGudang.getNm_item(),
                                    ApiItemGudang.getSisa(),ApiItemGudang.getNm_area());

                            modelItemGudang.setNm_satuan(ApiItemGudang.getNm_satuan());
                            modelItemGudang.setEceran(ApiItemGudang.getEceran());
                            modelItemGudang.setDt_gudang(ApiItemGudang.getDt_gudang());
                            modelItemGudang.setId_item(ApiItemGudang.getId_item());

                            modelItemGudangs.add(modelItemGudang);
                        }
                        shimmer_modal_item.setVisibility(View.GONE);
                        recyclerViewItemGudang.setVisibility(View.VISIBLE);
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
                        shimmer_modal_item.setVisibility(View.GONE);
                        System.out.println("Pilih barang="+t.getMessage());
                        Toast.makeText(getContext(), "Server error(Pilih brg)"+detailUserInt.get(SessionManager.USER_ID),
                                Toast.LENGTH_SHORT).show();
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

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Cancel Data")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setMessage("Semua data transfer akan dibatalkan apakah anda yakin?")
                        .setCancelable(true)
                        .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<ApiTransferDetail> call =
                                        jsonPlaceHolderApi.getTransferCancelCart(modelTransferDetails.get(0).getDt_keluar());
                                call.enqueue(new Callback<ApiTransferDetail>() {
                                    @Override
                                    public void onResponse(Call<ApiTransferDetail> call, Response<ApiTransferDetail> response) {
                                        if (!response.isSuccessful()){
                                            Toast.makeText(requireContext(), "Terjadi error yang tidak " +
                                                    "diketahui["+response.code()+"]", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        refreshPage(detailUserInt.get(SessionManager.USER_ID),daftar_gudang);
                                        if (response.body().isSts()){
                                            Toast.makeText(requireContext(),
                                                    ""+response.body().getMsg(),
                                                    Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(requireContext(), ""+response.body().getMsg(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiTransferDetail> call, Throwable t) {
                                        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(requireContext())
                        .setTitle("Simpan Data")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setMessage("Semua data transfer akan disimpan apakah anda yakin?")
                        .setCancelable(true)
                        .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Call<ApiTransferDetail> call =
                                        jsonPlaceHolderApi.getTransferSimpanCart(
                                                detailUserInt.get(SessionManager.USER_ID),
                                                "note"
                                        );
                                call.enqueue(new Callback<ApiTransferDetail>() {
                                    @Override
                                    public void onResponse(Call<ApiTransferDetail> call, Response<ApiTransferDetail> response) {
                                        if (!response.isSuccessful()){
                                            Toast.makeText(requireContext(), "Terjadi error yang tidak " +
                                                    "diketahui["+response.code()+"]", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        refreshPage(detailUserInt.get(SessionManager.USER_ID),daftar_gudang);
                                        if (response.body().getMsg()==null){
                                            Toast.makeText(requireContext(),
                                                    "Berhasil simpan data",
                                                    Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(requireContext(), ""+response.body().getMsg(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ApiTransferDetail> call, Throwable t) {
                                        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });
        return view;
    }

    private void openManualBook() {
        frame_layout_manual_book.setVisibility(View.VISIBLE);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frameManualBook,new ManualBookFragmentTransfer(frame_layout_manual_book))
                .commit();
    }

    public void loadTransferDetail(int id_user){
        System.out.println("load transfer detail start");
        recycler_transfer_detail.setAdapter(null);

        modelTransferDetails = new ArrayList<>();
        Call<ArrayList<ApiTransferDetail>> call = jsonPlaceHolderApi.getTransferCart(id_user);
        call.enqueue(new Callback<ArrayList<ApiTransferDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiTransferDetail>> call, Response<ArrayList<ApiTransferDetail>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(requireContext(),
                            "Terjadi error yang tidak diketahui["+response.code()+
                                    "]",
                            Toast.LENGTH_SHORT).show();
                    shimmer_tab_transfer.setVisibility(View.GONE);
                    btn_pilih_item.setEnabled(true);
                    return;
                }
                if(!session_manualBook.getManualBook(SessionManager.TRANSFER)){
                    openManualBook();
                }
                for (ApiTransferDetail apiTransferDetail: response.body()) {
                    modelTransferDetails.add(new ModelTransferDetail(
                            loadDaftarGudang.getNmArea(apiTransferDetail.getGudang_tujuan()),
                            apiTransferDetail.getQty_peng(),
                            apiTransferDetail.getId_peng(),
                            apiTransferDetail.getEceran(),
                            apiTransferDetail.getNm_item(),
                            apiTransferDetail.getNm_area(),
                            apiTransferDetail.getDt_keluar()));
                }
                adapterTransferDetail = new AdapterTransferDetail(modelTransferDetails);
                card_tranfer_detail.setVisibility(View.VISIBLE);
                recycler_transfer_detail.setVisibility(View.VISIBLE);
                recycler_transfer_detail.setAdapter(adapterTransferDetail);
                recycler_transfer_detail.setLayoutManager(new LinearLayoutManager(requireContext(),
                        LinearLayoutManager.VERTICAL,false));
                recycler_transfer_detail.setHasFixedSize(true);
                recycler_transfer_detail.setItemAnimator(new DefaultItemAnimator());

                linear_btn_transfer.setVisibility(View.VISIBLE);
                daftar_gudang.setText(modelTransferDetails.get(0).getGudang_tujuan());
                daftar_gudang.setEnabled(false);

                shimmer_tab_transfer.setVisibility(View.GONE);
                scroll_tab_transfer.setVisibility(View.VISIBLE);
//                Log.d("19201299",
//                        "ModelTransferDetail:"+modelTransferDetails.get(0).toString());
                adapterTransferDetail.setAdapterItemClickListener(new AdapterItemClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        TextView tv_gudang_tujuan = view.findViewById(R.id.tvGudangTujuan);
                        TextView tv_gudang_asal = view.findViewById(R.id.tvGudangAsal);
                        new AlertDialog.Builder(requireContext())
                                .setTitle("Hapus Item Transfer")
                                .setIcon(R.drawable.ic_baseline_warning_24)
                                .setMessage("Apakah anda yakin ingin menghapus item transferan " +
                                        "dari " +tv_gudang_asal.getText()+
                                        " ke "+tv_gudang_tujuan.getText()+"?")
                                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Call<ApiTransferDetail> call =
                                                jsonPlaceHolderApi.getTransferDelCart(
                                                        modelTransferDetails.get(position).getId_peng());
                                        call.enqueue(new Callback<ApiTransferDetail>() {
                                            @Override
                                            public void onResponse(Call<ApiTransferDetail> call, Response<ApiTransferDetail> response) {
                                                if(!response.isSuccessful()){
                                                    Log.d("19201299",
                                                            "onResponse: ["+response.code()+"]" +
                                                            " "+response.message());
                                                    Toast.makeText(requireContext(),
                                                            "Terjadi error yang tidak diketahui["+response.code()+"]", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                if (response.body().getMsg()!=null){
                                                    Toast.makeText(requireContext(),
                                                            response.body().getMsg(),
                                                            Toast.LENGTH_SHORT).show();
                                                }

                                                refreshPage(detailUserInt.get(SessionManager.USER_ID),daftar_gudang);
                                            }

                                            @Override
                                            public void onFailure(Call<ApiTransferDetail> call, Throwable t) {
                                                Log.d("19201299",
                                                        "onFailure: ["+t.getMessage()+"]");
                                                Toast.makeText(requireContext(),
                                                        "Server error",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).create().show();
                    }

                    @Override
                    public void onItemLongClick(int position, View view) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<ApiTransferDetail>> call, Throwable t) {
                Log.d("19201299",
                        "onFailure(id User :"+id_user+"): TransferFragment[479]"+t.getMessage());
                shimmer_tab_transfer.setVisibility(View.GONE);
                scroll_tab_transfer.setVisibility(View.VISIBLE);
//                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("load tranfer detail finish");
    }

    public void loadDaftarGudang(int id_user, AutoCompleteTextView autoCompleteTextView){
        System.out.println("load daftar gudang start");
        shimmer_tab_transfer.setVisibility(View.VISIBLE);
        scroll_tab_transfer.setVisibility(View.GONE);
        loadDaftarGudang = new LoadDaftarGudang(id_user,retrofit,jsonPlaceHolderApi);
        loadDaftarGudang.loadData();
        loadDaftarGudang.setApiResponListener(new ApiResponListener<ArrayList<ApiDaftarGudang>>() {
            @Override
            public void onResponse(boolean status, Response<ArrayList<ApiDaftarGudang>> body) {

            }

            @Override
            public void onResponse(boolean status, ArrayList<ApiDaftarGudang> body) {
                ArrayList<String> arrayListGudang = new ArrayList<>();
                for (ApiDaftarGudang apiDaftarGudang:body){
                    arrayListGudang.add(apiDaftarGudang.getNm_area());
                }
                listGudangAdapter = new ArrayAdapter<>(getActivity(),R.layout.tv_daftar_item,
                        arrayListGudang);
                loadTransferDetail(id_user);
                autoCompleteTextView.setAdapter(listGudangAdapter);
                shimmer_tab_transfer.setVisibility(View.GONE);

                autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        btn_pilih_item.setEnabled(true);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                shimmer_tab_transfer.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Server error", Toast.LENGTH_LONG).show();
            }
        });
        System.out.println("load daftar gudang finish");
    }

    public void refreshPage(int id_user,AutoCompleteTextView autoCompleteTextView){
        card_tranfer_detail.setVisibility(View.GONE);
        cardViewTransfer.setVisibility(View.GONE);
        btn_pilih_item.setEnabled(true);
        linear_btn_transfer.setVisibility(View.GONE);
        autoCompleteTextView.setEnabled(true);
        loadDaftarGudang(id_user,autoCompleteTextView);
    }

    @Override
    public void onItemClick(int position, View view) {
        TextView tv_nm_item =view.findViewById(R.id.tvResNmItem);
        TextView tv_stock =view.findViewById(R.id.tvResStock);
        TextView tv_area =view.findViewById(R.id.tvArea);

        this.tv_nm_item.setText(tv_nm_item.getText());
        this.tv_eceran.setText(modelItemGudangs.get(position).getEceran());
        this.tv_satuan.setText(modelItemGudangs.get(position).getNm_satuan());

        edt_qty_ecer.setText(null);
        edt_qty_satu.setText(null);
        edt_qty_satu.requestFocus();

        cardViewTransfer.setVisibility(View.VISIBLE);
        dialog.dismiss();

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireContext(),
//                        "Satuan "+edt_qty_satu.getText()+"\nEceran "+ edt_qty_ecer.getText(),
//                        Toast.LENGTH_SHORT).show();
                Call<ApiTransferDetail> call=
                        jsonPlaceHolderApi.getTransferAddCart(
                                detailUserInt.get(SessionManager.USER_ID),
                                loadDaftarGudang.getId(daftar_gudang.getText().toString()),
                                modelItemGudangs.get(position).getDt_gudang(),
                                modelItemGudangs.get(position).getId_item(),
                                edt_qty_satu.getText().toString(),
                                edt_qty_ecer.getText().toString()
                        );
                Log.d("19201299", "onClick: "+
                                detailUserInt.get(SessionManager.USER_ID)+", "+
                        loadDaftarGudang.getId(daftar_gudang.getText().toString())+", "+
                        modelItemGudangs.get(position).getDt_gudang()+","+
                        modelItemGudangs.get(position).getId_item()+","+
                        edt_qty_satu.getText().toString()+","+
                        edt_qty_ecer.getText().toString());
                call.enqueue(new Callback<ApiTransferDetail>() {
                    @Override
                    public void onResponse(Call<ApiTransferDetail> call, Response<ApiTransferDetail> response) {

                        if (!response.isSuccessful()){
                            Toast.makeText(requireContext(),
                                    "Terjadi error tidak diketahui["+response.code()+"]",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(response.body().getMsg()!=null){
                            Toast.makeText(requireContext(), ""+response.body().getMsg(),
                                    Toast.LENGTH_LONG).show();
                        }
                        refreshPage(detailUserInt.get(SessionManager.USER_ID),daftar_gudang);
                    }

                    @Override
                    public void onFailure(Call<ApiTransferDetail> call, Throwable t) {
                        Log.d("19201299", "onFailure: TrasnferFragment[596]"+t.getMessage());
//                        Toast.makeText(requireContext(), "Server error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onItemClick1(int position, View view) {

    }
}