package com.persediaan.de;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.adapter.AdapterDaftartBarang;
import com.persediaan.de.adapter.AdapterItemPenerimaan;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiDaftarBarang;
import com.persediaan.de.api.ApiDownload;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.ApiSimpan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.Currency;
import com.persediaan.de.data.DialogCustom;
import com.persediaan.de.data.ListItem;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.javaKey.JavaKeyBoard;
import com.persediaan.de.model.ModelDaftarItem;
import com.persediaan.de.model.ModelItemBrg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.http2.Http2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.HTTP;

public class DetailPenerimaanActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    public static String ID_TRANS = "ID_TRANS",
                        AREA = "AREA",
                        TITLE = "TITLE",
                        STS = "STS",
                        TGL_PURCHASE = "TGL_PURCHASE",
                        JML_ITEM ="JML_ITEM",
                        PENYEDIA = "PENYEDIA",
                        ALA_PENYEDIA = "ALA_PENYEDIA",
                        JML_ITEM_BOTTOM = "JML_ITEM_BOTTOM",
                        TOTAL_HRG = "TOTAL_HRG",
                        NOTE = "NOTE";
    double hrg=0,qty = 0;
    boolean isInputText=true;
    Vibrator vibrator;
    Currency formatNumber;

    Toolbar toolbar;
    ImageView tb_imgBtn,btn_add_item;
    ActionBar bar;

    ScrollView scrollView;

    RecyclerView recyclerViewItem;

    TextView tb_title,tv_idtrans,tv_area,tv_sts,tv_tglpurchase,tv_jml_item_bottom,tv_total_hrg,
            tv_total_hrg2,tv_not_found,tv_not_found_parent,tv_ala_supplier,tv_lbl_detailItem,tv_lblarea;
    TextView tv_supplier;
//  <</  Button Sheet
    RelativeLayout ttl_hrg_layout;
    RelativeLayout ttl_hrg2_layout;
    RelativeLayout btn_layout_bottom_sheet;
    LinearLayout linear_layout_tll_hrg;
    RelativeLayout relative_content;
    LinearLayout linear_bottom_sheet;
    LinearLayout bottom_sheet;
    TextInputEditText tiet_note;
    CardView card_bottom_sheet;

    int heightLong=0,heightShort=0,prsHeight=0,height = 0;
    float iTouchY = 0f;
//    Bottom Sheet />>

    ArrayList<ModelItemBrg> modelItemBrgs;

    View view_collapse;

//    Session
    Bundle extras;
    HashMap<String,Integer> detailUserInt;
    HashMap<String,String> listitemname;
    HashMap<String,String> detailUser;
    SessionManager sessionManagerUser;
    SessionManager sessionManagerScan;

    Button btn_simpan,btn_batal,btnDonloadPdf;
    boolean vibrateOnOf80 = true;
    boolean vibrateOnOf60 = true;

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

        formatNumber = new Currency("Rp. ",",");

        viewGroup = (ViewGroup) findViewById(android.R.id.content);

        scrollView = (ScrollView) findViewById(R.id.scrollViewDetail);
        scrollView.setVisibility(View.GONE);
        toolbar =findViewById(R.id.toolbar);
        tb_imgBtn = toolbar.findViewById(R.id.tbImgBtn);
        btn_add_item = findViewById(R.id.btnImageAddItem);
        tb_title = toolbar.findViewById(R.id.tb_title);

        tv_not_found = findViewById(R.id.tvItemNotFound);
        tv_lbl_detailItem = findViewById(R.id.tvlblDetailItem);
        tv_not_found_parent = findViewById(R.id.tvItemNotFoundParent);

        recyclerViewItem = findViewById(R.id.recyclerItem);

//      Bottom Sheet
        view_collapse = findViewById(R.id.viewCollapse);
        card_bottom_sheet = findViewById(R.id.card3Pener);
        ttl_hrg_layout = findViewById(R.id.ttlHrgLayout);
        ttl_hrg2_layout = findViewById(R.id.ttlHrg2Layout);

//        1
        linear_bottom_sheet = findViewById(R.id.linearBottomsheet);
//        2
        linear_layout_tll_hrg = findViewById(R.id.linearLayoutTllHrg);
//        3
        btn_layout_bottom_sheet = findViewById(R.id.btnLayoutBottomSheet);

        tv_total_hrg2 = findViewById(R.id.tvTotalhrg2);
        tv_supplier = findViewById(R.id.tvSupplierBottom);
        tv_ala_supplier = findViewById(R.id.tvAlamatBottom);

        bottom_sheet = findViewById(R.id.BottomSheet);

        relative_content = findViewById(R.id.RelativeContent);

        tv_idtrans = findViewById(R.id.tvPurchase);
        tv_area = findViewById(R.id.tvArea);
        tv_lblarea = findViewById(R.id.tv_lblarea);
        tv_sts = findViewById(R.id.tvSts);
        tv_tglpurchase = findViewById(R.id.tvTglpurchase);
        tv_jml_item_bottom = findViewById(R.id.tvJmlItemBottom);
        tv_total_hrg = findViewById(R.id.tvTotalhrg);
        tiet_note = findViewById(R.id.tietNote);

        btn_simpan = findViewById(R.id.btnSimpan);
        btn_batal = findViewById(R.id.btnBatal);
        btnDonloadPdf = findViewById(R.id.btnDonloadPdf);
        btnDonloadPdf.setVisibility(View.GONE);

        shimmerFrameLayoutItem = findViewById(R.id.shimerLayoutItem);
        shimmerFrameLayoutItem.startShimmer();
        shimmerFrameLayoutItem.setVisibility(View.VISIBLE);
        shimmerFrameLayout_parent = findViewById(R.id.ShimmerFrameParentDetail);
        shimmerFrameLayout_parent.startShimmer();
        shimmerFrameLayout_parent.setVisibility(View.VISIBLE);

        ttl_hrg2_layout.setVisibility(View.GONE);

        sessionManagerUser = new SessionManager(getApplicationContext(),"login");
        sessionManagerScan = new SessionManager(getApplicationContext(),"scan");
        detailUserInt = sessionManagerUser.getUserDetailInt();
        detailUser = sessionManagerUser.getUserDetail();



        tb_title.setText("Detail Penerimaan");
        height = card_bottom_sheet.getHeight();
        if (height==0){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    height = card_bottom_sheet.getHeight();
                }
            }, 500);
        }
        view_collapse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float xDown=0,yDown=0,movedX=0,movedY=0;
                int heightMove = 0;
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        xDown = motionEvent.getX();
                        yDown = motionEvent.getY();
//                        height = card_bottom_sheet.getHeight();
                        iTouchY = 0.0f;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        movedX = motionEvent.getX();
                        movedY = motionEvent.getY();

                        float distanceX = movedX-xDown;
                        float distancey = movedY-yDown;
                        iTouchY -=distancey;
                        heightMove = (int)(card_bottom_sheet.getHeight() - distancey);
                        prsHeight = (heightMove*100)/height;
                        if (prsHeight>=50&&prsHeight<=105){
                            card_bottom_sheet.getLayoutParams().height =heightMove;
                        }
                        card_bottom_sheet.requestLayout();
//                        tv_total_hrg.setText("Height 60%("+(60f/100f)*height+")"+prsHeight+
//                                "%=>Height 35%"+(35f/100f)*height);
//                        tv_total_hrg.setText("Height("+height+")"+prsHeight+"%=>"+heightMove);
//                        btn_simpan.setText(""+prsHeight+"%");
//                        if (iTouchY<=)
                        SessionManager session_setting = new SessionManager(DetailPenerimaanActivity.this,
                                SessionManager.SETTING);
                        if (session_setting.getSetting("vibrate")=="on") {
                            if (prsHeight >= 80) {
                                if (vibrateOnOf80) {
                                    vibrateOnOf80 = false;
                                    createVibrate();
                                }
                            } else if (prsHeight >= 60) {
                                if (vibrateOnOf60) {
                                    vibrateOnOf60 = false;
                                    createVibrate();
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        vibrateOnOf80 = true;
                        vibrateOnOf60 = true;
                        if (prsHeight>=80){
                            card_bottom_sheet.getLayoutParams().height = (int) ((100f/100f)*height);
                            linear_bottom_sheet.setVisibility(View.VISIBLE);
                            linear_layout_tll_hrg.setVisibility(View.VISIBLE);
                        }else if(prsHeight<80&&prsHeight>=60){
                            card_bottom_sheet.getLayoutParams().height = (int) ((65f/100f)*height);
                            linear_bottom_sheet.setVisibility(View.GONE);
                            linear_layout_tll_hrg.setVisibility(View.VISIBLE);
                        }else if(prsHeight<60){
                            card_bottom_sheet.getLayoutParams().height = (int) ((50f/100f)*height);
                            linear_bottom_sheet.setVisibility(View.GONE);
                            linear_layout_tll_hrg.setVisibility(View.GONE);
                        }
                        break;
                }
                return true;
            }
        });
        view_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                heightLong = btn_layout_bottom_sheet.getHeight()+
//                        linear_bottom_sheet.getHeight()+
//                        ttl_hrg2_layout.getHeight();
//                if (stscolapse) {
//                    linear_bottom_sheet.setVisibility(View.VISIBLE);
//                    linear_layout_tll_hrg.setVisibility(View.VISIBLE);
//                }else {
//                    linear_bottom_sheet.setVisibility(View.GONE);
//                }
                stscolapse = !stscolapse;
            }
        });

        tb_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        extras = getIntent().getExtras();
        tv_total_hrg.setText(extras.getString(TOTAL_HRG));

        tiet_note.setText(extras.getString(NOTE));
        if (extras.getString(ID_TRANS)!=null) {
            if(extras.getString(STS).equals("kv1")||extras.getString(STS).equals("kv0")){
                detailPener(extras.getString(STS),extras);
            }else{
                loadItem(detailUserInt.get(SessionManager.USER_ID), extras.getString(ID_TRANS));
            }
        }else{
            finish();
        }
        tiet_note.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b){
//                    card_bottom_sheet.getLayoutParams().height =
//                            ((heightShort)-50);
//                    card_bottom_sheet.requestLayout();
                    linear_bottom_sheet.setVisibility(View.GONE);
                    linear_layout_tll_hrg.setVisibility(View.GONE);

                    scrollView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.setScrollY(800);
                        }
                    },500);
                }
            }
        });
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailPenerimaanActivity.this);
                dialog.setTitle("Simpan Transaksi");
                dialog.setMessage("Transaksi ini akan disimpan, apakah anda ingin melanjutkan?");

                dialog.setIcon(R.drawable.ic_baseline_notification_important_24);
                dialog.setCancelable(true);
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
                AlertDialog dialog1 = dialog.create();
                dialog1.show();
            }
        });

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailPenerimaanActivity.this);
                dialog.setTitle("Batal Transaksi");
                dialog.setMessage("Transaksi ini akan dibatalkan, apakah anda ingin melanjutkan?");

                dialog.setIcon(R.drawable.ic_baseline_warning_24);
                dialog.setCancelable(true);
                dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

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
                AlertDialog dialog1 = dialog.create();
                dialog1.show();
            }
        });

        btn_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManagerScan.clearSession();
                sessionManagerScan.EditScanner(true);
                finish();
            }
        });
    }
    public void createVibrate(){
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, 10));
        } else {
            vibrator.vibrate(50);
        }
    }
    private void detailPener(String sts, Bundle extras) {
        btnDonloadPdf.setVisibility(View.VISIBLE);
        shimmerFrameLayoutItem.setVisibility(View.GONE);
        shimmerFrameLayoutItem.stopShimmer();

        shimmerFrameLayout_parent.stopShimmer();
        shimmerFrameLayout_parent.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        relative_content.setVisibility(View.VISIBLE);
//        bottom_sheet.setVisibility(View.VISIBLE);
        recyclerViewItem.setVisibility(View.VISIBLE);
        tv_not_found.setVisibility(View.GONE);

        tv_idtrans.setText(extras.getString(ID_TRANS));
        tv_area.setText(extras.getString(ALA_PENYEDIA));
        tv_lblarea.setText("Alamat");
        tv_sts.setText(extras.getString(STS).equals("kv1")?"konversi":"Belum dikonversi");

        tv_ala_supplier.setText(extras.getString(ALA_PENYEDIA));
//        tv_supplier.setText(extras.getString(PENYEDIA));

        tv_tglpurchase.setText(extras.getString(TGL_PURCHASE));

        tv_lbl_detailItem.setText(extras.getString(PENYEDIA)+"("+extras.getString(JML_ITEM)+")");

//      get item
        Call<ArrayList<ApiDaftarBarang>> call = jsonPlaceHolderApi.getiItem();
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(DetailPenerimaanActivity.this, "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<ApiDaftarBarang> apiDaftarBarangs = response.body();

                listitemname = new HashMap<>();
                for (ApiDaftarBarang apiDaftarBarang:apiDaftarBarangs){
                    listitemname.put(apiDaftarBarang.getId_item(),apiDaftarBarang.getNm_item());
                }

                {
                    //      Load Detail Item
                    ArrayList<ModelItemBrg> modelItemBrgsDetail = new ArrayList<>();
                    for (int i = 0; i < extras.getStringArrayList(ListItem.NM_ITEM).size(); i++) {
//                    for (int i = 0; i < (extras.getStringArrayList(ListItem.NM_ITEM).size() > 10? 100 : extras.getStringArrayList(ListItem.NM_ITEM).size()); i++) {
                        modelItemBrgsDetail.add(new ModelItemBrg(i,
                                        extras.getStringArrayList(ListItem.ID_ITEM).get(i),
                                        Integer.parseInt(extras.getStringArrayList(ListItem.QTY).get(i)), 0,
                                        Integer.parseInt(extras.getStringArrayList(ListItem.HARGA).get(i)), 0,
                                        listitemname.get(extras.getStringArrayList(ListItem.ID_ITEM).get(i)),
                                        extras.getStringArrayList(ListItem.NM_ECER).get(i),
                                        extras.getStringArrayList(ListItem.NM_SATUAN).get(i)

                                ).setAction(false)
                        );
                    }
//                    get size screen
//                    {
                        WindowManager wm =
                                (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                        Display display = wm.getDefaultDisplay();
                        DisplayMetrics metrics = new DisplayMetrics();
                        display.getMetrics(metrics);
                        int sHeight = metrics.heightPixels;
//                    }
                    AdapterItemPenerimaan adapter = new AdapterItemPenerimaan(modelItemBrgsDetail,
                            DetailPenerimaanActivity.this);
                    recyclerViewItem.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    recyclerViewItem.setHasFixedSize(true);
                    recyclerViewItem.setItemAnimator(new DefaultItemAnimator());
                    if(extras.getStringArrayList(ListItem.NM_ITEM).size()>5){
                        recyclerViewItem.getLayoutParams().height =
                                (int)((50f/100)*sHeight);
//                        recyclerViewItem.requestLayout();
                        Log.d("19201299",
                                "onResponse: "+(int)((50f/100)*sHeight)+
                                        "("+sHeight+")");
                    }
                    recyclerViewItem.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                Toast.makeText(DetailPenerimaanActivity.this,
                        "Server error["+JsonPlaceHolderApi.getMessageApi(t.getMessage())!="false"?t.getMessage()
                                :JsonPlaceHolderApi.getMessageApi(t.getMessage())+"]",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnDonloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDonloadPdf.setEnabled(false);
                if (ContextCompat.checkSelfPermission(DetailPenerimaanActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                        PackageManager.PERMISSION_GRANTED){
                    cekDownloadPdf(SessionManager.GETDOWNLOADPENER,tv_idtrans.getText().toString());
                }else {
                    requestPermission();
                }
            }
        });
    }

    private void loadDetailItem(){
        String[][] arrDaftarBarang=null;
        Call<ArrayList<ApiDaftarBarang>> call = jsonPlaceHolderApi.getiItem();
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(DetailPenerimaanActivity.this, "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<ApiDaftarBarang> apiDaftarBarangs = response.body();

                int index = 0;
                for (ApiDaftarBarang apiDaftarBarang:apiDaftarBarangs){
                    arrDaftarBarang[index][0]=apiDaftarBarang.getId_item();
                    arrDaftarBarang[index][1]=apiDaftarBarang.getNm_item();
                    arrDaftarBarang[index][2]=apiDaftarBarang.getNm_satuan();
                    index++;
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                Toast.makeText(DetailPenerimaanActivity.this,
                        "Server error["+JsonPlaceHolderApi.getMessageApi(t.getMessage())!="false"?t.getMessage()
                                :JsonPlaceHolderApi.getMessageApi(t.getMessage())+"]",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cekDownloadPdf(String getdownloadpener,String id_trans){
        Toast.makeText(DetailPenerimaanActivity.this, "Menghubungkan...", Toast.LENGTH_SHORT).show();
        Call<ApiDownload> call = jsonPlaceHolderApi.cekFilePdf(id_trans);
        call.enqueue(new Callback<ApiDownload>() {
            @Override
            public void onResponse(Call<ApiDownload> call, Response<ApiDownload> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(DetailPenerimaanActivity.this, "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_LONG).show();
                    btnDonloadPdf.setEnabled(true);
                    return;
                }
                if (!response.body().isSts()){
                    new AlertDialog.Builder(DetailPenerimaanActivity.this)
                            .setTitle("Server Error")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_danger)
                            .setMessage(response.body().getMsg())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    btnDonloadPdf.setEnabled(true);
                                }
                            }).create().show();
                }
            }

            @Override
            public void onFailure(Call<ApiDownload> call, Throwable t) {
                if (JsonPlaceHolderApi.getMessageApi(t.getMessage())!="false") {
                    String msg = JsonPlaceHolderApi.getMessageApi(t.getMessage());
                    new AlertDialog.Builder(DetailPenerimaanActivity.this)
                            .setTitle("Server Error")
                            .setCancelable(false)
                            .setIcon(R.drawable.ic_danger)
                            .setMessage(msg)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    btnDonloadPdf.setEnabled(true);
                                }
                            }).create().show();
                }else{
                    downloadFile(getdownloadpener+id_trans);
                }
            }
        });
    }

    private void downloadFile(String getdownloadpener) {
        String getUrl = getdownloadpener;

        AlertDialog.Builder dialog2 = new AlertDialog.Builder(DetailPenerimaanActivity.this);
        LayoutInflater inflater2= getLayoutInflater();
        View dialogView = inflater2.inflate(R.layout.type_item_code,null);
        dialog2.setView(dialogView);
        dialog2.setCancelable(true);
        dialog2.setIcon(R.mipmap.ic_launcher_packages_splashscreen);
        dialog2.setTitle("File Name");
        EditText edtCode = dialogView.findViewById(R.id.tietEditCode);
        TextInputLayout layoutInput = dialogView.findViewById(R.id.tfEditCode);
        layoutInput.setHint("Masukan Nama File");
        edtCode.setInputType(1);
        String title = "Terima Laporan "+tv_idtrans.getText();
        if(title!=null){
            edtCode.setText(title);
        }else{
            edtCode.setText("");
        }
        edtCode.requestFocus();
        dialog2.setPositiveButton("Next", (dialog, which) -> {
            String tb = edtCode.getText().toString();
            Log.d("19201299",
                    "downloadFile DetailPenerimaan[490]: title=>"+URLUtil.isFileUrl(URLUtil.guessUrl(getUrl)));
            Toast.makeText(DetailPenerimaanActivity.this, "Mendownload "+tb+".pdf",
                    Toast.LENGTH_LONG).show();
            try {
                DownloadManager.Request request= new DownloadManager.Request(Uri.parse(URLUtil.guessUrl(getUrl)));
                String title1 = URLUtil.guessFileName(getUrl,null,"application/pdf");
                request.setTitle(tb);
                request.setDescription(SessionManager.HOSTNAME);
                String cookie = CookieManager.getInstance().getCookie(getUrl);
                request.addRequestHeader("cookie",cookie);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        tb+".pdf");
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);

            } catch (Exception e) {
                Toast.makeText(DetailPenerimaanActivity.this, "Download Error", Toast.LENGTH_LONG).show();
                Log.d("19201299", "downloadFile[527]: Error!!"+e.getMessage());
            }

            dialog.dismiss();
        });
        dialog2.setNegativeButton("Cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                btnDonloadPdf.setEnabled(true);
            }
        });
        JavaKeyBoard javaKeyBoard = new JavaKeyBoard(dialog2.show().getWindow());
        javaKeyBoard.showInput();
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(DetailPenerimaanActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(DetailPenerimaanActivity.this)
                    .setTitle("Permession Needed")
                    .setCancelable(false)
                    .setMessage("This permission is needed bacause of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(DetailPenerimaanActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions(DetailPenerimaanActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                cekDownloadPdf(SessionManager.GETDOWNLOADPENER,tv_idtrans.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
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
                recyclerViewItem.setAdapter(null);
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
                                apiPenerimaan.getId(),
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
                jml_item = i-1;
                if (diterima==0){
                    status = "Belum Diterima";
                }else if (diterima ==1){
                    status = "Belum Dikonversi";
                }
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
                tiet_note.setText(note);
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
        lostFocus(getWindow().getCurrentFocus());

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
                ""+formatNumber.setFormatNumber((double)modelItemBrgs.get(position).getQty())
        );
        EditTextInput2.setText(
                "Rp. "+formatNumber.setFormatNumber((double)modelItemBrgs.get(position).getHarga())
        );

        dialogCustom.getDialog().setPositiveButton("Next", (dialogInterface,i)->{
            String m_input = EditTextInput.getText().toString();
            String m_input2 = EditTextInput2.getText().toString();

            Call <ApiPenerimaan> call =
                    jsonPlaceHolderApi.getResponEditCart(modelItemBrgs.get(position).getId(),
                            (int)qty,(int)hrg);
            call.enqueue(new Callback<ApiPenerimaan>() {
                @Override
                public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Terjadi error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.body().getMsg()!=null){
                        Toast.makeText(getApplicationContext(), ""+response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                    loadItem(detailUserInt.get(SessionManager.USER_ID),extras.getString(ID_TRANS));
                }

                @Override
                public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
                }
            });
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
                String r = (charSequence.toString()).replaceAll("[^0-9]","");
                if (!r.isEmpty()){
                    qty = Double.valueOf(r);
                }else{
                    qty = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newInput = editable.toString();
                String oldInput = EditTextInput2.getText().toString();

                if (isInputText){
                    isInputText = false;
                    EditTextInput.setText(formatNumber.setFormatNumber(qty));
                    EditTextInput.setSelection(EditTextInput.getText().length());
                }else{
                    isInputText = true;
                }

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
                String r = (charSequence.toString()).replaceAll("[^0-9]","");
                if (!r.isEmpty()){
                    hrg = Double.valueOf(r);
                }else{
                    hrg = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String newInput = editable.toString();
                String oldInput = EditTextInput2.getText().toString();
                if (isInputText){
                    isInputText = false;
                    EditTextInput2.setText("Rp. "+formatNumber.setFormatNumber(hrg));
                    EditTextInput2.setSelection(EditTextInput2.getText().length());
                }else{
                    isInputText = true;
                }
                if(TextUtils.isEmpty(editable)){
                    ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }else{
                    ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

    }

    @Override
    public void onItemClick1(int position, View view) {
         TextView nm_item = view.findViewById(R.id.tvNmItemPener);
         lostFocus(getWindow().getCurrentFocus());
         AlertDialog.Builder dialog = new AlertDialog.Builder(DetailPenerimaanActivity.this);
         dialog.setTitle("Hapus Item");
         dialog.setMessage("Apakah anda yakin ingin menghapus item "+nm_item.getText());

         dialog.setIcon(R.drawable.ic_baseline_warning_24);
         dialog.setCancelable(true);
         dialog.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dialogInterface.cancel();
             }
         });
         dialog.setPositiveButton("Next", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                Call<String> call =
                        jsonPlaceHolderApi.getResponHpsItem(modelItemBrgs.get(position).getId());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Terjadi erorr yang tidak diketahui", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (response.body()!=null){
                            Toast.makeText(getApplicationContext(),
                                    ""+response.body(),
                                    Toast.LENGTH_SHORT).show();
                            loadItem(detailUserInt.get(SessionManager.USER_ID), extras.getString(ID_TRANS));
                        }else {
                            Toast.makeText(getApplicationContext(),
                                    ""+response.body(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Server error", Toast.LENGTH_SHORT).show();
                    }
                });
             }
         });
         AlertDialog dialog1 = dialog.create();
         dialog1.show();
    }
    public void lostFocus(View viewFocus){
        tiet_note.clearFocus();
        if (viewFocus!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewFocus.getWindowToken(),0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SessionManager sessionTranstition = new SessionManager(DetailPenerimaanActivity.this,
                "transtition");
        sessionTranstition.setTranstition("receive",true);
    }
}