package com.persediaan.de.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.persediaan.de.LoginActivity;
import com.persediaan.de.MainActivity;

import java.util.HashMap;
import java.util.Set;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;
    private static final String LOGIN = "IS_LOGIN";
//    URL
    public static final String HOSTNAME = "Https://persediaan.djptkkpsystem.id/";
//    public static final String HOSTNAME = "Https://depo.gemes.id/";
//    User
    public static final String USERNAME = "USERNAME";
    public static final String PASSW = "PASWORD";
    public static final String USER_ID = "USER_ID";
    public static final String AREA_ID = "AREA_ID";
    public static final String NAMA = "NAMA";
    public static final String ALAMAT = "ALAMAT";
    public static final String LEVEL = "LEVEL";
    public static final String GAMBAR = "GAMBAR";
    public static final String AREA_NM = "AREA_NM";
    public static final String AREA_NM_SHORT = "AREA_NM_SHORT";
    public static final String SATKER_ID = "SATKER_ID";
    public static final String SATKER_KODE = "SATKER_KODE";
    public static final String SATKER_NM = "SATKER_NM";
    public static final String JENIS_KEW = "JENIS_KEW";
    public static final String ALAMAT_KANTOR = "ALAMAT_KANTOR";
    public static final String KPPN = "KPPN";

//    Scan
    public static final String SCANR = "SCANR";
    public static final String SCANF = "SCANF";
    public static final String SCANFULLR = "SCANFULLR";
    public static final String EDITSCANNER = "EDITSCANNER";

//  Manual Book
    public static final String DETAILPENER = "DETAILPENER";
    public static final String HOME = "HOME";
    public static final String RECEIVE = "RECEIVE";
    public static final String SCANNER = "SCANNER";
    public static final String SCANNER_EDIT_MANUAL_BOOK = "SCANNER_EDIT";
    public static final String MANUAL_BOOK = "MANUAL_BOOK";
    public static final String OPEN_MANUAL_BOOK = "OPEN_MANUAL_BOOK";

    public SessionManager(Context context,String name) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(name,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setEditUserNama(String editUserNama) {
        editor.remove(NAMA);
        editor.putString(NAMA,editUserNama);
        editor.apply();
    }

    public void setEditUserUsername(String editUserUsername) {
        editor.remove(USERNAME);
        editor.putString(USERNAME,editUserUsername);
        editor.apply();
    }

    public void setEditUserPassword(String editUserPassword) {

        editor.remove(PASSW);
        editor.putString(PASSW,editUserPassword);
        editor.apply();
    }

    public void setEditUserAlamat(String editUserAlamat) {
        editor.remove(ALAMAT);
        editor.putString(ALAMAT,editUserAlamat);
        editor.apply();
    }

    public void Login(
            String username, String pasword, String area, int user_id,
            int area_id, String nama, String alamat, int level, int gambar,
            String area_nm, String area_nm_short, int satker_id, int satker_kode,
            String satker_nm, String jenis_kew, String alamat_kantor, int kppn
    ){
        editor.putBoolean(LOGIN,true);
        editor.putString(USERNAME,username);
        editor.putString(PASSW,pasword);
        editor.putInt(USER_ID,user_id);
        editor.putInt(AREA_ID,area_id);
        editor.putString(NAMA,nama);
        editor.putString(ALAMAT,alamat);
        editor.putInt(LEVEL,level);
        editor.putInt(GAMBAR,gambar);
        editor.putString(AREA_NM,area_nm);
        editor.putString(AREA_NM_SHORT,area_nm_short);
        editor.putInt(SATKER_ID,satker_id);
        editor.putInt(SATKER_KODE,satker_kode);
        editor.putString(SATKER_NM,satker_nm);
        editor.putString(JENIS_KEW,jenis_kew);
        editor.putString(ALAMAT_KANTOR,alamat_kantor);
        editor.putInt(KPPN,kppn);

        editor.apply();
    }
    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){
        if (!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME,sharedPreferences.getString(USERNAME,null));
        user.put(PASSW,sharedPreferences.getString(PASSW,null));
        user.put(NAMA,sharedPreferences.getString(NAMA,null));
        user.put(AREA_NM,sharedPreferences.getString(AREA_NM,null));
        user.put(ALAMAT,sharedPreferences.getString(ALAMAT,null));
        user.put(SATKER_NM,sharedPreferences.getString(SATKER_NM,null));
        user.put(AREA_NM_SHORT,sharedPreferences.getString(AREA_NM_SHORT,null));
        user.put(ALAMAT_KANTOR,sharedPreferences.getString(ALAMAT_KANTOR,null));
        return user;
    }
    public HashMap<String,Integer> getUserDetailInt(){
        HashMap<String,Integer> userInt = new HashMap<>();
        userInt.put(USER_ID,sharedPreferences.getInt(USER_ID,0));
        userInt.put(LEVEL,sharedPreferences.getInt(LEVEL,0));
        userInt.put(GAMBAR,sharedPreferences.getInt(GAMBAR,0));
        return userInt;
    }

    public  void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }
    public void clearSession(){
        editor.clear();
        editor.commit();
    }

    public void createSessionScan(String r, String format, String resultScan) {
        editor.putString(SCANR,r);
        editor.putString(SCANF,format);
        editor.putString(SCANFULLR,resultScan);
        editor.apply();
    }
    public HashMap<String,String> getScanResult(){
        HashMap<String, String> r = new HashMap<>();
        r.put(SCANR,sharedPreferences.getString(SCANR,null));
        r.put(SCANF,sharedPreferences.getString(SCANF,null));
        r.put(SCANFULLR,sharedPreferences.getString(SCANFULLR,null));
        return r;
    }

    @Override
    public String toString() {
        return "SessionManager{" +
                "USERNAME=" + sharedPreferences.getString(USERNAME,null) +
                ", PASSW=" + sharedPreferences.getString(PASSW,null) +
                ", USER_ID=" + sharedPreferences.getInt(USER_ID,0) +
                ", AREA_ID=" + sharedPreferences.getInt(AREA_ID,0) +
                ", NAMA=" + sharedPreferences.getString(NAMA,null) +
                ", ALAMAT=" + sharedPreferences.getString(ALAMAT,null) +
                ", LEVEL=" + sharedPreferences.getInt(LEVEL,0) +
                ", GAMBAR=" + sharedPreferences.getInt(GAMBAR,0) +
                ", AREA_NM=" + sharedPreferences.getString(AREA_NM,null) +
                ", AREA_NM_SHORT=" + sharedPreferences.getString(AREA_NM_SHORT,null) +
                '}';
//        editor.putString(AREA_NM_SHORT,area_nm_short);
//        editor.putInt(SATKER_ID,satker_id);
//        editor.putInt(SATKER_KODE,satker_kode);
//        editor.putString(SATKER_NM,satker_nm);
//        editor.putString(JENIS_KEW,jenis_kew);
//        editor.putString(ALAMAT_KANTOR,alamat_kantor);
//        editor.putInt(KPPN,kppn);
    }

    public void EditScanner(boolean editScanner) {
        editor.putBoolean(EDITSCANNER,editScanner);
        editor.apply();
    }
    public boolean isEditScanner(){
        return sharedPreferences.getBoolean(EDITSCANNER,false);
    }
    public void setManualBook(String key,boolean status){
        editor.putBoolean(key,status);
        editor.apply();
    }
    public void OpenManualBook(boolean b){
        editor.putBoolean(OPEN_MANUAL_BOOK,b);
        editor.apply();
    }
    public boolean OpenManualBook(){
        return sharedPreferences.getBoolean(OPEN_MANUAL_BOOK,false);
    }
    public void setTranstition(String key,boolean status){
        editor.putBoolean(key,status);
        editor.apply();
    }

    public boolean getTranstition(String key){
        return sharedPreferences.getBoolean(key,false);
    }

    public boolean getManualBook(String key){
        return sharedPreferences.getBoolean(key,false);
    }
}
