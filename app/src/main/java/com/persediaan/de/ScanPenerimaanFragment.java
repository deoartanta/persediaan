package com.persediaan.de;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanPenerimaanFragment extends Fragment implements ZXingScannerView.ResultHandler {
    ZXingScannerView scan_barcode;
    SessionManager scan_session;
    HashMap<String,String> getScan;
    ImageView img_edit_kode;
    Button btn_manual;
    Context ctx;
    public ScanPenerimaanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan_penerimaan,container,false);
        scan_barcode = view.findViewById(R.id.scanBarcode);
        scan_session = new SessionManager(getContext(),"scan");

        btn_manual = view.findViewById(R.id.btnManualInput);

        scan_barcode.setResultHandler(this);
        scan_barcode.startCamera();
        ctx = getContext();
        btn_manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(getContext());
                LayoutInflater inflater2= getLayoutInflater();
                View dialogView = inflater2.inflate(R.layout.type_item_code,null);
                dialog2.setView(dialogView);
                dialog2.setCancelable(true);
                dialog2.setIcon(R.mipmap.ic_launcher);
                dialog2.setTitle("Add Code");
                EditText edtCode = dialogView.findViewById(R.id.tietEditCode);
                edtCode.setText("");
                dialog2.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String R = edtCode.getText().toString();
                        String rArr[]=R.split("-");
                        String r = "0";
                        if (rArr.length<1){
                            r = rArr[1];
                        }else{
                            r = R;
                        }
                        scan_session.createSessionScan(r,null,R);
                        dialog.dismiss();
                    }

                });
                dialog2.show();
                dialog2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        newPage(ctx);
                    }
                });
            }
        });
        return view;
    }
    private void newPage(Context context) {
        Intent pener_action = new Intent(context,ActionPenerimaanActivity.class);
        startActivity(pener_action);
        Fragment frg = new PenerimaanFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,frg)
                .commit();
    }
    @Override
    public void handleResult(Result rawResult) {
        scan_barcode.stopCamera();

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Hasil Scan");
        alertDialog.setMessage("Hasil : "+rawResult.getText()+"\nFormat : "+rawResult.getBarcodeFormat().toString());
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "NEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
        String R = rawResult.getText();
        String rArr[]=R.split("-");
        String r = "0";
        if (rArr.length<1){
            r = rArr[1];
        }
        scan_session.createSessionScan(r,rawResult.getBarcodeFormat().toString(),R);
        newPage(ctx);

    }

    @Override
    public void onPause() {
        super.onPause();
        scan_barcode.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (scan_barcode!=null&&scan_session.getScanResult().get(SessionManager.SCANR)!=null){
            newPage(ctx);
        }else{
            scan_barcode.startCamera();
        }
    }
}