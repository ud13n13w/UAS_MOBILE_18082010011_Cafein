package com.example.cafein.menu

import androidx.lifecycle.LiveData
import androidx.room.*

//Dao digunakan untuk pengoperasian query database dengan cara pemanggilan method
@Dao
interface MenuDao {

    @Insert
    fun insert(menu : Menu)

    @Update
    fun update(menu : Menu)

    @Delete
    fun delete(menu : Menu)

    @Query("DELETE FROM menu_table")
    fun deleteAllMenus()

    @Query("SELECT * FROM menu_table ORDER BY id_menu ASC")
    fun getAllMenus(): LiveData<List<Menu>>

}