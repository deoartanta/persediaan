package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.zxing.Result;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    public static String TYPESCAN ="TYPESCAN";
    public static String SCANNER_TYPE_1 ="SCANNER_TYPE_1";
    public static String SCANNER_TYPE_2 ="SCANNER_TYPE_2";
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
    boolean sts_flash_cam = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanner = new ZXingScannerView(this);
        setContentView(R.layout.activity_scan);

        sessionScan = new SessionManager(this,"scan");
        scanResult = sessionScan.getScanResult();

        sessionTranstition = new SessionManager(ScanActivity.this,"transtition");

        frameScan2 = findViewById(R.id.frameScan2);
        tv_flash_cam = findViewById(R.id.tvFlashCam);
        btn_type_manual = findViewById(R.id.btnManualInput);
        btn_batal = findViewById(R.id.btnBatalEditScan);
        btn_cancel = findViewById(R.id.btnCancelScan);

        if (sts_flash_cam){
            sts_flash_cam = !sts_flash_cam;
            tv_flash_cam.setBackground(getDrawable(R.drawable.ic_baseline_flash_on_24));
        }else {
            tv_flash_cam.setBackground(getDrawable(R.drawable.ic_baseline_flash_off_24));
            sts_flash_cam = !sts_flash_cam;
        }
        if (sessionScan.isEditScanner()){
            if ((sessionScan.getScanResult().get(SessionManager.SCANFULLR))!=null) {
                btn_batal.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.GONE);
            }else{
                btn_batal.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.VISIBLE);
                sessionScan.clearSession();
            }
        }else{
            btn_cancel.setVisibility(View.VISIBLE);
            btn_batal.setVisibility(View.GONE);
        }

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
                dialog2.setIcon(R.mipmap.ic_launcher);
                dialog2.setTitle("Add Code");
                EditText edtCode = dialogView.findViewById(R.id.tietEditCode);
                edtCode.setText("");
                edtCode.requestFocus();

                scanner.stopCamera();

                dialog2.setPositiveButton("Next", (dialog, which) -> {
                    String R = edtCode.getText().toString();
                    String rArr[]=R.split("-");
                    String r = "0";
                    if (rArr.length>1){
                        r = rArr[1];
                    }
                    sessionScan.createSessionScan(r,null,R);
//                    cam = true;
                    dialog.dismiss();
                    newPage(ScanActivity.this);
                    finish();
                });
                dialog2.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    scanner.startCamera();
                    dialogInterface.dismiss();
                });

                dialog2.show();
            }
        });
        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPage(ScanActivity.this);
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

        Bundle extras = getIntent().getExtras();
        if (extras.getString(TYPESCAN).equals(SCANNER_TYPE_1)){
            setContentView(scanner);
        }else{
            loadScanner(scanner);
        }

    }

    private void loadScanner(ZXingScannerView scanner) {
        frameScan2.removeAllViews();
        frameScan2.addView(scanner);
        scanner.startCamera();
        scanner.setResultHandler(ScanActivity.this);
    }
    public void createVibrate(long millisecond,int repeat){
        for (int i = 0; i < 5; i++) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= 26) {
                        vibrator.vibrate(VibrationEffect.createOneShot(1000, 2));
                    } else {
                        vibrator.vibrate(millisecond);
                    }
                }
            }, 2000);
        }

    }


    @Override
    public void handleResult(Result result) {
        scanner.stopCamera();
        String R = result.getText();
        String[] rArr =R.split("-");
        String r = "0";
        if (rArr.length>1){
            r = rArr[1];
        }
        if (sessionScan.isEditScanner()) {
            sessionScan.clearSession();
        }
        createVibrate(500,5);
        sessionScan.createSessionScan(r, result.getBarcodeFormat().toString(), R);
        newPage(ScanActivity.this);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanner.startCamera();
        if (sessionScan!=null&&sessionScan.getScanResult().get(SessionManager.SCANR)!=null){
            if (!sessionScan.isEditScanner()) {
                newPage(ScanActivity.this);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (sessionScan.isEditScanner()){
            newPage(ScanActivity.this);
        }else {
            sessionTranstition.setTranstition("receive", true);
        }
    }

    private void newPage(Context ctx) {
        Intent pener_action = new Intent(ctx,ActionPenerimaanActivity.class);
        startActivity(pener_action);
    }
}