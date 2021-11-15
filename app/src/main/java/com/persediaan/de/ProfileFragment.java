package com.persediaan.de;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.adapter.AdapterAkunSetting;
import com.persediaan.de.adapter.RecyclerViewClickExpendInterface;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.ModelProfileRowExpand;
import com.persediaan.de.model.ModelProfileRowItem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements RecyclerViewClickExpendInterface,RecyclerViewClickInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int i_List_Item;

    SessionManager sessionManagerProfil;
    HashMap<String,String> detail_profile;

    TextView satker,nama_lengkap,alamat_profile;
    ImageView imgProfile;

    private AdapterAkunSetting adapterProfile;
//    private AdapterAkunSetting adapter;

    ArrayList<ModelProfileRowExpand>arrayList_profileRowExpand;

    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;

    RecyclerView recyclerView_profileSetting;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        recyclerView_profileSetting = view.findViewById(R.id.reyclerProfileSetting);
        sessionManagerProfil = new SessionManager(requireContext(),"login");
        detail_profile = sessionManagerProfil.getUserDetail();
        HashMap<String,Integer>detail_profile_int = sessionManagerProfil.getUserDetailInt();

        nama_lengkap = view.findViewById(R.id.profileTvName);
        satker = view.findViewById(R.id.profileTvSatker);
        alamat_profile = view.findViewById(R.id.profileTvAlamat);
        imgProfile = view.findViewById(R.id.imgProfilUser);

        nama_lengkap.setText(detail_profile.get(SessionManager.NAMA));
        satker.setText(detail_profile.get(SessionManager.SATKER_NM));
        alamat_profile.setText(detail_profile.get(SessionManager.ALAMAT));
        imgProfile.setImageResource(detail_profile_int.get(SessionManager.GAMBAR));

        loadSettingProfile();
        return view;
    }
    public ArrayList<ModelProfileRowItem> createRowItem(String[]item){
        ArrayList<ModelProfileRowItem>itemArray=new ArrayList<ModelProfileRowItem>();
        for (String itemLopp:item){
            itemArray.add(new ModelProfileRowItem(itemLopp,
                    R.drawable.ic_baseline_keyboard_arrow_right_24));
        }
        return itemArray;
    };
    public ArrayList<ModelProfileRowItem> createRowItem(String[]item,String[]result){
        ArrayList<ModelProfileRowItem>itemArray=new ArrayList<ModelProfileRowItem>();

        for (String itemLopp:item){
            itemArray.add(new ModelProfileRowItem(itemLopp,
                    R.drawable.ic_baseline_keyboard_arrow_right_24).set_Result(result));
        }
        return itemArray;
    };
    public void loadSettingProfile(){
        arrayList_profileRowExpand = new ArrayList<ModelProfileRowExpand>();
        String  namaUser = detail_profile.get(SessionManager.NAMA),
                usernameShord="",
                username = detail_profile.get(SessionManager.USERNAME),
                password = detail_profile.get(SessionManager.PASSW),
                alamat = detail_profile.get(SessionManager.ALAMAT),
                passwordHide= "";

        if (namaUser.length()>8){
            for (int i =0;i<8;i++){
                usernameShord = usernameShord+String.valueOf(namaUser.charAt(i));
            }
            usernameShord = usernameShord+"...";
        }else{
            usernameShord = namaUser;
        }

        if (password.length()<8){
            for (int i=0;i<password.length();i++){
                passwordHide =passwordHide+"*";
            }
        }else{
            for (int i=0;i<8;i++){
                passwordHide =passwordHide+"*";
            }
        }

        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                "Akun",R.drawable.ic_person_edit,
                createRowItem(
                        new String[]{"Nama","Username","Password","Alamat"},
                        new String[]{usernameShord,username,passwordHide,alamat}
                        ), true));
//        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
//                "Setting",R.drawable.ic_person_edit,
//                createRowItem(
//                        new String[]{"Screen Orientation","Themes"},
//                        new String[]{"Portrait","Light"}
//                ), true));
        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                "Logout",R.drawable.ic_baseline_power_settings_new_24,
                null, false));

        adapterProfile = new AdapterAkunSetting(
                arrayList_profileRowExpand,ProfileFragment.this);

        recyclerView_profileSetting.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView_profileSetting.setHasFixedSize(true);
        recyclerView_profileSetting.setItemAnimator(new DefaultItemAnimator());

        recyclerView_profileSetting.setAdapter(adapterProfile);


    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(requireContext(), "Position "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemExpendClick(int position, View view,boolean isEnable) {
        if (isEnable){
            AlertDialog.Builder dialog1;
            View viewInflated;
            viewInflated = LayoutInflater.from(getContext()).inflate(
                    R.layout.input_alert_dialog, (ViewGroup) getView(), false);
            final TextInputLayout inputLayout = viewInflated.findViewById(R.id.layoutInput);
            final TextInputEditText EditTextInput = viewInflated.findViewById(R.id.editTextInput);

            dialog1 = new AlertDialog.Builder(requireContext());
            dialog1.setIcon(R.drawable.ic_person_edit);
            switch (position){
                case 0:
                    inputLayout.setHint("New Name");
                    dialog1.setTitle("Nama");
                    dialog1.setView(viewInflated);
                    EditTextInput.setInputType(InputType.TYPE_CLASS_TEXT);

                    dialog1.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String m_input = EditTextInput.getText().toString();
//                        editProfile(m_input);

                            System.out.println("tes output :"+m_input);
                        }
                    });

                    dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                    });
                    dialog1.create().show();
                    break;
                case 1:
                    inputLayout.setHint("New Username");
                    dialog1.setTitle("Username");
                    EditTextInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    dialog1.setView(viewInflated);

                    dialog1.setPositiveButton("Next", (dialogInterface,i)->{
                        String m_input = EditTextInput.getText().toString();
                        editProfile(m_input);
                    });

                    dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                    });
                    dialog1.create().show();
                    break;
                case 2:
                    inputLayout.setHint("New Password");
                    inputLayout.setPasswordVisibilityToggleEnabled(true);
                    dialog1.setTitle("Password");
                    EditTextInput.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    dialog1.setView(viewInflated);

                    dialog1.setPositiveButton("Next", (dialogInterface,i)->{
                        String m_input = EditTextInput.getText().toString();
                        editProfile(m_input);
                    });

                    dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                    });
                    dialog1.create().show();
                    break;
                case 3:
                    inputLayout.setHint("New Alamat");
                    dialog1.setTitle("Alamat");
                    EditTextInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    dialog1.setView(viewInflated);

                    dialog1.setPositiveButton("Next", (dialogInterface,i)->{
                        String m_input = EditTextInput.getText().toString();
                        editProfile(m_input);
                    });

                    dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                        dialogInterface.cancel();
                    });
                    dialog1.show();
                    break;
            }

        }else{
            switch (position){
                case 1:
                    sessionManagerProfil.logout();
                    break;
            }
        }
    }

    private void editProfile(String m_input) {
        Toast.makeText(requireContext(),m_input,Toast.LENGTH_SHORT);
    }
}