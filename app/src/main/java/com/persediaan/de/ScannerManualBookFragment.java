package com.persediaan.de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.persediaan.de.data.ManualBookListener;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerManualBookFragment extends Fragment {

    FrameLayout frame_scanner_manual_book;
    LottieAnimationView tap_flash_cam_manual_book,
                        tap_manual_input_Manual_book,
                        tap_batal_edit_scan_Manual_book,
                        tap_cancel_scan_Manual_book;
    Button btn_manual_input,btn_batal_edit_scan,btn_cancel_scan,btn_next,btn_lewati;
    TextView tv_flash_cam,lbl_manual_book;

//    Session
    SessionManager sessionScanner,session_manual_book;

    HashMap<String, String> result_scanner;
    ManualBookListener manualBookListener;

    int index = 0;
    public ScannerManualBookFragment(FrameLayout frame_scanner_manual_book) {
        this.frame_scanner_manual_book = frame_scanner_manual_book;
    }
    public void setOnManualBookListener(ManualBookListener manualBookListener){
        this.manualBookListener = manualBookListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner_manual_book, container, false);
        frame_scanner_manual_book.setVisibility(View.VISIBLE);

//        SessionManager
        {
            sessionScanner = new SessionManager(requireContext(), "scan");
            result_scanner = sessionScanner.getScanResult();

            session_manual_book = new SessionManager(requireContext(),
                    SessionManager.MANUAL_BOOK);
        }

        //      LottieAnimationView
        {
            tap_flash_cam_manual_book = view.findViewById(R.id.tapFlashCamManualBook);
            tap_manual_input_Manual_book = view.findViewById(R.id.tapManualInputManualBook);
            tap_batal_edit_scan_Manual_book = view.findViewById(R.id.tapBatalEditScanManualBook);
            tap_cancel_scan_Manual_book = view.findViewById(R.id.tapCancelScanManualBook);

//            Set Visibility
            tap_manual_input_Manual_book.setVisibility(View.GONE);
            tap_batal_edit_scan_Manual_book.setVisibility(View.GONE);
            tap_cancel_scan_Manual_book.setVisibility(View.GONE);

//            Set On Click Listener
            tap_manual_input_Manual_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextManualBook();
                    if (manualBookListener!=null){
                        manualBookListener.onNext(index);
                    }
                }
            });
            tap_batal_edit_scan_Manual_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextManualBook();
                    if (manualBookListener!=null){
                        manualBookListener.onNext(index);
                    }
                }
            });
            tap_cancel_scan_Manual_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextManualBook();
                    if (manualBookListener!=null){
                        manualBookListener.onNext(index);
                    }
                }
            });
            tap_flash_cam_manual_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextManualBook();
                    if (manualBookListener!=null){
                        manualBookListener.onNext(index);
                    }
                }
            });
        }

        //        Button
        {
            btn_manual_input = view.findViewById(R.id.btnManualInput);
            btn_batal_edit_scan = view.findViewById(R.id.btnBatalEditScan);
            btn_cancel_scan = view.findViewById(R.id.btnCancelScan);

            btn_next = view.findViewById(R.id.btnNext);
            btn_lewati = view.findViewById(R.id.btnLewati);

            //            Set Visibility
            btn_manual_input.setVisibility(View.GONE);
            btn_batal_edit_scan.setVisibility(View.GONE);
            btn_cancel_scan.setVisibility(View.GONE);

            //            Set On Click Listener
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NextManualBook();
                    if (manualBookListener!=null){
                        manualBookListener.onNext(index);
                    }
                }
            });

            btn_lewati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = 22;
                    NextManualBook();
                    if (manualBookListener!=null){
                        manualBookListener.onLewati();
                    }
                }
            });

        }

        //        TextView
        {
            tv_flash_cam = view.findViewById(R.id.tvFlashCam);
            lbl_manual_book = view.findViewById(R.id.lblManualBook);

            //            Set Text
            lbl_manual_book.setText("Tap disini jika ingin menyalakan lampu");
        }
        //            More
        if (sessionScanner.isEditScanner()){
            index = 22;
            session_manual_book.setManualBook(SessionManager.SCANNER_EDIT_MANUAL_BOOK,
                    true);
            lbl_manual_book.setText("Tap disini untuk membatalkan aksi edit kode barang");

            btn_batal_edit_scan.setVisibility(View.VISIBLE);
            tap_batal_edit_scan_Manual_book.setVisibility(View.VISIBLE);

            btn_cancel_scan.setVisibility(View.GONE);
            tap_cancel_scan_Manual_book.setVisibility(View.GONE);

            tv_flash_cam.setVisibility(View.GONE);
            tap_flash_cam_manual_book.setVisibility(View.GONE);

            btn_manual_input.setVisibility(View.INVISIBLE);
            btn_next.setText("Finish");
            btn_lewati.setVisibility(View.GONE);
        }
        return view;
    }
    private void NextManualBook(){
        switch (index){
            case 0:
                index = 1;
                tv_flash_cam.setVisibility(View.INVISIBLE);
                tap_flash_cam_manual_book.setVisibility(View.INVISIBLE);

                btn_manual_input.setVisibility(View.VISIBLE);
                tap_manual_input_Manual_book.setVisibility(View.VISIBLE);

                lbl_manual_book.setText("Tap disini untuk mengetikkan kode barang secara menual " +
                        "\nFormat kode penulisan : xxxx-x-x, contoh : 0012-8-11");
                break;
            case 1:
                index = 2;

                tap_manual_input_Manual_book.setVisibility(View.GONE);

                btn_cancel_scan.setVisibility(View.VISIBLE);
                tap_cancel_scan_Manual_book.setVisibility(View.VISIBLE);

                lbl_manual_book.setText("Tap disini untuk menutup kamera");

                btn_next.setText("Finish");
                break;
            default:
                frame_scanner_manual_book.setVisibility(View.GONE);
                if (manualBookListener!=null){
                    manualBookListener.onFinish(index);
                }
                break;
        }
    }
}