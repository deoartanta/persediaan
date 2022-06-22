package com.persediaan.de.api;

public class ApiDownload {
    boolean sts = false;
    String msg = "";

    public ApiDownload(boolean sts, String msg) {
        this.sts = sts;
        this.msg = msg;
    }

    public boolean isSts() {
        return sts;
    }

    public String getMsg() {
        return msg;
    }
}
