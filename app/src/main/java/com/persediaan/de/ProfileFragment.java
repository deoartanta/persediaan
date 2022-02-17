package com.persediaan.de;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.persediaan.de.adapter.AdapterAkunSetting;
import com.persediaan.de.adapter.RecyclerViewClickExpendInterface;
import com.persediaan.de.adapter.RecyclerViewClickInterface;
import com.persediaan.de.api.ApiLogin;
import com.persediaan.de.api.JsonPlaceHolderApi;
import com.persediaan.de.data.DialogCustom;
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

    public static String AKUN = "AKUN";
    String state = "";
    //Connection
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private int i_List_Item;

    SessionManager sessionManagerProfil;
    SessionManager session_manual_book;
    HashMap<String,String> detail_profile;
    HashMap<String,Integer> detail_profile_int;

    TextView satker,nama_lengkap,alamat_profile;
    ImageView imgProfile;

    private AdapterAkunSetting adapterProfile;
    MeowBottomNavigation bottomNavigation;
//    private AdapterAkunSetting adapter;

    ArrayList<ModelProfileRowExpand>arrayList_profileRowExpand;

    ArrayList<ModelPenerimaan> modelPenerimaanArrayList;

    FrameLayout frame_layout_manual_book;
    LinearLayout main_linearlayout;

    RecyclerView recyclerView_profileSetting;

    public ProfileFragment() {
        // Required empty public constructor
    }
    public ProfileFragment(String state) {
        this.state = state;
    }
    public ProfileFragment(String state, MeowBottomNavigation bottomNavigation, FrameLayout frame_layout_manual_book, LinearLayout main_linearlayout) {
        this.state = state;
        this.bottomNavigation = bottomNavigation;

        this.frame_layout_manual_book = frame_layout_manual_book;
        this.main_linearlayout = main_linearlayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        recyclerView_profileSetting = view.findViewById(R.id.reyclerProfileSetting);
        sessionManagerProfil = new SessionManager(requireContext(),"login");
        session_manual_book = new SessionManager(requireContext(),
                SessionManager.MANUAL_BOOK);

        detail_profile = sessionManagerProfil.getUserDetail();
        detail_profile_int = sessionManagerProfil.getUserDetailInt();

        retrofit = new Retrofit.Builder()
                .baseUrl(SessionManager.HOSTNAME)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        nama_lengkap = view.findViewById(R.id.profileTvName);
        satker = view.findViewById(R.id.profileTvSatker);
        alamat_profile = view.findViewById(R.id.profileTvAlamat);
        imgProfile = view.findViewById(R.id.imgProfilUser);

        setProfile();
        loadSettingProfile(state);
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
        if (state==AKUN){
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
        if(detail_profile_int.get(SessionManager.LEVEL)==1) {
            arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                    2, "Daftar Barang", R.drawable.ic_baseline_view_list_24,
                    null, false));
        }
        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                3,"Setting", R.drawable.ic_baseline_settings_24,
                createRowItem(
                        new String[]{"Screen Orientation","Themes"},
                        new String[]{"Portrait","Light"}
                ), true));

        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                4,"Help", R.drawable.ic_help,
                createRowItem(
                        new String[]{"Home page tutorial","Receive page tutorial","Reception " +
                                "details page tutorial","How to" +
                                " use scanner"},
                        new String[]{"","","",""}
                ), true).setImgResourceRight(R.drawable.ic_baseline_keyboard_arrow_right_24));

        arrayList_profileRowExpand.add(new ModelProfileRowExpand(
                5,"Logout",R.drawable.ic_baseline_power_settings_new_24,
                null, false).setMarginBot(130));

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
//        Toast.makeText(requireContext(), "Position "+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick1(int position, View view) {

    }

    @Override
    public void onItemExpendClick(int id,int position, View view,boolean isEnable) {
        DialogCustom dialogCustom = new DialogCustom(getContext(),(ViewGroup) getView(),
                R.layout.input_alert_dialog,R.drawable.ic_person_edit);
        dialogCustom.setCustomDialog();

        TextView tv_edit_old = view.findViewById(R.id.tvSettingResult);
            AlertDialog.Builder dialog1;
            View viewInflated;
            viewInflated = dialogCustom.getViewInflated();
            final TextInputLayout inputLayout = viewInflated.findViewById(R.id.layoutInput);
            final TextInputEditText EditTextInput = viewInflated.findViewById(R.id.editTextInput);

            dialog1 = dialogCustom.getDialog();
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
                                    editProfile(
                                            "",
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
                            AlertDialog dialog2 = dialog1.create();
                            dialog2.show();
                            ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            EditTextInput.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if(TextUtils.isEmpty(editable)){
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    }else{
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                    }
                                }
                            });
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
                                editProfile("",
                                        detail_profile_int.get(SessionManager.USER_ID), m_input, "username");
                            });

                            dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                                dialogInterface.cancel();
                            });
                            dialog2 = dialog1.create();
                            dialog2.show();
                            ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            EditTextInput.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    String usernameLama =
                                            String.valueOf(detail_profile.get(SessionManager.USERNAME));
                                    String usernameBaru = String.valueOf(editable.toString());
//                                    Log.d("19201299",
//                                            "afterTextChanged: \nUsernameLama : "+usernameLama+
//                                                    "\nUsernameBaru : "+usernameBaru+
//                                                    "\nBollean :"+TextUtils.equals(editable,
//                                                    usernameLama));
                                    if(TextUtils.isEmpty(editable)||TextUtils.equals(editable,
                                            usernameLama)){
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    }else{
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                    }
                                }
                            });
                            break;
                        case 2:
                            final TextInputLayout inputLayout2 =
                                    viewInflated.findViewById(R.id.layoutInput2);
                            final TextInputEditText EditTextInput2 =
                                    viewInflated.findViewById(R.id.editTextInput2);
                            inputLayout2.setVisibility(View.VISIBLE);
                            inputLayout2.setHint("New Password");
                            inputLayout.setHint("Current Password");
                            inputLayout.setPasswordVisibilityToggleEnabled(true);
                            inputLayout2.setPasswordVisibilityToggleEnabled(true);
                            dialog1.setTitle("Password");
                            EditTextInput.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            EditTextInput2.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            dialog1.setView(viewInflated);

                            dialog1.setPositiveButton("Next", (dialogInterface,i)->{
                                String m_input = EditTextInput2.getText().toString();
                                String m_input2 = EditTextInput.getText().toString();
                                String m_output = "-";
                                editProfile(m_input2,
                                        detail_profile_int.get(
                                                SessionManager.USER_ID
                                        ), m_input, "password");
                            });

                            dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                                dialogInterface.cancel();
                            });
                            dialog2 = dialog1.create();
                            dialog2.show();
                            ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            EditTextInput.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    String passwBaru = editable.toString();
                                    String passwLama = EditTextInput2.getText().toString();
                                    if(TextUtils.isEmpty(editable)||passwBaru.length()<6||TextUtils.equals(passwLama,passwBaru)){
                                        if(TextUtils.equals(passwLama,passwBaru)){
                                            EditTextInput2.setError("current and new password " +
                                                    "cannot be the same");
                                            EditTextInput.setPadding(20,0,80,0);
                                            EditTextInput2.setPadding(20,0,80,0);
                                        }else if(passwBaru.length()<6){
                                            EditTextInput.setError("Password can't be less than " +
                                                    "6");
                                            EditTextInput.setPadding(20,0,80,0);
                                        }else{
                                            EditTextInput.setPadding(20,0,80,0);
                                            EditTextInput.setError(null);
                                        }
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    }else{
                                        EditTextInput.setPadding(20,0,0,0);
                                        EditTextInput.setError(null);
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                    }
                                }
                            });
                            EditTextInput2.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    String passwBaru = editable.toString();
                                    String passwLama = EditTextInput.getText().toString();
                                    if(TextUtils.isEmpty(editable)||TextUtils.isEmpty(passwLama)||passwLama.length()<6||passwBaru.length()<6||TextUtils.equals(passwLama,passwBaru)){
                                        if(TextUtils.equals(passwLama,passwBaru)){
                                            EditTextInput2.setError("current and new password " +
                                                    "cannot be the same");
                                            EditTextInput2.setPadding(15,0,80,0);
                                        }else if (passwBaru.length()<6){
                                            EditTextInput2.setError("Password can't be less than " +
                                                    "6");
                                            EditTextInput2.setPadding(15,0,80,0);
                                        }else{
                                            EditTextInput2.setPadding(15,0,80,0);
                                            EditTextInput2.setError(null);
                                        }
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    }else{
                                        EditTextInput2.setPadding(15,0,0,0);
                                        EditTextInput2.setError(null);
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                    }
                                }
                            });
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
                                editProfile("",
                                        detail_profile_int.get(SessionManager.USER_ID), m_input, "alamat");
                            });

                            dialog1.setNegativeButton("Cancel", (dialogInterface, i) -> {
                                dialogInterface.cancel();
                            });
                            dialog2 = dialog1.create();
                            dialog2.show();
                            ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            EditTextInput.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if(TextUtils.isEmpty(editable)){
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                    }else{
                                        ((AlertDialog) dialog2).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                    }
                                }
                            });
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + position);
                    }
                case 1:

                    break;
                case 2:
                    SessionManager sessionTranstition = new SessionManager(requireContext(),
                            "transtition");
                    sessionTranstition.setTranstition("setting",true);
                    newPage(ItemActivity.class);
                    break;
                case 3:
                    Toast.makeText(requireContext(), ""+tv_edit_old.getText(),
                            Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    switch (position){
                        case 0:
                            session_manual_book.setManualBook(SessionManager.HOME,false);
                            if (bottomNavigation!=null){
                                bottomNavigation.show(1,true);
                            }
                            break;
                        case 1:
                            session_manual_book.setManualBook(SessionManager.RECEIVE,false);
                            if (bottomNavigation!=null){
                                bottomNavigation.show(2,true);
                            }
                            break;
                        case 2:
                            session_manual_book.setManualBook(SessionManager.DETAILPENER,false);
                            Intent i = new Intent(requireContext(),DetailPenerimaanActivity.class);
                            String sNull = null;

                            i.putExtra(DetailPenerimaanActivity.ID_TRANS,sNull);
                            i.putExtra(DetailPenerimaanActivity.TGL_PURCHASE,sNull);
                            i.putExtra(DetailPenerimaanActivity.PENYEDIA,sNull);
                            i.putExtra(DetailPenerimaanActivity.AREA,sNull);
                            i.putExtra(DetailPenerimaanActivity.STS,sNull);
                            i.putExtra(DetailPenerimaanActivity.JML_ITEM,sNull);
                            i.putExtra(DetailPenerimaanActivity.TOTAL_HRG,sNull);
                            if (bottomNavigation!=null){
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.frameManualBook,
                                                new DetailPenerManualBookFragment(frame_layout_manual_book,
                                                        main_linearlayout,i))
                                        .commit();
                                session_manual_book.setManualBook(SessionManager.DETAILPENER,true);
                            }
                            break;
                        case 3:
                            session_manual_book.setManualBook(SessionManager.SCANNER,false);
                            session_manual_book.setManualBook(SessionManager.SCANNER_EDIT_MANUAL_BOOK,false);
                            if (bottomNavigation!=null){
                                bottomNavigation.show(3,true);
                            }
                            break;

                    }
                    break;
                case 5:
                    sessionManagerProfil.logout();
                    break;
            }
    }

    private void newPage(Class cls) {
        Intent i = new Intent(requireContext(),cls);
        startActivity(i);
    }

    private String editProfile(String tv_edit_old, int p_iduser, String m_input, String type) {
        String input_nama="";
        String input_username = "";
        String input_currentpassword ="";
        String input_password ="";
        String input_alamat ="";
        String old_result="";

        if (type==String.valueOf("nama")){
            input_nama = m_input;
        }else if (type==String.valueOf("username")) {
            input_username = m_input;
        }else if (type==String.valueOf("password")) {
            input_password = m_input;
            input_currentpassword = tv_edit_old;
        } else if (type==String.valueOf("alamat")) {
            input_alamat = m_input;
        }else{
            input_alamat = m_input;
        }

        Call<ApiLogin> call = jsonPlaceHolderApi.getResponEditUser(
                p_iduser,input_nama,input_username,input_password,input_currentpassword,input_alamat
        );

        call.enqueue(new Callback<ApiLogin>() {
            @Override
            public void onResponse(Call<ApiLogin> call, Response<ApiLogin> response) {
                String result = null;

                ApiLogin editLogin = response.body();
                if(!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Edit "+type+" gagal", Toast.LENGTH_SHORT).show();
                    return;
                }

                int user_id = detail_profile_int.get(SessionManager.USER_ID);
                String nama = editLogin.getNama();
                String username = editLogin.getUsername();
                String password = editLogin.getPassword();
                String alamat = editLogin.getAlamat();
                if (nama!=null) {
                    Toast.makeText(requireContext(), "Edit "+type+" berhasil", Toast.LENGTH_SHORT).show();
                    sessionManagerProfil.setEditUserNama(nama);
                    sessionManagerProfil.setEditUserUsername(username);
                    sessionManagerProfil.setEditUserPassword(password);
                    sessionManagerProfil.setEditUserAlamat(alamat);
                    setProfile();
                    loadSettingProfile("akun");
                }else{
                    if (!editLogin.getSts()){
                        Toast.makeText(requireContext(), ""+editLogin.getMsg(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireContext(), "Gagal edit "+type, Toast.LENGTH_SHORT).show();
                    }
                }
//                String area_nm = editLogin.getNm_area();
//                String area_singkat_nm = editLogin.getNm_singkat();
//                int satker_id = editLogin.getId_satker();
//                int kode_satker = editLogin.getKode_satker();
//                String satker_nm = editLogin.getNm_satker();
//                String jenis_kew = editLogin.getJenis_kew();
//                String alamat_kantor = editLogin.getAlamat_kantor();
//                int kppn = editLogin.getKppn();
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