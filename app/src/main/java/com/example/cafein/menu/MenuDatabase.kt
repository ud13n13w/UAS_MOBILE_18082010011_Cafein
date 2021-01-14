package com.example.cafein.menu

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.cafein.R

@Database(entities = [Menu::class], version = 1)
abstract class MenuDatabase : RoomDatabase() {

    //DAO
    abstract fun menuDao(): MenuDao

    companion object {
        private var instance: MenuDatabase? = null
        fun getInstance(context: Context): MenuDatabase? {
            if (instance == null) {
                synchronized(MenuDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MenuDatabase::class.java, "menu_database"
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
    class PopulateDbAsyncTask(db: MenuDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val menuDao = db?.menuDao()
        override fun doInBackground(vararg p0: Unit?) {

            menuDao?.insert(Menu("Brown Sugar Coffe Boba", 15000, "Menu Boba Series, disajikan dengan campuran kopi, gula aren, dan susu dengan topping boba", R.drawable.boba_kopigulaaren))
            menuDao?.insert(Menu("Chocolate Boba", 17000, "Menu Boba Series, sajian coklat dan susu dengan tambahan topping boba", R.drawable.boba_coklat))
            menuDao?.insert(Menu("Strawberry Boba", 17000, "Menu Boba Series, sajian strawberry dan susu dengan tambahan topping boba", R.drawable.boba_strawberry))
            menuDao?.insert(Menu("Matcha Boba", 18000, "Menu Boba Series, paduan teh hijau dengan susu dengan tambahan topping boba", R.drawable.boba_matcha))

            menuDao?.insert(Menu("Chocholate Milkshake", 13000, "MilkShake Series, campuran susu dengan coklat kocok dengan cita rasa dasar susu", R.drawable.susu_coklat))
            menuDao?.insert(Menu("Vanilla Milkshake", 13000, "MilkShake Series, campuran susu dengan vanilla kocok dengan cita rasa dasar susu", R.drawable.susu_vanila))
            menuDao?.insert(Menu("Strawberry Milkshake", 13000, "MilkShake Series, campuran susu dengan starberry kocok dengan cita rasa dasar susu", R.drawable.susu_strawberry))
            menuDao?.insert(Menu("Alpukat Chocolate Milkshake", 18000, "MilkShake Series, campuran alpukat, coklat, dan susu kocok dengan cita rasa dasar susu", R.drawable.susu_alpukatcoklat))

            menuDao?.insert(Menu("Espresso One Shot", 7000, "Coffee Series, habiskan kopi espresso dengan satu kali tegukan. Menghilangkan rasa kantuk seketika", R.drawable.kopi_espresso))
            menuDao?.insert(Menu("Charcoal Coffee", 13000, "Coffee Series, kopi dengan tambahan susu sedikit menciptakan sensasi khusus dengan tambahan arang", R.drawable.kopi_arang))
            menuDao?.insert(Menu("Cappucino", 15000, "Coffee Series, cappucino original dengan rasa kombinasi yang pas dengan susu", R.drawable.kopi_cappucino))

            menuDao?.insert(Menu("Lemon Tea", 13000, "Tea Series, Teh dengan rasa campuran dengan Lemon", R.drawable.teh_lemon))
            menuDao?.insert(Menu("Apple Tea", 13000, "Tea Series, Teh dengan rasa campuran dengan Apel", R.drawable.teh_apel))
            menuDao?.insert(Menu("Lychee Tea", 15000, "Tea Series, Teh dengan rasa campuran dengan Leci", R.drawable.teh_leci))

            menuDao?.insert(Menu("French Fries", 15000, "Snack Series, kentang goreng original cafein, rasakan kenikmatan dengan kematangan kentang yang pas", R.drawable.teh_lemon))
            menuDao?.insert(Menu("Cochobanana Bite", 15000, "Snack Series, campuran coklat dengan pisang yang telah dilebur disajikan dalam adonan lumpia", R.drawable.teh_leci))
            menuDao?.insert(Menu("Cochocheese Bread", 15000, "Snack Series, roti dengan isi campuran coklat dan keju dipanggang secara khusus menciptakan sensasi yang berbeda", R.drawable.teh_apel))

            menuDao?.insert(Menu("Indomie", 15000, "Foodies Series, siapa yang tidak kenal dengan mie orang indonesia? tentu saja indomie", R.drawable.makan_indomie))
            menuDao?.insert(Menu("Fried Rice", 17000, "Foodies Series, nasi goreng resep original cafein dengan paduan mentega dan bumbu jawa", R.drawable.makan_nasigoreng))
            menuDao?.insert(Menu("Japan Chicken Curry", 25000, "Foodies Series, base pasta kari jepang dengan ayam katsu yang digoreng dengan bumbu yang melimpah", R.drawable.makan_karijepang))


        }
    }
}
