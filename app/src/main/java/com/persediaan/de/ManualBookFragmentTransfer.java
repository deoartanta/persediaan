package com.persediaan.de;

import android.os.Bundle;

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

public class ManualBookFragmentTransfer extends Fragment {
    FrameLayout frame_layout_manual_book;

    SessionManager session_mnBook;

    LinearLayout card_drop_transfer,btn_pilih_barang,card_transfer,card_transfer_detail,
            linear_btn_transfer;

    TextView title_mnBook_transfer,title2_mnBook_transfer;

    Button btn_lewati,btn_next;

    LottieAnimationView arraw_itemGd_mnBook,tap_tran_pilih_brg, tap_tran_add_mnBook,
            tap_tran_del_mnBook,tap_tran_cancel_mnBook,tap_tran_simpan_mnBook;
    int id = 0;
    public ManualBookFragmentTransfer() {
        // Required empty public constructor
    }

    public ManualBookFragmentTransfer(FrameLayout frame_layout_manual_book) {
        this.frame_layout_manual_book = frame_layout_manual_book;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manual_book_fragment_transfer, container, false);

        frame_layout_manual_book.setVisibility(View.VISIBLE);
        session_mnBook = new SessionManager(requireContext(),SessionManager.MANUAL_BOOK);
        session_mnBook.setManualBook(SessionManager.OPEN_MANUAL_BOOK,true);

        card_drop_transfer = view.findViewById(R.id.cardDropTransfer);
        btn_pilih_barang = view.findViewById(R.id.btnPilihBarang);
        card_transfer = view.findViewById(R.id.cardTransfer);
        card_transfer_detail = view.findViewById(R.id.cardTransferDetail);
        linear_btn_transfer = view.findViewById(R.id.linearBtnTransfer);

        title_mnBook_transfer = view.findViewById(R.id.titleMnBook);
        title2_mnBook_transfer = view.findViewById(R.id.title2MnBook);

        arraw_itemGd_mnBook = view.findViewById(R.id.arrawItemGdMnBook);
        tap_tran_pilih_brg = view.findViewById(R.id.tapTranPilihBrg);
        tap_tran_add_mnBook = view.findViewById(R.id.tapTranAddMnBook);
        tap_tran_del_mnBook = view.findViewById(R.id.tapTranDelMnBook);
        tap_tran_cancel_mnBook = view.findViewById(R.id.tapTranCancelMnBook);
        tap_tran_simpan_mnBook = view.findViewById(R.id.tapTranSimpanMnBook);

        btn_next = view.findViewById(R.id.btnNext);
        btn_lewati = view.findViewById(R.id.btnLewati);
//        Set Visibility
        {
            btn_pilih_barang.setVisibility(View.INVISIBLE);
            tap_tran_pilih_brg.setVisibility(View.INVISIBLE);
            card_transfer.setVisibility(View.INVISIBLE);
            tap_tran_add_mnBook.setVisibility(View.INVISIBLE);
            card_transfer_detail.setVisibility(View.INVISIBLE);
            tap_tran_del_mnBook.setVisibility(View.INVISIBLE);
            linear_btn_transfer.setVisibility(View.INVISIBLE);
            tap_tran_cancel_mnBook.setVisibility(View.INVISIBLE);
            tap_tran_simpan_mnBook.setVisibility(View.INVISIBLE);

            title2_mnBook_transfer.setText("");
        }
//        Set on clik
        {
            card_drop_transfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_tran_pilih_brg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_tran_add_mnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_tran_del_mnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_tran_cancel_mnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
            tap_tran_simpan_mnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mnNext();
                    id++;
                }
            });
        }
        title_mnBook_transfer.setText("Semua area yang terdaftar sebagai gudang tujuan " +
                "ada disini");

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
        return view;
    }
    private void mnNext(){
        switch (id){
            case 0:
                card_drop_transfer.setVisibility(View.INVISIBLE);
                arraw_itemGd_mnBook.setVisibility(View.GONE);

                btn_pilih_barang.setVisibility(View.VISIBLE);
                tap_tran_pilih_brg.setVisibility(View.VISIBLE);

                title_mnBook_transfer.setText("Tap disini untuk memilih barang yang akan " +
                        "ditransfer, Tombol ini akan aktif secara otomatis setelah menentukan " +
                        "gudang tujuan ");
                break;
            case 1:
                btn_pilih_barang.setVisibility(View.INVISIBLE);
                tap_tran_pilih_brg.setVisibility(View.GONE);

                card_transfer.setVisibility(View.VISIBLE);
                tap_tran_add_mnBook.setVisibility(View.VISIBLE);
                title_mnBook_transfer.setText("");
                title2_mnBook_transfer.setText("Tap disini untuk menambahkan barang yang sudah " +
                        "siap untuk ditransfer");
                break;
            case 2:
                card_transfer.setVisibility(View.GONE);
                tap_tran_add_mnBook.setVisibility(View.GONE);

                card_transfer_detail.setVisibility(View.VISIBLE);
                tap_tran_del_mnBook.setVisibility(View.VISIBLE);

                title2_mnBook_transfer.setText("Tap disini untuk menghapus barang yang akan " +
                        "ditransfer");
                break;
            case 3:
                card_transfer_detail.setVisibility(View.INVISIBLE);
                card_drop_transfer.setVisibility(View.GONE);
                btn_pilih_barang.setVisibility(View.GONE);
                tap_tran_del_mnBook.setVisibility(View.GONE);

                linear_btn_transfer.setVisibility(View.VISIBLE);
                tap_tran_cancel_mnBook.setVisibility(View.VISIBLE);

                title2_mnBook_transfer.setText("Tap disini untuk membatalkan semua barang yang " +
                        "akan ditransfer");
                break;
            case 4:
                tap_tran_cancel_mnBook.setVisibility(View.GONE);
                tap_tran_simpan_mnBook.setVisibility(View.VISIBLE);

                title2_mnBook_transfer.setText("Tap disini untuk menyimpan semua barang yang akan" +
                        " " +
                        "ditransfer");
                btn_next.setText("Finish");
                break;
            default:
                frame_layout_manual_book.setVisibility(View.GONE);
                session_mnBook.setManualBook(SessionManager.TRANSFER,true);
                break;
        }
    }
}