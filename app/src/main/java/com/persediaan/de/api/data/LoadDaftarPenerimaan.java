package com.persediaan.de.api.data;

import android.content.Context;
import android.widget.Toast;

import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadDaftarPenerimaan {
    public static String KONVERSI = "KONVERSI";
    public static String DISKONVERSI = "DISKONVERSI";
    public static String RECEIVE = "RECEIVE";
    int id_user,size = 0;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    ApiResponListener<ArrayList<ApiPenerimaan>> apiResponListener;
    ArrayList<ApiPenerimaan> dataKonversi;
    ArrayList<ApiPenerimaan> dataDiskonversi;

    Context ctx;
    boolean sts;

    HashMap<String,Integer> sizeHashMap;

    public LoadDaftarPenerimaan(int id_user,
                                Retrofit retrofit,
                                JsonPlaceHolderApi jsonPlaceHolderApi,Context ctx) {
        this.id_user = id_user;
        this.retrofit = retrofit;
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
//        this.apiResponListener = apiResponListener;

        sizeHashMap = new HashMap<>();
        dataKonversi = new ArrayList<>();
        dataDiskonversi = new ArrayList<>();
        this.ctx = ctx;
    }

    public void setApiResponListener(ApiResponListener<ArrayList<ApiPenerimaan>> apiResponListener) {
        this.apiResponListener = apiResponListener;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setSize(String key, int value) {
        sizeHashMap.put(key,value);
    }

    public ArrayList<ApiPenerimaan> getDataKonversi() {
        return dataKonversi;
    }

    public void setDataKonversi(ApiPenerimaan dataKonversi) {
        this.dataKonversi.add(dataKonversi);
    }

    public ArrayList<ApiPenerimaan> getDataDiskonversi() {
        return dataDiskonversi;
    }

    public void setDataDiskonversi(ApiPenerimaan dataDiskonversi) {
        this.dataDiskonversi.add(dataDiskonversi);
    }

    public void LoadData(){
        Call<ArrayList<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaanList(id_user);
        call.enqueue(new Callback<ArrayList<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiPenerimaan>> call, Response<ArrayList<ApiPenerimaan>> response) {
                if (!response.isSuccessful()){
                    sts = false;
                }else{
                    sts = true;
                }

                if (apiResponListener != null) {
                    apiResponListener.onResponse(sts,response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ApiPenerimaan>> call, Throwable t) {
                if (apiResponListener !=null){
                    apiResponListener.onFailure(t);
                }
            }
        });

    }

    public int getSize(String key) {
        return sizeHashMap.get(key);
    }
}
