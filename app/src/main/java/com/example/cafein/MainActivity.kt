package com.example.cafein

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_MENU_REQUEST = 1
        const val EDIT_MENU_REQUEST = 2
    }

    private lateinit var menuViewModel: MenuViewModel

    //Drawer
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Drawerlayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.drawer_menu -> {
                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )
                    true
                }
                R.id.drawer_pesanan -> {
                    startActivity(
                        Intent(this, PesananActivity::class.java)
                    )
                    true
                }
                R.id.drawer_about -> {
                    startActivity(
                        Intent(this, AboutActivity::class.java)
                    )
                    true
                }
            }
            true
        }

        //Ketika Action button di klik
        buttonAddMenu.setOnClickListener {
            startActivityForResult(
                Intent(this, MenuAddEdit::class.java), ADD_MENU_REQUEST
            )
        }

        buttonLihatPesanan.setOnClickListener {
            startActivity(
                Intent(this, PesananActivity::class.java)
            )
        }

        //Settting RecyclerView
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = MenuAdapter()
        recycler_view.adapter = adapter

        //Setting ViewModel
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        menuViewModel.getAllMenus().observe(this, Observer<List<com.example.cafein.menu.Menu>> {
            adapter.submitList(it)
        })

        //Ketika item dipilih, maka akan mengirimkan data dari item yg dipilih dan intent berupa Edit Request
        adapter.setOnItemClickListener(object : MenuAdapter.OnItemClickListener {
            override fun onItemClick(menu: com.example.cafein.menu.Menu) {
                val intent = Intent(baseContext, MenuAddEdit::class.java)
                intent.putExtra(MenuAddEdit.EXTRA_ID, menu.id_menu)
                intent.putExtra(MenuAddEdit.EXTRA_NAMA, menu.nama_menu)
                intent.putExtra(MenuAddEdit.EXTRA_HARGA, menu.harga_menu)
                intent.putExtra(MenuAddEdit.EXTRA_DESKRIPSI, menu.deskripsi_menu)
                intent.putExtra(MenuAddEdit.EXTRA_GAMBAR, menu.gambar_menu)
                intent.putExtra(MenuAddEdit.EXTRA_DELETE_REQUEST, "false")
                startActivityForResult(intent, EDIT_MENU_REQUEST)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //Ketika option menu dipilih
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return when (item?.itemId) {
            //Ketika menu option delete all menu dipilih, maka akan menjalankan function deleteAllNotes()
            R.id.refresh_menu -> {
                menuViewModel.getAllMenus()
                Toast.makeText(this, "Menu Diperbarui", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.tambah_menu -> {
                startActivityForResult(
                    Intent(this, MenuAddEdit::class.java), ADD_MENU_REQUEST
                )
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    //Action ketika activity menerima result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //Jika result intent berupa ADD_NOTE_REQUEST dan RESULT_OK, maka jalankan insert()
        if (requestCode == ADD_MENU_REQUEST && resultCode == Activity.RESULT_OK) {
            val newMenu = com.example.cafein.menu.Menu(
                data!!.getStringExtra(MenuAddEdit.EXTRA_NAMA).toString(),
                data.getIntExtra(MenuAddEdit.EXTRA_HARGA, 1),
                data.getStringExtra(MenuAddEdit.EXTRA_DESKRIPSI).toString(),
                data.getIntExtra(MenuAddEdit.EXTRA_GAMBAR, 0)
                //data.get
            )
            menuViewModel.insert(newMenu)
            Toast.makeText(this, "Menu baru disimpan!", Toast.LENGTH_SHORT).show()

            //Jika result intent berupa EDIT_NOTE_REQUEST dan RESULT_OK, maka jalankan update()
        } else if (requestCode == EDIT_MENU_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(MenuAddEdit.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val Menu = com.example.cafein.menu.Menu(
                data!!.getStringExtra(MenuAddEdit.EXTRA_NAMA).toString(),
                data.getIntExtra(MenuAddEdit.EXTRA_HARGA,1),
                data.getStringExtra(MenuAddEdit.EXTRA_DESKRIPSI).toString(),
                data.getIntExtra(MenuAddEdit.EXTRA_GAMBAR, 0)
            )
            Menu.id_menu = data.getIntExtra(MenuAddEdit.EXTRA_ID, -1)
            val deleterequest = data.getStringExtra(MenuAddEdit.EXTRA_DELETE_REQUEST)

            if(deleterequest.equals("true")){
                menuViewModel.delete(Menu)
                //Toast.makeText(this, "DO DELETE!", Toast.LENGTH_SHORT).show()
            }else{
                menuViewModel.update(Menu)
                //Toast.makeText(this, "DO UPDATE!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}