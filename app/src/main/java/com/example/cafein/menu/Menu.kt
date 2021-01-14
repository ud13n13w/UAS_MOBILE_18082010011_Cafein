package com.example.cafein.menu

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Merepresentasikan tabel SQLite bernama menu_table
@Entity(tableName = "menu_table")

//Menunjukkan bahwa tabel menu_table berisi kolom id dengan tipe data int dengan auto increment
data class Menu(
    var nama_menu: String,
    var harga_menu: Int,
    var deskripsi_menu: String,
    val gambar_menu : Int

    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB) val gambar_barang : ByteArray? = null
){
    @PrimaryKey(autoGenerate = true) var id_menu: Int = 0
}