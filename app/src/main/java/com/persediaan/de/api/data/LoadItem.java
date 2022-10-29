package com.persediaan.de.api.data;

import com.persediaan.de.api.ApiDaftarBarang;
import com.persediaan.de.api.ApiDaftarGudang;
import com.persediaan.de.api.ApiItemGudang;
import com.persediaan.de.api.JsonPlaceHolderApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoadItem {
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private ApiResponListener<ArrayList<ApiDaftarBarang>> apiResponListener;
    int size;

    public LoadItem(Retrofit retrofit, JsonPlaceHolderApi jsonPlaceHolderApi) {
        this.retrofit = retrofit;
        this.jsonPlaceHolderApi = jsonPlaceHolderApi;
    }

    public void loadData(){
        Call<ArrayList<ApiDaftarBarang>> call=jsonPlaceHolderApi.getiItem();
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                boolean sts = false;
                if (!response.isSuccessful()){
                    sts= false;
                }else {
                    sts = true;
                }
                size = response.body().size();
                if (apiResponListener!=null){
                    apiResponListener.onResponse(sts,response.body());
                    apiResponListener.onResponse(sts,response);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                if (apiResponListener!=null){
                    apiResponListener.onFailure(t);
                }
            }
        });
    }
    public int getSize(){
        return this.size;
    }
    public void setApiResponListener(ApiResponListener<ArrayList<ApiDaftarBarang>> apiResponListener) {
        this.apiResponListener = apiResponListener;
    }
}
