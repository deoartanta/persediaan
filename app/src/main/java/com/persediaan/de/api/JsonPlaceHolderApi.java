package com.persediaan.de.api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

                                //----------Konversi----------\\
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
    @POST("api/konversi/addCart")
    @FormUrlEncoded
    Call<ApiKonversi> getAddCartKonversi(
            @Field("id_user") Integer id_user,
            @Field("id") Integer id,
            @Field("qty") Integer qty,
            @Field("sisa") Integer sisa
    );
    @POST("api/konversi/cart")
    @FormUrlEncoded
    Call<List<ApiKonversi>> getShowCartKonversi(
            @Field("id_user") Integer id_user
    );
    @POST("api/konversi/deleteCart")
    @FormUrlEncoded
    Call<Integer> getDeleteCartKonversi(
            @Field("id") Integer id
    );
    @POST("api/konversi/batal")
    @FormUrlEncoded
    Call<String> getCancelKonversi(
            @Field("id_user") String id_user
    );
    @POST("api/konversi/simpan")
    @FormUrlEncoded
    Call<ApiKonversi> getSimpanKonversi(
            @Field("id_user") String id_user
    );

    // Item
    @GET("api/item")
    Call<ArrayList<ApiDaftarBarang>> getiItem(

    );
    @POST("api/item/delete")
    @FormUrlEncoded
    Call<ApiDaftarBarang> getHpsItem(
            @Field("id_item") int id_item
    );
    @POST("api/item/add")
    @FormUrlEncoded
    Call<ApiDaftarBarang> getAdditem(
            @Field("nm_item") String nm_item,
            @Field("id_satuan") int id_satuan
    );
    @POST("api/item/edit")
    @FormUrlEncoded
    Call<ArrayList<ApiDaftarBarang>> getEdititem(
            @Field("id_item") int id_item,
            @Field("nm_item") String nm_item,
            @Field("id_satuan") int id_satuan
    );

//    Satuan
    @GET("api/item/listSatuan")
    Call<ArrayList<ApiSatuan>> getSatuan(

    );

                        //----------Stock/Home----------\\
    @GET("api/stock/index")
    Call<List<ApiStock>> getStock();

                       //----------Transfer----------\\
    @POST("api/transfer/noTransfer")
    @FormUrlEncoded
    Call<String> getResponNoTransfer(
            @Field("id_user") int id_user
    );

    @POST("api/transfer/listGudang")
    @FormUrlEncoded
    Call<ArrayList<ApiDaftarGudang>> getDaftarGudang(
            @Field("id_user") int id_user
    );

    @POST("api/transfer")
    @FormUrlEncoded
    Call<ArrayList<ApiItemGudang>> getItemGudang(
            @Field("id_user") int id_user
    );
    @POST("api/transfer/cart")
    @FormUrlEncoded
    Call<ArrayList<ApiTransferDetail>> getTransferCart(
            @Field("id_user") int id_user
    );

    @POST("api/transfer/addCart")
    @FormUrlEncoded
    Call<ApiTransferDetail> getTransferAddCart(
            @Field("id_user") int id_user,
            @Field("g_tujuan") int g_tujuan,
            @Field("dt_gudang") String dt_gudang,
            @Field("id_item") int id_item,
            @Field("qty_satu") String qty_satu,
            @Field("qty_ecer") String qty_ecer
    );

    @POST("api/transfer/deleteCart")
    @FormUrlEncoded
    Call<ApiTransferDetail> getTransferDelCart(
            @Field("id_peng") int id_peng
    );

    @POST("api/transfer/batal")
    @FormUrlEncoded
    Call<ApiTransferDetail> getTransferCancelCart(
            @Field("dt_keluar") String dt_keluar
    );
    @POST("api/transfer/simpan")
    @FormUrlEncoded
    Call<ApiTransferDetail> getTransferSimpanCart(
            @Field("id_user") int id_user,
            @Field("note") String note
    );
}
