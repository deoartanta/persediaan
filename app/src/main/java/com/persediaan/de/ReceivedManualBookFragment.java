package com.persediaan.de;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ReceivedManualBookFragment extends Fragment {

    LinearLayout card_linear_manual_book,linearExpandDetailContainerManualBook;
    TextView tap_bg_manual_book,lbl_manual_book;


    AppCompatButton btn_detail_manual_book;

    Button btn_next,btn_lewati;

    LottieAnimationView tap_manual_book,tap2_manual_book;
    int index = 1;
    boolean expand = false;
    FrameLayout frame_layout_manual_book;
    LinearLayout main_linearlayout;

    public ReceivedManualBookFragment(FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout) {
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_received_manual_book, container, false);
        frame_layout_manual_book.setVisibility(View.VISIBLE);

        tap_bg_manual_book = view.findViewById(R.id.tapBackgroundManualBook);
        card_linear_manual_book = view.findViewById(R.id.cardLinearManualBook);

        tap_manual_book = view.findViewById(R.id.tapManualBook);
        tap2_manual_book = view.findViewById(R.id.tap2ManualBook);

        lbl_manual_book = view.findViewById(R.id.lblManualBook);
        tap2_manual_book.setVisibility(View.GONE);

        btn_next = view.findViewById(R.id.btnNext);
        btn_lewati = view.findViewById(R.id.btnLewati);

        linearExpandDetailContainerManualBook =
                        view.findViewById(R.id.linearExpandDetailContainerManualBook);
        btn_detail_manual_book = view.findViewById(R.id.btnShowDetail);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (index ==1){
                    if(expand==false){
                        linearExpandDetailContainerManualBook.setVisibility(View.VISIBLE);
                    }
                    expand = true;
                    tap_manual_book.setVisibility(View.GONE);
                    tap2_manual_book.setVisibility(View.VISIBLE);
                    btn_next.setText("Finish");
                }else if(index==2){
                    tap2_manual_book.setVisibility(View.GONE);
                }

                if (index>=2){
                    frame_layout_manual_book.setVisibility(View.GONE);
                    main_linearlayout.setVisibility(View.VISIBLE);
                }
                index++;
            }
        });
        btn_lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame_layout_manual_book.setVisibility(View.GONE);
                main_linearlayout.setVisibility(View.VISIBLE);
            }
        });
        card_linear_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expand==false){
                    linearExpandDetailContainerManualBook.setVisibility(View.VISIBLE);
                }
                expand = true;
                if(index<2){
                    if (expand){
                        tap_manual_book.setVisibility(View.GONE);
                        tap2_manual_book.setVisibility(View.VISIBLE);
                    }
                }
                if (index>=2){
                    tap_manual_book.setVisibility(View.GONE);
                    tap2_manual_book.setVisibility(View.GONE);
                    btn_next.setText("Finish");
                }
                index++;
            }
        });
        btn_detail_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index<2){
                }
                if (index>=2){
                    tap_manual_book.setVisibility(View.GONE);
                    tap2_manual_book.setVisibility(View.GONE);
                    btn_next.setText("Finish");
                }
                index++;
            }
        });


        tap_bg_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index>2){
                    frame_layout_manual_book.setVisibility(View.GONE);
                    main_linearlayout.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}