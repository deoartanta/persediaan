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
import com.persediaan.de.data.SessionManager;

public class ReceivedManualBookFragment extends Fragment {

    LinearLayout card_linear_manual_book,linearExpandDetailContainerManualBook;
    TextView tap_bg_manual_book,lbl_manual_book;


    AppCompatButton btn_detail_manual_book;

    Button btn_next,btn_lewati;

    SessionManager sessionManualBook;

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

        View view = inflater.inflate(R.layout.manual_book_fragment_received, container, false);
        frame_layout_manual_book.setVisibility(View.VISIBLE);

        sessionManualBook= new SessionManager(requireContext(),
                "manualbook");

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
//                    if(expand==false){
//                        linearExpandDetailContainerManualBook.setVisibility(View.VISIBLE);
//                    }
                    expand = true;
                    tap_manual_book.setVisibility(View.GONE);
                    tap2_manual_book.setVisibility(View.VISIBLE);
                    btn_next.setText("Finish");
                }else if(index==2){
                    tap2_manual_book.setVisibility(View.GONE);
                }

                if (index>=2){
                    sessionManualBook.OpenManualBook(false);
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
                sessionManualBook.OpenManualBook(false);
            }
        });
        card_linear_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expand==false){
                    linearExpandDetailContainerManualBook.setVisibility(View.VISIBLE);
                    tap_manual_book.setVisibility(View.GONE);
                    tap2_manual_book.setVisibility(View.VISIBLE);
                    index++;
                }
                expand = true;
//                if(index<2){
//                    if (expand){
//
//                    }
//                }
//                if (index>=2){
//                    tap_manual_book.setVisibility(View.GONE);
//                    tap2_manual_book.setVisibility(View.GONE);
//                    btn_next.setText("Finish");
//                }
            }
        });
        btn_detail_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index>=2){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frameManualBook,
                                    new DetailPenerManualBookFragment(frame_layout_manual_book,main_linearlayout))
                            .commit();
                    sessionManualBook.setManualBook(SessionManager.DETAILPENER,true);
                    tap_manual_book.setVisibility(View.GONE);
                    tap2_manual_book.setVisibility(View.GONE);

//                    btn_next.setText("Finish");
                    index++;
                }
            }
        });


        tap_bg_manual_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (index>2){
                    frame_layout_manual_book.setVisibility(View.GONE);
                    main_linearlayout.setVisibility(View.VISIBLE);
                    sessionManualBook.OpenManualBook(false);
                }
            }
        });
        return view;
    }
}