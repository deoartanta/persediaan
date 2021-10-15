package com.persediaan.de;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.persediaan.de.data.SessionManager;

import java.util.HashMap;

public class ActionPenerimaanActivity extends AppCompatActivity {
    TextView code_brg;
    SessionManager sessionScanner;

    HashMap<String,String> result_scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_penerimaan);

        code_brg = findViewById(R.id.codeBRG);

        Toolbar toolbar = findViewById(R.id.toolbarPenerimaan);
        toolbar.setTitle("Add Penerimaan");
        setSupportActionBar(toolbar);
        sessionScanner = new SessionManager(this,"scan");
        result_scanner = sessionScanner.getScanResult();

        String sCodeBRG = result_scanner.get(SessionManager.SCANR);

        code_brg.setText(sCodeBRG);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        sessionScanner.clearSession();

    }
}