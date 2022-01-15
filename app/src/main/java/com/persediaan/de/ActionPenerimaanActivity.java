package com.persediaan.de;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.persediaan.de.api.ApiPenerimaan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.Currency;
import com.persediaan.de.data.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActionPenerimaanActivity extends AppCompatActivity {

    TextView code_brg,nm_supplier,ala_supplier,nm_item,ttl_harga,id_item,satuan;
    TextInputEditText tiet_harga,tiet_qty;
    Button btn_add;
    ImageButton btn_scan_again;
    LinearLayout linearLayout_scroll;

//    Shimmer Loading
    ShimmerFrameLayout shimmerFrameLayout;

//  Session
    SessionManager sessionScanner;
    SessionManager sessionUser;
    SessionManager sessionTranstition;
    HashMap<String,Integer> detailUserInt;
    HashMap<String,String> detailUserString;

    boolean editScanner = false;

    int id;

    boolean isInputText=false;

    double hrg=0,qty = 0;
    String result = "";

    View viewFocus;


//    Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Currency formatNumber;

    HashMap<String,String> result_scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_penerimaan);

        formatNumber = new Currency("Rp. ",",");

        sessionTranstition = new SessionManager(this, "transtition");

//        Connection
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        code_brg = findViewById(R.id.codeBRG);
        nm_supplier = findViewById(R.id.tvResNmSupplier);
        ala_supplier = findViewById(R.id.tvResAlaSupplier);
        id_item = findViewById(R.id.tvResIdItem);
        nm_item = findViewById(R.id.tvResNmItem);
        tiet_harga = findViewById(R.id.tietHarga);
        tiet_qty = findViewById(R.id.tietQty);
        satuan = findViewById(R.id.tvResSatuan);
        ttl_harga = findViewById(R.id.tvResTotalHrg);

        btn_add = findViewById(R.id.btnAdd);
        btn_scan_again = findViewById(R.id.btnEditScan);
        linearLayout_scroll = findViewById(R.id.LinearScrollAction);

        Toolbar toolbar = findViewById(R.id.toolbarPenerimaan);
        toolbar.setTitle("Add Penerimaan");
        setSupportActionBar(toolbar);

        shimmerFrameLayout = findViewById(R.id.ShimmerLayout);


//      Init Session
        sessionUser = new SessionManager(this,"login");
        detailUserString = sessionUser.getUserDetail();
        detailUserInt = sessionUser.getUserDetailInt();

        sessionScanner = new SessionManager(this,"scan");
        result_scanner = sessionScanner.getScanResult();

        String sCodeBRG = result_scanner.get(SessionManager.SCANFULLR);

        code_brg.setText(sCodeBRG);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadData(sCodeBRG,tiet_harga);
//        tiet_harga.requestFocus();
        tiet_harga.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    isInputText = true;
                }
            }
        });
        tiet_qty.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    isInputText = true;
                }
            }
        });
        tiet_qty.addTextChangedListener(new TextWatcher() {
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
                if (isInputText){
                    isInputText = false;
                    tiet_qty.setText(formatNumber.setFormatNumber(qty));
                    tiet_qty.setSelection(tiet_qty.getText().length());
                    ttl_harga.setText("Rp. "+formatNumber.setFormatNumber((hrg*qty)));
                }else{
                    isInputText = true;
                }
            }
        });

        tiet_harga.addTextChangedListener(new TextWatcher() {
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
//                String r = (editable.toString()).replaceAll("[^0-9]","");
                if (isInputText){
                    isInputText = false;
                    tiet_harga.setText("Rp. "+formatNumber.setFormatNumber(hrg));
                    tiet_harga.setSelection(tiet_harga.getText().length());
                    ttl_harga.setText("Rp. "+formatNumber.setFormatNumber((hrg*qty)));
                }else{
                    isInputText = true;
                }
//                Log.d("19201299", "afterTextChanged: i= "+editable.toString());
            }
        });

        btn_scan_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editScanner = true;
                sessionScanner.EditScanner(editScanner);
                sessionTranstition.setTranstition("scan",true);
                finish();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lostFocus(getWindow().getCurrentFocus());
                Call <ApiPenerimaan> call = jsonPlaceHolderApi.getResponAddItem(
                        detailUserInt.get(SessionManager.USER_ID),
                        id,(int) qty,(int) hrg
                );
                call.enqueue(new Callback<ApiPenerimaan>() {
                    @Override
                    public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                        if (!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),
                                    "Terjadi Error yang tidak diketahui", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        sessionScanner.clearSession();
                        onBackPressed();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void createVibrate(long millisecond,int repeat){
//        for (int i = 0; i < 5; i++) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                    if (Build.VERSION.SDK_INT >= 26) {
//                        vibrator.vibrate(VibrationEffect.createOneShot(millisecond, 10));
//                    } else {
//                        vibrator.vibrate(millisecond);
//                    }
//                }
//            }, 2000);
//        }

    }

    public void loadData(String barcode,TextInputEditText focus){
        Call <List<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaan(barcode);
        call.enqueue(new Callback<List<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<List<ApiPenerimaan>> call, Response<List<ApiPenerimaan>> response) {
                List<ApiPenerimaan> apiPenerimaans = response.body();
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_SHORT).show();
                    sessionScanner.clearSession();
                    sessionScanner.EditScanner(true);
                    finish();
                    return;
                }

                hrg = (double)
                        apiPenerimaans.get(0).getHarga();
                qty = (double)apiPenerimaans.get(0).getQty();

                id = apiPenerimaans.get(0).getId();

                nm_supplier.setText(apiPenerimaans.get(0).getNm_suplier());
                ala_supplier.setText(apiPenerimaans.get(0).getAlasuplier());
                id_item.setText(""+apiPenerimaans.get(0).getId_item());
                nm_item.setText(apiPenerimaans.get(0).getNm_item());
                tiet_harga.setText("Rp. "+formatNumber.
                        setFormatNumber((double)
                                        apiPenerimaans.get(0).getHarga())
                );
                tiet_qty.setText(formatNumber.setFormatNumber((double)apiPenerimaans.get(0).getQty()));
                ttl_harga.setText("Rp. "+formatNumber.
                        setFormatNumber((double)
                                (apiPenerimaans.get(0).getHarga()*
                                apiPenerimaans.get(0).getQty())
                        ));
                tiet_harga.setSelection(tiet_harga.getText().length(),0);
                code_brg.setVisibility(View.VISIBLE);
                btn_scan_again.setVisibility(View.VISIBLE);
                linearLayout_scroll.setVisibility(View.VISIBLE);
                shimmerFrameLayout.setVisibility(View.GONE);
                tiet_harga.requestFocus();
                showSoftInput(tiet_harga);
            }

            @Override
            public void onFailure(Call<List<ApiPenerimaan>> call, Throwable t) {
                Call<ApiPenerimaan> callStatus=
                        jsonPlaceHolderApi.getResponPenerimaanStatus(barcode,
                                detailUserInt.get(SessionManager.USER_ID));
                callStatus.enqueue(new Callback<ApiPenerimaan>() {
                    @Override
                    public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Terjadi Error Yang tidak diketahui", Toast.LENGTH_SHORT).show();
                            sessionScanner.clearSession();
                            sessionScanner.EditScanner(true);
                            finish();
                            return;
                        }
                        if(!(response.body().getMsg()).isEmpty()){
                            Toast.makeText(getApplicationContext(), ""+response.body().getMsg().toUpperCase(Locale.ROOT),
                                    Toast.LENGTH_LONG).show();
                            sessionScanner.clearSession();
                            sessionScanner.EditScanner(true);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                "Format barcode tidak sesuai",
                                Toast.LENGTH_SHORT).show();
                        createVibrate(1000,0);
                        sessionScanner.clearSession();
                        sessionScanner.EditScanner(true);
                        sessionTranstition.setTranstition("scan",true);
                        finish();
                    }
                });
            }
        });
    }
    public void lostFocus(View viewFocus){
        tiet_harga.clearFocus();
        tiet_qty.clearFocus();
        if (viewFocus!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewFocus.getWindowToken(),0);
        }
    }
    public void showSoftInput(View viewFocus){
        if (viewFocus!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(viewFocus,0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!sessionScanner.isEditScanner()){
            Intent PageDetailPenerimaan = new Intent(getApplicationContext(),DetailPenerimaanActivity.class);
            String idtrans = result_scanner.get(SessionManager.SCANFULLR);
            PageDetailPenerimaan.putExtra(DetailPenerimaanActivity.ID_TRANS,
                    idtrans);
            startActivity(PageDetailPenerimaan);
        }else {
            sessionScanner.EditScanner(false);
            sessionTranstition.setTranstition("receive",true);
        }
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
            if (!sessionScanner.isEditScanner()){
                sessionScanner.clearSession();
            }
    }
}