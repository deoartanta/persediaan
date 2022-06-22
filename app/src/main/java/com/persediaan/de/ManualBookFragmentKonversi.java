package com.persediaan.de;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.data.SessionManager;

public class ManualBookFragmentKonversi extends Fragment {
    FrameLayout frame_layout_manual_book;
    TextView title_mn_book,title2_mn_book;

    Button btn_next,btn_lewati;
    ImageButton btn_edt_konversi,btn_add_konversi;

    LinearLayout card_list_barang,card_transfer_detail,foot_btn, lyt_qty, lyt_sisa,ly_cy_konversi;

    TextInputLayout tfno_receipt;

    CardView lil_kon_mn_book;

    LottieAnimationView arraw_no_rec_mn_book,tap_kon_mn_book,tap_kon_del_mn_book,
            tap_kon_cancel_mn_book,tap_kon_simpan_mn_book,tap_kon_back_mn_book,tap_kon_send_mn_book;

    SessionManager session_manualBook;
    int id=0;
    public ManualBookFragmentKonversi() {
        // Required empty public constructor
    }

    public ManualBookFragmentKonversi(FrameLayout frame_layout_manual_book) {
        this.frame_layout_manual_book= frame_layout_manual_book;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.manual_book_fragment_konversi, container, false);

        session_manualBook = new SessionManager(requireContext(),SessionManager.MANUAL_BOOK);
        session_manualBook.setManualBook(SessionManager.OPEN_MANUAL_BOOK,true);

        title_mn_book = view.findViewById(R.id.titleMnBook);
        title2_mn_book = view.findViewById(R.id.title2MnBook);

        card_list_barang = view.findViewById(R.id.crdListBarang);
        card_transfer_detail = view.findViewById(R.id.cardTransferDetail);
        foot_btn = view.findViewById(R.id.footBtn);

        lyt_qty = view.findViewById(R.id.lytQty);
        lyt_sisa = view.findViewById(R.id.lytSisa);
        ly_cy_konversi = view.findViewById(R.id.lyCyKonversi);

        tfno_receipt = view.findViewById(R.id.tfno_receipt);

        lil_kon_mn_book = view.findViewById(R.id.LilKonMnBook);

        btn_next = view.findViewById(R.id.btnNext);
        btn_lewati = view.findViewById(R.id.btnLewati);

        btn_edt_konversi = view.findViewById(R.id.btnEdtKonversi);
        btn_add_konversi = view.findViewById(R.id.btnAddKonversi);

        arraw_no_rec_mn_book = view.findViewById(R.id.arrawNoRecMnBook);
        tap_kon_mn_book = view.findViewById(R.id.tapKonMnBook);
        tap_kon_del_mn_book= view.findViewById(R.id.tapKonDelMnBook);
        tap_kon_cancel_mn_book= view.findViewById(R.id.tapKonCancelMnBook);
        tap_kon_simpan_mn_book= view.findViewById(R.id.tapKonSimpanMnBook);

        tap_kon_back_mn_book= view.findViewById(R.id.tapKonBackMnBook);
        tap_kon_send_mn_book= view.findViewById(R.id.tapKonSendMnBook);

        title_mn_book.setText("Tap disini untuk memilih no receipt yang akan di konversi");
//        Set visibility
        {
            title2_mn_book.setVisibility(View.INVISIBLE);
            card_list_barang.setVisibility(View.INVISIBLE);
            card_transfer_detail.setVisibility(View.INVISIBLE);
            foot_btn.setVisibility(View.INVISIBLE);

            tap_kon_mn_book.setVisibility(View.INVISIBLE);
            tap_kon_del_mn_book.setVisibility(View.INVISIBLE);
            tap_kon_cancel_mn_book.setVisibility(View.INVISIBLE);
            tap_kon_simpan_mn_book.setVisibility(View.INVISIBLE);
            tap_kon_send_mn_book.setVisibility(View.INVISIBLE);
            tap_kon_back_mn_book.setVisibility(View.INVISIBLE);
        }
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mnNext();
                id++;
            }
        });
        btn_lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = 20;
                mnNext();
            }
        });
//        Tap Next Manual Book
        {
            tap_kon_mn_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_kon_del_mn_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_kon_cancel_mn_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_kon_simpan_mn_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tfno_receipt.getEditText().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
        }
        return view;
    }
    private void mnNext(){
        switch (id){
            case 0:
                tap_kon_mn_book.setVisibility(View.VISIBLE);
                card_list_barang.setVisibility(View.VISIBLE);
                arraw_no_rec_mn_book.setVisibility(View.GONE);
                lil_kon_mn_book.setVisibility(View.INVISIBLE);
                title_mn_book.setText("tap disini untuk membuka form pengisian quantity, form ini" +
                        " diperlukan untuk memulai konversi");
                break;
            case 1:
                ly_cy_konversi.setOrientation(LinearLayout.VERTICAL);
                tap_kon_mn_book.setVisibility(View.GONE);
                btn_edt_konversi.setImageResource(R.drawable.ic_baseline_reply_24);
                btn_add_konversi.setVisibility(View.VISIBLE);
                lyt_qty.setVisibility(View.VISIBLE);
                lyt_sisa.setVisibility(View.VISIBLE);
                tap_kon_send_mn_book.setVisibility(View.VISIBLE);
                title_mn_book.setText("Tap disini untuk memulai proses konversi");
                break;
            case 2:
                tap_kon_send_mn_book.setVisibility(View.GONE);
                tap_kon_back_mn_book.setVisibility(View.VISIBLE);
                title_mn_book.setText("Tap disini untuk menutup form quantity");
                break;
            case 3:
                ly_cy_konversi.setOrientation(LinearLayout.HORIZONTAL);
                tap_kon_back_mn_book.setVisibility(View.GONE);
                title_mn_book.setVisibility(View.GONE);
                lyt_qty.setVisibility(View.GONE);
                lyt_sisa.setVisibility(View.GONE);
                tap_kon_del_mn_book.setVisibility(View.VISIBLE);
                card_list_barang.setVisibility(View.INVISIBLE);
                title2_mn_book.setVisibility(View.VISIBLE);
                title2_mn_book.setText("Tap disini untuk menghapus hasil konversi");
                card_transfer_detail.setVisibility(View.VISIBLE);
                break;
            case 4:
                foot_btn.setVisibility(View.VISIBLE);
                card_transfer_detail.setVisibility(View.INVISIBLE);
                tap_kon_del_mn_book.setVisibility(View.GONE);
                tap_kon_cancel_mn_book.setVisibility(View.VISIBLE);
                title_mn_book.setVisibility(View.VISIBLE);
                title2_mn_book.setVisibility(View.GONE);
                title_mn_book.setText("Tap disini untuk membatalkan semua hasil konversi");
                break;
            case 5:
                tap_kon_cancel_mn_book.setVisibility(View.GONE);
                tap_kon_simpan_mn_book.setVisibility(View.VISIBLE);
                title_mn_book.setText("Tap disini untuk menyimpan semua hasil konversi");

                btn_next.setText("Finish");
                break;
            default:
                frame_layout_manual_book.setVisibility(View.GONE);
                session_manualBook.setManualBook(SessionManager.KONVERSI,true);
                break;
        }
    }
}