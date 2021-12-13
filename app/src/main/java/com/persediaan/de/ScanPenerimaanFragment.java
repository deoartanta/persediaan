package com.persediaan.de;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.zxing.Result;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanPenerimaanFragment extends Fragment implements ZXingScannerView.ResultHandler {
    public static String BARCODE = "BARCODE";

    ZXingScannerView scan_barcode;
    SessionManager scan_session;
    HashMap<String,String> getScan;
    ImageView img_edit_kode;

    LinearLayout linearLayout_scan;

    Button btn_manual,btn_batal_edit_scan;
    Context ctx;
    boolean cam = true;
    MeowBottomNavigation btnNavBottom;
    public ScanPenerimaanFragment(MeowBottomNavigation btnNavBottom) {
        this.btnNavBottom = btnNavBottom;
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
        scan_session = new SessionManager(requireContext(),"scan");

        linearLayout_scan = view.findViewById(R.id.linearLayoutScan);

        btn_manual = view.findViewById(R.id.btnManualInput);
        btn_batal_edit_scan = view.findViewById(R.id.btnBatalEditScan);

        if (scan_session.isEditScanner()){
            if ((scan_session.getScanResult().get(SessionManager.SCANFULLR))!=null) {
                btn_batal_edit_scan.setVisibility(View.VISIBLE);
            }else{
                btn_batal_edit_scan.setVisibility(View.GONE);
                scan_session.clearSession();
            }
        }else{
            btn_batal_edit_scan.setVisibility(View.GONE);
        }

        scan_barcode.setResultHandler(this);
        scan_barcode.startCamera();
        ctx = getContext();
        btn_manual.setOnClickListener(view1 -> {
            AlertDialog.Builder dialog2 = new AlertDialog.Builder(requireContext());
            LayoutInflater inflater2= getLayoutInflater();
            View dialogView = inflater2.inflate(R.layout.type_item_code,null);
            cam = false;
            dialog2.setView(dialogView);
            dialog2.setCancelable(false);
            dialog2.setIcon(R.mipmap.ic_launcher);
            dialog2.setTitle("Add Code");
            EditText edtCode = dialogView.findViewById(R.id.tietEditCode);
            edtCode.setText("");
            edtCode.requestFocus();

            scan_barcode.stopCamera();

            dialog2.setPositiveButton("Next", (dialog, which) -> {
                String R = edtCode.getText().toString();
                String rArr[]=R.split("-");
                String r = "0";
                if (rArr.length>1){
                    r = rArr[1];
                }
                scan_session.createSessionScan(r,null,R);
                cam = true;
                dialog.dismiss();
                newPage(ctx);
            });
            dialog2.setNegativeButton("Cancel", (dialogInterface, i) -> {
                scan_barcode.startCamera();
                dialogInterface.dismiss();
            });

            dialog2.show();

        });
        btn_batal_edit_scan.setOnClickListener(view1 -> {
            newPage(ctx);
        });
        return view;
    }

    private void showSoftKeyboard(Fragment fragment) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = (View) fragment.requireView().getRootView().getWindowToken();
//        if (view==null){
//
//        }
//        inputMethodManager.sh


    }

    private void newPage(Context context) {
        Intent pener_action = new Intent(context,ActionPenerimaanActivity.class);
        startActivity(pener_action);
        btnNavBottom.show(2,true);
    }

    @Override
    public void handleResult(Result rawResult) {
        scan_barcode.stopCamera();
        String R = rawResult.getText();
        String[] rArr =R.split("-");
        String r = "0";
        if (rArr.length>1){
            r = rArr[1];
        }
        newPage(ctx);
        if (scan_session.isEditScanner()) {
            scan_session.clearSession();
        }
        scan_session.createSessionScan(r, rawResult.getBarcodeFormat().toString(), R);
    }


    @Override
    public void onPause() {
        super.onPause();
        ctx = requireActivity();
        scan_barcode.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (scan_barcode!=null&&scan_session.getScanResult().get(SessionManager.SCANR)!=null){
            if (!scan_session.isEditScanner()) {
                newPage(ctx);
            }
        }else{
//            assert scan_barcode != null;
            scan_barcode.startCamera();
        }
    }
}