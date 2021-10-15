package com.persediaan.de.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.persediaan.de.LoginActivity;
import com.persediaan.de.MainActivity;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;
    private static final String LOGIN = "IS_LOGIN";

//    User
    public static final String USERNAME = "USERNAME";
    public static final String PASSW = "PASWORD";
    public static final String AREA = "AREA";

//    Scan
    public static final String SCANR = "SCANR";
    public static final String SCANF = "SCANF";
    public static final String SCANFULLR = "SCANFULLR";

    public SessionManager(Context context,String name) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(name,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void Login(String username,String pasword,String area){
        editor.putBoolean(LOGIN,true);
        editor.putString(USERNAME,username);
        editor.putString(USERNAME,pasword);
        editor.putString(AREA,area);
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
        user.put(AREA,sharedPreferences.getString(AREA,null));
        return user;
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
}
