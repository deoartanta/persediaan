package com.persediaan.de;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanPenerimaanFragment extends Fragment implements ZXingScannerView.ResultHandler {
    public static String BARCODE = "BARCODE";

    ZXingScannerView scan_barcode;
    FrameLayout frameLayout;
    SessionManager scan_session;
    HashMap<String,String> getScan;
    ImageView img_edit_kode;

    boolean a = true;
//    Setting
    View view_collapse;
    CardView card_bottom_sheet;
    LinearLayout linear_bottom_sheet;
    LinearLayout linear_bottom_setting;
    int heightLong=0,heightShort=0,height = 0,height_frame;

    Switch auto_focus,flash;

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

        frameLayout = view.findViewById(R.id.scanBarcode);
        scan_barcode = (ZXingScannerView) new ZXingScannerView(requireContext());

//        scan_barcode.setFlash(true);
        scan_session = new SessionManager(requireContext(),"scan");

        linearLayout_scan = view.findViewById(R.id.linearLayoutScan);

        view_collapse = view.findViewById(R.id.viewCollapseScan);
        card_bottom_sheet = view.findViewById(R.id.cardBtnActionScanner);
        linear_bottom_sheet = view.findViewById(R.id.linearLayoutScan);
        linear_bottom_setting = view.findViewById(R.id.linearLayoutStting);

        auto_focus = view.findViewById(R.id.switchAutoFocus);
        flash = view.findViewById(R.id.switchFlash);

        btn_manual = view.findViewById(R.id.btnManualInput);
        btn_batal_edit_scan = view.findViewById(R.id.btnBatalEditScan);

        frameLayout.addView(scan_barcode);

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

//        btn_manual.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                scan_barcode.removeAllViews();
//            scan_barcode.addView(new ZXingScannerView(requireContext()));
//            }
//        });

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                heightLong = card_bottom_sheet.getHeight();
                height_frame = frameLayout.getHeight();
                heightShort = heightLong - linear_bottom_setting.getHeight();

                card_bottom_sheet.getLayoutParams().height = heightShort-20;
                card_bottom_sheet.requestLayout();
                linear_bottom_setting.setVisibility(View.GONE);

                frameLayout.getLayoutParams().height =
                        (height_frame+(heightShort));
                frameLayout.requestLayout();
            }
        }, 500);

//        Setting Kamera
        flash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                scan_barcode.setFlash(b);
            }
        });
        auto_focus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                scan_barcode.setAutoFocus(b);
            }
        });
        view_collapse.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float xDown=0,yDown=0,movedX=0,movedY=0;
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        xDown = motionEvent.getX();
                        yDown = motionEvent.getY();
                        height = card_bottom_sheet.getHeight();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        movedX = motionEvent.getX();
                        movedY = motionEvent.getY();

                        float distanceX = movedX-xDown;
                        float distancey = movedY-yDown;

//                        if (((card_bottom_sheet.getHeight())-distancey)>400&&
//                                (((card_bottom_sheet.getHeight())-distancey)<800)) {
//                            tv_total_hrg2.setText(""+((card_bottom_sheet.getHeight())-distancey));
//                            card_bottom_sheet.getLayoutParams().height = (int) ((card_bottom_sheet.getHeight()) - distancey);
//                            card_bottom_sheet.requestLayout();
//                            height = (int) ((card_bottom_sheet.getHeight())-distancey);
//                        }
                        if (((card_bottom_sheet.getHeight())-distancey)>((heightShort/2)+30)&&
                                (((card_bottom_sheet.getHeight())-distancey)<heightLong)) {
                            linear_bottom_setting.setVisibility(View.VISIBLE);
//                            tv_total_hrg2.setText(""+((card_bottom_sheet.getHeight())-distancey));
                            card_bottom_sheet.getLayoutParams().height = (int) ((card_bottom_sheet.getHeight()) - distancey);
                            card_bottom_sheet.requestLayout();
                            height = (int) ((card_bottom_sheet.getHeight())-distancey);
                        }
//                        flash.setText(""+height+", "+heightLong+", "+heightShort);
                        xDown= movedX;
                        yDown = movedY;
                        break;
                    case MotionEvent.ACTION_UP:
                        if ((height<(heightShort+20))&&
                            height>((heightShort/2)+50)){
                            card_bottom_sheet.getLayoutParams().height = heightShort-20;
                            card_bottom_sheet.requestLayout();
                            linear_bottom_setting.setVisibility(View.GONE);

                            frameLayout.getLayoutParams().height =
                                    (height_frame+(heightShort+100));
                            frameLayout.requestLayout();

                        }else if (height<((heightShort/2)+70)){
                            card_bottom_sheet.getLayoutParams().height = (heightShort/2)+20;
                            card_bottom_sheet.requestLayout();
                            linear_bottom_setting.setVisibility(View.GONE);

                            frameLayout.getLayoutParams().height =
                                    (height_frame+((heightShort/2)+200));
                            frameLayout.requestLayout();
                        }else{
                            card_bottom_sheet.getLayoutParams().height = heightLong;
                            card_bottom_sheet.requestLayout();
                            linear_bottom_setting.setVisibility(View.VISIBLE);

                            frameLayout.getLayoutParams().height =
                                    (height_frame+(heightLong+200));
                            frameLayout.requestLayout();
                        }
                        break;
                }
                return false;
            }
        });

        view_collapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
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
//        ctx = requireActivity();
        scan_barcode.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        scan_barcode.startCamera();
        if (scan_barcode!=null&&scan_session.getScanResult().get(SessionManager.SCANR)!=null){
            if (!scan_session.isEditScanner()) {
                newPage(ctx);
            }
        }
    }
}