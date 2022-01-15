package com.persediaan.de;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.adapter.AdapterPenerimaan;
import com.persediaan.de.adapter.AdapterStock;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.adapter.myAdapter;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.ApiStock;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.api.data.ApiResponListener;
import com.persediaan.de.api.data.LoadDaftarPenerimaan;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.javatouch.JavaHover;
import com.persediaan.de.javatouch.JavaHoverListener;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.ModelStock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements RecyclerViewClickInterface {

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
    LoadDaftarPenerimaan loadDaftarPenerimaan;

//    Linear Layout
    LinearLayout linear_root_card_home1;
    LinearLayout linear_root_card_home2;
    LinearLayout linear_root_card_home3;
    LinearLayout linear_root_card_home4;

//    AppCompatImage
    AppCompatImageView img_receive;
    AppCompatImageView img_receive2;
    AppCompatImageView img_receive3;
    AppCompatImageView img_receive4;

//    Card
    CardView card_home_1;
    CardView card_home_2;
    CardView card_home_3;
    CardView card_home_4;

//    TextView
    TextView card_home_jml_1,card_home_tittle_1;
    TextView card_home_jml_2,card_home_tittle_2;
    TextView card_home_jml_3,card_home_tittle_3;
    TextView card_home_jml_4,card_home_tittle_4;

//    Scalle
    float scalleX,scalleY;

    MeowBottomNavigation bottomNavigation;

    public HomeFragment(MeowBottomNavigation bottomNavigation) {
        this.bottomNavigation = bottomNavigation;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
//        SessionManager
        SessionManager sessionManagerUser = new SessionManager(requireContext(),"login");
        HashMap<String,Integer> detailUserInt = sessionManagerUser.getUserDetailInt();

//        Inizialize Variable
        {
            linear_root_card_home1 = view.findViewById(R.id.linearRootCardHome1);
            img_receive = view.findViewById(R.id.imgHomeReceive);
            card_home_1 = view.findViewById(R.id.cardHome1);
            card_home_jml_1 = view.findViewById(R.id.cardHomeJml1);
            card_home_tittle_1 = view.findViewById(R.id.cardHomeTittle1);

            linear_root_card_home2 = view.findViewById(R.id.linearRootCardHome2);
            img_receive2 = view.findViewById(R.id.imgHomeReceive2);
            card_home_2 = view.findViewById(R.id.cardHome2);
            card_home_jml_2 = view.findViewById(R.id.cardHomeJml2);
            card_home_tittle_2 = view.findViewById(R.id.cardHomeTittle2);

            linear_root_card_home3 = view.findViewById(R.id.linearRootCardHome3);
            img_receive3 = view.findViewById(R.id.imgHomeReceive3);
            card_home_3 = view.findViewById(R.id.cardHome3);
            card_home_jml_3 = view.findViewById(R.id.cardHomeJml3);
            card_home_tittle_3 = view.findViewById(R.id.cardHomeTittle3);

            linear_root_card_home4 = view.findViewById(R.id.linearRootCardHome4);
            img_receive4 = view.findViewById(R.id.imgHomeReceive4);
            card_home_4 = view.findViewById(R.id.cardHome4);
            card_home_jml_4 = view.findViewById(R.id.cardHomeJml4);
            card_home_tittle_4 = view.findViewById(R.id.cardHomeTittle4);
        }

        //        Load daftar penerimaan
        {
            loadDaftarPenerimaan = new LoadDaftarPenerimaan(detailUserInt.get(SessionManager.USER_ID)
                    , retrofit,
                    jsonPlaceHolderApi);
            loadDaftarPenerimaan.LoadData();
            loadDaftarPenerimaan.setApiResponListener(new ApiResponListener<ArrayList<ApiPenerimaan>>() {
                @Override
                public void onResponse(boolean status, ArrayList<ApiPenerimaan> body) {
                    HashMap<String,String> konversi = new HashMap<>();
                    HashMap<String,String> disKonversi = new HashMap<>();
                    String logKonversi = "";
                    String logDisKonversi = "";
                    for (ApiPenerimaan apiPenerimaan :
                            body) {
                        if (apiPenerimaan.getDikonversi()==1) {
                            loadDaftarPenerimaan.setDataKonversi(apiPenerimaan);
                            if (konversi.get(apiPenerimaan.getId_purchase()) == null) {
                                konversi.put(apiPenerimaan.getId_purchase(),
                                        apiPenerimaan.getId_purchase());
                                logKonversi +=apiPenerimaan.getDikonversi()+","+"=>"+apiPenerimaan.getId_purchase()+"\n";
                            }
                        }else{
                            loadDaftarPenerimaan.setDataDiskonversi(apiPenerimaan);
                            if (disKonversi.get(apiPenerimaan.getId_purchase()) == null) {
                                disKonversi.put(apiPenerimaan.getId_purchase(),
                                        apiPenerimaan.getId_purchase());
                                logDisKonversi +=apiPenerimaan.getDikonversi()+","+"=>"+apiPenerimaan.getId_purchase()+"\n";
                            }
                        }
                    }
                    Log.d("19201299", "Konversi: "+logKonversi);
                    Log.d("19201299", "DisKonversi: "+logDisKonversi);
                    loadDaftarPenerimaan.setSize(LoadDaftarPenerimaan.RECEIVE,(konversi.size()+disKonversi.size()));
                    loadDaftarPenerimaan.setSize(LoadDaftarPenerimaan.KONVERSI, konversi.size());
                    loadDaftarPenerimaan.setSize(LoadDaftarPenerimaan.DISKONVERSI, disKonversi.size());

                    card_home_jml_1.setText(loadDaftarPenerimaan.getSize(LoadDaftarPenerimaan.RECEIVE)+
                            "\nPurchase");
                    card_home_tittle_1.setText("Receive");
                    card_home_jml_2.setText(loadDaftarPenerimaan.getSize(LoadDaftarPenerimaan.KONVERSI)+
                            "\nPurchase");
                    card_home_tittle_2.setText("Konversi");
                    card_home_jml_3.setText(loadDaftarPenerimaan.getSize(LoadDaftarPenerimaan.DISKONVERSI)+
                            "\nPurchase");
                    card_home_tittle_3.setText("Belum Konversi");



                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }

        img_receive.setColorFilter(ContextCompat.getColor(requireContext(), R.color.splashBackground));
        img_receive2.setColorFilter(ContextCompat.getColor(requireContext(), R.color.BgGreen));
        img_receive3.setColorFilter(ContextCompat.getColor(requireContext(), R.color.BgRed));
        img_receive4.setColorFilter(ContextCompat.getColor(requireContext(), R.color.teal_700));

        JavaHoverListener javaHoverListener1 = new JavaHoverListener() {
            @Override
            public void hoverIn(View view) {
                scalleX = view.getScaleX();
                scalleY = view.getScaleY();
                card_home_1.setCardBackgroundColor(getResources().getColor(R.color.buttonTransClicked));
                card_home_1.setCardElevation(1);
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
            }

            @Override
            public void hoverOut(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_1.setCardElevation(5);
                card_home_1.setCardBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void hoverMove(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_1.setCardElevation(5);
                card_home_1.setCardBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void onClick(View view) {
                bottomNavigation.show(2,true);
            }
        };
        JavaHoverListener javaHoverListener2 = new JavaHoverListener() {
            @Override
            public void hoverIn(View view) {
                scalleX = view.getScaleX();
                scalleY = view.getScaleY();
                card_home_2.setCardBackgroundColor(getResources().getColor(R.color.buttonTransClicked));
                card_home_2.setCardElevation(1);
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
            }

            @Override
            public void hoverOut(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_2.setCardElevation(5);
                card_home_2.setCardBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void hoverMove(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_2.setCardElevation(5);
                card_home_2.setCardBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void onClick(View view) {
                bottomNavigation.show(2,true);
            }
        };
        JavaHoverListener javaHoverListener3 = new JavaHoverListener() {
            @Override
            public void hoverIn(View view) {
                scalleX = view.getScaleX();
                scalleY = view.getScaleY();
                card_home_3.setCardBackgroundColor(getResources().getColor(R.color.buttonTransClicked));
                card_home_3.setCardElevation(1);
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
            }

            @Override
            public void hoverOut(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_3.setCardElevation(5);
                card_home_3.setCardBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void hoverMove(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_3.setCardElevation(5);
                card_home_3.setCardBackgroundColor(getResources().getColor(R.color.white));

            }

            @Override
            public void onClick(View view) {
                bottomNavigation.show(2,true);
            }
        };
        JavaHover jvH1 = new JavaHover(linear_root_card_home1,0,
                0);
        jvH1.create();
        jvH1.setJavaHoverListener(javaHoverListener1);

        JavaHover jvH2 = new JavaHover(linear_root_card_home2,0,
                0);
        jvH2.create();
        jvH2.setJavaHoverListener(javaHoverListener2);

        JavaHover jvH3 = new JavaHover(linear_root_card_home3,0,
                0);
        jvH3.create();
        jvH3.setJavaHoverListener(javaHoverListener3);

        JavaHover jvH4 = new JavaHover(linear_root_card_home4,0,
                0);
        
        jvH4.create();
        jvH4.setJavaHoverListener(new JavaHoverListener() {
            @Override
            public void hoverIn(View view) {
                scalleX = view.getScaleX();
                scalleY = view.getScaleY();
                card_home_4.setCardBackgroundColor(getResources().getColor(R.color.buttonTransClicked));
                card_home_4.setCardElevation(1);
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
            }

            @Override
            public void hoverOut(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_4.setCardElevation(5);
                card_home_4.setCardBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void hoverMove(View view) {
                view.setScaleX(scalleX);
                view.setScaleY(scalleY);
                card_home_4.setCardElevation(5);
                card_home_4.setCardBackgroundColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onClick(View view) {
                newPage(ItemActivity.class);
            }
        });

        loadCards();
        return view;
    }
    private void newPage(Class cls) {
        Intent i = new Intent(requireContext(),cls);
        startActivity(i);
    }

    public void loadFragment(Fragment pFragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,pFragment)
                .commit();
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