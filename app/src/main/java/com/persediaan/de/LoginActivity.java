package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.api.*;
import com.persediaan.de.data.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String item[] = {"Malang","Jember","Probolinggo"},
            valItem,msgErrorPassw=null,msgErrorUsername=null;
    Boolean stsArea = true;
    Intent home;

    ArrayAdapter <String> adapter;
    AutoCompleteTextView autoCompleteTextView;
    TextInputEditText tiet_username,tiet_passw;
    Button btn_signin;
    ProgressBar progess_login;
    ImageView img_login;
    ImageButton btn_refresh;
    int img_hasil_r;
    Animation rotate;

//    Connection
    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    int img[]={
            R.drawable.avatar,R.drawable.avatar04,R.drawable.avatar5,
            R.drawable.avatar6, R.drawable.avatar7,R.drawable.avatar8,
            R.drawable.avatar9,R.drawable.avatar10,R.drawable.avatar11,
            R.drawable.avatar2,R.drawable.avatar3,R.drawable.avatar12,
            R.drawable.avatar13
    };
    Random iImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        home = new Intent(this,MainActivity.class);

        autoCompleteTextView = findViewById(R.id.autoCompleteArea);
        tiet_username = findViewById(R.id.tietUsername);
        tiet_passw = findViewById(R.id.tietPassw);

        btn_signin = findViewById(R.id.btnSignin);
        btn_refresh = findViewById(R.id.btnRefresh);

        img_login = findViewById(R.id.imgLogin);
        
        progess_login = findViewById(R.id.progressLoginBar);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        btn_refresh.setAnimation(rotate);

        iImage= new Random();
        img_hasil_r = img[iImage.nextInt(img.length)];
        img_login.setImageResource(img_hasil_r);

        adapter = new ArrayAdapter<>(this,R.layout.list_item,item);
        autoCompleteTextView.setAdapter(adapter);
        sessionManager = new SessionManager(getApplicationContext(),"login");

        //        Cek status login
        if (sessionManager.isLoggin()) {
            startActivity(home);
            finish();
        }

//        Area Dropdown
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                valItem = adapterView.getItemAtPosition(i).toString();
                stsArea = true;
                loginDataChanged(tiet_username.getText().toString(),tiet_passw.getText().toString());
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginDataChanged(tiet_username.getText().toString(),
                        tiet_passw.getText().toString());
            }
        };

        tiet_username.addTextChangedListener(afterTextChangedListener);
        tiet_passw.addTextChangedListener(afterTextChangedListener);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImg();
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                progess_login.setVisibility(View.VISIBLE);
                btn_signin.setText(null);
                btn_signin.setEnabled(false);

                retrofit = new Retrofit.Builder()
                        .baseUrl(SessionManager.HOSTNAME)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                String username = tiet_username.getText().toString(),
                        password = tiet_passw.getText().toString(),
                        area = valItem;
                Call<ApiLogin> call=jsonPlaceHolderApi.getResponLogin(
                        username,
                        password);
                call.enqueue(new Callback<ApiLogin>() {
                    @Override
                    public void onResponse(Call<ApiLogin> call, Response<ApiLogin> response) {
                        ApiLogin login = response.body();
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                            progess_login.setVisibility(View.GONE);
                            btn_signin.setText("SIGN IN");
                            return ;
                        }
//
//                        System.out.println(login.toString());
                        Log.d("19201299", "onResponse: "+login.toString());

                        String nama = login.getNama();
                        String username = login.getUsername();
                        int user_id = login.getUser_id();
//                        String password = login.getPassword();
                        int area_id = login.getId_area();
                        String alamat = login.getAlamat();
                        int level = login.getLevel();
                        int gambar = img_hasil_r;
                        String area_nm = login.getNm_area();
                        String area_singkat_nm = login.getNm_singkat();
                        int satker_id = login.getId_satker();
                        int kode_satker = login.getKode_satker();
                        String satker_nm = login.getNm_satker();
                        String jenis_kew = login.getJenis_kew();
                        String alamat_kantor = login.getAlamat_kantor();
                        int kppn = login.getKppn();
                        Toast.makeText(getApplicationContext(), "Username : "+username, Toast.LENGTH_SHORT).show();


                            sessionManager.Login(
                                    username,password,area_nm,user_id,area_id,nama,alamat,level,
                                    gambar,area_nm,area_singkat_nm,satker_id,kode_satker,satker_nm
                                    ,jenis_kew,alamat_kantor,kppn

                            );
                        if (sessionManager.isLoggin()){
                            startActivity(home);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                        progess_login.setVisibility(View.GONE);
                        btn_signin.setText("SIGN IN");
                        btn_signin.setEnabled(true);
                    }

                    @Override
                    public void onFailure(Call<ApiLogin> call, Throwable t) {
                        progess_login.setVisibility(View.GONE);
                        btn_signin.setText("SIGN IN");
                        btn_signin.setEnabled(true);
                        Toast.makeText(getApplicationContext(), "Login Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void changeImg(){
        rotate = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
        btn_refresh.setAnimation(rotate);
        img_hasil_r = img[iImage.nextInt(img.length)];
        img_login.setImageResource(img_hasil_r);
    }
    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        Boolean sts = true;
        if (!username.trim().isEmpty()) {
            msgErrorUsername = "Required Field!";
            sts = false;
        }
        if (username.contains("@")) {
            sts = Patterns.EMAIL_ADDRESS.matcher(username).matches();
            msgErrorUsername = "Invalid Email";
        } else {
            return !username.trim().isEmpty();
        }
        if(sts !=false){
            msgErrorUsername = null;
        }
        return sts;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        Boolean sts = false;
        if (password == null){
            msgErrorPassw = "Required Field!";
            sts = false;
        }
        if(password.trim().length() <= 6){
            msgErrorPassw = "password can't be less than 6";
            sts = false;
        }
        if (password != null && password.trim().length() > 5){
            msgErrorPassw = null;
            sts = true;
        }
        return sts;
    }

    private String getMsgPassw(){
        return msgErrorPassw;
    }

    public String getMsgErrorUsername() {
        return msgErrorUsername;
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            tiet_username.setError(getMsgErrorUsername());
        } else if (!isPasswordValid(password)) {
            tiet_passw.setError(getMsgPassw());
        }
        if (isPasswordValid(password) && isUserNameValid(username) && stsArea){
            btn_signin.setEnabled(true);
        }else{
            btn_signin.setEnabled(false);
        }
    }
}