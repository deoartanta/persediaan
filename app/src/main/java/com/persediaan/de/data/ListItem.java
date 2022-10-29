package com.persediaan.de.data;

import com.persediaan.de.api.JsonPlaceHolderApi;

import java.util.ArrayList;

public class ListItem {
    public static String ID_ITEM = "ID_ITEM";
    public static String NM_ITEM = "NM_ITEM";
    public static String NM_SATUAN = "NM_SATUAN";
    public static String NM_ECER = "NM_ECER";
    public static String QTY = "QTY";
    public static String HARGA = "HARGA";

    String id_item,nm_item;
    ArrayList<String>get_item;
    int id_user;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

}
