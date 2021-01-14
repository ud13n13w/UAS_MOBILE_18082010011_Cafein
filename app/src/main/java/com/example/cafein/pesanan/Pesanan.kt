package com.example.cafein.pesanan

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Merepresentasikan tabel SQLite bernama menu_table
@Entity(tableName = "pesanan_table")

//Menunjukkan bahwa tabel menu_table berisi kolom id dengan tipe data int dengan auto increment
data class Pesanan(
    var no_pesanan: Int,
    var nama_pesanan: String,
    var menu_pesanan: String,
    var harga_pesanan: Int,
    var jumlah_pesanan: Int,
    var total_pesanan: Int,
    var meja: Int
    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB) val gambar_barang : ByteArray? = null
){
    @PrimaryKey(autoGenerate = true) var id_pesanan: Int = 0
}