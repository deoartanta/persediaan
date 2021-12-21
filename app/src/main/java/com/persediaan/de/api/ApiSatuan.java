package com.persediaan.de.api;

public class ApiSatuan {
    int id_satuan,jumlah;
    String eceran,nm_satuan,msg;
    boolean sts = false;

    public int getId_satuan() {
        return id_satuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public String getEceran() {
        return eceran;
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
}
