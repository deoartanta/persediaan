package com.persediaan.de;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class TestingTranstitionFragment extends Fragment {

    Button btn_scanner1,btn_scanner2,btn_scanner3;
    FrameLayout frame_testing;

    LinearLayout linear_button_testing;
    MeowBottomNavigation meowBottomNavigation;

    public TestingTranstitionFragment(MeowBottomNavigation meowBottomNavigation) {
        this.meowBottomNavigation = meowBottomNavigation;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_testing_transtition, container, false);

        frame_testing = view.findViewById(R.id.frameTesting);

        linear_button_testing = view.findViewById(R.id.linearButtonTesting);

        btn_scanner1 = view.findViewById(R.id.scanner1);
        btn_scanner2 = view.findViewById(R.id.scanner2);
        btn_scanner3 = view.findViewById(R.id.scanner3);

        btn_scanner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage(ScanActivity.class,ScanActivity.SCANNER_TYPE_1);
            }
        });
        btn_scanner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage(ScanActivity.class,ScanActivity.SCANNER_TYPE_2);
            }
        });
        btn_scanner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPage(new ScanPenerimaanFragment(meowBottomNavigation));
            }
        });

        return view;
    }

    private void openPage(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.frameTesting,fragment)
                .commit();
    }
    private void openPage(Class cls, String scannerType){
        Intent i = new Intent(requireContext(),cls);
        if (scannerType!=null){
            i.putExtra(ScanActivity.TYPESCAN,scannerType);
        }

        startActivity(i);
    }
}