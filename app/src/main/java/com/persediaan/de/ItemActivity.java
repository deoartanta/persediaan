package com.persediaan.de;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.adapter.AdapterDaftartBarang;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiDaftarBarang;
import com.persediaan.de.api.ApiSatuan;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.javaKey.JavaKeyBoard;
import com.persediaan.de.model.ModelDaftarItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ItemActivity extends AppCompatActivity implements RecyclerViewClickInterface {
    AppCompatButton btn_add;

    ArrayList<ModelDaftarItem> modelDaftarItems;
    RecyclerView recycle_daftar_barang;

    LayoutInflater layoutInflater;
    View viewinflater;
    TextInputEditText tiet_nm_item;
    String id_item ="",nm_item ="",satuanItemcard="";
    AutoCompleteTextView autoCompleteSatuan;

    ArrayAdapter<String> adapter_item;
//    Connection
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    AdapterDaftartBarang adapterDaftartBarang;

    HashMap<String,Integer>modelSatuan;
    ImageButton tb_img_btn;

    JavaKeyBoard javaKeyBoard;

    int positionItemCard = 0;
    View viewItemCard = null;
    boolean addItem = false;

    ShimmerFrameLayout shimmer_item_avtivity;
    ConstraintLayout constraint_item_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //Connection
        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        shimmer_item_avtivity = findViewById(R.id.shimmerItemActivity);
        constraint_item_content = findViewById(R.id.constraintItemContent);
        constraint_item_content.setVisibility(View.GONE);

        btn_add = findViewById(R.id.btnAddItem);
        tb_img_btn = findViewById(R.id.tbImgBtnItem);
        tb_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recycle_daftar_barang = findViewById(R.id.recycleDaftarBarang);
        loadData();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialogAddItem(false);
                addItem = true;
            }
        });

    }

    private void addData(String pid_item,String nm_item, Integer id_satuan) {
        Call<ApiDaftarBarang> call = jsonPlaceHolderApi.getAdditem(
                pid_item,nm_item,id_satuan
        );
        call.enqueue(new Callback<ApiDaftarBarang>() {
            @Override
            public void onResponse(Call<ApiDaftarBarang> call, Response<ApiDaftarBarang> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ItemActivity.this,
                            "Terjadi error yang tidak diketahui["+response.code()+"]",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ItemActivity.this, response.body().getNm_item()+" berhasil " +
                                "ditambah",
                        Toast.LENGTH_LONG).show();
//                Snackbar.make(getApplicationContext(),findViewById(R.id.rootLayout),"",
//                        Snackbar.LENGTH_LONG).show();
                loadData();
            }

            @Override
            public void onFailure(Call<ApiDaftarBarang> call, Throwable t) {
                Toast.makeText(ItemActivity.this, JsonPlaceHolderApi.getMessageApi(t.getMessage()
                                !="false"?JsonPlaceHolderApi.getMessageApi(t.getMessage()):"Server error"),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadData(){
        shimmer_item_avtivity.setVisibility(View.VISIBLE);
        constraint_item_content.setVisibility(View.GONE);
        recycle_daftar_barang.setAdapter(null);
        Call<ArrayList<ApiDaftarBarang>> call = jsonPlaceHolderApi.getiItem();
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ItemActivity.this, "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_SHORT).show();

                    shimmer_item_avtivity.setVisibility(View.GONE);
                    constraint_item_content.setVisibility(View.VISIBLE);

                    return;
                }
                ArrayList<ApiDaftarBarang> apiDaftarBarangs = response.body();

                modelDaftarItems = new ArrayList<>();
                for (ApiDaftarBarang apiDaftarBarang:apiDaftarBarangs){
                    modelDaftarItems.add(
                            new ModelDaftarItem(
                                    apiDaftarBarang.getNm_item(),
                                    apiDaftarBarang.getNm_satuan(),
                                    apiDaftarBarang.getId_item()
                            )
                    );
                }

                adapterDaftartBarang = new AdapterDaftartBarang(modelDaftarItems,ItemActivity.this);
                recycle_daftar_barang.setAdapter(adapterDaftartBarang);
                recycle_daftar_barang.setLayoutManager(new LinearLayoutManager(ItemActivity.this,
                        LinearLayoutManager.VERTICAL, false));
                recycle_daftar_barang.setHasFixedSize(true);
                recycle_daftar_barang.setItemAnimator(new DefaultItemAnimator());

                shimmer_item_avtivity.setVisibility(View.GONE);
                constraint_item_content.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                shimmer_item_avtivity.setVisibility(View.GONE);
                constraint_item_content.setVisibility(View.VISIBLE);
                Toast.makeText(ItemActivity.this,
                        "Server error["+JsonPlaceHolderApi.getMessageApi(t.getMessage())!="false"?t.getMessage()
                                :JsonPlaceHolderApi.getMessageApi(t.getMessage())+"]",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSatuan(AutoCompleteTextView autoCompleteSatuan){
        Call<ArrayList<ApiSatuan>> call=jsonPlaceHolderApi.getSatuan();
        call.enqueue(new Callback<ArrayList<ApiSatuan>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiSatuan>> call, Response<ArrayList<ApiSatuan>> response) {
                ArrayList<ApiSatuan> arraySatuan = response.body();
                ArrayList<String> listSatuan = new ArrayList<>();
                modelSatuan  = new HashMap<>();
                if (!response.isSuccessful()){
                    Toast.makeText(ItemActivity.this, "Terjadi error yang tidak diketahui",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (Build.VERSION.SDK_INT < 26) {
//                    modelSatuan.put("", -1);
//                    listSatuan.add(""+Build.VERSION.SDK_INT);
//                }
                for (ApiSatuan apiSatuan:arraySatuan){
                    listSatuan.add(apiSatuan.getNm_satuan());
                    modelSatuan.put(apiSatuan.getNm_satuan().toString(),apiSatuan.getId_satuan());
                }
                adapter_item = new ArrayAdapter<>(ItemActivity.this,
                        R.layout.tv_daftar_item,
                        listSatuan);
                autoCompleteSatuan.setAdapter(adapter_item);
                autoCompleteSatuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        autoCompleteSatuan.clearFocus();
                    }
                });
                autoCompleteSatuan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {

                    }
                });

            }

            @Override
            public void onFailure(Call<ArrayList<ApiSatuan>> call, Throwable t) {
                Toast.makeText(ItemActivity.this, JsonPlaceHolderApi.getMessageApi(t.getMessage()
                        !="false"?JsonPlaceHolderApi.getMessageApi(t.getMessage()):"Server error"),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void editItem(String pid_item,String pid_baru,String nm_item,int id_satuan){
        Call<ArrayList<ApiDaftarBarang>> call = jsonPlaceHolderApi.getEdititem(
                pid_item,pid_baru,nm_item,id_satuan
        );
        call.enqueue(new Callback<ArrayList<ApiDaftarBarang>>() {
            @Override
            public void onResponse(Call<ArrayList<ApiDaftarBarang>> call, Response<ArrayList<ApiDaftarBarang>> response) {
                for (ApiDaftarBarang apiDaftarBarang:response.body()) {
                    if (apiDaftarBarang.getMsg()==null) {
                        loadData();
                      Toast.makeText(ItemActivity.this, "Edit data berhasil",
                              Toast.LENGTH_SHORT).show();
//                        Snackbar.make(ItemActivity.this,findViewById(android.R.id.content),
//                                "Berhasil edit data",Snackbar.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(ItemActivity.this,
                                ""+apiDaftarBarang.getMsg(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ApiDaftarBarang>> call, Throwable t) {
                Toast.makeText(ItemActivity.this, JsonPlaceHolderApi.getMessageApi(t.getMessage()
                                !="false"?JsonPlaceHolderApi.getMessageApi(t.getMessage()):"Server error"),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemClick(int position, View view) {
        viewItemCard = view;
        positionItemCard = position;
        TextView nm_item = viewItemCard.findViewById(R.id.tvResNmItem);
        TextView nm_stn = viewItemCard.findViewById(R.id.tvResNmStn);
        ItemActivity.this.nm_item = (String) nm_item.getText();
        satuanItemcard = (String) nm_stn.getText();
        id_item = modelDaftarItems.get(position).getId_item();
        createDialogItem(false);
        id_item = "";
        addItem = false;
    }
    private void createDialogAddItem(boolean scanCode){
        LayoutInflater layoutInflater= getLayoutInflater();
        View viewinflater =layoutInflater.inflate(R.layout.input_alert_dialog_item,null);

        TextInputEditText tiet_nm_item = viewinflater.findViewById(R.id.editTextNamaItem);
        AutoCompleteTextView autoCompleteSatuan= viewinflater.findViewById(R.id.autoCompleteSatuan);

        TextInputEditText tiet_id_item = viewinflater.findViewById(R.id.editTextIdItem);
        TextInputLayout l_id_item = viewinflater.findViewById(R.id.layoutInputIdItem);
        if(id_item.isEmpty()){
            l_id_item.setVisibility(View.GONE);
        }else{
            l_id_item.setVisibility(View.VISIBLE);
            tiet_id_item.setText(id_item);
        }
        loadSatuan(autoCompleteSatuan);

        AlertDialog dialogAdd= new AlertDialog.Builder(ItemActivity.this)
                .setIcon(R.drawable.ic_baseline_add_circle_24_success)
                .setTitle("Add Item")
                .setView(viewinflater)
                .setCancelable(false)
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Log.d("19201299",
//                                "onClick: "+tiet_nm_item.getText().toString()+", "+
//                                        tiet_nm_item.getText().toString().toUpperCase(Locale.ROOT)+", "+
//                                        modelSatuan.get(autoCompleteSatuan.getText().toString()));

                        addData(tiet_id_item.getText().toString(),tiet_nm_item.getText().toString(),
                                modelSatuan.get(autoCompleteSatuan.getText().toString())
                        );
                    }
                })
                .setNeutralButton("Scan Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent in = new Intent(ItemActivity.this,
                                ScanActivity.class);
                        in.putExtra(ScanActivity.RESULT_FULL,"");
                        in.putExtra(ScanActivity.SCANNER_FOR_RESULT,true);
                        in.putExtra(ScanActivity.TYPESCAN,
                                ScanActivity.SCANNER_TYPE_2);
                        startActivityForResult(in,1);
                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        javaKeyBoard = new JavaKeyBoard(dialogAdd.getWindow());
        javaKeyBoard.showInput();
        dialogAdd.show();
        tiet_nm_item.requestFocus();
        autoCompleteSatuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                javaKeyBoard = new JavaKeyBoard(dialogAdd.getWindow());
                javaKeyBoard.setInputMethodManager(imm);
                javaKeyBoard.hideInput();

            }
        });
    }
    private void createDialogItem(boolean scanCode){
        String id_item = ItemActivity.this.id_item;
        layoutInflater= getLayoutInflater();
        viewinflater =layoutInflater.inflate(R.layout.input_alert_dialog_item,null);
        tiet_nm_item = viewinflater.findViewById(R.id.editTextNamaItem);
        autoCompleteSatuan= viewinflater.findViewById(R.id.autoCompleteSatuan);

        tiet_nm_item.setText(nm_item.toUpperCase(Locale.ROOT));
        loadSatuan(autoCompleteSatuan);

        TextInputEditText tiet_id_item = viewinflater.findViewById(R.id.editTextIdItem);
        TextInputLayout l_id_item = viewinflater.findViewById(R.id.layoutInputIdItem);
        l_id_item.setVisibility(View.VISIBLE);
        if(id_item.isEmpty()){
            l_id_item.setVisibility(View.GONE);
        }else{
            l_id_item.setVisibility(View.VISIBLE);
            tiet_id_item.setText(id_item);
        }

        autoCompleteSatuan.setText(satuanItemcard);

        AlertDialog dialogEdit = new AlertDialog.Builder(ItemActivity.this)
                .setIcon(R.drawable.ic_baseline_create_24_primary)
                .setTitle("Edit Item")
                .setView(viewinflater)
                .setCancelable(false)
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Log.d("19201299",
//                                "onClick: "+modelDaftarItems.get(positionItemCard).getId_item()+", "+
//                                        tiet_nm_item.getText().toString().toUpperCase(Locale.ROOT)+", "+
//                                        modelSatuan.get(autoCompleteSatuan.getText().toString()));

                        editItem(modelDaftarItems.get(positionItemCard).getId_item(),
                                tiet_id_item.getText().toString(),
                                tiet_nm_item.getText().toString().toUpperCase(Locale.ROOT),
                                modelSatuan.get(autoCompleteSatuan.getText().toString())
                        );
                    }
                })
                .setNeutralButton("Scan Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (ContextCompat.checkSelfPermission(ItemActivity.this,
                                Manifest.permission.CAMERA)==
                                PackageManager.PERMISSION_GRANTED){
                            openScanPage(ScanActivity.SCANNER_TYPE_2);
                        }else
                        {
                            requestCameraPermission();
                        }

                    }
                })
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create();
        javaKeyBoard = new JavaKeyBoard(dialogEdit.getWindow());
        javaKeyBoard.showInput();
        dialogEdit.show();
        tiet_nm_item.requestFocus();
        autoCompleteSatuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm=
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                javaKeyBoard = new JavaKeyBoard(dialogEdit.getWindow());
                javaKeyBoard.setInputMethodManager(imm);
                javaKeyBoard.hideInput();
            }
        });
    }

    private void openScanPage(String scannerType) {
        Intent in = new Intent(ItemActivity.this,
                ScanActivity.class);
        in.putExtra(ScanActivity.RESULT_FULL,"");
        in.putExtra(ScanActivity.SCANNER_FOR_RESULT,true);
        in.putExtra(ScanActivity.TYPESCAN,
                scannerType);
        startActivityForResult(in,1);
    }
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(ItemActivity.this,
                Manifest.permission.CAMERA)){
            new AlertDialog.Builder(ItemActivity.this)
                    .setTitle("Permession Needed")
                    .setCancelable(false)
                    .setMessage("This permission is needed bacause of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(ItemActivity.this,
                                    new String[]{Manifest.permission.CAMERA},1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions(ItemActivity.this,
                    new String[]{Manifest.permission.CAMERA},1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        id_item = data.getExtras().getString(ScanActivity.RESULT_FULL);
        if (addItem){
            createDialogAddItem(true);
        }else{
            createDialogItem(true);
        }
        id_item = "";
    }

    @Override
    public void onItemClick1(int position, View view) {
        TextView nm_item = view.findViewById(R.id.tvResNmItem);

        Call<ApiDaftarBarang> call =
                jsonPlaceHolderApi.getHpsItem(modelDaftarItems.get(position).getId_item());

        new AlertDialog.Builder(ItemActivity.this)
                .setIcon(R.drawable.ic_baseline_warning_24)
                .setTitle("Hapus Item")
                .setMessage("Apakah anda yakin ingin menghapus "+nm_item.getText())
                .setCancelable(true)
                .setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        call.enqueue(new Callback<ApiDaftarBarang>() {
                            @Override
                            public void onResponse(Call<ApiDaftarBarang> call, Response<ApiDaftarBarang> response) {
                                if (!response.isSuccessful()){
                                    Toast.makeText(ItemActivity.this, "Terjadi error yang tidak diketahui",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (response.body().isSts()){
                                    Toast.makeText(ItemActivity.this, nm_item.getText().toString().toUpperCase(Locale.ROOT)+
                                                    " berhasil dihapus",
                                            Toast.LENGTH_SHORT).show();
                                    loadData();
                                }else{
                                    Toast.makeText(ItemActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiDaftarBarang> call, Throwable t) {
                                Toast.makeText(ItemActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
    public void lostFocus(View viewFocus){
        if (viewFocus!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewFocus.getWindowToken(),0);
        }else{
            Toast.makeText(ItemActivity.this, "ViewFocus null", Toast.LENGTH_SHORT).show();
        }
    }
}