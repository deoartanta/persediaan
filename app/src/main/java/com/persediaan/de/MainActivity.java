package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.persediaan.de.data.SessionManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ScanInterface{
    MeowBottomNavigation bottomNavigation;

    LinearLayout main_linearlayout;

    SessionManager sessionManager;
    HashMap<String,String> user;

    String permissionMsg;

    String page="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation= findViewById(R.id.bottom_nav);

        sessionManager = new SessionManager(this,"login");
        sessionManager.checkLogin();
        user = sessionManager.getUserDetail();

        main_linearlayout = findViewById(R.id.mainLinearLayout);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_penerimaan));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_qr_code_scanner_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.rotate_sign_out_alt_solid));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_person_24));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                Animation goUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_up);
                Animation goDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_down);
                switch (item.getId()){
                    case 1:
                        page = "home";
                        fragment = new HomeFragment();
                        main_linearlayout.setVisibility(View.VISIBLE);
                        main_linearlayout.setAnimation(goDown);
                        loadFragment(fragment);
                        break;
                    case 2:
                        page = "penerimaan";
                        fragment = new PenerimaanFragment();
                        main_linearlayout.setVisibility(View.VISIBLE);
                        main_linearlayout.setAnimation(goDown);
                        loadFragment(fragment);

                        break;
                    case 3:
                        page = "scan";
                        runScanner(new ScanPenerimaanFragment());
                        main_linearlayout.setVisibility(View.GONE);

//                        finish();
                        break;
                    case 4:
                        page = "barang keluar";
                        fragment = new BrgKeluarFragment();
                        main_linearlayout.setVisibility(View.VISIBLE);
                        main_linearlayout.setAnimation(goDown);
                        loadFragment(fragment);
                        break;
                    case 5:
                        page = "profil";
                        fragment = new ProfileFragment();
                        main_linearlayout.setAnimation(goUp);
                        main_linearlayout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                main_linearlayout.setVisibility(View.GONE);
                            }
                        },200);
                        loadFragment(fragment);
                        break;
                }
            }
        });
        bottomNavigation.setCount(1,"new");
        bottomNavigation.show(1,true);


        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),
                        "You clicked "+page+" : "+item.getId(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(),
                        "You Reselected "+page+" : "+item.getId(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
    private void fragmentAction(Fragment pFragment, String action) {
        if (action=="hide") {
            getSupportFragmentManager()
                    .beginTransaction()
                    .hide(pFragment).commit();
        }else if(action == "show"){
            getSupportFragmentManager()
                    .beginTransaction()
                    .show(pFragment).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        Fragment fragment = new ScanPenerimaanFragment();
        loadFragment(fragment);
    }
}