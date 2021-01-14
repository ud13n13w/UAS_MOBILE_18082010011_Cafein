package com.example.cafein

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cafein.menu.Menu
import com.example.cafein.menu.MenuRepository

//ViewModel digunakan untuk mengelola data yang dibutuhkan untuk ditampilkan kedalam Layout maupun Fragment
class MenuViewModel(application: Application) : AndroidViewModel(application)
{
    //Repository
    private var repository: MenuRepository = MenuRepository(application)

    //All Menus
    private var allMenus: LiveData<List<Menu>> = repository.getAllMenus()

    fun insert(menu : Menu) {
        repository.insert(menu)
    }

    fun update(menu : Menu) {
        repository.update(menu)
    }

    fun delete(menu : Menu) {
        repository.delete(menu)
    }

    fun deleteAllMenus() {
        repository.deleteAllMenus()
    }

    fun getAllMenus(): LiveData<List<Menu>> {
        return allMenus
    }
}