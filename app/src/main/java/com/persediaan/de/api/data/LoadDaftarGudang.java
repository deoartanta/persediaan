package com.persediaan.de.api.data;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.persediaan.de.R;
import com.persediaan.de.api.ApiDaftarGudang;
import com.persediaan.de.api.JsonPlaceHolderApi;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadDaftarGudang {
    int id_user;
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    HashMap<String,Integer> id;
    HashMap<Integer,String> nm_area;
    ApiResponListener<ArrayList<ApiDaftarGudang>> apiResponListener;

    public LoadDaftarGudang(int id_user, Retrofit retrofit, JsonPlaceHolderApi jsonPlaceHolderApi) {
        this.id_user = id_user;
        this.retrofit = retrofit;
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
        id = new HashMap<>();
        nm_area = new HashMap<>();

    }
    public void loadData(){
        Call<ArrayList<ApiDaftarGudang>> call=jsonPlaceHolderApi.getDaftarGudang(id_user);
        call.enqueue(new Callback<ArrayList<ApiDaftarGudang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarGudang>> call, Response<ArrayList<ApiDaftarGudang>> response) {
                boolean sts = false;
                if (!response.isSuccessful()){
                    sts = false;
                }else {
                    sts = true;
                }
                if (sts){
                    for (ApiDaftarGudang apiDaftarGudang:response.body()){
                        id.put(apiDaftarGudang.getNm_area(),apiDaftarGudang.getId_area());
                        nm_area.put(apiDaftarGudang.getId_area(),apiDaftarGudang.getNm_area());
                    }
                }
                if (apiResponListener!=null) {
                    apiResponListener.onResponse(sts, response.body());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarGudang>> call, Throwable t) {
                if (apiResponListener!=null) {
                    apiResponListener.onFailure(t);
                }

            }
        });
    }
    public String getNmArea(int id){
        return nm_area.get(id);
    }
    public int getId(String nm_area){
        return id.get(nm_area);
    }

    public void setApiResponListener(ApiResponListener<ArrayList<ApiDaftarGudang>> apiResponListener) {
        this.apiResponListener = apiResponListener;
    }

    @Override
    public String toString() {
        return "LoadDaftarGudang{" +
                "nm_area=" + nm_area.toString() +
                '}';
    }
}
