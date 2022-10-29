package com.persediaan.de;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.health.TimerStat;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.persediaan.de.api.ApiLogin;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashscreenActivity extends AppCompatActivity {
    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    Animation topAnim,botAnim;
    ProgressBar progressBar;

    LottieAnimationView imgAnim;

    TextView title,tv_notif;
    Intent home,login;

    SessionManager sessionManagerLogin;
    HashMap<String,Integer> detailUserInt;
    HashMap<String,String> detailUser;

    String msg = "";
    boolean connection,
            timeOutConnect,
            noInternet,noKuota,
            reload=false,sts=true;
    int s = 5,i=1,ms = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        home=new Intent(this,MainActivity.class);
        login=new Intent(this,LoginActivity.class);

        imgAnim = findViewById(R.id.animation_view);
        title = findViewById(R.id.tvTitle);
        tv_notif = findViewById(R.id.tvNotif);
        tv_notif.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressSplash);
        progressBar.setVisibility(View.GONE);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_to_bottom);
        botAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_to_top);

        sessionManagerLogin = new SessionManager(this,"login");
        detailUser = sessionManagerLogin.getUserDetail();
        detailUserInt = sessionManagerLogin.getUserDetailInt();

        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        imgAnim.setAnimation(topAnim);
        title.setAnimation(botAnim);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        },1000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    tv_notif.setText("Menghubungkan...");
                    tv_notif.setVisibility(View.VISIBLE);
                }
            },1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                progressBar.setVisibility(View.VISIBLE);
                Call<ApiLogin> call =
                        jsonPlaceHolderApi.getCheckLogin(
                                String.valueOf(detailUserInt.get(SessionManager.USER_ID)),
                                detailUser.get(SessionManager.PASSW));
                if (sessionManagerLogin.isLoggin()){
                    call.enqueue(new Callback<ApiLogin>() {
                        @Override
                        public void onResponse(Call<ApiLogin> call, Response<ApiLogin> response) {
                            ApiLogin responLogin = response.body();
                            progressBar.setVisibility(View.GONE);
                            if (!response.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "login gagal",
                                        Toast.LENGTH_SHORT).show();
                                tv_notif.setText("Gagal terhubung["+response.code()+"]");
                                new AlertDialog.Builder(SplashscreenActivity.this)
                                        .setIcon(R.drawable.ic_baseline_warning_24)
                                        .setTitle("Server error["+response.code()+"]")
                                        .setMessage("Kemungkinan server sedang sibuk, cobalah " +
                                                "beberapa saat lagi...")
                                        .setCancelable(true)
                                        .setPositiveButton("Login",
                                                new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                sessionManagerLogin.clearSession();
                                                Pair[] pairs = new Pair[2];
                                                pairs[0] = new Pair<View,String>(imgAnim,"imgTrans");
                                                pairs[1] = new Pair<View,String>(title,"titleTrans");
                                                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashscreenActivity.this,pairs);
                                                startActivity(login,options.toBundle());
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Keluar",
                                                new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.dismiss();
                                                finish();
                                            }
                                        }).create().show();
                                return;
                            }
                            System.out.println(responLogin.toString());
                            String nama = responLogin.getNama();
                            String username = responLogin.getUsername();
                            int user_id = responLogin.getUser_id();
                            String password = responLogin.getPassword();
                            int area_id = responLogin.getId_area();
                            String alamat = responLogin.getAlamat();
                            int level = responLogin.getLevel();
                            int gambar = detailUserInt.get(SessionManager.GAMBAR);
                            String area_nm = detailUser.get(SessionManager.AREA_NM);
                            String area_singkat_nm = detailUser.get(SessionManager.AREA_NM_SHORT);;
                            Integer satker_id = detailUserInt.get(SessionManager.SATKER_ID);
                            Integer kode_satker = detailUserInt.get(SessionManager.SATKER_KODE);;
                            String satker_nm = detailUser.get(SessionManager.SATKER_NM);
                            String jenis_kew = detailUser.get(SessionManager.JENIS_KEW);
                            String alamat_kantor = detailUser.get(SessionManager.ALAMAT_KANTOR);
                            Integer kppn = detailUserInt.get(SessionManager.KPPN);
                            sessionManagerLogin.clearSession();

                            if (nama!=null){
                                sessionManagerLogin.Login(
                                        username,password,area_nm,user_id,area_id,nama,alamat,level,
                                        gambar,area_nm,area_singkat_nm,satker_id==null?0:satker_id,
                                        kode_satker==null?0:kode_satker,satker_nm,jenis_kew,alamat_kantor
                                        ,kppn==null?0:kppn
                                );
                            }else{
//                            sessionManagerLogin.logout();
                                Toast.makeText(getApplicationContext(), responLogin.getMsg()+
                                                "\nSilahkan Login Kembali",
                                        Toast.LENGTH_SHORT).show();
                                tv_notif.setText("Gagal terhubung");
                            }
                            tv_notif.setText("Terhubung");
                            Pair[] pairs = new Pair[2];
                            pairs[0] = new Pair<View,String>(imgAnim,"imgTrans");
                            pairs[1] = new Pair<View,String>(title,"titleTrans");
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashscreenActivity.this,pairs);
                            startActivity(login,options.toBundle());
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ApiLogin> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            msg = t.getMessage();
                            connection = msg.contains("Unable");
                            timeOutConnect = msg.contains("failed");
                            noInternet = msg.contains("timed out");
                            noKuota = msg.contains("Failed");
                            reload = true;
                            s = 5;i=1;ms = 1;
                            if (connection){
                                msg = "Mohon periksa kembali koneksi anda";
                            }
                            if (timeOutConnect){
                                msg ="Koneksi anda lambat, coba lagi dilain waktu";
                            }
                            if (noInternet){
                                msg = "Tidak ada koneksi internet";
                            }
                            if (noKuota){
                                msg = "Tidak ada koneksi internet, pastikan anda mempunyai kuota " +
                                        "internet agar dapat menggunakan aplikasi ini ";
                            }

                            tv_notif.setPadding(5,5,5,5);
                            tv_notif.setText("Gagal terhubung");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    sessionManagerLogin.clearSession();
                                    new AlertDialog.Builder(SplashscreenActivity.this)
                                            .setIcon(R.drawable.ic_baseline_warning_24)
                                            .setTitle("Gagal Terhubung")
                                            .setMessage(msg)
                                            .setCancelable(true)
                                            .setPositiveButton("Login",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            sessionManagerLogin.clearSession();
                                                            Pair[] pairs = new Pair[2];
                                                            pairs[0] = new Pair<View,String>(imgAnim,"imgTrans");
                                                            pairs[1] = new Pair<View,String>(title,"titleTrans");
                                                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashscreenActivity.this,pairs);
                                                            startActivity(login,options.toBundle());
                                                            finish();
                                                        }
                                                    })
                                            .setNegativeButton("Keluar",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            dialogInterface.dismiss();
                                                            finish();
                                                        }
                                                    }).create().show();

//                                    Pair[] pairs = new Pair[2];
//                                    pairs[0] = new Pair<View,String>(imgAnim,"imgTrans");
//                                    pairs[1] = new Pair<View,String>(title,"titleTrans");
//                                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashscreenActivity.this,pairs);
//                                    startActivity(login,options.toBundle());
//                                    finish();
                                }
                            }, 2000);
                        }
                    });
                }else{
                    tv_notif.setText("Membuka halaman login...");
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(imgAnim,"imgTrans");
                    pairs[1] = new Pair<View,String>(title,"titleTrans");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashscreenActivity.this,pairs);
                    startActivity(login,options.toBundle());
                    finish();
                }
            }
        },2000);
    }

    private void reload(int i) {
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long l) {
                tv_notif.setText(""+l);
            }

            @Override
            public void onFinish() {
                sts = true;

            }
        }.start();
    }
}