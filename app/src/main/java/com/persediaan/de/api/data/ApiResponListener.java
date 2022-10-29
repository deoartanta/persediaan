package com.persediaan.de.api.data;

import retrofit2.Response;

public interface ApiResponListener<T> {
    void onResponse(boolean status, Response<T> body);
    void onResponse(boolean status, T body);
    void onFailure(Throwable t);
}
