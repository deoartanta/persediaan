package com.persediaan.de;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.persediaan.de.data.SessionManager;

public class DetailPenerManualBookFragment extends Fragment {

    FrameLayout frame_layout_manual_book;
    LinearLayout main_linearlayout,layout_root_content_detail_pener_manual_book,bottom_sheet,
                bg_view_Collapse_manual_book,root_bg,linear_bottom_sheet,linear_layout_ttl_hrg;
    Intent i;

    CardView card_content;

    LottieAnimationView btn_hps,btn_edit,btn_batal,btn_simpan,btn_swipe;

    TextView lbl_manual_book;

    Button btn_lewati,btn_next;
    int prsHeight=0,height = 0,log = 50,log2 = 80;

    SessionManager session_manual_book;

    int index = 0;
    public DetailPenerManualBookFragment(FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout) {
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
    }

    public DetailPenerManualBookFragment(FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout, Intent i) {
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
        this.i = i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manual_book_fragment_detail_pener, container, false);
        session_manual_book = new SessionManager(requireContext(),"manualbook");
        frame_layout_manual_book.setVisibility(View.VISIBLE);
        main_linearlayout.setVisibility(View.GONE);

        //        Animation
        Animation goAlphaIn = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_in);
        Animation goAlphaOut = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_out);
        //        LinearLayout
        {
            layout_root_content_detail_pener_manual_book =
                    view.findViewById(R.id.layoutRootContentDetailPenerManualBook);
            bottom_sheet = view.findViewById(R.id.BottomSheet);

            bg_view_Collapse_manual_book = view.findViewById(R.id.bgViewCollapseManualBook);

            linear_bottom_sheet = view.findViewById(R.id.linearBottomsheet);
            linear_layout_ttl_hrg = view.findViewById(R.id.linearLayoutTllHrg);
            root_bg = view.findViewById(R.id.rootBg);

            //            setVisibility
            layout_root_content_detail_pener_manual_book.setVisibility(View.VISIBLE);
            linear_bottom_sheet.setVisibility(View.GONE);
            linear_layout_ttl_hrg.setVisibility(View.GONE);
            bottom_sheet.setVisibility(View.INVISIBLE);

            //            setAnimation
            layout_root_content_detail_pener_manual_book.setAnimation(goAlphaIn);
        }

        //        RelativeLayout
        {
            card_content = view.findViewById(R.id.card2MnBook);
        }

        //        LottieAnimationView
        {
           btn_edit = view.findViewById(R.id.tapEditDetailPenerManualBook);
           btn_hps = view.findViewById(R.id.tapHpsDetailPenerManualBook);

           btn_simpan = view.findViewById(R.id.tapSimpanDetailPenerManualBook);
           btn_batal = view.findViewById(R.id.tapBatalDetailPenerManualBook);

           btn_swipe = view.findViewById(R.id.swipeBtnSheetDetailPenerManualBook2);

           //           setVisibility
            btn_hps.setVisibility(View.GONE);

            btn_simpan.setVisibility(View.GONE);
            btn_batal.setVisibility(View.GONE);

            btn_swipe.setVisibility(View.INVISIBLE);

            //            set Listener
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextManualBook();
                }
            });
            btn_hps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextManualBook();

                }
            });

            btn_swipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextManualBook();
                }
            });
            btn_swipe.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    nextManualBook();
                    return true;
                }
            });
            btn_simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextManualBook();
                }
            });
            btn_batal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nextManualBook();
                }
            });
        }

        //        Button
        {
            btn_next = view.findViewById(R.id.btnNext);
            btn_lewati = view.findViewById(R.id.btnLewati);

            //            set Listener
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (index==2){
                        index = 3;
                    }
                    nextManualBook();
                }
            });

            btn_lewati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    index = 5;
                    nextManualBook();
//                    frame_layout_manual_book.setVisibility(View.GONE);
//                    main_linearlayout.setVisibility(View.VISIBLE);
//                    if (i!=null){
//                        startActivity(i);
//                    }
                }
            });
        }

        //        TextView
        {
            lbl_manual_book = view.findViewById(R.id.lblManualBook);
        }
        return view;
    }

    public void nextManualBook(){
        Animation goUp= AnimationUtils.loadAnimation(requireContext(),R.anim.go_up);
        Animation goAlphaIn = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_in);
        Animation goAlphaOut = AnimationUtils.loadAnimation(requireContext(), R.anim.alpha_out);
        switch (index){
            case 0:
                lbl_manual_book.setText("Tap disini untuk menghapus item dari transaksi anda");
                btn_edit.setVisibility(View.GONE);
                btn_hps.setVisibility(View.VISIBLE);
                index = 1;
                break;
            case 1:
                lbl_manual_book.setText("Geser ke atas untuk melihat total harga dari transaksi anda");
                btn_hps.setVisibility(View.GONE);
                btn_swipe.setVisibility(View.VISIBLE);
                bottom_sheet.setVisibility(View.VISIBLE);
                card_content.setVisibility(View.INVISIBLE);
                card_content.setAnimation(goAlphaOut);
                bottom_sheet.setAnimation(goAlphaIn);
                log2 = 80;
                index = 2;
                break;
            case 2:
                lbl_manual_book.setText("Geser lagi untuk melihat detail transaksi anda");
                linear_layout_ttl_hrg.setVisibility(View.VISIBLE);
//                linear_layout_ttl_hrg.setAnimation(goUp);
                log2 = 105;
                log = 80;
                index=3;
                break;
            case 3:
                lbl_manual_book.setText("Tombol simpan digunakan  untuk menyimpan transaksi yang " +
                        "telah anda buat");
                linear_bottom_sheet.setVisibility(View.VISIBLE);
//                linear_bottom_sheet.setAnimation(goUp);
                btn_simpan.setVisibility(View.VISIBLE);
                btn_swipe.setVisibility(View.INVISIBLE);

                bg_view_Collapse_manual_book.setBackgroundColor(0);
                root_bg.setBackgroundResource(0);
                index=4;
                break;
            case 4:
                lbl_manual_book.setText("Tombol batal digunakan jika transaksi yang anda " +
                        "buat tidak sesuai dan ingin membatalkannya");
                btn_simpan.setVisibility(View.GONE);
                btn_batal.setVisibility(View.VISIBLE);
                btn_next.setText("Finish");
                index=5;
                break;
            case 5:
                frame_layout_manual_book.setVisibility(View.GONE);
                main_linearlayout.setVisibility(View.VISIBLE);
                session_manual_book.OpenManualBook(false);
                if (i!=null){
                    startActivity(i);
                }

                break;
        }
    }
}