package com.persediaan.de.api;

import com.google.gson.annotations.SerializedName;

public class ApiPenerimaan {
    private String id_purchase,note,dt_purchase,
            nm_area,nm_singkat,nm_item,nm_satuan,
            eceran,alasuplier,npwp,id_detail,
            name_penyedia,area,alamat,id_trans,msg,
            tgl_trans,id_item;
    boolean sts;
    private int dikonversi,updated;

    @SerializedName("nm_suplier")
    String nm_suplier;

    public boolean getSts() {
        return sts;
    }

    public String getMsg() {
        return msg;
    }

    private int admin,id,id_area,id_suplier,qty,id_satuan,
            harga,diterima,jumlah,harga_total=0,tgl_purchase,created;

    public String getId_detail() {
        return id_detail;
    }

    public String getTgl_trans() {
        return tgl_trans;
    }

    public int getDikonversi() {
        return dikonversi;
    }

    public int getUpdated() {
        return updated;
    }

    public String getId_purchase() {
        return id_purchase;
    }

    public int getTgl_purchase() {
        return tgl_purchase;
    }

    public int getCreated() {
        return created;
    }

    public String getNote() {
        return note;
    }

    public String getDt_purchase() {
        return dt_purchase;
    }

    public String getNm_area() {
        return nm_area;
    }

    public String getNm_singkat() {
        return nm_singkat;
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

    public String getNm_suplier() {
        return nm_suplier;
    }

    public String getAlasuplier() {
        return alasuplier;
    }

    public String getNpwp() {
        return npwp;
    }

    public String getName_penyedia() {
        return name_penyedia;
    }

    public String getArea() {
        return area;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getId_trans() {
        return id_trans;
    }

    public int getAdmin() {
        return admin;
    }

    public int getId() {
        return id;
    }

    public int getId_area() {
        return id_area;
    }

    public int getId_suplier() {
        return id_suplier;
    }

    public String getId_item() {
        return id_item;
    }

    public int getQty() {
        return qty;
    }

    public int getId_satuan() {
        return id_satuan;
    }

    public int getHarga() {
        return harga;
    }

    public int getDiterima() {
        return diterima;
    }

    public int getJumlah() {
        return jumlah;
    }

    public int getHarga_total() {
        return harga_total;
    }

    @Override
    public String toString() {
        return "ApiPenerimaan{" +
                "id_purchase='" + id_purchase + '\'' +
                ", note='" + note + '\'' +
                ", dt_purchase='" + dt_purchase + '\'' +
                ", nm_area='" + nm_area + '\'' +
                ", nm_singkat='" + nm_singkat + '\'' +
                ", nm_item='" + nm_item + '\'' +
                ", nm_satuan='" + nm_satuan + '\'' +
                ", eceran='" + eceran + '\'' +
                ", nm_suplier='" + nm_suplier + '\'' +
                ", alasuplier='" + alasuplier + '\'' +
                ", npwp='" + npwp + '\'' +
                ", name_penyedia='" + name_penyedia + '\'' +
                ", area='" + area + '\'' +
                ", alamat='" + alamat + '\'' +
                ", id_trans='" + id_trans + '\'' +
                ", admin=" + admin +
                ", id=" + id +
                ", id_area=" + id_area +
                ", id_suplier=" + id_suplier +
                ", id_item=" + id_item +
                ", qty=" + qty +
                ", id_satuan=" + id_satuan +
                ", harga=" + harga +
                ", diterima=" + diterima +
                ", jumlah=" + jumlah +
                ", harga_total=" + harga_total +
                ", tgl_purchase=" + tgl_purchase +
                ", created=" + created +
                '}';
    }
}
