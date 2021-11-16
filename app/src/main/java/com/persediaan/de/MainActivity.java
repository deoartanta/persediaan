package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.os.Bundle;
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
    TextView tv_name,username,tv_satker,tv_alamat;

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

        tv_name.setText(user.get(SessionManager.NAMA));
        tv_satker.setText(user.get(SessionManager.SATKER_NM));
        tv_alamat.setText(user.get(SessionManager.AREA_NM));
        username.setText(user.get(SessionManager.USERNAME));
        img_Profile.setImageResource(user_Int.get(SessionManager.GAMBAR));


//        Toast.makeText(getApplicationContext(), "Welcome "+user.get(SessionManager.NAMA), Toast.LENGTH_SHORT).show();

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_penerimaan));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_qr_code_scanner_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.rotate_sign_out_alt_solid));
        bottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.ic_person_24));
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
                        fragment = new BrgKeluarFragment(getSupportFragmentManager());
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
        Fragment fragment = new ScanPenerimaanFragment(bottomNavigation);
        loadFragment(fragment);
    }
}