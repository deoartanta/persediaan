package com.persediaan.de;

import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

public class ManualBookFragmentSetting extends Fragment {

    FrameLayout frame_layout_manual_book;

    TextView tv_setting_result01, tv_setting_result02, tv_setting_result03,
            tv_setting_result04;

    SessionManager session_datauser,session_manualBook;

    HashMap<String,String> detail_profile;
    HashMap<String,Integer> detail_profileInt;

    LinearLayout linear_card_profile_expand,linear_card_item,linear_card_setting_expand,
            linear_card_help_expand,linear_card_logout;
    LottieAnimationView arraw_akun_mnBook,arraw_item_mnBook,arraw_setting_mnBook,arraw_help_mnBook,
            arraw_logout_mnBook;
    LottieAnimationView arraw_nm_mnBook,arraw_usernm_mnBook,arraw_passw_mnBook,arraw_alamat_mnBook;
    RelativeLayout ly_nm_mnbook,ly_usernm_mnbook,ly_passw_mnbook,ly_alamat_mnbook,ly_akun_mnBook;
    ConstraintLayout ly_expend_akun;
    TextView titleMnBook;
    ImageView img_right;

    Button btn_next,btn_lewati;
    int id=0;
    public ManualBookFragmentSetting() {
        // Required empty public constructor
    }
    String stateMnBook="";
    public ManualBookFragmentSetting(FrameLayout frame_layout_manual_book,String stateMnBook) {
        this.frame_layout_manual_book = frame_layout_manual_book;
        this.stateMnBook= stateMnBook;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        session_datauser = new SessionManager(requireContext(), "login");
        session_manualBook = new SessionManager(requireContext(), SessionManager.MANUAL_BOOK);
        session_manualBook.setManualBook(SessionManager.OPEN_MANUAL_BOOK, true);
        detail_profile = session_datauser.getUserDetail();
        detail_profileInt = session_datauser.getUserDetailInt();
        View view = inflater.inflate(R.layout.manual_book_fragment_setting, container, false);
        tv_setting_result01 = view.findViewById(R.id.tvSettingResult01);
        tv_setting_result02 = view.findViewById(R.id.tvSettingResult02);
        tv_setting_result03 = view.findViewById(R.id.tvSettingResult03);
        tv_setting_result04 = view.findViewById(R.id.tvSettingResult04);

        linear_card_profile_expand = view.findViewById(R.id.linear_card_profile_expand);
        linear_card_item = view.findViewById(R.id.linear_card_item);
        linear_card_setting_expand = view.findViewById(R.id.linear_card_setting_expand);
        linear_card_help_expand = view.findViewById(R.id.linear_card_help_expand);
        linear_card_logout = view.findViewById(R.id.linear_card_logout);

        arraw_akun_mnBook = view.findViewById(R.id.arrawAkunMnBook);
        arraw_item_mnBook = view.findViewById(R.id.arrawItemMnBook);
        arraw_setting_mnBook = view.findViewById(R.id.arrawSettingMnBook);
        arraw_help_mnBook = view.findViewById(R.id.arrawHelpMnBook);
        arraw_logout_mnBook = view.findViewById(R.id.arrawLogoutMnBook);

        ly_nm_mnbook = view.findViewById(R.id.lyNmMnbook);
        ly_usernm_mnbook = view.findViewById(R.id.lyUsernmMnbook);
        ly_passw_mnbook = view.findViewById(R.id.lyPasswMnbook);
        ly_alamat_mnbook = view.findViewById(R.id.lyAlamatMnbook);
        ly_akun_mnBook = view.findViewById(R.id.lyAkunMnBook);
        ly_expend_akun = view.findViewById(R.id.lyExpendAkun);

        arraw_nm_mnBook = view.findViewById(R.id.arrawNmMnBook);
        arraw_usernm_mnBook = view.findViewById(R.id.arrawUsernmMnBook);
        arraw_passw_mnBook = view.findViewById(R.id.arrawPasswMnBook);
        arraw_alamat_mnBook = view.findViewById(R.id.arrawAlamatMnBook);

        titleMnBook = view.findViewById(R.id.titleMnBook);
        img_right = view.findViewById(R.id.imgRight);

        btn_next = view.findViewById(R.id.btnNext);
        btn_lewati = view.findViewById(R.id.btnLewati);

//        Set result
        {
            tv_setting_result01.setText(textWrap(detail_profile.get(SessionManager.NAMA), 8, "text"));
            tv_setting_result02.setText(textWrap(detail_profile.get(SessionManager.USERNAME), 8,
                    "text"));
            tv_setting_result03.setText(textWrap(detail_profile.get(SessionManager.PASSW), 8,
                    "password"));
            tv_setting_result04.setText(textWrap(detail_profile.get(SessionManager.ALAMAT), 8,
                    "text"));
        }
//        Set visibility
        {
            frame_layout_manual_book.setVisibility(View.VISIBLE);

//            linear_card_profile_expand.setVisibility(View.GONE);
            linear_card_item.setVisibility(View.GONE);
            linear_card_setting_expand.setVisibility(View.GONE);
            linear_card_help_expand.setVisibility(View.INVISIBLE);
            linear_card_logout.setVisibility(View.INVISIBLE);

//            arraw_akun_mnBook.setVisibility(View.GONE);
            arraw_item_mnBook.setVisibility(View.GONE);
            arraw_setting_mnBook.setVisibility(View.GONE);
            arraw_help_mnBook.setVisibility(View.GONE);
            arraw_logout_mnBook.setVisibility(View.GONE);


            ly_usernm_mnbook.setVisibility(View.GONE);
            ly_passw_mnbook.setVisibility(View.GONE);
            ly_alamat_mnbook.setVisibility(View.GONE);
            ly_expend_akun.setVisibility(View.GONE);

//            arraw_nm_mnBook.setVisibility(View.VISIBLE);
            arraw_usernm_mnBook.setVisibility(View.GONE);
            arraw_passw_mnBook.setVisibility(View.GONE);
            arraw_alamat_mnBook.setVisibility(View.GONE);
        }
        titleMnBook.setText("Tempat untuk mengedit informasi akun anda");
        arraw_akun_mnBook.playAnimation();
        // Set onclick listener
        {
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (stateMnBook == "akun") {
                        mnBookNextAkun();
                    } else {
                        mnBookNext();
                    }
                    id++;
                }
            });
            btn_lewati.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    id = 20;
                    if (stateMnBook == "akun") {
                        mnBookNextAkun();
                    } else {
                        mnBookNext();
                    }
                }
            });
        }

        if(stateMnBook =="akun"){
//            ly_akun_mnBook.setVisibility(View.INVISIBLE);
            img_right.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            ly_expend_akun.setVisibility(View.VISIBLE);
            linear_card_logout.setVisibility(View.GONE);
            arraw_akun_mnBook.setVisibility(View.GONE);

            ly_nm_mnbook.setVisibility(View.VISIBLE);
            titleMnBook.setText("Ketuk untuk mengganti nama anda");
        }
        return view;
    }

    private void mnBookNext(){
        switch (id){
            case 0:
                linear_card_profile_expand.setVisibility(View.INVISIBLE);
                arraw_akun_mnBook.setVisibility(View.GONE);

                if (detail_profileInt.get(SessionManager.LEVEL)==1){
                    linear_card_item.setVisibility(View.VISIBLE);
                    arraw_item_mnBook.setVisibility(View.VISIBLE);
                    arraw_item_mnBook.playAnimation();

                    titleMnBook.setText("Tombol untuk mengarahkan ke halaman pengeditan daftar barang");
                }else{
                    id++;
                    linear_card_item.setVisibility(View.GONE);
                    arraw_item_mnBook.setVisibility(View.GONE);
                    linear_card_profile_expand.setVisibility(View.GONE);
                    mnBookNext();
                }
                break;
            case 1:
                linear_card_item.setVisibility(View.INVISIBLE);
                arraw_item_mnBook.setVisibility(View.GONE);

                linear_card_setting_expand.setVisibility(View.VISIBLE);
                arraw_setting_mnBook.setVisibility(View.VISIBLE);
                arraw_setting_mnBook.playAnimation();

                titleMnBook.setText("Tempat untuk mengatur beberapa pengaturan di aplikasi " +
                        "persediaan");
                break;
            case 2:
                linear_card_setting_expand.setVisibility(View.INVISIBLE);
                arraw_setting_mnBook.setVisibility(View.GONE);

                linear_card_help_expand.setVisibility(View.VISIBLE);
                arraw_help_mnBook.setVisibility(View.VISIBLE);
                arraw_help_mnBook.playAnimation();

                titleMnBook.setText("Tempat untuk melihat panduan aplikasi persediaan");
                break;
            case 3:
                linear_card_help_expand.setVisibility(View.INVISIBLE);
                arraw_help_mnBook.setVisibility(View.GONE);

                linear_card_logout.setVisibility(View.VISIBLE);
                arraw_logout_mnBook.setVisibility(View.VISIBLE);
                arraw_logout_mnBook.playAnimation();

                titleMnBook.setText("Tombol untuk untuk mengakhiri sesi");
                break;
            default:
                frame_layout_manual_book.setVisibility(View.GONE);
                session_manualBook.setManualBook(SessionManager.OPEN_MANUAL_BOOK,false);
                session_manualBook.setManualBook(SessionManager.SETTING,true);
                break;
        }
    }
    private void mnBookNextAkun(){
        switch (id){
            case 0:
                arraw_nm_mnBook.setVisibility(View.GONE);
//                ly_nm_mnbook.setVisibility(View.INVISIBLE);

                ly_usernm_mnbook.setVisibility(View.VISIBLE);
                arraw_usernm_mnBook.setVisibility(View.VISIBLE);
                titleMnBook.setText("Ketuk untuk mengganti username anda");
                break;
            case 1:
//                ly_usernm_mnbook.setVisibility(View.INVISIBLE);
                arraw_usernm_mnBook.setVisibility(View.GONE);

                ly_passw_mnbook.setVisibility(View.VISIBLE);
                arraw_passw_mnBook.setVisibility(View.VISIBLE);

                titleMnBook.setText("Ketuk untuk mengganti password");
                break;
            case 2:
//                ly_passw_mnbook.setVisibility(View.INVISIBLE);
                arraw_passw_mnBook.setVisibility(View.GONE);

                ly_alamat_mnbook.setVisibility(View.VISIBLE);
                arraw_alamat_mnBook.setVisibility(View.VISIBLE);

                titleMnBook.setText("Ketuk untuk mengganti alamat");
                break;
            default:
                frame_layout_manual_book.setVisibility(View.GONE);
                session_manualBook.setManualBook(SessionManager.OPEN_MANUAL_BOOK,false);
                session_manualBook.setManualBook(SessionManager.SETAKUN,true);
                break;
        }
    }

    private String textWrap(String plaintText, int length, String type) {
        String result ="";
        switch (type){
            case "text":
                if (plaintText.length()>length){
                    for (int i =0;i<length;i++){
                        result = result+String.valueOf(plaintText.charAt(i));
                    }
                    result = result+"...";
                }else{
                    result = plaintText;
                }
                break;
            case "password":
                if (plaintText.length()<length){
                    for (int i=0;i<plaintText.length();i++){
                        result =result+"*";
                    }
                }else{
                    for (int i=0;i<length;i++){
                        result =result+"*";
                    }
                }
                break;
        }
        return result;
    }
}