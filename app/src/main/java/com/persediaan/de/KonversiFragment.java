package com.persediaan.de;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.persediaan.de.api.ApiKonversi;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelKonversi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonversiFragment extends Fragment {
    TextView tv_no_konversi ;

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    public KonversiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_konversi, container, false);
        ModelKonversi dt_konversi = new ModelKonversi("123-123-123");
        tv_no_konversi = view.findViewById(R.id.tvno_konversi);
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<ApiKonversi>> call = jsonPlaceHolderApi.getResponKonversi(
                "11");
        call.enqueue(new Callback<List<ApiKonversi>>() {
            @Override
            public void onResponse(Call<List<ApiKonversi>> call, Response<List<ApiKonversi>> response) {
                List<ApiKonversi> dt_konversi=response.body();
                if (!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Tidak ada data", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(ApiKonversi ak : dt_konversi){
                    tv_no_konversi.setText(ak.getNo_konversi());
                }
            }

            @Override
            public void onFailure(Call<List<ApiKonversi>> call, Throwable t) {

            }
        });
        return view;
    }
}