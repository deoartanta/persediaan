package com.persediaan.de;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.persediaan.de.data.SessionManager;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends Activity implements ZXingScannerView.ResultHandler {
    public static String BARCODE = "BARCODE";

    public ZXingScannerView mSanView;
    public SessionManager session_scan;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,@Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState,persistentState);
        mSanView =  new ZXingScannerView(ScanCodeActivity.this);
        setContentView(mSanView);
        mSanView.setResultHandler(this);
        mSanView.startCamera();
        new AlertDialog.Builder(ScanCodeActivity.this)
                .setTitle("Hasil")
                .setMessage("Page Scanner")
                .setCancelable(false)
                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        setContentView(mSanView);
                        mSanView.setResultHandler(ScanCodeActivity.this);
                        mSanView.startCamera();
//                        mSanView.startCamera();
//                        mSanView.resumeCameraPreview(ScanCodeActivity.this);
                    }
                }).create().show();
    }

    @Override
    public void handleResult(Result rawResult){
        String R = rawResult.getText();
        String rArr[]=R.split("-");
        String r = "0";
        if (rArr.length!=0){
            r = rArr[1];
        }
        Bundle bundle = new Bundle();
        bundle.putString(BARCODE,R);

        if (session_scan.isEditScanner()){
            session_scan.clearSession();
        }
        new AlertDialog.Builder(ScanCodeActivity.this)
                .setTitle("Hasil")
                .setMessage("Result : "+R+"\nCode : "+rawResult.getBarcodeFormat())
                .setCancelable(false)
                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mSanView.startCamera();
                        mSanView.resumeCameraPreview(ScanCodeActivity.this);
                    }
                }).create().show();
        session_scan = new SessionManager(getApplicationContext(),"scan");
        session_scan.createSessionScan(r,rawResult.getBarcodeFormat().toString(),R);

    }

    @Override
    public  void onPause() {
        super.onPause();
        if (mSanView!=null){
            mSanView.stopCamera();
        }
    }
//
    @Override
    public void onResume() {
        super.onResume();
        if (mSanView!=null){
            mSanView.setResultHandler(this);
            mSanView.startCamera();
        }
    }
//


}
