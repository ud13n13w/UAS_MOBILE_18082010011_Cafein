package com.example.cafein.menu

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class MenuRepository(application: Application) {

    private var menuDao: MenuDao

    private var allMenus: LiveData<List<Menu>>

    init {
        val database: MenuDatabase = MenuDatabase.getInstance(
            application.applicationContext
        )!!
        menuDao = database.menuDao()
        allMenus = menuDao.getAllMenus()
    }

    //Penerapan Asyntask pada method wrapper sesuai dengan method yg ada pada DAO
    fun insert(menu : Menu) {
        val insertNoteAsyncTask = InsertMenuAsyncTask(menuDao).execute(menu)
    }

    fun update(menu : Menu) {
        val updateNoteAsyncTask = UpdateMenuAsyncTask(menuDao).execute(menu)
    }

    fun delete(menu : Menu) {
        val deleteNoteAsyncTask = DeleteMenuAsyncTask(menuDao).execute(menu)
    }

    fun deleteAllMenus() {
        val deleteAllNotesAsyncTask = DeleteAllMenuAsyncTask(
            menuDao
        ).execute()
    }

    fun getAllMenus(): LiveData<List<Menu>> {
        return allMenus
    }



    //Ayntask Threading
    companion object {
        private class InsertMenuAsyncTask(menuDao: MenuDao) : AsyncTask<Menu, Unit, Unit>() {
            val menuDao = menuDao
            override fun doInBackground(vararg p0: Menu?) {
                menuDao.insert(p0[0]!!)
            }
        }
        private class UpdateMenuAsyncTask(menuDao: MenuDao) : AsyncTask<Menu, Unit, Unit>() {
            val menuDao = menuDao
            override fun doInBackground(vararg p0: Menu?) {
                menuDao.update(p0[0]!!)
            }
        }
        private class DeleteMenuAsyncTask(menuDao: MenuDao) : AsyncTask<Menu, Unit, Unit>() {
            val menuDao = menuDao
            override fun doInBackground(vararg p0: Menu?) {
                menuDao.delete(p0[0]!!)
            }
        }
        private class DeleteAllMenuAsyncTask(menuDao: MenuDao) : AsyncTask<Unit, Unit, Unit>() {
            val menuDao = menuDao
            override fun doInBackground(vararg p0: Unit?) {
                menuDao.deleteAllMenus()
            }
        }
    }
}