package com.persediaan.de;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ScanInterface{
    MeowBottomNavigation bottomNavigation;

    LinearLayout main_linearlayout;
    CardView cardViewprofile;

//<</User
    SessionManager sessionManager;
    HashMap<String,String> user;
    HashMap<String,Integer> user_Int;
//User/>>

//    <</Transtition
    SessionManager sessionTranstition;
//    Transtition/>>
    String permissionMsg;
    FrameLayout frame_layout;
    FrameLayout frame_layout_manual_book;

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

        sessionTranstition = new SessionManager(this,"transtition");
        user = sessionManager.getUserDetail();
        user_Int = sessionManager.getUserDetailInt();

        main_linearlayout = findViewById(R.id.mainLinearLayout);
        cardViewprofile = findViewById(R.id.cardView);

        img_Profile = findViewById(R.id.imgProfilUser);
        tv_name = findViewById(R.id.tvName);
        tv_satker = findViewById(R.id.tvSatker);
        tv_alamat = findViewById(R.id.tvAlamat);
        username = findViewById(R.id.tvUserName);

//        Fragment Layout
        frame_layout = findViewById(R.id.frame_layout);
        frame_layout_manual_book = findViewById(R.id.frameManualBook);

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
        bottomNavigation.add(new MeowBottomNavigation.Model(5,
                R.drawable.ic_ant_design_setting_filled));
        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                SessionManager session_manual_book = new SessionManager(MainActivity.this,
                        "manualbook");
                Fragment fragment = null;
//                <<Animation>>
//                Animation goUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_up);
//                Animation goDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.go_down);

                switch (item.getId()){
                    case 1:
                        if (!session_manual_book.getManualBook("home")){
                            loadFragmentManualBook(new ManualBookFragment(bottomNavigation.getModels(),
                                    frame_layout_manual_book,main_linearlayout));
                            main_linearlayout.setVisibility(View.GONE);
                            session_manual_book.setManualBook("home",true);
                        }
                        page = "home";
                        fragment = new HomeFragment(bottomNavigation);
                        cardViewprofile.setVisibility(View.VISIBLE);
                        loadFragment(fragment);
                        break;
                    case 2:
                        if (!session_manual_book.getManualBook("receive")){
                            loadFragmentManualBook(new ReceivedManualBookFragment(frame_layout_manual_book,main_linearlayout));
                            main_linearlayout.setVisibility(View.GONE);
                            session_manual_book.setManualBook("receive",true);
                        }
                        page = "penerimaan";
                        fragment = new PenerimaanFragment();
                        cardViewprofile.setVisibility(View.VISIBLE);
                        loadFragment(fragment);

                        break;
                    case 3:
                        page = "scan";
                        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)==
                                PackageManager.PERMISSION_GRANTED){
//                            Toast.makeText(getApplicationContext(),
//                                    "You have already granted this permission!", Toast.LENGTH_SHORT).show();
//                            loadFragment(new ScanPenerimaanFragment(bottomNavigation));
//                            loadFragment(new TestingTranstitionFragment(bottomNavigation));
                            openPage(ScanActivity.class,ScanActivity.SCANNER_TYPE_2);
//                            cardViewprofile.setVisibility(View.GONE);
                        }else
                        {
                            requestCameraPermission();
                        }
//                        runScanner();

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
//                if(bottomNavigation.isShowing(item.getId())){
                    setTittleLabel(item.getId());
//                };
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setTittleLabel(item.getId());
//                    }
//                },500);
            }
        });

//        bottomNavigation.setCount(1,"new");
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
    void runScanner(){

        ZXingScannerView zXingScannerView = new ZXingScannerView(MainActivity.this);
//        View view_btn_action = View.inflate(MainActivity.this,R.layout.btn_action_scan,
//                (ViewGroup) getWindow().getCurrentFocus().getRootView());
        zXingScannerView.startCamera();
//        zXingScannerView.setFlash(true);
        frame_layout.addView(zXingScannerView);

    }

    public void loadFragment(Fragment pFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,pFragment)
                .commit();
    }
    public void loadFragmentManualBook(Fragment pFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameManualBook,pFragment)
                .commit();
    }

    private void openPage(Class cls, String scannerType){
        Intent i = new Intent(MainActivity.this,cls);
        if (scannerType!=null){
            i.putExtra(ScanActivity.TYPESCAN,scannerType);
        }

        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionManager sessionScan = new SessionManager(getApplicationContext(),"scan");

        if(sessionTranstition.getTranstition("receive")){
            bottomNavigation.show(2,true);
            sessionTranstition.clearSession();
        }else if(sessionTranstition.getTranstition("scan")){
            bottomNavigation.show(3,true);
            sessionTranstition.clearSession();
        }else if(sessionTranstition.getTranstition("setting")){
            bottomNavigation.show(5,true);
            sessionTranstition.clearSession();
        }
        count =0;
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA)){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Permession Needed")
                    .setMessage("This permission is needed bacause of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            bottomNavigation.show(1,false);
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                cardViewprofile.setVisibility(View.GONE);
//                loadFragment(new ScanPenerimaanFragment(bottomNavigation));
                openPage(ScanActivity.class,ScanActivity.SCANNER_TYPE_1);
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bottomNavigation.show(1,false);
                    }
                }, 500);
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void OnHandlerResult(String r) {
//        Fragment fragment = new ScanPenerimaanFragment(bottomNavigation);
//        loadFragment(fragment);
    }
}