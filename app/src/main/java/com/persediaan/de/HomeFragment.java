package com.persediaan.de;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.AdapterStock;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.adapter.myAdapter;
import com.persediaan.de.api.ApiStock;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.ModelStock;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private ActionBar actionBar;
    private ArrayList<ModelStock> mdlStock;

    private myAdapter adapter;
    private AdapterStock adapterStock;

    ViewPager viewPager;
    ViewPager2 viewPager2;
    RecyclerView recycle_stock;
    ScrollView svHome;

    MeowBottomNavigation bottomNavigation;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment(MeowBottomNavigation bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2, MeowBottomNavigation btnNavBottom) {
        HomeFragment fragment = new HomeFragment(btnNavBottom);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

        recycle_stock = view.findViewById(R.id.recycle_stock);
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        loadCards();
        return view;
    }

    private void loadCards() {
        mdlStock = new ArrayList<ModelStock>();
        Call<List<ApiStock>> call = jsonPlaceHolderApi.getStock();
        call.enqueue(new Callback<List<ApiStock>>() {
            @Override
            public void onResponse(Call<List<ApiStock>> call, Response<List<ApiStock>> response) {
                List<ApiStock> Items = response.body();
                mdlStock = new ArrayList<>();
                for(ApiStock arr:Items){
                    mdlStock.add(new ModelStock(
                            arr.getId(),
                            arr.getDt_gudang(),
                            arr.getAdmin(),
                            arr.getId_area(),
                            arr.getId_item(),
                            arr.getQty(),
                            arr.getHarga(),
                            arr.getTipe(),
                            arr.getTgl_gudang(),
                            arr.getSisa(),
                            arr.getNm_item(),
                            arr.getId_satuan(),
                            arr.getNm_satuan(),
                            arr.getJumlah(),
                            arr.getEceran(),
                            arr.getNm_area(),
                            arr.getNm_singkat()
                    ));
                }
                adapterStock = new AdapterStock(getContext(), mdlStock);
                recycle_stock.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                recycle_stock.setItemAnimator(new DefaultItemAnimator());
                recycle_stock.setAdapter(adapterStock);
            }

            @Override
            public void onFailure(Call<List<ApiStock>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        TextView tv_idtrans = view.findViewById(R.id.tvIdTrans);
        Intent i = new Intent(requireContext(),DetailPenerimaanActivity.class);

        String id_trans = (String) tv_idtrans.getText();
        i.putExtra(DetailPenerimaanActivity.ID_TRANS,id_trans);
        startActivity(i);
    }

    @Override
    public void onItemClick1(int position, View view) {

    }
}