package com.persediaan.de;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ProfileFragment extends Fragment implements RecyclerViewClickExpendInterface,RecyclerViewClickInterface {

    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

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

        setProfile();
        loadSettingProfile("akun");
        return view;
    }
    public void setProfile(){
        detail_profile = sessionManagerProfil.getUserDetail();
        detail_profile_int = sessionManagerProfil.getUserDetailInt();

        nama_lengkap.setText(detail_profile.get(SessionManager.NAMA));
        satker.setText(detail_profile.get(SessionManager.SATKER_NM));
        alamat_profile.setText(detail_profile.get(SessionManager.ALAMAT));
        imgProfile.setImageResource(detail_profile_int.get(SessionManager.GAMBAR));
    }
    public ArrayList<ModelProfileRowItem> createRowItem(String[]item){
        ArrayList<ModelProfileRowItem>itemArray=new ArrayList<ModelProfileRowItem>();
        for (String itemLopp:item){
            itemArray.add(new ModelProfileRowItem(itemLopp,
                    R.drawable.ic_baseline_keyboard_arrow_right_24));
        }
        return itemArray;
    };
    public ArrayList<ModelProfileRowItem> createRowItem(
            String[]item,String[]result){
        ArrayList<ModelProfileRowItem>itemArray=new ArrayList<ModelProfileRowItem>();

        for (String itemLopp:item){
            itemArray.add(new ModelProfileRowItem(itemLopp,
                    R.drawable.ic_baseline_keyboard_arrow_right_24).set_Result(result));
        }
        return itemArray;
    };
    public ArrayList<ModelProfileRowItem> createRowItem(
            String[]item,String[]result,int[] typeText){
        ArrayList<ModelProfileRowItem>itemArray=new ArrayList<ModelProfileRowItem>();

        for (String itemLopp:item){
            itemArray.add(new ModelProfileRowItem(itemLopp,
                    R.drawable.ic_baseline_keyboard_arrow_right_24)
                    .set_Result(result)
                    .set_TypeText(typeText));
        }
        return itemArray;
    };
    public void loadSettingProfile( String state){
        detail_profile = sessionManagerProfil.getUserDetail();
        arrayList_profileRowExpand = new ArrayList<ModelProfileRowExpand>();
        String  namaUser = detail_profile.get(SessionManager.NAMA),
                usernameShord="",
                username = detail_profile.get(SessionManager.USERNAME),
                password = detail_profile.get(SessionManager.PASSW),
                alamat = detail_profile.get(SessionManager.ALAMAT),
                stateAkun ="",
                passwordHide= "";

        usernameShord = textWrap(
                namaUser,
                8,"text"
        );
        passwordHide = textWrap(
                password,
                8,"password"
        );
        if (state.toLowerCase()=="akun"){
            stateAkun = "true";
        }else{
            stateAkun = "false";
        }

        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                0,"Akun",R.drawable.ic_person_edit,
                createRowItem(
                        new String[]{"Nama","Username","Password","Alamat","state"},
                        new String[]{usernameShord,username,passwordHide,alamat,stateAkun},
                        new int[]{ModelProfileRowExpand.TYPE_TEXT,
                                ModelProfileRowExpand.TYPE_TEXT,
                                ModelProfileRowExpand.TYPE_TEXT_PASSWORD,
                                ModelProfileRowExpand.TYPE_TEXT_AREA,
                                ModelProfileRowExpand.TYPE_TEXT_BOOLEAN}
                        ), true));

        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                1,"Setting", R.drawable.ic_baseline_settings_24,
                createRowItem(
                        new String[]{"Screen Orientation","Themes"},
                        new String[]{"Portrait","Light"}
                ), true));
        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                2,"Logout",R.drawable.ic_baseline_power_settings_new_24,
                null, false));

        adapterProfile = new AdapterAkunSetting(
                arrayList_profileRowExpand,ProfileFragment.this);

        recyclerView_profileSetting.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView_profileSetting.setHasFixedSize(true);
        recyclerView_profileSetting.setItemAnimator(new DefaultItemAnimator());

        recyclerView_profileSetting.setAdapter(adapterProfile);


    }

    private String textWrap(String plaintText, int length, String type) {
        String result ="";
        switch (type){
            case "text":
                if (plaintText.length()>length){
                    for (int i =0;i<length;i++){
                        result = result+String.valueOf(plaintText.charAt(i));
                    }
                    result = result+"...";
                }else{
                    result = plaintText;
                }
                break;
            case "password":
                if (plaintText.length()<length){
                    for (int i=0;i<plaintText.length();i++){
                        result =result+"*";
                    }
                }else{
                    for (int i=0;i<length;i++){
                        result =result+"*";
                    }
                }
                break;
        }
        return result;
    }

    @Override
    public void onItemClick(int position, View view) {
        Toast.makeText(requireContext(), "Position "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemExpendClick(int id,int position, View view,boolean isEnable) {
        TextView tv_edit_old = view.findViewById(R.id.tvSettingResult);

        if (isEnable){
            AlertDialog.Builder dialog1;
            View viewInflated;
            viewInflated = LayoutInflater.from(getContext()).inflate(
                    R.layout.input_alert_dialog, (ViewGroup) getView(), false);
            final TextInputLayout inputLayout = viewInflated.findViewById(R.id.layoutInput);
            final TextInputEditText EditTextInput = viewInflated.findViewById(R.id.editTextInput);

            dialog1 = new AlertDialog.Builder(requireContext());
            dialog1.setIcon(R.drawable.ic_person_edit);
            switch (id){
                case 0:
                    switch (position){
                        case 0:
                            inputLayout.setHint("New Name");
                            dialog1.setTitle("Nama");
                            dialog1.setView(viewInflated);
                            EditTextInput.setInputType(InputType.TYPE_CLASS_TEXT);
                            EditTextInput.setText(detail_profile.get(
                                    SessionManager.NAMA
                            ));
//                    EditTextInput.setText(tv_edit_old.getText());

                            dialog1.setPositiveButton("Next", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String m_input = EditTextInput.getText().toString();
                                    String m_output = "-";
                                    tv_edit_old.setText("tes");
                                    editProfile(
                                            tv_edit_old,
                                            detail_profile_int.get(
                                                    SessionManager.USER_ID
                                            ),
                                            m_input,"nama"
                                    );

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
                            EditTextInput.setText(detail_profile.get(
                                    SessionManager.USERNAME
                            ));

                            dialog1.setPositiveButton("Next", (dialogInterface,i)->{
                                String m_input = EditTextInput.getText().toString();
//                        String m_output = "-";
                                editProfile(tv_edit_old,
                                        detail_profile_int.get(SessionManager.USER_ID), m_input, "username");
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
                                editProfile(tv_edit_old,
                                        detail_profile_int.get(
                                                SessionManager.USER_ID
                                        ), m_input, "password");
                            });

                            dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                                dialogInterface.cancel();
                            });
                            dialog1.create().show();
                            break;
                        case 3:
                            inputLayout.setHint("New Alamat");
                            dialog1.setTitle("Alamat");
//                            EditTextInput.setInputType(InputType.TYPE_CLASS);
                            dialog1.setView(viewInflated);
                            EditTextInput.setText(detail_profile.get(
                                    SessionManager.ALAMAT
                            ));

                            dialog1.setPositiveButton("Next", (dialogInterface,i)->{
                                String m_input = EditTextInput.getText().toString();
                                editProfile(tv_edit_old,
                                        detail_profile_int.get(SessionManager.USER_ID), m_input, "alamat");
                            });

                            dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                                dialogInterface.cancel();
                            });
                            dialog1.show();
                            break;
                    }
                case 1:
                    Toast.makeText(requireContext(), tv_edit_old.getText(), Toast.LENGTH_SHORT).show();
            }

        }else{
            switch (position){
                case 1:
                    sessionManagerProfil.logout();
                    break;
            }
        }
    }

    private String editProfile(TextView tv_edit_old, int p_iduser, String m_input, String type) {
        retrofit = new Retrofit.Builder()
                    .baseUrl(SessionManager.HOSTNAME)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        String input_nama=detail_profile.get(SessionManager.NAMA);
        String input_username = detail_profile.get(SessionManager.USERNAME);
        String input_password =detail_profile.get(SessionManager.PASSW);
        String input_alamat =detail_profile.get(SessionManager.ALAMAT);
        String old_result="";

        if (type==String.valueOf("nama")){
            input_nama = m_input;
        }else if (type==String.valueOf("username")) {
            input_username = m_input;
        }else if (type==String.valueOf("password")) {
            input_password = m_input;
        } else if (type==String.valueOf("alamat")) {
            input_alamat = m_input;
        }else{
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

                int user_id = detail_profile_int.get(SessionManager.USER_ID);
                String nama = editLogin.getNama();
                String username = editLogin.getUsername();
                String password = editLogin.getPassword();
                String alamat = editLogin.getAlamat();
                sessionManagerProfil.setEditUserNama(nama);
                sessionManagerProfil.setEditUserUsername(username);
                sessionManagerProfil.setEditUserPassword(password);
                sessionManagerProfil.setEditUserAlamat(alamat);

//                String area_nm = editLogin.getNm_area();
//                String area_singkat_nm = editLogin.getNm_singkat();
//                int satker_id = editLogin.getId_satker();
//                int kode_satker = editLogin.getKode_satker();
//                String satker_nm = editLogin.getNm_satker();
//                String jenis_kew = editLogin.getJenis_kew();
//                String alamat_kantor = editLogin.getAlamat_kantor();
//                int kppn = editLogin.getKppn();
                    setProfile();
                    loadSettingProfile("akun");
//                if (type=="password") {
//                     tv_edit_old.setText(textWrap(m_input,8,"password"));
//                }else{
//                    tv_edit_old.setText(textWrap(m_input,8,"text"));
//                }



            }

            @Override
            public void onFailure(Call<ApiLogin> call, Throwable t) {
                Toast.makeText(requireContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

        return m_input;
    }
}