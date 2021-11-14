package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailPenerimaanActivity extends AppCompatActivity {

    public static String ID_TRANS = "ID_TRANS";

    TextView tv_idtrans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penerimaan);

        tv_idtrans = findViewById(R.id.tvIdTrans);
        Bundle extras = getIntent().getExtras();

        tv_idtrans.setText(extras.getString(ID_TRANS));
    }

}