package com.persediaan.de;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.adapter.AdapterItemPenerimaan;
import com.persediaan.de.adapter.AdapterPenerimaan2;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.ApiSimpan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.Currency;
import com.persediaan.de.data.DialogCustom;
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

    ScrollView scrollView;

    RecyclerView recyclerViewItem;

    TextView tb_title,tv_idtrans,tv_area,tv_sts,tv_tglpurchase,tv_jml_item_bottom,tv_total_hrg,
            tv_total_hrg2,tv_not_found,tv_not_found_parent,tv_ala_supplier;

    RelativeLayout ttl_hrg_layout;
    RelativeLayout ttl_hrg2_layout;
    RelativeLayout relative_content;

    TextInputEditText tiet_note;

    ArrayList<ModelItemBrg> modelItemBrgs;

    View view_collapse;

    LinearLayout linear_bottom_sheet;
    LinearLayout bottom_sheet;

    Button btn_simpan,btn_batal;

    ShimmerFrameLayout shimmerFrameLayoutItem,shimmerFrameLayout_parent;

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    ViewGroup viewGroup;

    boolean stscolapse = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penerimaan);

//        Connection
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        viewGroup = (ViewGroup) findViewById(android.R.id.content);

        scrollView = findViewById(R.id.scrollViewDetail);
        scrollView.setVisibility(View.GONE);
        toolbar =findViewById(R.id.toolbar);
        tb_imgBtn = toolbar.findViewById(R.id.tbImgBtn);
        tb_title = toolbar.findViewById(R.id.tb_title);
        view_collapse = findViewById(R.id.viewCollapse);
        tv_not_found = findViewById(R.id.tvItemNotFound);
        tv_not_found_parent = findViewById(R.id.tvItemNotFoundParent);

        recyclerViewItem = findViewById(R.id.recyclerItem);

        ttl_hrg_layout = findViewById(R.id.ttlHrgLayout);
        ttl_hrg2_layout = findViewById(R.id.ttlHrg2Layout);
        tv_total_hrg2 = findViewById(R.id.tvTotalhrg2);
        tv_ala_supplier = findViewById(R.id.tvAlamatBottom);

        relative_content = findViewById(R.id.RelativeContent);

        tv_idtrans = findViewById(R.id.tvPurchase);
        tv_area = findViewById(R.id.tvArea);
        tv_sts = findViewById(R.id.tvSts);
        tv_tglpurchase = findViewById(R.id.tvTglpurchase);
        tv_jml_item_bottom = findViewById(R.id.tvJmlItemBottom);
        tv_total_hrg = findViewById(R.id.tvTotalhrg);
        tiet_note = findViewById(R.id.tietNote);

        btn_simpan = findViewById(R.id.btnSimpan);
        btn_batal = findViewById(R.id.btnBatal);

        shimmerFrameLayoutItem = findViewById(R.id.shimerLayoutItem);
        shimmerFrameLayoutItem.startShimmer();
        shimmerFrameLayoutItem.setVisibility(View.VISIBLE);
        shimmerFrameLayout_parent = findViewById(R.id.ShimmerFrameParentDetail);
        shimmerFrameLayout_parent.startShimmer();
        shimmerFrameLayout_parent.setVisibility(View.VISIBLE);

        ttl_hrg2_layout.setVisibility(View.GONE);

        linear_bottom_sheet = findViewById(R.id.linearBottomsheet);
        bottom_sheet = findViewById(R.id.BottomSheet);

        SessionManager sessionManagerUser;
        HashMap<String,Integer> detailUserInt;

        sessionManagerUser = new SessionManager(getApplicationContext(),"login");
        detailUserInt = sessionManagerUser.getUserDetailInt();


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

        Bundle extras = getIntent().getExtras();

//        tv_idtrans.setText(extras.getString(ID_TRANS));
//        tv_area.setText(extras.getString(AREA));
//        tv_sts.setText(extras.getString(STS));
//        tv_tglpurchase.setText(extras.getString(TGL_PURCHASE));
//        tv_jml_item.setText("("+extras.getString(JML_ITEM)+")");
//        tv_penyedia.setText(extras.getString(PENYEDIA));
//        tv_jml_item_bottom.setText(extras.getString(JML_ITEM));
        tv_total_hrg.setText(extras.getString(TOTAL_HRG));

        tiet_note.setText(extras.getString(NOTE));
        if (extras.getString(ID_TRANS)!=null) {
            loadItem(detailUserInt.get(SessionManager.USER_ID), extras.getString(ID_TRANS));
        }else{
            finish();
        }
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<ApiSimpan> call =
                        jsonPlaceHolderApi.getResponSimpan(detailUserInt.get(SessionManager.USER_ID),
                                ""+tiet_note.getText());
                call.enqueue(new Callback<ApiSimpan>() {
                    @Override
                    public void onResponse(Call<ApiSimpan> call, Response<ApiSimpan> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Terjadi Error yang tidak " +
                                    "diketahui", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "Data Disimpan", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiSimpan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Server Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<String> call =
                        jsonPlaceHolderApi.getResponBatal(detailUserInt.get(SessionManager.USER_ID));
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Terjadi Error yang tidak " +
                                    "diketahui", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "No Purchase "+response.body()+
                                        ", berhasil dibatalkan",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Server Error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadItem(int id_user,String id_trans) {

        Currency formatNumber = new Currency("Rp. ",",");

        Call<List<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaanCart(id_user);
        call.enqueue(new Callback<List<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<List<ApiPenerimaan>> call, Response<List<ApiPenerimaan>> response) {

                List<ApiPenerimaan> apiPenerimaans = response.body();
                shimmerFrameLayoutItem.setVisibility(View.GONE);
                shimmerFrameLayoutItem.stopShimmer();

                shimmerFrameLayout_parent.stopShimmer();
                shimmerFrameLayout_parent.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
//                Log.d("192012992", "onResponse2: "+apiPenerimaans.get(0).toString());
//                ArrayList<ModelItemBrg> modelItemBrgs = new ArrayList<ModelPenerimaan>();
                if (!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error yang tidak diketahui", Toast.LENGTH_SHORT).show();
//                    tv_lblDtKosong.setVisibility(View.GONE);
                    finish();
                    return;
                }
                String id_purchase = null,
                        note = null,dt_purchase = null,nm_area = null,nm_suplier = null,
                        alasuplier = null, npwp = null,name_penyedia = null, status = null,
                        area = null, alamat = null, date,id_trans = null,nm_singkat = null;
                int admin = 0,id = 0,id_area = 0,id_supplier = 0,diterima = 0,i=0,
                        jml_item=0,tgl_purchase = 0;
                double harga_total=0;

                modelItemBrgs = new ArrayList<ModelItemBrg>();
                for (ApiPenerimaan apiPenerimaan:apiPenerimaans){
//                    tv_lblDtKosong.setVisibility(View.GONE);
                    Log.d("19201299", "onResponse: "+apiPenerimaan.toString());
                    if(modelItemBrgs.size()<(apiPenerimaans.size()-1)){
                        modelItemBrgs.add(new ModelItemBrg(
                                apiPenerimaan.getId_item(),
                                apiPenerimaan.getQty(),
                                apiPenerimaan.getId_satuan(),
                                apiPenerimaan.getHarga(),
                                apiPenerimaan.getJumlah(),
                                apiPenerimaan.getNm_item(),
                                apiPenerimaan.getEceran(),
                                apiPenerimaan.getNm_satuan()));
                    }
                    if (i==(apiPenerimaans.size()-1)){
                        nm_suplier = apiPenerimaan.getNm_suplier();
                        alasuplier = apiPenerimaan.getAlasuplier();
                    }
                    if (i==0){
                        id_purchase = apiPenerimaan.getId_purchase();
                        tgl_purchase = apiPenerimaan.getCreated();
                        note = apiPenerimaan.getNote();
                        dt_purchase =apiPenerimaan.getDt_purchase();
                        nm_area =apiPenerimaan.getNm_area();
                        nm_singkat =apiPenerimaan.getNm_singkat();

                        nm_suplier = apiPenerimaan.getNm_suplier();

                        npwp = apiPenerimaan.getNpwp();
                        name_penyedia = apiPenerimaan.getName_penyedia();
                        area = apiPenerimaan.getArea();
                        alamat = apiPenerimaan.getAlamat();
                        id_trans = apiPenerimaan.getId_trans();

                        admin = apiPenerimaan.getAdmin();
                        id = apiPenerimaan.getId();
                        id_area = apiPenerimaan.getId_area();
                        diterima = apiPenerimaan.getDiterima();
                    }

                    harga_total +=(apiPenerimaan.getHarga()*apiPenerimaan.getQty());
//                    (new SimpleDateFormat("dd MMM yyyy")
//                            .format(
//                                    new Date((Long.parseLong(penerimaanbrgApi.getCreated())*1000))
//                            )).toString()
                    i++;
                }
//                Log.d("19201299",
//                        "onResponse:("+modelItemBrgs.size()+")Model Item BRGS: "+modelItemBrgs.get(0).toString());
//                Log.d("19201299", "onResponse: "+
//                                "{id_purchase = "+id_purchase+","+
//                                "tgl_purchase = "+tgl_purchase+","+
//                                "note = "+note+","+
//                                "dt_purchase = "+dt_purchase+","+
//                                "nm_area = "+nm_area+","+
//                                "nm_singkat = "+nm_singkat+","+
//                                "nm_suplier = "+nm_suplier+","+
//                                "alasuplier = "+alasuplier+","+
//                                "npwp = "+npwp+","+
//                                "name_penyedia = "+name_penyedia+","+
//                                "status = "+status+","+
//                                "area = "+area+","+
//                                "alamat = "+alamat+","+
//                                "id_trans = "+id_trans+","+
//                                "jml_item = "+jml_item+","+
//                                "admin = "+admin+", Id = "+
//                                id+", Id_area = "+
//                                id_area+", Id_supplier = "+
//                                id_supplier+", Diterima = "+
//                                diterima+",Harga_total = "+
//                        harga_total
//                        );
                jml_item = i-1;
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

                tv_idtrans.setText(id_purchase);
                tv_area.setText(nm_area);
                tv_sts.setText(status);
                tv_ala_supplier.setText(alasuplier);
                tv_tglpurchase.setText((new SimpleDateFormat("dd MMMM yyyy", Locale.US)
                        .format(
                                new Date((Long.parseLong(String.valueOf(tgl_purchase))*1000))
                        )).toString());
                tv_jml_item_bottom.setText(String.valueOf(jml_item));
                if (harga_total < (double) 100000000) {
                    tv_total_hrg.setText("Rp. "+formatNumber.setFormatNumber((double) harga_total));
                    ttl_hrg2_layout.setVisibility(View.GONE);
                    ttl_hrg_layout.setVisibility(View.VISIBLE);
                }else{
                    tv_total_hrg2.setText("Rp. "+formatNumber.setFormatNumber((double) harga_total));
                    ttl_hrg2_layout.setVisibility(View.VISIBLE);
                    ttl_hrg_layout.setVisibility(View.GONE);
                }

                recyclerViewItem.setAdapter(adapter);
                recyclerViewItem.setVisibility(View.VISIBLE);
                relative_content.setVisibility(View.VISIBLE);
                bottom_sheet.setVisibility(View.VISIBLE);
                tv_not_found.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ApiPenerimaan>> call, Throwable t) {
                Call<ApiPenerimaan> callStatus =
                        jsonPlaceHolderApi.getResponPenerimaanCartStatus(id_user);
                callStatus.enqueue(new Callback<ApiPenerimaan>() {
                    @Override
                    public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Terjadi error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                        }
                        shimmerFrameLayoutItem.setVisibility(View.GONE);
                        shimmerFrameLayoutItem.stopShimmer();

                        shimmerFrameLayout_parent.stopShimmer();
                        shimmerFrameLayout_parent.setVisibility(View.GONE);

                        tv_not_found.setVisibility(View.VISIBLE);
                        tv_not_found_parent.setVisibility(View.VISIBLE);
                        relative_content.setVisibility(View.GONE);
                        bottom_sheet.setVisibility(View.GONE);
                        if (!response.body().getMsg().isEmpty()) {
                            tv_not_found_parent.setText((response.body().getMsg()).toUpperCase(Locale.ROOT));
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                        shimmerFrameLayoutItem.setVisibility(View.GONE);
                        shimmerFrameLayoutItem.stopShimmer();
                        shimmerFrameLayout_parent.stopShimmer();
                        shimmerFrameLayout_parent.setVisibility(View.GONE);
                        tv_not_found.setVisibility(View.VISIBLE);
                        tv_not_found_parent.setVisibility(View.VISIBLE);
                        relative_content.setVisibility(View.GONE);
                        bottom_sheet.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    @Override
    public void onItemClick(int position, View view) {
        AlertDialog.Builder dialog;
        TextView tv_qty,tv_ttlHarga;

        tv_qty = view.findViewById(R.id.tvQtyPener);
        tv_ttlHarga = view.findViewById(R.id.tvTtlHrgPener);
        DialogCustom dialogCustom = new DialogCustom(DetailPenerimaanActivity.this,
                viewGroup,
                R.layout.input_alert_dialog,
                R.drawable.ic_person_edit);
        dialogCustom.setCustomDialog();
        final TextInputLayout inputLayout2 =
                dialogCustom.getViewInflated().findViewById(R.id.layoutInput);
        final TextInputEditText EditTextInput2 =
                dialogCustom.getViewInflated().findViewById(R.id.editTextInput);

        final TextInputLayout inputLayout =
                dialogCustom.getViewInflated().findViewById(R.id.layoutInput2);
        final TextInputEditText EditTextInput =
                dialogCustom.getViewInflated().findViewById(R.id.editTextInput2);

        inputLayout2.setVisibility(View.VISIBLE);
        inputLayout.setVisibility(View.VISIBLE);
        inputLayout2.setHint("Harga");

        dialogCustom.setTitle("Edit Item");

        inputLayout.setHint("QTY");

        dialogCustom.setView(dialogCustom.getViewInflated());

        EditTextInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditTextInput2.setInputType(InputType.TYPE_CLASS_NUMBER);
        EditTextInput.setText(
                tv_qty.getText()
        );
        EditTextInput2.setText(
                ""+modelItemBrgs.get(position).getHarga()
        );

        dialogCustom.getDialog().setPositiveButton("Next", (dialogInterface,i)->{
            String m_input = EditTextInput.getText().toString();
            String m_input2 = EditTextInput2.getText().toString();
            Toast.makeText(DetailPenerimaanActivity.this,
                    "id item"+modelItemBrgs.get(position).getId_item(), Toast.LENGTH_SHORT).show();
        });

        dialogCustom.getDialog().setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.cancel();
        });

        AlertDialog dialog2 = dialogCustom.getDialog().create();
        dialog2.show();
        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        EditTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newInput = editable.toString();
                String oldInput = EditTextInput2.getText().toString();
                if(TextUtils.isEmpty(editable)){
                    ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }else{
                    ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
        EditTextInput2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newInput = editable.toString();
                String oldInput = EditTextInput2.getText().toString();
                if(TextUtils.isEmpty(editable)){
                    ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }else{
                    ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

    }
}