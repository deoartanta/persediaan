package com.persediaan.de;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.persediaan.de.data.ManualBookListener;
import com.persediaan.de.data.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static String TYPESCAN ="TYPESCAN";
    public static String SCANNER_TYPE_1 ="SCANNER_TYPE_1";
    public static String SCANNER_TYPE_2 ="SCANNER_TYPE_2";
    public static String RESULT_FULL ="RESULT_FULL";
    public static String SCANNER_FOR_RESULT ="SCANNER_FOR_RESULT";
    public static String FORMAT_BARCODE ="FORMAT_BARCODE";

    Bundle extras;
    String dataResult;

    FrameLayout frame_scanner_manual_book;

    Vibrator vibrator;

    ZXingScannerView scanner;

    FrameLayout frameScan2;
    TextView tv_flash_cam;

    Button btn_type_manual,btn_batal,btn_cancel;
//    <</Scan
    SessionManager sessionScan;
    HashMap<String, String> scanResult;
//    Scan/>>
//    <</Transtition
    SessionManager sessionTranstition;
//        Transtition/>>
//    Manual Book
    SessionManager session_manual_book;

    boolean sts_flash_cam = false;
    int test = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test++;
        scanner = new ZXingScannerView(this);
//        Log.d("19201299", "onCreate: Test Error=>"+test);
        setContentView(R.layout.activity_scan);
        extras = getIntent().getExtras();

        session_manual_book = new SessionManager(this,
                SessionManager.MANUAL_BOOK);

        sessionScan = new SessionManager(this,"scan");
        scanResult = sessionScan.getScanResult();

        sessionTranstition = new SessionManager(ScanActivity.this,"transtition");
        frame_scanner_manual_book = findViewById(R.id.frameScanManualBook);
        frame_scanner_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        frameScan2 = findViewById(R.id.frameScan2);
        tv_flash_cam = findViewById(R.id.tvFlashCam);
        btn_type_manual = findViewById(R.id.btnManualInput);
        btn_type_manual.setVisibility(View.VISIBLE);
        btn_batal = findViewById(R.id.btnBatalEditScan);
        btn_cancel = findViewById(R.id.btnCancelScan);

        if (sts_flash_cam){
            sts_flash_cam = !sts_flash_cam;
            tv_flash_cam.setBackground(getDrawable(R.drawable.ic_baseline_flash_on_24));
        }else {
            tv_flash_cam.setBackground(getDrawable(R.drawable.ic_baseline_flash_off_24));
            sts_flash_cam = !sts_flash_cam;
        }

        dataResult = extras.getString(RESULT_FULL);

        if (extras.getBoolean(SCANNER_FOR_RESULT)){
//            if(dataResult!=null){
                btn_batal.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.GONE);

                if (!session_manual_book.getManualBook(SessionManager.SCANNER_EDIT_MANUAL_BOOK)){
                    session_manual_book.setManualBook(SessionManager.SCANNER_EDIT_MANUAL_BOOK,true);
                    session_manual_book.setManualBook(SessionManager.SCANNER,false);
                }else{
                    frame_scanner_manual_book.setVisibility(View.GONE);
                }
//            }else{
//                btn_batal.setVisibility(View.GONE);
//                btn_cancel.setVisibility(View.VISIBLE);
//                sessionScan.clearSession();
//            }
        }else{
            btn_cancel.setVisibility(View.VISIBLE);
            btn_batal.setVisibility(View.GONE);
        }

//        if (sessionScan.isEditScanner()){
//            if ((sessionScan.getScanResult().get(SessionManager.SCANFULLR))!=null) {
//                btn_batal.setVisibility(View.VISIBLE);
//                btn_cancel.setVisibility(View.GONE);
//            }else{
//                btn_batal.setVisibility(View.GONE);
//                btn_cancel.setVisibility(View.VISIBLE);
//                sessionScan.clearSession();
//            }
//        }else{
//            btn_cancel.setVisibility(View.VISIBLE);
//            btn_batal.setVisibility(View.GONE);
//        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btn_type_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.app.AlertDialog.Builder dialog2 =
                        new androidx.appcompat.app.AlertDialog.Builder(ScanActivity.this);
                LayoutInflater inflater2= getLayoutInflater();
                View dialogView = inflater2.inflate(R.layout.type_item_code,null);
//                cam = false;
                dialog2.setView(dialogView);
                dialog2.setCancelable(false);
                dialog2.setIcon(R.mipmap.ic_launcher_packages_splashscreen);
                dialog2.setTitle("Add Code");
                EditText edtCode = dialogView.findViewById(R.id.tietEditCode);
                if(dataResult!=null){
                    edtCode.setText(dataResult);
                }else{
                    edtCode.setText("");
                }
                edtCode.requestFocus();

                scanner.stopCamera();

                dialog2.setPositiveButton("Next", (dialog, which) -> {
                    String R = edtCode.getText().toString();
                    if(extras.getBoolean(SCANNER_FOR_RESULT)){
                        sendResultBack(R,"");
                    }else{
                        newPage(ScanActivity.this,R,"");
                        finish();
                    }
                    dialog.dismiss();
                    finish();
                });
                dialog2.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    scanner.startCamera();
                    scanner.resumeCameraPreview(ScanActivity.this::handleResult);
                    dialogInterface.dismiss();
                });

                dialog2.show();
            }
        });
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                newPage(ScanActivity.this,"","");
                finish();
            }
        });
        tv_flash_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sts_flash_cam){
                    sts_flash_cam = !sts_flash_cam;
                    tv_flash_cam.setBackground(getDrawable(R.drawable.ic_baseline_flash_on_24));
                    scanner.setFlash(true);
                }else {
                    tv_flash_cam.setBackground(getDrawable(R.drawable.ic_baseline_flash_off_24));
                    sts_flash_cam = !sts_flash_cam;
                    scanner.setFlash(false);
                }
            }
        });

        scanner.startCamera();
        scanner.setResultHandler(this);

        extras = getIntent().getExtras();

        ScannerManualBookFragment fragmentScanManualBook =
                new ScannerManualBookFragment(frame_scanner_manual_book);
        if (!session_manual_book.getManualBook(SessionManager.SCANNER)){
        fragmentScanManualBook.setOnManualBookListener(new ManualBookListener() {
                @Override
                public void onNext(int index) {

                }

                @Override
                public void onLewati() {

                }

                @Override
                public void onFinish(int index) {
                    if (extras.getString(TYPESCAN).equals(SCANNER_TYPE_1)){
                        setContentView(scanner);
                    }else{
                        loadScanner(scanner);
                    }
                }
            });
        session_manual_book.setManualBook(SessionManager.SCANNER_EDIT_MANUAL_BOOK,true);
        loadFragmentManualBook(fragmentScanManualBook);
        session_manual_book.setManualBook(SessionManager.SCANNER,true);
        session_manual_book.OpenManualBook(true);
        }else{
            frame_scanner_manual_book.setVisibility(View.GONE);
            if (extras.getString(TYPESCAN).equals(SCANNER_TYPE_1)){
                setContentView(scanner);
            }else{
                loadScanner(scanner);
            }
        }

    }
    public void loadFragmentManualBook(Fragment pFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameScanManualBook,pFragment)
                .commit();
    }
    private void loadScanner(ZXingScannerView scanner) {

        frameScan2.removeAllViews();
        frameScan2.addView(scanner);
        scanner.startCamera();
        scanner.setResultHandler(ScanActivity.this);
    }
    public void createVibrate(){
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, 10));
        } else {
            vibrator.vibrate(50);
        }
    }


    @Override
    public void handleResult(Result result) {
        scanner.stopCamera();
        SessionManager session_setting = new SessionManager(ScanActivity.this,
                SessionManager.SETTING);
        if (session_setting.getSetting("vibrate")=="on"){
            createVibrate();
        }

        String R = result.getText();
        new AlertDialog.Builder(ScanActivity.this)
                .setTitle("Result")
                .setCancelable(false)
                .setMessage("Result: "+R+"\nFormat: "+result.getBarcodeFormat())
                .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(extras.getBoolean(SCANNER_FOR_RESULT)){
                            sendResultBack(R,result.getBarcodeFormat().toString());
                        }else{
                            newPage(ScanActivity.this,R,result.getBarcodeFormat().toString());
                            finish();
                        }
                    }
                }).create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.startCamera();
        scanner.resumeCameraPreview(ScanActivity.this::handleResult);
//        if (sessionScan!=null&&sessionScan.getScanResult().get(SessionManager.SCANR)!=null){
//            if (!sessionScan.isEditScanner()) {
//                newPage(ScanActivity.this);
//                finish();
//            }
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        sessionTranstition.setTranstition("receive", true);
    }

    private void newPage(Context ctx,String hlsScan,String formatScan) {
        Intent pener_action = new Intent(ctx,ActionPenerimaanActivity.class);
        pener_action.putExtra(RESULT_FULL,hlsScan)
                    .putExtra(FORMAT_BARCODE,formatScan);
        startActivity(pener_action);
    }
    private void sendResultBack(String s, String barcodeFormat){
        Intent i = new Intent();
        i.putExtra(RESULT_FULL,s)
         .putExtra(FORMAT_BARCODE,barcodeFormat);
        setResult(RESULT_OK,i);
        finish();
    }
}