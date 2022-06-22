package com.persediaan.de;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.data.SessionManager;

import java.util.ArrayList;

public class ManualBookFragment extends Fragment {

    ArrayList<MeowBottomNavigation.Model> models;
    MeowBottomNavigation meowBottomNavigation;
    LinearLayout main_linearlayout;
    Button btn_lewati,btn_next;
    FrameLayout frameLayout;
    TextView btn_nav_manualbook;
    LottieAnimationView animHand;
    LinearLayout cardProfile;
    CardView card_view_manual_book;

    TextView tv_lbl_tittle_home,tv_lbl_tittle_receive,
            tv_lbl_tittle_scan,tv_lbl_tittle_spending,
            tv_lbl_tittle_setting;
    TextView lbl_manual_book;
    SessionManager session_manual_book;
    View.OnClickListener cardOnclickProfile;
    int id = 1;
    public ManualBookFragment(ArrayList<MeowBottomNavigation.Model> models,
                              FrameLayout frameLayout, LinearLayout main_linearlayout) {
        this.models = models;
        this.frameLayout = frameLayout;
        this.main_linearlayout = main_linearlayout;
    }

    public ManualBookFragment(ArrayList<MeowBottomNavigation.Model> models,
                              FrameLayout frameLayout, LinearLayout main_linearlayout,
                              View.OnClickListener cardOnclickProfile) {
        this.models = models;
        this.frameLayout = frameLayout;
        this.main_linearlayout = main_linearlayout;
        this.cardOnclickProfile = cardOnclickProfile;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manual_book_fragment, container, false);
        session_manual_book = new SessionManager(requireContext(),"manualbook");
        meowBottomNavigation = view.findViewById(R.id.bottomNavManualBook);
        frameLayout.setVisibility(View.VISIBLE);
        btn_lewati = view.findViewById(R.id.btnLewati);
        btn_next = view.findViewById(R.id.btnNext);

        tv_lbl_tittle_home = view.findViewById(R.id.lblBotNavTittleHomeManualBook);
        tv_lbl_tittle_receive = view.findViewById(R.id.lblBotNavtittlePenerManualBook);
        tv_lbl_tittle_scan = view.findViewById(R.id.lblBotNavTittleScanManualBook);
        tv_lbl_tittle_spending = view.findViewById(R.id.lblBotNavTittleItemOutManualBook);
        tv_lbl_tittle_setting = view.findViewById(R.id.lblBotNavTittleSettingManualBook);

        lbl_manual_book = view.findViewById(R.id.lblManualBook);
        cardProfile = view.findViewById(R.id.cardView);
        card_view_manual_book = view.findViewById(R.id.cardViewManualBook);
        animHand = view.findViewById(R.id.tapCardView);
        cardProfile.setVisibility(View.GONE);
        animHand.setVisibility(View.GONE);
        btn_nav_manualbook = view.findViewById(R.id.btnNavManualbook);

//        meowBottomNavigation.setModels(model);
        for (MeowBottomNavigation.Model model:models){
            meowBottomNavigation.add(new MeowBottomNavigation.Model(model.getId(),model.getIcon()));
        }
//        meowBottomNavigation.setModels(models);
        cardProfile.setOnClickListener(cardOnclickProfile);
        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                switch (item.getId()){
                    case 1:
                        lbl_manual_book.setText("Tombol navigasi ini digunakan untuk membuka " +
                                "halaman beranda, tap untuk melanjutkan");
                        break;
                    case 2:
                        lbl_manual_book.setText("Tombol navigasi ini digunakan untuk melihat " +
                                "segala penerimaan barang setelah melakukan pembelian, tap untuk " +
                                "melanjutkan");
                        break;
                    case 3:
                        lbl_manual_book.setText("Tombol ini digunakan untuk memindai item " +
                                "ber type BARCODE, tap untuk melanjutkan");
                        break;
                    case 4:
                        lbl_manual_book.setText("Tombol navigasi ini digunakan untuk menuju ke " +
                                "halaman yang berkaitan dengan barang keluar, tap untuk " +
                                "melanjutkan");
                        break;
                    case 5:
                        lbl_manual_book.setText("Segala pengaturan ada di tombol navigasi ini, " +
                                "tap untuk melanjutkan");
                        break;
                }
                setTittleLabel(item.getId());
            }
        });
        meowBottomNavigation.show(id,true);
        meowBottomNavigation.setEnabled(false);
//        cardProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                frameLayout.setVisibility(View.GONE);
//                main_linearlayout.setVisibility(View.VISIBLE);
//                session_manual_book.OpenManualBook(false);
//
//            }
//        });
        btn_nav_manualbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id2 = (id+1)>models.size()?0:id+1;
                id++;
                if (id2==0){
                    btn_nav_manualbook.setVisibility(View.GONE);
                }else{
                    meowBottomNavigation.show(id2,true);
                }
                if ((id+1)>models.size()){
                    btn_nav_manualbook.setVisibility(View.GONE);
                }
            }
        });

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (id< item.getId()){
                    id = item.getId();
                }

                card_view_manual_book.setVisibility(View.GONE);
                cardProfile.setVisibility(View.VISIBLE);
                animHand.setVisibility(View.VISIBLE);
                lbl_manual_book.setText("Tap disini untuk pergi ke halaman pengaturan akun");

                if ((id+1)>models.size()){
                    btn_next.setText("Finish");
                    btn_nav_manualbook.setVisibility(View.GONE);
                }
            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                card_view_manual_book.setVisibility(View.GONE);
                cardProfile.setVisibility(View.VISIBLE);
                animHand.setVisibility(View.VISIBLE);
                lbl_manual_book.setText("Tap disini untuk pergi ke halaman pengaturan akun");
            }
        });

        btn_lewati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.GONE);
                main_linearlayout.setVisibility(View.VISIBLE);
                session_manual_book.OpenManualBook(false);
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id++;
                if ((id+1)>(models.size()+1)){
                    btn_next.setText("Finish");
                    btn_nav_manualbook.setVisibility(View.GONE);
                    main_linearlayout.setVisibility(View.VISIBLE);
                }
                if (id>(models.size()+1)) {
                    frameLayout.setVisibility(View.GONE);
                    main_linearlayout.setVisibility(View.VISIBLE);
                    session_manual_book.OpenManualBook(false);

                }else if (id>models.size()){
                    card_view_manual_book.setVisibility(View.GONE);
                    cardProfile.setVisibility(View.VISIBLE);
                    animHand.setVisibility(View.VISIBLE);
                    lbl_manual_book.setText("Tap disini untuk pergi ke halaman pengaturan akun");
                }else{
                    meowBottomNavigation.show(id, true);
                    main_linearlayout.setVisibility(View.VISIBLE);
                }
            }
        });


        return view;
    }
    private void setTittleLabel(int id) {
        Animation goDown = AnimationUtils.loadAnimation(requireContext(),R.anim.go_down);
        Animation goUp = AnimationUtils.loadAnimation(requireContext(),R.anim.go_down_in);

        if (tv_lbl_tittle_home.getVisibility()==View.GONE){
            tv_lbl_tittle_home.setVisibility(View.VISIBLE);
            tv_lbl_tittle_home.setAnimation(goUp);
        }
        if (tv_lbl_tittle_receive.getVisibility()==View.GONE){
            tv_lbl_tittle_receive.setVisibility(View.VISIBLE);
            tv_lbl_tittle_receive.setAnimation(goUp);
        }
        if (tv_lbl_tittle_scan.getVisibility()==View.GONE){
            tv_lbl_tittle_scan.setVisibility(View.VISIBLE);
            tv_lbl_tittle_scan.setAnimation(goUp);
        }
        if (tv_lbl_tittle_spending.getVisibility()==View.GONE){
            tv_lbl_tittle_spending.setVisibility(View.VISIBLE);
            tv_lbl_tittle_spending.setAnimation(goUp);
        }
        if (tv_lbl_tittle_setting.getVisibility()==View.GONE){
            tv_lbl_tittle_setting.setVisibility(View.VISIBLE);
            tv_lbl_tittle_setting.setAnimation(goUp);
        }
        switch (id){
            case 1:
                if (tv_lbl_tittle_home.getVisibility()!=View.GONE){
                    goDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tv_lbl_tittle_home.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    tv_lbl_tittle_home.startAnimation(goDown);
                }

                break;
            case 2:
                if (tv_lbl_tittle_receive.getVisibility()!=View.GONE){
                    goDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tv_lbl_tittle_receive.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    tv_lbl_tittle_receive.startAnimation(goDown);
                }
                break;
            case 3:
                if (tv_lbl_tittle_scan.getVisibility()!=View.GONE){
                    goDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tv_lbl_tittle_scan.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    tv_lbl_tittle_scan.startAnimation(goDown);
                }
                break;
            case 4:
                if (tv_lbl_tittle_spending.getVisibility()!=View.GONE){
                    goDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tv_lbl_tittle_spending.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    tv_lbl_tittle_spending.startAnimation(goDown);
                }
                break;
            case 5:
                if (tv_lbl_tittle_setting.getVisibility()!=View.GONE){
                    goDown.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            tv_lbl_tittle_setting.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    tv_lbl_tittle_setting.startAnimation(goDown);
                }
                break;
        }
    }
}