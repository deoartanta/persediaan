package com.persediaan.de;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.persediaan.de.adapter.AdapterItemPenerimaan;
import com.persediaan.de.adapter.AdapterPenerimaan2;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelItemBrg;
import com.persediaan.de.model.ModelPenerimaan;

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

public class DetailPenerimaanActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    public static String ID_TRANS = "ID_TRANS",
                        AREA = "AREA",
                        TITLE = "TITLE",
                        STS = "STS",
                        TGL_PURCHASE = "TGL_PURCHASE",
                        JML_ITEM ="JML_ITEM",
                        PENYEDIA = "PENYEDIA",
                        JML_ITEM_BOTTOM = "JML_ITEM_BOTTOM",
                        TOTAL_HRG = "TOTAL_HRG",
                        NOTE = "NOTE";
    Toolbar toolbar;
    ImageView tb_imgBtn;
    ActionBar bar;

    RecyclerView recyclerViewItem;

    TextView tb_title;
    TextView tv_idtrans;
    TextView tv_area;
    TextView tv_sts;
    TextView tv_tglpurchase;
    TextView tv_jml_item;
    TextView tv_penyedia;
    TextView tv_jml_item_bottom;
    TextView tv_total_hrg;

    TextInputEditText tiet_note;

    View view_collapse;

    LinearLayout linear_bottom_sheet;

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    boolean stscolapse = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penerimaan);

        toolbar =findViewById(R.id.toolbar);
        tb_imgBtn = toolbar.findViewById(R.id.tbImgBtn);
        tb_title = toolbar.findViewById(R.id.tb_title);
        view_collapse = findViewById(R.id.viewCollapse);

        recyclerViewItem = findViewById(R.id.recyclerItem);

        SessionManager sessionManagerUser;
        HashMap<String,Integer> detailUserInt;

        sessionManagerUser = new SessionManager(getApplicationContext(),"login");
        detailUserInt = sessionManagerUser.getUserDetailInt();

        linear_bottom_sheet = findViewById(R.id.linearBottomsheet);

        tb_title.setText("Detail Penerimaan");

        view_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stscolapse) {
                    linear_bottom_sheet.setVisibility(View.VISIBLE);
                }else {
                    linear_bottom_sheet.setVisibility(View.GONE);
                }
                stscolapse = (!stscolapse);
            }
        });
        view_collapse.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (stscolapse) {
                    linear_bottom_sheet.setVisibility(View.VISIBLE);
                }else {
                    linear_bottom_sheet.setVisibility(View.GONE);
                }
                stscolapse = (!stscolapse);
                return false;
            }
        });
        tb_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        tv_idtrans = findViewById(R.id.tvPurchase);
        tv_area = findViewById(R.id.tvArea);
        tv_sts = findViewById(R.id.tvSts);
        tv_tglpurchase = findViewById(R.id.tvTglpurchase);
        tv_jml_item = findViewById(R.id.tvJmlitem);
        tv_penyedia = findViewById(R.id.tvSupplier);
        tv_jml_item_bottom = findViewById(R.id.tvJmlItemBottom);
        tv_total_hrg = findViewById(R.id.tvTotalhrg);

        tiet_note = findViewById(R.id.tietNote);
        Bundle extras = getIntent().getExtras();

//        tv_idtrans.setText(extras.getString(ID_TRANS));
//        tv_area.setText(extras.getString(AREA));
//        tv_sts.setText(extras.getString(STS));
//        tv_tglpurchase.setText(extras.getString(TGL_PURCHASE));
//        tv_jml_item.setText("("+extras.getString(JML_ITEM)+")");
//        tv_penyedia.setText(extras.getString(PENYEDIA));
//        tv_jml_item_bottom.setText(extras.getString(JML_ITEM));
//        tv_total_hrg.setText(extras.getString(TOTAL_HRG));

        tiet_note.setText(extras.getString(NOTE));
        loadItem(detailUserInt.get(SessionManager.USER_ID));
    }

    private void loadItem(int id_user) {

        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaanCart(id_user);
        call.enqueue(new Callback<List<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<List<ApiPenerimaan>> call, Response<List<ApiPenerimaan>> response) {

                List<ApiPenerimaan> apiPenerimaans = response.body();
//                ArrayList<ModelItemBrg> modelItemBrgs = new ArrayList<ModelPenerimaan>();
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error yang tidak diketahui", Toast.LENGTH_SHORT).show();
//                    tv_lblDtKosong.setVisibility(View.GONE);
                    return;
                }
                ArrayList<ModelItemBrg> modelItemBrgs;
                String id_purchase = null,
                        note = null,dt_purchase = null,nm_area = null,nm_suplier = null,
                        alasuplier = null, npwp = null,name_penyedia = null, status = null,
                        area = null, alamat = null, date,id_trans = null,nm_singkat = null;
                int admin = 0,id = 0,id_area = 0,id_supplier = 0,diterima = 0,harga_total=0,i=0,
                        jml_item=0,tgl_purchase = 0;

                modelItemBrgs = new ArrayList<ModelItemBrg>();
                for (ApiPenerimaan apiPenerimaan:apiPenerimaans){
//                    tv_lblDtKosong.setVisibility(View.GONE);
                    modelItemBrgs.add(new ModelItemBrg(
                            apiPenerimaan.getId_item(),
                            apiPenerimaan.getQty(),
                            apiPenerimaan.getId_satuan(),
                            apiPenerimaan.getHarga(),
                            apiPenerimaan.getJumlah(),
                            apiPenerimaan.getNm_item(),
                            apiPenerimaan.getEceran(),
                            apiPenerimaan.getNm_satuan()));
                    if (i==0){
                        id_purchase = apiPenerimaan.getId_purchase();
                        tgl_purchase = apiPenerimaan.getCreated();
                        note = apiPenerimaan.getNote();
                        dt_purchase =apiPenerimaan.getDt_purchase();
                        nm_area =apiPenerimaan.getNm_area();
                        nm_singkat =apiPenerimaan.getNm_singkat();

                        nm_suplier = apiPenerimaan.getNm_suplier();
                        alasuplier = apiPenerimaan.getAlasuplier();
                        npwp = apiPenerimaan.getNpwp();
                        name_penyedia = apiPenerimaan.getName_penyedia();
                        area = apiPenerimaan.getArea();
                        alamat = apiPenerimaan.getAlamat();
                        id_trans = apiPenerimaan.getId_trans();

                        admin = apiPenerimaan.getAdmin();
                        id = apiPenerimaan.getId();
                        id_area = apiPenerimaan.getId_area();
                        id_supplier = apiPenerimaan.getId_suplier();
                        diterima = apiPenerimaan.getDiterima();
                    }

                    harga_total +=apiPenerimaan.getHarga();
//                    (new SimpleDateFormat("dd MMM yyyy")
//                            .format(
//                                    new Date((Long.parseLong(penerimaanbrgApi.getCreated())*1000))
//                            )).toString()
                    i++;
                }
                Log.d("19201299", "onResponse:Model Item BRGS: "+modelItemBrgs.toString());
                jml_item = i;
                if (diterima==0){
                    status = "Belum Diterima";
                }else if (diterima ==1){
                    status = "Belum Dikonversi";
                }
//                modelPenerimaanArrayList.add(new ModelPenerimaan(
//                        id_purchase,tgl_purchase,note,dt_purchase,nm_area,nm_singkat,
//                        nm_suplier,alasuplier,npwp,name_penyedia,status,area,
//                        alamat,id_trans,jml_item,admin,id,id_area,id_supplier,diterima,
//                        harga_total,modelItemBrgs
//                ));
                AdapterItemPenerimaan adapter = new AdapterItemPenerimaan(modelItemBrgs,
                        DetailPenerimaanActivity.this);
                recyclerViewItem.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                        LinearLayoutManager.VERTICAL, false));
                recyclerViewItem.setHasFixedSize(true);
                recyclerViewItem.setItemAnimator(new DefaultItemAnimator());

                tv_idtrans.setText(id_trans);
                tv_area.setText(nm_area);
                tv_sts.setText(status);
                tv_tglpurchase.setText((new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                        .format(
                                new Date((Long.parseLong(String.valueOf(tgl_purchase))*1000))
                        )).toString());
                tv_jml_item.setText("("+jml_item+")");
                tv_penyedia.setText(nm_suplier);
                tv_jml_item_bottom.setText(String.valueOf(jml_item));
                tv_total_hrg.setText(String.valueOf(harga_total));

                recyclerViewItem.setAdapter(adapter);
                recyclerViewItem.setVisibility(View.VISIBLE);
//                shimmerFrameLayout.stopShimmer();
//                shimmerFrameLayout.setVisibility(View.GONE);
//                recyclerPenerimaan.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<ApiPenerimaan>> call, Throwable t) {
//                shimmerFrameLayout.setVisibility(View.GONE);
//                tv_lblDtKosong.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(int position, View view) {

    }
}