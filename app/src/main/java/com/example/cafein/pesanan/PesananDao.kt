package com.example.cafein.pesanan

import androidx.lifecycle.LiveData
import androidx.room.*

//Dao digunakan untuk pengoperasian query database dengan cara pemanggilan method
@Dao
interface PesananDao {

    @Insert
    fun insert(pesanan : Pesanan)

    @Update
    fun update(pesanan : Pesanan)

    @Delete
    fun delete(pesanan : Pesanan)

    @Query("DELETE FROM pesanan_table")
    fun deleteAllPesanan()

    @Query("SELECT * FROM pesanan_table ORDER BY no_pesanan ASC")
    fun getAllPesanan(): LiveData<List<Pesanan>>

}