package com.persediaan.de.api;

public class ApiDaftarBarang {
    String nm_item,nm_satuan,msg;
    boolean sts = false;
    int id_item;

    public String getNm_item() {
        return nm_item;
    }

    public String getNm_satuan() {
        return nm_satuan;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSts() {
        return sts;
    }

    public int getId_item() {
        return id_item;
    }
}
