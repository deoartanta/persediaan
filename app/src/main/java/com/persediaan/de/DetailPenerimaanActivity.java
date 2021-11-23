package com.persediaan.de;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class DetailPenerimaanActivity extends AppCompatActivity {

    public static String ID_TRANS = "ID_TRANS";
    Toolbar toolbar;
    ActionBar bar;

    TextView tv_idtrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_penerimaan);

//        toolbar =findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
//        toolbar.setTitle("Detail Penerimaan");
//        toolbar.setTitleMarginStart(0);
//        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//        setSupportActionBar(toolbar);

//        if(toolbar!=null) {
//            getSupportActionBar().setTitle();
//        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//
//        });

        tv_idtrans = findViewById(R.id.tvPurchase);
        Bundle extras = getIntent().getExtras();

        tv_idtrans.setText(extras.getString(ID_TRANS));
    }

}