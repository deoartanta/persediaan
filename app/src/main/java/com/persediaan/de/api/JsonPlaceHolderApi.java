package com.persediaan.de.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    public static String API_KEY_NAME ="API-KEY";
    public static String API_KEY_VALUE = "keycoba";

                        //-------------User-------------\\
//  Login
    @POST("api/auth/login")
    @FormUrlEncoded
    Call<ApiLogin> getResponLogin(
            @Field("username") String username,
            @Field("password") String password
    );

//  Check Login
    @POST("api/auth/check")
    @FormUrlEncoded
    Call<ApiLogin> getCheckLogin(
            @Field("id_user") String idUser,
            @Field("hash_pass") String password
    );

//  Edi Data User
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

                        //----------Penerimaan----------\\
//    Show Cart Penerimaan
    @POST("api/reception/cart")
    @FormUrlEncoded
    Call<List<ApiPenerimaan>> getResponPenerimaanCart(
            @Field("id_user") int iduser
    );
    @POST("api/reception/cart")
    @FormUrlEncoded
    Call<ApiPenerimaan> getResponPenerimaanCartStatus(
            @Field("id_user") int iduser
    );

//  Show Item By Barcode
    @POST("api/reception/item")
    @FormUrlEncoded
    Call <List<ApiPenerimaan>> getResponPenerimaan(
            @Field("barcode") String barcode
    );

    @POST("api/reception/item")
    @FormUrlEncoded
    Call <ApiPenerimaan> getResponPenerimaanStatus(
            @Field("barcode") String barcode,
            @Field("id_user") int id_user
    );
//Delete Item
    @POST("api/reception/deleteCart")
    @FormUrlEncoded
    Call <String> getResponHpsItem(
            @Field("id") int id
    );

//  Add Cart Penerimaan
    @POST("api/reception/addCart")
    @FormUrlEncoded
    Call <ApiPenerimaan> getResponAddItem(
            @Field("id_user") int id_user,
            @Field("id") int id,
            @Field("qty") int qty,
            @Field("harga") int harga
    );

//  Edit Cart Penerimaan
    @POST("api/reception/editCart")
    @FormUrlEncoded
    Call<ApiPenerimaan> getResponEditCart(
            @Field("id") int id,
            @Field("qty") int qty,
            @Field("harga") int hrg
    );

//  Simpan Penerimaan
    @POST("api/reception/simpan")
    @FormUrlEncoded
    Call<ApiSimpan> getResponSimpan(
            @Field("id_user") int id_user,
            @Field("note") String note
    );

//  Batal Penerimaan
    @POST("api/reception/batal")
    @FormUrlEncoded
    Call<String> getResponBatal(
            @Field("id_user") int id_user
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
