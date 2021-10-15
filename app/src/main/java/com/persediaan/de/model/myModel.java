package com.persediaan.de.model;

public class myModel {
    String name_penyedia,
            status,
            area,
            alamat,
            hrg_total,tgl;
    int img_bg_card;
    int img_bg_label;
    int no_penerimaan;
    String color_label;

    public myModel(
            String name_penyedia,
            String status,
            String area,
            String alamat,
            String hrg_total,
            String tgl,
            int img_bg_card,
            int no_penerimaan) {
        this.name_penyedia = name_penyedia;
        this.status = status;
        this.area = area;
        this.alamat = alamat;
        this.hrg_total = hrg_total;
        this.tgl = tgl;
        this.img_bg_card = img_bg_card;
        this.no_penerimaan = no_penerimaan;
    }

    public int getNo_penerimaan() {
        return no_penerimaan;
    }

    public void customLabel(int background, String color){
        this.color_label = color;
        this.img_bg_label = background;
    }

    public String getColor_label() {
        return color_label;
    }

    public int getImg_bg_label() {
        return img_bg_label;
    }

    public String getName_penyedia() {
        return name_penyedia;
    }

    public String getStatus() {
        return status;
    }

    public String getArea() {
        return area;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getHrg_total() {
        return hrg_total;
    }

    public String getTgl() {
        return tgl;
    }

    public int getImg_bg_card() {
        return img_bg_card;
    }
}
