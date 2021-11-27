package com.persediaan.de.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    public static String API_KEY_NAME ="API-KEY";
    public static String API_KEY_VALUE = "keycoba";

    //User
    @POST("api/auth/login")
    @FormUrlEncoded
    Call<ApiLogin> getResponLogin(
            @Field("username") String username,
            @Field("password") String password
    );
    //User
    @POST("api/auth/check")
    @FormUrlEncoded
    Call<ApiLogin> getCheckLogin(
            @Field("id_user") String idUser,
            @Field("hash_pass") String password
    );
    @POST("api/user/edit")
    @FormUrlEncoded
    Call<ApiLogin> getResponEditUser(
            @Field("id_user") int iduser,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("pass_lama") String current_password,
            @Field("alamat") String alamat

    );

    @POST("api/reception/cart")
    @FormUrlEncoded
    Call<List<ApiPenerimaan>> getResponPenerimaanCart(
            @Field("id_user") int iduser
    );

    @POST("api/reception/item")
    @FormUrlEncoded
    Call<List<ApiPenerimaan>> getResponPenerimaan(
            @Field("barcode") String barcode
    );

    //Konversi
    @POST("api/konversi/noKonversi")
    @FormUrlEncoded
    Call<String> getResponNoKonversi(
            @Field("id_user") String id_user
    );
    @POST("api/konversi/noReceipt")
    @FormUrlEncoded
    Call<List<ApiKonversi>> getResponNoReceipt(
            @Field("id_user") Integer id_user
    );
    @POST("api/konversi/items")
    @FormUrlEncoded
    Call<List<ApiKonversi>> getResponItems(
            @Field("id_trans") String id_trans
    );

}
