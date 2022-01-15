package com.persediaan.de;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.data.SessionManager;

import java.util.ArrayList;

public class ManualBookFragment extends Fragment {

    ArrayList<MeowBottomNavigation.Model> models;
    MeowBottomNavigation meowBottomNavigation;
    LinearLayout main_linearlayout;
    Button btn_lewati,btn_next;
    FrameLayout frameLayout;

    TextView tv_lbl_tittle_home,tv_lbl_tittle_receive,
            tv_lbl_tittle_scan,tv_lbl_tittle_spending,
            tv_lbl_tittle_setting;
    TextView lbl_manual_book;
    SessionManager session_manual_book;
    int id = 1;
    public ManualBookFragment(ArrayList<MeowBottomNavigation.Model> models,
                              FrameLayout frameLayout, LinearLayout main_linearlayout) {
        this.models = models;
        this.frameLayout = frameLayout;
        this.main_linearlayout = main_linearlayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual_book, container, false);
        session_manual_book = new SessionManager(requireContext(),"manualbook");
        meowBottomNavigation = view.findViewById(R.id.bottomNavManualBook);
        btn_lewati = view.findViewById(R.id.btnLewati);
        btn_next = view.findViewById(R.id.btnNext);

        tv_lbl_tittle_home = view.findViewById(R.id.lblBotNavTittleHomeManualBook);
        tv_lbl_tittle_receive = view.findViewById(R.id.lblBotNavtittlePenerManualBook);
        tv_lbl_tittle_scan = view.findViewById(R.id.lblBotNavTittleScanManualBook);
        tv_lbl_tittle_spending = view.findViewById(R.id.lblBotNavTittleItemOutManualBook);
        tv_lbl_tittle_setting = view.findViewById(R.id.lblBotNavTittleSettingManualBook);

        lbl_manual_book = view.findViewById(R.id.lblManualBook);

//        meowBottomNavigation.setModels(model);
        for (MeowBottomNavigation.Model model:models){
            meowBottomNavigation.add(new MeowBottomNavigation.Model(model.getId(),model.getIcon()));
        }
//        meowBottomNavigation.setModels(models);
        meowBottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()){
                    case 1:
                        lbl_manual_book.setText("Tap disini untuk membuka halaman HOME");
                        break;
                    case 2:
                        lbl_manual_book.setText("Tap disini untuk membuka halaman RECEIVE");
                        break;
                    case 3:
                        lbl_manual_book.setText("Tap disini untuk memindai item BARCODE");
                        break;
                    case 4:
                        lbl_manual_book.setText("Tap disini untuk membuka halaman SPENDING");
                        break;
                    case 5:
                        lbl_manual_book.setText("Tap disini untuk membuka halaman SETTING");
                        break;
                }
                setTittleLabel(item.getId());
            }
        });
        meowBottomNavigation.show(id,true);

        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                if (id< item.getId()){
                    id = item.getId();
                }
                if ((id+1)>models.size()){
                    btn_next.setText("Finish");
                }
            }
        });
        meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

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
                if ((id+1)>models.size()){
                    btn_next.setText("Finish");
                    main_linearlayout.setVisibility(View.VISIBLE);
                }
                if (id>models.size()) {
                    frameLayout.setVisibility(View.GONE);
                    main_linearlayout.setVisibility(View.VISIBLE);
                    session_manual_book.OpenManualBook(false);

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