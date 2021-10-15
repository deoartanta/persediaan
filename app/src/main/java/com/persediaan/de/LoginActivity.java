package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.data.SessionManager;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    SessionManager sessionManager;
    String item[] = {"Malang","Jember","Probolinggo"},
            valItem,msgErrorPassw=null,msgErrorUsername=null;
    Boolean stsArea = false;
    Intent home;

    ArrayAdapter <String> adapter;
    AutoCompleteTextView autoCompleteTextView;
    TextInputEditText tiet_username,tiet_passw;
    Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        home = new Intent(this,MainActivity.class);

        autoCompleteTextView = findViewById(R.id.autoCompleteArea);
        tiet_username = findViewById(R.id.tietUsername);
        tiet_passw = findViewById(R.id.tietPassw);
        btn_signin = findViewById(R.id.btnSignin);

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

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = tiet_username.getText().toString(),
                        password = tiet_passw.getText().toString(),
                        area = valItem;
                sessionManager.Login(username,password,area);
//                if (sessionManager.isLoggin()){
                    startActivity(home);
                    finish();
//                }else{
//                    Toast.makeText(getApplicationContext(),
//                            "Login gagal", Toast.LENGTH_SHORT).show();
//                }
            }
        });
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