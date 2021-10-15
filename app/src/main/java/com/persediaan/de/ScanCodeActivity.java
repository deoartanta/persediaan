package com.persediaan.de;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;
import com.persediaan.de.data.SessionManager;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public ZXingScannerView mSanView;
    public SessionManager sessionManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,@Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState,persistentState);
        mSanView =  new ZXingScannerView(this);
        setContentView(mSanView);
        mSanView.setResultHandler(this);
        mSanView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Hasil Scan");
        alertDialog.setMessage("Hasil : "+rawResult.getText()+"\nFormat : "+rawResult.getBarcodeFormat().toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                onBackPressed();
            }
        });
        alertDialog.show();
        String R = rawResult.getText();
        String rArr[]=R.split("-");
        String r = "0";
        if (rArr.length!=0){
            r = rArr[1];
        }
        sessionManager = new SessionManager(getApplicationContext(),"scan");
        sessionManager.createSessionScan(r,rawResult.getBarcodeFormat().toString(),R);
//        Log.d("19201299Hasil",
//                "onResponseErrors: "+
//                        data[8]);
//        onBackPressed();
//        FragmentPenerimaanBrg.textView.setText("HASIL SCANNER : \n"+"   result : "+rawResult.getText()+"\n   Format : "+rawResult.getBarcodeFormat().toString());
    }

    @Override
    public  void onPause() {
        super.onPause();
        mSanView.stopCamera();
    }
//
    @Override
    public void onResume() {
        super.onResume();
        if (mSanView!=null){
            mSanView.startCamera();
        }
    }
//


}
