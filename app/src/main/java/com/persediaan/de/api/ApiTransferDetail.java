package com.persediaan.de.api;

public class ApiTransferDetail {
    int id,id_area,id_satker,jenis_keluar,qty,jumlah,kode_satker,jenis_kew,
            id_satuan,harga,tgl_barang,gudang_tujuan,created,
            kppn,tgl_dipa,dt_satker,admin,tipe,tgl_gudang,sisa,id_peng,qty_peng;
//    int satker_tujuan;

    String dt_keluar,nm_item,nm_area,nm_singkat,nm_satuan,eceran,
            nm_satker,alamat_kantor,no_dipa,dt_gudang,msg,id_item;

    boolean sts = false;

    public String getDt_keluar() {
        return dt_keluar;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isSts() {
        return sts;
    }

    public int getGudang_tujuan() {
        return gudang_tujuan;
    }

    public int getQty_peng() {
        return qty_peng;
    }

    public String getNm_item() {
        return nm_item;
    }

    public String getEceran() {
        return eceran;
    }

    public String getNm_area() {
        return nm_area;
    }

    public int getId_peng() {
        return id_peng;
    }
}
