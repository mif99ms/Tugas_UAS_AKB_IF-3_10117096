package com.Miftah.UAS_10117096.model

//NIM : 10117096
//Nama : Miftah Muhammad Sidqi
//Kelas : IF-3
//Waktu Pengerjaam : 11/08/2020
data class WisataFirestoreModel (
        val nama: String,
        val deskripsi: String,
        val alamat: String,
        val sumber: String,
        val harga: String,
        val lat: String,
        val lon: String,
        val phone: String,
        val foto1: String,
        val foto2: String,
        val foto3: String ) {
    constructor(): this("","","","","","","","","","","")
}