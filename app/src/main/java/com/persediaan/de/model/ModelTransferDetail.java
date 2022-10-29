package com.persediaan.de.model;

public class ModelTransferDetail {
    int id,id_area,id_satker,jenis_keluar,id_item,qty,jumlah,kode_satker,jenis_kew,
        id_satuan,harga,tgl_barang,dt_gudang,satker_tujuan,created,
        kppn,tgl_dipa,dt_satker,admin,tipe,tgl_gudang,sisa,id_peng,qty_peng;
   String dt_keluar,nm_item,nm_area,nm_singkat,nm_satuan,eceran,
           nm_satker,alamat_kantor,no_dipa,gudang_tujuan;

    public ModelTransferDetail(String gudang_tujuan, int qty_peng,int id_peng,String eceran,
                               String nm_item,
                               String nm_area,String dt_keluar) {
        this.gudang_tujuan = gudang_tujuan;
        this.qty_peng = qty_peng;
        this.nm_item = nm_item;
        this.nm_area = nm_area;
        this.eceran = eceran;
        this.id_peng = id_peng;
        this.dt_keluar = dt_keluar;
    }

    public String getDt_keluar() {
        return dt_keluar;
    }

    public String getGudang_tujuan() {
        return gudang_tujuan;
    }

    public int getQty_peng() {
        return qty_peng;
    }

    public String getNm_item() {
        return nm_item;
    }

    public String getNm_satuan() {
        return nm_satuan;
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

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", id_area=" + id_area +
                ", id_satker=" + id_satker +
                ", jenis_keluar=" + jenis_keluar +
                ", id_item=" + id_item +
                ", qty=" + qty +
                ", jumlah=" + jumlah +
                ", kode_satker=" + kode_satker +
                ", jenis_kew=" + jenis_kew +
                ", id_satuan=" + id_satuan +
                ", harga=" + harga +
                ", tgl_barang=" + tgl_barang +
                ", dt_gudang=" + dt_gudang +
                ", gudang_tujuan=" + gudang_tujuan +
                ", satker_tujuan=" + satker_tujuan +
                ", created=" + created +
                ", kppn=" + kppn +
                ", tgl_dipa=" + tgl_dipa +
                ", dt_satker=" + dt_satker +
                ", admin=" + admin +
                ", tipe=" + tipe +
                ", tgl_gudang=" + tgl_gudang +
                ", sisa=" + sisa +
                ", id_peng=" + id_peng +
                ", qty_peng=" + qty_peng +
                ", dt_keluar='" + dt_keluar + '\'' +
                ", nm_item='" + nm_item + '\'' +
                ", nm_area='" + nm_area + '\'' +
                ", nm_singkat='" + nm_singkat + '\'' +
                ", nm_satuan='" + nm_satuan + '\'' +
                ", eceran='" + eceran + '\'' +
                ", nm_satker='" + nm_satker + '\'' +
                ", alamat_kantor='" + alamat_kantor + '\'' +
                ", no_dipa='" + no_dipa + '\'' +
                '}';
    }
}
