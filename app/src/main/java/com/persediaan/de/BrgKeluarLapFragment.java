package com.persediaan.de;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.persediaan.de.data.SessionManager;

public class BrgKeluarLapFragment extends Fragment {

    public BrgKeluarLapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SessionManager transtition;
        transtition = new SessionManager(requireContext(),"transtition");
//        transtition.setTranstition("laptransfer",true);
        View view = inflater.inflate(R.layout.fragment_brg_keluar_lap, container, false);
        return view;
    }
}