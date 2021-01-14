package com.example.cafein

import android.R.attr.bitmap
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_menu_add_edit.*


class MenuAddEdit : AppCompatActivity() {

    companion object {
        const val EXTRA_ID= "EXTRA_ID"
        const val EXTRA_NAMA = "EXTRA_NAMA"
        const val EXTRA_HARGA = "EXTRA_HARGA"
        const val EXTRA_DESKRIPSI = "EXTRA_DESKRIPSI"
        const val EXTRA_GAMBAR = "EXTRA_GAMBAR"
        const val EXTRA_DELETE_REQUEST = "EXTRA_DELETE_REQUEST"
    }

    var deleteRequest : Boolean = false;
    val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_add_edit)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        //Jika intent memiliki ID (Ketika Item dipilih pada MainActivity)
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Menu"

            var gambar_menu = intent.getIntExtra(EXTRA_GAMBAR, 0)

            edit_namamenu.setText(intent.getStringExtra(EXTRA_NAMA))
            edit_hargamenu.setText(intent.getIntExtra(EXTRA_HARGA, 1).toString())
            edit_deskripsi.setText(intent.getStringExtra(EXTRA_DESKRIPSI))

            iv_gambarmenudetil.setImageResource(gambar_menu)

        }else {
            title = "Tambah Menu"
            btn_buatpesanan.setText("Simpan Menu")
        }

        val iv_gambarmenudetil = findViewById<ImageView>(R.id.iv_gambarmenudetil)
        iv_gambarmenudetil.setOnClickListener{
            openGalleryForImage()
        }

        //Button Simpan
        val btn_buatpesanan = findViewById<Button>(R.id.btn_buatpesanan)
        btn_buatpesanan.setOnClickListener{

            if (intent.hasExtra(EXTRA_ID)) {
                val pesanan = Intent(baseContext, PesananActivity::class.java)

                //
                intent.putExtra(MenuAddEdit.EXTRA_ID, intent.getIntExtra(EXTRA_ID,1))
                //
                pesanan.putExtra(PesananActivity.EXTRA_MENUPESANAN, intent.getStringExtra(EXTRA_NAMA))
                pesanan.putExtra(PesananActivity.EXTRA_HARGAPESANAN, intent.getIntExtra(EXTRA_HARGA, 1))
                pesanan.putExtra(PesananActivity.EXTRA_PESANAN_REQUEST, "true")

                Toast.makeText(
                    this,
                    "Data terkirim :" + intent.getIntExtra(EXTRA_HARGA, 1).toString(),
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(pesanan)
                finish()
            }else{
                saveMenu()
            }

        }

    }

    //Override menu pada activity agar sesuai dengan custom menu kita
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu_menu, menu)
        return true
    }

    //Trigger event, ketika layout save di tekan, maka akan menjalankan function saveNote()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.delete_menu -> {
                deleteMenu()
                saveMenu()
                true
            }
            R.id.simpan_menu -> {
                saveMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
            iv_gambarmenudetil.setImageURI(data?.data) // handle chosen image
        }
    }

    private fun saveMenu(){

        //Jika data yang dimasukkan masih kosong
        if (edit_namamenu.text.toString().trim().isBlank() || edit_hargamenu.text.toString().trim().isBlank()
            ||edit_deskripsi.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Menu kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        //Jika data tidak kosong, maka kirim semua data ke intent dan kirim sebagai RESULT OK
        val data = Intent().apply {
            putExtra(EXTRA_NAMA, edit_namamenu.text.toString())
            putExtra(EXTRA_HARGA, edit_hargamenu.text.toString().toInt())
            putExtra(EXTRA_DESKRIPSI, edit_deskripsi.text.toString())
            //putExtra(EXTRA_GAMBAR, iv_gambarmenudetil.toString().toInt())

            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))

                if(deleteRequest == true){
                    putExtra(EXTRA_DELETE_REQUEST, "true")
                }
            }
        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun deleteMenu(){
        deleteRequest = true;
        //Toast.makeText(this, "DELETE REQUEST SET TRUE!", Toast.LENGTH_SHORT).show()
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

}