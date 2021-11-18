package com.persediaan.de.model;

import java.util.ArrayList;
import java.util.List;

public class ModelPenerimaan {
    String id_purchase,note,dt_purchase,
            nm_area,nm_singkat,nm_item,nm_satuan,
            eceran,nm_suplier,alasuplier,npwp,
            name_penyedia,status,area,alamat,tgl,id_trans;

    int admin,id,id_area,id_suplier,diterima,jumlah,harga_total=0,jml_item,tgl_purchase;

    ArrayList<ModelItemBrg> modelItemBrgs;

    boolean expand = false;

    public ModelPenerimaan(
            String id_purchase, int tgl_purchase, String note,
            String dt_purchase, String nm_area, String nm_singkat,
            String nm_suplier, String alasuplier, String npwp,
            String name_penyedia, String status, String area, String alamat,
            String id_trans, int jml_item, int admin, int id, int id_area,
            int id_suplier, int diterima, int harga_total, ArrayList<ModelItemBrg> item_brg
    ) {
        this.id_purchase = id_purchase;this.tgl_purchase = tgl_purchase;
        this.note = note;this.dt_purchase = dt_purchase;this.nm_area = nm_area;
        this.nm_singkat = nm_singkat;this.nm_item = nm_item;this.nm_satuan = nm_satuan;
        this.eceran = eceran;this.nm_suplier = nm_suplier;this.alasuplier = alasuplier;
        this.npwp = npwp;this.name_penyedia = name_penyedia;this.status = status;
        this.area = area;this.alamat = alamat;this.tgl = tgl;this.id_trans = id_trans;
        this.admin = admin;this.id = id;this.id_area = id_area;this.id_suplier = id_suplier;
        this.diterima = diterima;this.harga_total = harga_total;this.jml_item = jml_item;
        this.modelItemBrgs = item_brg;
    }

    public ArrayList<ModelItemBrg> getItemBrgs() {
        return modelItemBrgs;
    }

    public String getId_purchase() {
        return id_purchase;
    }

    public int getTgl_purchase() {
        return tgl_purchase;
    }

    public int getJml_item() {
        return jml_item;
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

    public String getNm_suplier() {
        return nm_suplier;
    }

    public String getAlasuplier() {
        return alasuplier;
    }

    public String getNpwp() {
        return npwp;
    }

    public String getArea() {
        return area;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getTgl() {
        return tgl;
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

    public int getDiterima() {
        return diterima;
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

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isExpand() {
        return expand;
    }

    public int getHarga_total() {
        return harga_total;
    }

    public String getName_penyedia() {
        return name_penyedia;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String  toString() {
        return "ModelPenerimaan{" +
                "id_purchase='" + id_purchase + '\'' +
                ", tgl_purchase='" + tgl_purchase + '\'' +
                ", note='" + note + '\'' +
                ", dt_purchase='" + dt_purchase + '\'' +
                ", nm_area='" + nm_area + '\'' +
                ", nm_singkat='" + nm_singkat + '\'' +
                ", nm_suplier='" + nm_suplier + '\'' +
                ", alasuplier='" + alasuplier + '\'' +
                ", npwp='" + npwp + '\'' +
                ", name_penyedia='" + name_penyedia + '\'' +
                ", status='" + status + '\'' +
                ", area='" + area + '\'' +
                ", alamat='" + alamat + '\'' +
                ", tgl='" + tgl + '\'' +
                ", id_trans='" + id_trans + '\'' +
                ", admin=" + admin +
                ", id=" + id +
                ", id_area=" + id_area +
                ", id_suplier=" + id_suplier +
                ", diterima=" + diterima +
                ", jumlah=" + jumlah +
                ", harga_total=" + harga_total +
                '}';
    }
}
