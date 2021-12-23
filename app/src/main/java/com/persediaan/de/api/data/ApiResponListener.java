package com.persediaan.de.api.data;

public interface ApiResponListener<T> {
    void onResponse(boolean status,T body);
    void onFailure(Throwable t);
}
