package com.persediaan.de.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    public static String API_KEY_NAME ="API-KEY";
    public static String API_KEY_VALUE = "keycoba";

    @POST("api/auth/login")
    @FormUrlEncoded
    Call<ApiLogin> getResponLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("api/user/edit")
    @FormUrlEncoded
    Call<ApiLogin> getResponEditUser(
            @Field("id_user") int iduser,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("alamat") String alamat

    );

    @POST("api/reception/item")
    @FormUrlEncoded
    Call<List<ApiPenerimaan>> getResponPenerimaan(
            @Field("barcode") String barcode
    );
}
