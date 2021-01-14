package com.example.cafein.pesanan

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Pesanan::class], version = 1)
abstract class PesananDatabase : RoomDatabase() {

    //DAO
    abstract fun pesananDao(): PesananDao

    companion object {
        private var instance: PesananDatabase? = null
        fun getInstance(context: Context): PesananDatabase? {
            if (instance == null) {
                synchronized(PesananDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PesananDatabase::class.java, "pesanan_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    //Proses penerapan asyntask pada database
    class PopulateDbAsyncTask(db: PesananDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val pesananDao = db?.pesananDao()
        override fun doInBackground(vararg p0: Unit?) {
            pesananDao?.insert(Pesanan(1, "Burhanuddin","Kopi Arabika", 15000, 1,15000, 1 ))
            /*pesananDao?.insert(Pesanan("Kopi Robusta", 15000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla cursus dui sit amet lobortis ultrices. Quisque tempus augue eu ex dapibus, et pharetra dui porttitor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec purus ante, bibendum vitae ante a, viverra pharetra nisi. Vivamus ante neque, consectetur ac consectetur ac, elementum ac est. Donec rhoncus neque nibh, ut dignissim metus viverra eget."))
            pesananDao?.insert(Pesanan("Kopi Bali", 15000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla cursus dui sit amet lobortis ultrices. Quisque tempus augue eu ex dapibus, et pharetra dui porttitor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec purus ante, bibendum vitae ante a, viverra pharetra nisi. Vivamus ante neque, consectetur ac consectetur ac, elementum ac est. Donec rhoncus neque nibh, ut dignissim metus viverra eget."))
            pesananDao?.insert(Pesanan("Es Coklat", 15000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla cursus dui sit amet lobortis ultrices. Quisque tempus augue eu ex dapibus, et pharetra dui porttitor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec purus ante, bibendum vitae ante a, viverra pharetra nisi. Vivamus ante neque, consectetur ac consectetur ac, elementum ac est. Donec rhoncus neque nibh, ut dignissim metus viverra eget."))
            pesananDao?.insert(Pesanan("Es Vanilla", 15000, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla cursus dui sit amet lobortis ultrices. Quisque tempus augue eu ex dapibus, et pharetra dui porttitor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Donec purus ante, bibendum vitae ante a, viverra pharetra nisi. Vivamus ante neque, consectetur ac consectetur ac, elementum ac est. Donec rhoncus neque nibh, ut dignissim metus viverra eget."))*/
        }
    }
}
