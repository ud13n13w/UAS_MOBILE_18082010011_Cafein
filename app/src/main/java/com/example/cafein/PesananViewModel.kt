package com.example.cafein

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.cafein.menu.Menu
import com.example.cafein.menu.MenuRepository
import com.example.cafein.pesanan.Pesanan
import com.example.cafein.pesanan.PesananRepository

//ViewModel digunakan untuk mengelola data yang dibutuhkan untuk ditampilkan kedalam Layout maupun Fragment
class PesananViewModel(application: Application) : AndroidViewModel(application)
{
    //Repository
    private var repository: PesananRepository = PesananRepository(application)

    //All Menus
    private var allPesanan: LiveData<List<Pesanan>> = repository.getAllPesanan()

    fun insert(pesanan : Pesanan) {
        repository.insert(pesanan)
    }

    fun update(pesanan : Pesanan) {
        repository.update(pesanan)
    }

    fun delete(pesanan : Pesanan) {
        repository.delete(pesanan)
    }

    fun deleteAllPesanan() {
        repository.deleteAllPesanan()
    }

    fun getAllPesanan(): LiveData<List<Pesanan>> {
        return allPesanan
    }
}