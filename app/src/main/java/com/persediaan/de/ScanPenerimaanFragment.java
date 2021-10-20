package com.persediaan.de;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.zxing.Result;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;
import java.util.Objects;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanPenerimaanFragment extends Fragment implements ZXingScannerView.ResultHandler {
    ZXingScannerView scan_barcode;
    SessionManager scan_session;
    HashMap<String,String> getScan;
    ImageView img_edit_kode;
    Button btn_manual;
    Context ctx;
    boolean cam = true;
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
        scan_session = new SessionManager(requireContext(),"scan");

        btn_manual = view.findViewById(R.id.btnManualInput);

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

            dialog2.setPositiveButton("Lanjut", (dialog, which) -> {
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
            dialog2.setNegativeButton("Batal", (dialogInterface, i) -> {
                scan_barcode.startCamera();
                dialogInterface.dismiss();
            });

            InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);

            dialog2.show();

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
        Fragment frg = new PenerimaanFragment();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,frg)
                .commit();
        LinearLayout main_linearlayout = requireActivity().findViewById(R.id.mainLinearLayout);
        main_linearlayout.setVisibility(View.VISIBLE);
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

        AlertDialog alertDialog = new AlertDialog.Builder(requireContext()).create();
        alertDialog.setTitle("Hasil Scan");
        alertDialog.setMessage("Hasil : "+rawResult.getText()+"\n Code Barang : "+r+"\nFormat : "+rawResult.getBarcodeFormat().toString());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "NEXT", (dialogInterface, i) -> {
            dialogInterface.dismiss();
            newPage(ctx);
        });
        alertDialog.show();
        scan_session.createSessionScan(r,rawResult.getBarcodeFormat().toString(),R);

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
            assert scan_barcode != null;
            scan_barcode.startCamera();
        }
    }
}