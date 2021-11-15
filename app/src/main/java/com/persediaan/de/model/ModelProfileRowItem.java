package com.persediaan.de.model;

public class ModelProfileRowItem {
    String row_name_item,nama,username,password,alamat;
    int img_left;
    private String[] result;

    public ModelProfileRowItem(String row_name_item, int img_left) {
        this.row_name_item = row_name_item;
        this.img_left = img_left;
    }
    public void set_Result(
            String nama, String username, String password, String alamat
    ){
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.alamat = alamat;
    }

    public String getRow_name_item() {
        return row_name_item;
    }

    public int getImg_left() {
        return img_left;
    }

    public ModelProfileRowItem set_Result(String[] result) {
        this.result = result;
        return this;
    }

    public String[] getResult() {
        return result;
    }
}
