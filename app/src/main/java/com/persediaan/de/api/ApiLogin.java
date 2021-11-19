package com.persediaan.de.api;

public class ApiLogin {
    private int user_id;
    private String username;
    private String password;
    private int id_area;
    private String nama;
    private String alamat;
    private int level;
    private String gambar;
    private String nm_area;
    private String nm_singkat;
    private int id_satker;
    private int kode_satker;
    private String nm_satker;
    private String jenis_kew;
    private String alamat_kantor;
    private String msg;
    private boolean sts;
    private int kppn;


    public int getUser_id() {
        return user_id;
    }

    public String getMsg() {
        return msg;
    }

    public boolean getSts() {
        return sts;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getId_area() {
        return id_area;
    }

    public int getLevel() {
        return level;
    }

    public String getGambar() {
        return gambar;
    }

    public String getNm_area() {
        return nm_area;
    }

    public String getNm_singkat() {
        return nm_singkat;
    }

    public int getId_satker() {
        return id_satker;
    }

    public int getKode_satker() {
        return kode_satker;
    }

    public String getNm_satker() {
        return nm_satker;
    }

    public String getJenis_kew() {
        return jenis_kew;
    }

    public String getAlamat_kantor() {
        return alamat_kantor;
    }

    public int getKppn() {
        return kppn;
    }

    @Override
    public String toString() {
        return "ApiLogin{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id_area=" + id_area +
                ", nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", level=" + level +
                ", gambar='" + gambar + '\'' +
                ", nm_area='" + nm_area + '\'' +
                ", nm_singkat='" + nm_singkat + '\'' +
                ", id_satker=" + id_satker +
                ", kode_satker=" + kode_satker +
                ", nm_satker='" + nm_satker + '\'' +
                ", jenis_kew='" + jenis_kew + '\'' +
                ", alamat_kantor='" + alamat_kantor + '\'' +
                ", kppn=" + kppn +
                '}';
    }
}
