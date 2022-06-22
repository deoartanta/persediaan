package com.persediaan.de;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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

    Vibrator vibrator;

    int id;
    Bundle extra;
    String sCodeBRG;

    boolean isInputText=false;

    double hrg=0,qty = 0;
    String idTrans = "";



//    Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Currency formatNumber;

    HashMap<String,String> result_scanner;
    SessionManager session_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_penerimaan);

        extra = getIntent().getExtras();

        formatNumber = new Currency("Rp. ",",");

        sessionTranstition = new SessionManager(this, "transtition");

        session_setting = new SessionManager(ActionPenerimaanActivity.this,
                SessionManager.SETTING);

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

        sCodeBRG = extra.getString(ScanActivity.RESULT_FULL);

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

        loadData(sCodeBRG,tiet_harga, false);
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
                Intent i = new Intent(ActionPenerimaanActivity.this, ScanActivity.class);
                i.putExtra(ScanActivity.RESULT_FULL,sCodeBRG);
                i.putExtra(ScanActivity.SCANNER_FOR_RESULT,true);
                i.putExtra(ScanActivity.TYPESCAN,ScanActivity.SCANNER_TYPE_2);
                startActivityForResult(i,1);
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lostFocus(getWindow().getCurrentFocus());
                new AlertDialog.Builder(ActionPenerimaanActivity.this)
                        .setTitle("Konfirmasi")
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setMessage("Anda yakin ingin melanjutkan?")
                        .setCancelable(true)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sessionTranstition.setTranstition("receive",true);
                                {
                                    Call<ApiPenerimaan> call = jsonPlaceHolderApi.getResponAddItem(
                                            detailUserInt.get(SessionManager.USER_ID),
                                            id, (int) qty, (int) hrg
                                    );
                                    call.enqueue(new Callback<ApiPenerimaan>() {
                                        @Override
                                        public void onResponse(Call<ApiPenerimaan> call, Response<ApiPenerimaan> response) {
                                            if (!response.isSuccessful()) {
                                                createAlertDialog("Server Error","Terjadi Error " +
                                                        "yang tidak diketahui",R.drawable.ic_baseline_warning_24);
                                                return;
                                            }
                                            new AlertDialog.Builder(ActionPenerimaanActivity.this)
                                                    .setTitle("Informasi")
                                                    .setIcon(R.drawable.ic_baseline_warning_24)
                                                    .setMessage("Tambah item berhasil, ingin " +
                                                            "menambah data lagi?")
                                                    .setCancelable(false)
                                                    .setNegativeButton("Tidak",
                                                            new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            Intent PageDetailPenerimaan =
                                                                    new Intent(ActionPenerimaanActivity.this,
                                                                            DetailPenerimaanActivity.class);
                                                            PageDetailPenerimaan.putExtra(DetailPenerimaanActivity.ID_TRANS,
                                                                    sCodeBRG);
                                                            startActivity(PageDetailPenerimaan);
                                                            finish();
                                                        }
                                                    })
                                                    .setPositiveButton("Ya",
                                                            new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                                            sessionTranstition.setTranstition("scan",true);
                                                            finish();
                                                        }
                                                    })
                                                    .create().show();
                                        }

                                        @Override
                                        public void onFailure(Call<ApiPenerimaan> call, Throwable t) {
                                            Toast.makeText(getApplicationContext(),
                                                    "Server Error", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                }
                            }
                        })
                        .create().show();
            }
        });

    }
    private void createAlertDialog(String title,String msg,@DrawableRes int id){
        new AlertDialog.Builder(ActionPenerimaanActivity.this)
                .setTitle(title)
                .setIcon(id)
                .setMessage(msg)
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionScanner.EditScanner(false);
                        sessionTranstition.clearSession();
                        sessionTranstition.setTranstition("receive",true);
                        finish();
                    }
                })
                .setPositiveButton("Page scan Open", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        sessionTranstition.setTranstition("scan",true);
                        finish();
                    }
                })
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1){
            if (resultCode == RESULT_OK){
                loadData(data.getExtras().getString(ScanActivity.RESULT_FULL),tiet_harga,true);
                code_brg.setText(data.getExtras().getString(ScanActivity.RESULT_FULL));
            }
        }
    }

    public void createVibrate(){
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, 10));
        } else {
            vibrator.vibrate(100);
        }
    }

    public void loadData(String barcode, TextInputEditText focus, boolean b){
        Call <ArrayList<ApiPenerimaan>> call = jsonPlaceHolderApi.getResponPenerimaan(barcode,
                detailUserInt.get(SessionManager.USER_ID));
        call.enqueue(new Callback<ArrayList<ApiPenerimaan>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiPenerimaan>> call, Response<ArrayList<ApiPenerimaan>> response) {
                ArrayList<ApiPenerimaan> apiPenerimaans = response.body();
                if(!response.isSuccessful()){
                    if (session_setting.getSetting("vibrate")=="on"){
                        createVibrate();
                    }
                    Toast.makeText(ActionPenerimaanActivity.this,
                            JsonPlaceHolderApi.getMessageApi(response.message()),
                            Toast.LENGTH_SHORT).show();
                    sessionScanner.clearSession();
                    sessionScanner.EditScanner(false);
                    finish();
                    return;
                }

               if(apiPenerimaans.get(0).getMsg()!=null){
                   if (session_setting.getSetting("vibrate")=="on"){
                       createVibrate();
                   }
                   new AlertDialog.Builder(ActionPenerimaanActivity.this)
                           .setTitle("Gagal Mendapatkan Data")
                           .setIcon(R.drawable.ic_danger)
                           .setMessage(apiPenerimaans.get(0).getMsg())
                           .setCancelable(false)
                           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   if(!b){
                                       sessionScanner.EditScanner(false);
                                       sessionTranstition.clearSession();
                                       sessionTranstition.setTranstition("receive",true);
                                       finish();
                                   }else{
                                       dialogInterface.dismiss();
                                   }
                               }
                           })
                           .setPositiveButton("Page scan Open", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   if(!b){
//                                       Log.d("19201299", "ActionPenerimaanActivity: b=> "+b);
                                       finish();
                                   }else{
//                                       Log.d("19201299", "ActionPenerimaanActivity: b2=> "+b);
                                       Intent in = new Intent(ActionPenerimaanActivity.this,
                                               ScanActivity.class);
                                       in.putExtra(ScanActivity.RESULT_FULL,sCodeBRG);
                                       in.putExtra(ScanActivity.SCANNER_FOR_RESULT,true);
                                       in.putExtra(ScanActivity.TYPESCAN,
                                               ScanActivity.SCANNER_TYPE_2);
                                       startActivityForResult(in,1);
                                   }
                               }
                           })
                           .create().show();
               }else{
                   {
                       hrg = (double)
                               apiPenerimaans.get(0).getHarga();
                       qty = (double) apiPenerimaans.get(0).getQty();

                       id = apiPenerimaans.get(0).getId();

                       nm_supplier.setText(apiPenerimaans.get(0).getNm_suplier());
                       ala_supplier.setText(apiPenerimaans.get(0).getAlasuplier());
                       id_item.setText("" + apiPenerimaans.get(0).getId_item());
                       nm_item.setText(apiPenerimaans.get(0).getNm_item());
                       tiet_harga.setText("Rp. " + formatNumber.
                               setFormatNumber((double)
                                       apiPenerimaans.get(0).getHarga())
                       );
                       tiet_qty.setText(formatNumber.setFormatNumber((double) apiPenerimaans.get(0).getQty()));
                       ttl_harga.setText("Rp. " + formatNumber.
                               setFormatNumber((double)
                                       (apiPenerimaans.get(0).getHarga() *
                                               apiPenerimaans.get(0).getQty())
                               ));
                       tiet_harga.setSelection(tiet_harga.getText().length(), 0);
                       code_brg.setVisibility(View.VISIBLE);
                       btn_scan_again.setVisibility(View.VISIBLE);
                       linearLayout_scroll.setVisibility(View.VISIBLE);
                       shimmerFrameLayout.setVisibility(View.GONE);
                       tiet_harga.requestFocus();
                       showSoftInput(tiet_harga);
                   }
               }

            }

            @Override
            public void onFailure(Call<ArrayList<ApiPenerimaan>> call, Throwable t) {
                if (session_setting.getSetting("vibrate")=="on"){
                    createVibrate();
                }
                new AlertDialog.Builder(ActionPenerimaanActivity.this)
                    .setTitle("Server Sibuk")
                    .setIcon(R.drawable.ic_baseline_warning_24)
                    .setMessage(JsonPlaceHolderApi.getMessageApi(t.getMessage())!="false"?
                            JsonPlaceHolderApi.getMessageApi(t.getMessage()):"kode " +
                            "item anda["+barcode+"] tidak bisa kami identifikasi")
                    .setCancelable(true)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!b){
                                sessionScanner.EditScanner(false);
                                sessionTranstition.clearSession();
                                sessionTranstition.setTranstition("receive",true);
                                finish();
                            }else{
                                dialogInterface.dismiss();
                            }
                        }
                    })
                    .setPositiveButton("Page scan Open", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(!b){
//                                sessionTranstition.setTranstition("scan",true);
                                finish();
                            }else{
                                Intent in = new Intent(ActionPenerimaanActivity.this,
                                        ScanActivity.class);
                                in.putExtra(ScanActivity.RESULT_FULL,sCodeBRG);
                                in.putExtra(ScanActivity.SCANNER_FOR_RESULT,true);
                                in.putExtra(ScanActivity.TYPESCAN,
                                        ScanActivity.SCANNER_TYPE_2);
                                startActivityForResult(in,1);
                            }
                        }
                    })
                    .create().show();
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
        sessionTranstition.setTranstition("receive", true);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sessionScanner.clearSession();
    }
}