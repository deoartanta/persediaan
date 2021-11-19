package com.persediaan.de.api;

import com.google.gson.annotations.SerializedName;

public class ApiKonversi {
    @SerializedName("nokonversi")
    private String no_konversi;

    public String getNo_konversi() {
        return no_konversi;
    }
}
