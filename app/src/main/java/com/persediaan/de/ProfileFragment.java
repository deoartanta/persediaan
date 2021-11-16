package com.persediaan.de;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.se.omapi.Session;
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
import com.persediaan.de.api.ApiLogin;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.SessionManager;
import com.persediaan.de.model.ModelPenerimaan;
import com.persediaan.de.model.ModelProfileRowExpand;
import com.persediaan.de.model.ModelProfileRowItem;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements RecyclerViewClickExpendInterface,RecyclerViewClickInterface {

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

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
    HashMap<String,Integer> detail_profile_int;

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
        detail_profile_int = sessionManagerProfil.getUserDetailInt();

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
                            String m_output = "-";
                            m_output = editProfile(
                                    detail_profile_int.get(SessionManager.USER_ID),
                                    m_input,"nama");
                            EditTextInput.setText(m_output);
                            System.out.println("tes output :"+m_output);
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
                        String m_output = "-";
                        m_output = editProfile(
                                detail_profile_int.get(SessionManager.USER_ID),
                                m_input,"username");
                        EditTextInput.setText(m_output);
                        System.out.println("tes output :"+m_output);
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
                        String m_output = "-";
                        m_output = editProfile(
                                detail_profile_int.get(SessionManager.USER_ID),
                                m_input,"password");
                        EditTextInput.setText(m_output);
                        System.out.println("tes output :"+m_output);
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
                        String m_output = "-";
                        m_output = editProfile(
                                detail_profile_int.get(SessionManager.USER_ID),
                                m_input,"alamat");
                        EditTextInput.setText(m_output);
                        System.out.println("tes output :"+m_output);
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

    private String editProfile(int p_iduser,String m_input,String type) {
        retrofit = new Retrofit.Builder()
                    .baseUrl(SessionManager.HOSTNAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        String input_nama = detail_profile.get(SessionManager.NAMA);
        String input_username = detail_profile.get(SessionManager.USERNAME);
        String input_password = detail_profile.get(SessionManager.PASSW);
        String input_alamat = detail_profile.get(SessionManager.ALAMAT);
        String old_result="";

        if (type.toLowerCase().equals("nama")){
            input_nama = m_input;
        }else if (type.toLowerCase().equals("username")) {
            input_username = m_input;
        }else if (type.toLowerCase().equals("password")) {
            input_password = m_input;
        } else if (type.toLowerCase().equals("alamat")) {
            input_alamat = m_input;
        }

        Call<ApiLogin> call = jsonPlaceHolderApi.getResponEditUser(
                p_iduser,input_nama,input_username,input_password,input_alamat
        );

        call.enqueue(new Callback<ApiLogin>() {
            @Override
            public void onResponse(Call<ApiLogin> call, Response<ApiLogin> response) {
                String result = null;

                ApiLogin editLogin = response.body();
                if(!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Edit profil gagal", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(requireContext(), "Edit profil berhasil", Toast.LENGTH_SHORT).show();

                if (type.toLowerCase().equals("nama")){
                    result = editLogin.getNama();
                }else if (type.toLowerCase().equals("username")) {
                    result = editLogin.getAlamat();
                }else if (type.toLowerCase().equals("password")) {
                    result = editLogin.getPassword();
                } else if (type.toLowerCase().equals("alamat")) {
                    result = editLogin.getUsername();
                }
//                old_result = result;

            }

            @Override
            public void onFailure(Call<ApiLogin> call, Throwable t) {
                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return old_result;
    }
}