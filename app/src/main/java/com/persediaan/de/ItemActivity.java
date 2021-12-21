package com.persediaan.de;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.persediaan.de.adapter.AdapterDaftartBarang;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiDaftarBarang;
import com.persediaan.de.api.ApiSatuan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelDaftarItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    AppCompatButton btn_add;

    ArrayList<ModelDaftarItem> modelDaftarItems;
    RecyclerView recycle_daftar_barang;

    ArrayAdapter<String> adapter_item;
//    Connection
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    AdapterDaftartBarang adapterDaftartBarang;

    HashMap<String,Integer>modelSatuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //Connection
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        btn_add = findViewById(R.id.btnAddItem);
        recycle_daftar_barang = findViewById(R.id.recycleDaftarBarang);
        loadData();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater= getLayoutInflater();
                View viewinflater =layoutInflater.inflate(R.layout.input_alert_dialog_item,null);

                TextInputEditText tiet_nm_item = viewinflater.findViewById(R.id.editTextNamaItem);
                AutoCompleteTextView autoCompleteSatuan= viewinflater.findViewById(R.id.autoCompleteSatuan);

                loadSatuan(autoCompleteSatuan);
                new AlertDialog.Builder(ItemActivity.this)
                        .setIcon(R.drawable.ic_baseline_create_24_primary)
                        .setTitle("Add Item")
                        .setView(viewinflater)
                        .setCancelable(true)
                        .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.d("19201299",
                                        "onClick: "+tiet_nm_item.getText().toString()+", "+
                                                tiet_nm_item.getText().toString().toUpperCase(Locale.ROOT)+", "+
                                                modelSatuan.get(autoCompleteSatuan.getText().toString()));

                                addData(tiet_nm_item.getText().toString(),
                                        modelSatuan.get(autoCompleteSatuan.getText().toString())
                                );
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

    }

    private void addData(String nm_item, Integer id_satuan) {
        Call<ApiDaftarBarang> call = jsonPlaceHolderApi.getAdditem(
                nm_item,id_satuan
        );
        call.enqueue(new Callback<ApiDaftarBarang>() {
            @Override
            public void onResponse(Call<ApiDaftarBarang> call, Response<ApiDaftarBarang> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),
                            "Terjadi error yang tidak diketahui["+response.code()+"]",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), response.body().getNm_item()+" berhasil " +
                                "ditambah",
                        Toast.LENGTH_LONG).show();
//                Snackbar.make(getApplicationContext(),findViewById(R.id.rootLayout),"",
//                        Snackbar.LENGTH_LONG).show();
                loadData();
            }

            @Override
            public void onFailure(Call<ApiDaftarBarang> call, Throwable t) {

            }
        });
    }

    private void loadData(){
        recycle_daftar_barang.setAdapter(null);
        Call<ArrayList<ApiDaftarBarang>> call = jsonPlaceHolderApi.getiItem();
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ItemActivity.this, "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<ApiDaftarBarang> apiDaftarBarangs = response.body();

                modelDaftarItems = new ArrayList<>();
                for (ApiDaftarBarang apiDaftarBarang:apiDaftarBarangs){
                    modelDaftarItems.add(
                            new ModelDaftarItem(
                                    apiDaftarBarang.getNm_item(),
                                    apiDaftarBarang.getNm_satuan(),
                                    apiDaftarBarang.getId_item()
                            )
                    );
                }

                adapterDaftartBarang = new AdapterDaftartBarang(modelDaftarItems,ItemActivity.this);
                recycle_daftar_barang.setAdapter(adapterDaftartBarang);
                recycle_daftar_barang.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL, false));
                recycle_daftar_barang.setHasFixedSize(true);
                recycle_daftar_barang.setItemAnimator(new DefaultItemAnimator());
            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                Toast.makeText(ItemActivity.this, "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSatuan(AutoCompleteTextView autoCompleteSatuan){
        Call<ArrayList<ApiSatuan>> call=jsonPlaceHolderApi.getSatuan();
        call.enqueue(new Callback<ArrayList<ApiSatuan>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiSatuan>> call, Response<ArrayList<ApiSatuan>> response) {
                ArrayList<ApiSatuan> arraySatuan = response.body();
                ArrayList<String> listSatuan = new ArrayList<>();
                modelSatuan  = new HashMap<>();
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Terjadi error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (ApiSatuan apiSatuan:arraySatuan){
                    listSatuan.add(apiSatuan.getNm_satuan());
                    modelSatuan.put(apiSatuan.getNm_satuan().toString(),apiSatuan.getId_satuan());
                }
                adapter_item = new ArrayAdapter<>(getApplicationContext(),R.layout.list_item,
                        listSatuan);
                autoCompleteSatuan.setAdapter(adapter_item);



            }

            @Override
            public void onFailure(Call<ArrayList<ApiSatuan>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void editItem(int id_item,String nm_item,int id_satuan){
        Call<ArrayList<ApiDaftarBarang>> call = jsonPlaceHolderApi.getEdititem(
                id_item,nm_item,id_satuan
        );
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                for (ApiDaftarBarang apiDaftarBarang:response.body()) {
                    if (apiDaftarBarang.getMsg()==null) {
                        loadData();
                        Toast.makeText(getApplicationContext(), "Edit data berhasil", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),
                                ""+apiDaftarBarang.getMsg(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(int position, View view) {
        TextView nm_item = view.findViewById(R.id.tvResNmItem);

        LayoutInflater layoutInflater= getLayoutInflater();
        View viewinflater =layoutInflater.inflate(R.layout.input_alert_dialog_item,null);

        TextInputEditText tiet_nm_item = viewinflater.findViewById(R.id.editTextNamaItem);
        AutoCompleteTextView autoCompleteSatuan= viewinflater.findViewById(R.id.autoCompleteSatuan);
        tiet_nm_item.setText(nm_item.getText().toString().toUpperCase(Locale.ROOT));
        loadSatuan(autoCompleteSatuan);
        new AlertDialog.Builder(ItemActivity.this)
                .setIcon(R.drawable.ic_baseline_create_24_primary)
                .setTitle("Add Item")
                .setView(viewinflater)
                .setCancelable(true)
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("19201299",
                                "onClick: "+modelDaftarItems.get(position).getId_item()+", "+
                                tiet_nm_item.getText().toString().toUpperCase(Locale.ROOT)+", "+
                                modelSatuan.get(autoCompleteSatuan.getText().toString()));

                        editItem(modelDaftarItems.get(position).getId_item(),
                                tiet_nm_item.getText().toString().toUpperCase(Locale.ROOT),
                                modelSatuan.get(autoCompleteSatuan.getText().toString())
                                );
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
    public void onItemClick1(int position, View view) {
        TextView nm_item = view.findViewById(R.id.tvResNmItem);
        TextView nm_stn = view.findViewById(R.id.tvResNmStn);

        Call<ApiDaftarBarang> call =
                jsonPlaceHolderApi.getHpsItem(modelDaftarItems.get(position).getId_item());

        new AlertDialog.Builder(ItemActivity.this)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setTitle("Hapus Item")
                .setMessage("Apakah anda yakin ingin menghapus "+nm_item.getText())
                .setCancelable(true)
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.enqueue(new Callback<ApiDaftarBarang>() {
                            @Override
                            public void onResponse(Call<ApiDaftarBarang> call, Response<ApiDaftarBarang> response) {
                                if (!response.isSuccessful()){
                                    Toast.makeText(ItemActivity.this, "Terjadi error yang tidak diketahui",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (response.body().isSts()){
                                    Toast.makeText(ItemActivity.this, nm_item.getText().toString().toUpperCase(Locale.ROOT)+
                                                    " berhasil dihapus",
                                            Toast.LENGTH_SHORT).show();
                                    loadData();
                                }else{
                                    Toast.makeText(ItemActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiDaftarBarang> call, Throwable t) {
                                Toast.makeText(ItemActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
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
}