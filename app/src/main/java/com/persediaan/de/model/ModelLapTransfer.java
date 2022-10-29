package com.persediaan.de.model;

public class ModelLapTransfer {
    String dt_pengeluaran,nm_area,nm_gudang_tujuan;
    int total_item, total_harga,tgl_transfer;

    public ModelLapTransfer(String dt_pengeluaran, String nm_area, String nm_gudang_tujuan, int total_item, int total_harga, int tgl_transfer) {
        this.dt_pengeluaran = dt_pengeluaran;
        this.nm_area = nm_area;
        this.nm_gudang_tujuan = nm_gudang_tujuan;
        this.total_item = total_item;
        this.total_harga = total_harga;
        this.tgl_transfer = tgl_transfer;
    }

    public String getDt_pengeluaran() {
        return dt_pengeluaran;
    }

    public String getNm_area() {
        return nm_area;
    }

    public String getNmGudangTujuan() {
        return nm_gudang_tujuan;
    }

    public int getTotalItem() {
        return total_item;
    }

    public int getTotalHarga() {
        return total_harga;
    }

    public int getTglTransfer() {
        return tgl_transfer;
    }
}
