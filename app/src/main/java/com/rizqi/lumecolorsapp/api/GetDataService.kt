package com.rizqi.lumecolorsapp.api

import com.rizqi.lumecolorsapp.response.*
import retrofit2.Call
import retrofit2.http.*

interface GetDataService {

    @FormUrlEncoded
    @POST("account/login")
    fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("id_gudang") id_gudang: String,
        @Field("level") level: String,
        @Field("periode") periode: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("loading_in/history")
    fun listHistory(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String
    ): Call<ResponseHistory>

    @FormUrlEncoded
    @POST("loading_in/simpan")
    fun loadingInSave(
        @Field("id_produk") id_produk: String,
        @Field("tgl") tgl: String,
        @Field("no_delivery") no_delivery: String,
        @Field("no_batch") no_batch: String,
        @Field("exp_date") exp_date: String,
        @Field("qty_lolos") qty_lolos: String,
        @Field("qty_reject") qty_reject: String,
        @Field("insert_by") insert_by: String,
        @Field("insert_dt") insert_dt: String,
    ): Call<ResponseHistory>

    @FormUrlEncoded
    @POST("stok/in")
    fun stokIn(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String
    ): Call<ResponseStok>

    @FormUrlEncoded
    @POST("stok/out")
    fun stokOut(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String
    ): Call<ResponseStok>

    @FormUrlEncoded
    @POST("stok/opname")
    fun opname(
        @Field("periode") periode: String,
    ): Call<ResponseOpname>

    @FormUrlEncoded
    @POST("loading_in/qr")
    fun listQr(
        @Field("id_loading_in") id_loading_in: String,
    ): Call<ResponseListQR>

//    @Headers("Content-Type: application/json")
    @FormUrlEncoded
    @POST("stok/detail")
    fun detailStok(
        @Field("id") id: String,
    ): Call<ResponseStokDetail>

    @FormUrlEncoded
    @POST("approve_out/history")
    fun approveOutHistory(
        @Field("dari") dari: String,
        @Field("sampai") sampai: String,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("master/produk")
    fun dataProduk(): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/get_qr")
    fun QRByProduk(
        @Field("id_produk") id_produk: String,
    ): Call<ResponseListQR>

    @FormUrlEncoded
    @POST("approve_out/get_qr_sampai")
    fun QRByProdukSampai(
        @Field("id_produk") id_produk_sampai: String,
        @Field("qr_code_dari") qr_code_dari: String,
    ): Call<ResponseListQRSampai>

    @FormUrlEncoded
    @POST("approve_out/isi_tab_qr")
    fun tabQR(
        @Field("order_id") order_id: String,
    ): Call<ResponseTabQR>

    @FormUrlEncoded
    @POST("approve_out/approve_qr")
    fun approveOut(
        @Field("order_id") order_id: String,
        @Field("level") level: String,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/approve_packing")
    fun approvePacking(
        @Field("id") id: String,
        @Field("sts_approve") sts_approve: Int,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/approve_sender")
    fun approveSender(
        @Field("id") id: String,
        @Field("sts_approve") sts_approve: Int,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/approve_picker")
    fun approvePicker(
        @Field("id") id: String,
        @Field("sts_approve") sts_approve: Int,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/approve_checker")
    fun approveChecker(
        @Field("id") id: String,
        @Field("sts_approve") sts_approve: Int,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/cetak_qrcode")
    fun cetakQR(
        @Field("id") id : String,
    ):Call<ResponseCetakQR>

    @FormUrlEncoded
    @POST("approve_out/simpan_qr")
    fun saveQR(
        @Field("order_id") id: String,
        @Field("id_produk_qr") id_produk_qr: String,
        @Field("qrcode_dari") qrcode_dari: String,
        @Field("qrcode_sampai") qrcode_sampai: String,
    ): Call<ResponseApprove>

    @FormUrlEncoded
    @POST("approve_out/hapus_qr")
    fun removeQR(
        @Field("qr") qr: String,
    ): Call<ResponseDeleteQR>

    @FormUrlEncoded
    @POST("approve_out/list_produk")
    fun listProduk(
        @Field("order_id") order_id: String,
    ): Call<ResponseProduk>

    @FormUrlEncoded
    @POST("master/gudang")
    fun location(
        @Field("id_gudang") id_gudang: String,
    ): Call<ResponseLocation>

    @FormUrlEncoded
    @POST("stok/simpan_opname")
    fun saveOpname(
        @Field("id_produk") id_produk: String,
        @Field("qty") qty: String,
        @Field("periode") periode: String,
        @Field("id_gudang") id_gudang: String,
        @Field("id_user") id_user : String,
    ): Call<ResponseOpname>

}