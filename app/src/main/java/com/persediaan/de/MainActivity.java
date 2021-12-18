package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.tabs.TabLayout;
import com.persediaan.de.data.SessionManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements ScanInterface{
    MeowBottomNavigation bottomNavigation;

    LinearLayout main_linearlayout;
    CardView cardViewprofile;

    SessionManager sessionManager;
    HashMap<String,String> user;
    HashMap<String,Integer> user_Int;

    String permissionMsg;
//  Profile
    CircleImageView img_Profile;
    TextView tv_name,username,tv_satker,tv_alamat,
            tv_lbl_tittle_home,tv_lbl_tittle_receive,
            tv_lbl_tittle_scan,tv_lbl_tittle_spending,
            tv_lbl_tittle_setting;

    String page="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation= findViewById(R.id.bottom_nav);

        sessionManager = new SessionManager(this,"login");
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();
        user_Int = sessionManager.getUserDetailInt();

        main_linearlayout = findViewById(R.id.mainLinearLayout);
        cardViewprofile = findViewById(R.id.cardView);

        img_Profile = findViewById(R.id.imgProfilUser);
        tv_name = findViewById(R.id.tvName);
        tv_satker = findViewById(R.id.tvSatker);
        tv_alamat = findViewById(R.id.tvAlamat);
        username = findViewById(R.id.tvUserName);

//        Bottom Navigation
        tv_lbl_tittle_home = findViewById(R.id.lblBotNavTittleHome);
        tv_lbl_tittle_receive = findViewById(R.id.lblBotNavtittlePener);
        tv_lbl_tittle_scan = findViewById(R.id.lblBotNavTittleScan);
        tv_lbl_tittle_spending = findViewById(R.id.lblBotNavTittleItemOut);
        tv_lbl_tittle_setting = findViewById(R.id.lblBotNavTittleSetting);

        tv_name.setText(user.get(SessionManager.NAMA));
        tv_satker.setText(user.get(SessionManager.SATKER_NM));
        tv_alamat.setText(user.get(SessionManager.AREA_NM)!=null?
                user.get(SessionManager.AREA_NM).toLowerCase(Locale.ROOT):
                user.get(SessionManager.AREA_NM)+"null");
        username.setText(user.get(SessionManager.USERNAME));
        img_Profile.setImageResource(user_Int.get(SessionManager.GAMBAR));
//        Log.d("19201299", "onCreate: "+sessionManager.toString());
//        Toast.makeText(this, user.get(SessionManager.AREA_NM), Toast.LENGTH_SHORT).show();
//
//
//        Toast.makeText(getApplicationContext(), "Welcome "+user.get(SessionManager.NAMA), Toast.LENGTH_SHORT).show();
        MeowBottomNavigation.Model model_bot_nav = new MeowBottomNavigation.Model(1,
                R.drawable.ic_home_24);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_dashicons_admin_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_fluent_mail_inbox_arrow_down_16_filled));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_bx_bx_barcode_reader));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_fluent_mail_inbox_arrow_up_20_filled));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_ant_design_setting_filled));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
//                <<Animation>>
//                Animation goUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_up);
//                Animation goDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_down);

                switch (item.getId()){
                    case 1:
                        page = "home";
                        fragment = new HomeFragment(bottomNavigation);
                        cardViewprofile.setVisibility(View.VISIBLE);
                        loadFragment(fragment);
                        break;
                    case 2:
                        page = "penerimaan";
                        fragment = new PenerimaanFragment();
                        cardViewprofile.setVisibility(View.VISIBLE);
                        loadFragment(fragment);

                        break;
                    case 3:
                        page = "scan";
                        runScanner(new ScanPenerimaanFragment(bottomNavigation));
                        cardViewprofile.setVisibility(View.GONE);

//                        finish();
                        break;
                    case 4:
                        page = "barang keluar";
                        fragment = new BrgKeluarFragment(getSupportFragmentManager(), bottomNavigation);
                        cardViewprofile.setVisibility(View.VISIBLE);
                        loadFragment(fragment);
                        break;
                    case 5:
                        page = "profil";
                        fragment = new ProfileFragment();
                        cardViewprofile.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cardViewprofile.setVisibility(View.GONE);
                            }
                        },200);
                        loadFragment(fragment);
                        break;
                }
                setTittleLabel(item.getId());
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setTittleLabel(item.getId());
//                    }
//                },500);
            }
        });
        bottomNavigation.setCount(1,"new");
        bottomNavigation.show(1,true);


        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                bottomNavigation.show(item.getId(),true);
            }
        });
    }

    private void setTittleLabel(int id) {
        Animation goDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_down);
        Animation goUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_down_in);

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

    int count = 0;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (page.equals("home")) {
            count++;
        } else {
            page = "noHome";
            bottomNavigation.show(1,true);
            count = 0;

        }
        if(count==1){
            if(page.equals("home")){
                Toast.makeText(getApplicationContext(), "Tekan lagi untuk menutup "+page, Toast.LENGTH_SHORT).show();
            }
        }else if(count>=2){
            if(page.equals("home")){
                finish();
            }
        }
        page = "home";
    }

    private void loadFragment(Fragment pFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,pFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionScan = new SessionManager(getApplicationContext(),"scan");
        if (sessionScan.isEditScanner()){
            bottomNavigation.show(3,false);
        }
        count =0;
    }

    public void runScanner(Fragment pFragment){

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        permissionMsg = null;
                        loadFragment(pFragment);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        permissionMsg = "Anda bisa menyalakan perizinan camera di pengaturan";
                        permissionDeniedResponse.getRequestedPermission();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(
                            PermissionRequest permissionRequest,
                            PermissionToken permissionToken) {
                        permissionMsg = "Akses kamera belum diizinkan";
                    }
                }).check();
        if (permissionMsg!=null) {
            Toast.makeText(getApplicationContext(), permissionMsg, Toast.LENGTH_LONG).show();
//            Snackbar.make(getCurrentFocus(), permissionMsg, Snackbar.LENGTH_LONG)
//                    .setAction("Info", null).show();
        }
    }

    @Override
    public void OnHandlerResult(String r) {
        Fragment fragment = new ScanPenerimaanFragment(bottomNavigation);
        loadFragment(fragment);
    }
}