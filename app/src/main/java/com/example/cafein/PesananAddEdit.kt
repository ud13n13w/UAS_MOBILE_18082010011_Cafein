package com.example.cafein

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.cafein.pesanan.Pesanan
import kotlinx.android.synthetic.main.activity_menu_add_edit.*
import kotlinx.android.synthetic.main.activity_pesanan_add_edit.*

class PesananAddEdit : AppCompatActivity() {

    companion object {
        const val EXTRA_ID= "EXTRA_ID"
        const val EXTRA_NOPESANAN = "EXTRA_NOPESANAN"
        const val EXTRA_NAMAPESANAN = "EXTRA_NAMAPESANAN"
        const val EXTRA_MENUPESANAN = "EXTRA_MENUPESANAN"
        const val EXTRA_HARGAPESANAN = "EXTRA_HARGAPESANAN"
        const val EXTRA_JUMLAHPESANAN = "EXTRA_JUMLAHPESANAN"
        const val EXTRA_TOTALPESANAN = "EXTRA_TOTALPESANAN"
        const val EXTRA_MEJA = "EXTRA_MEJA"
        const val EXTRA_DELETE_REQUEST = "EXTRA_DELETE_REQUEST"
        const val EXTRA_PESANAN_REQUEST = "EXTRA_PESANAN_REQUEST"
    }

    var deleteRequest : Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan_add_edit)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        //Jika intent memiliki ID (Ketika Item dipilih pada MainActivity)
        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Pesanan"
            edit_nopesanan.setText(intent.getIntExtra(EXTRA_NOPESANAN, 1).toString())
            edit_namapesanan.setText(intent.getStringExtra(EXTRA_NAMAPESANAN))
            edit_menupesanan.setText(intent.getStringExtra(EXTRA_MENUPESANAN))
            edit_hargapesanan.setText(intent.getIntExtra(EXTRA_HARGAPESANAN, 1).toString())
            edit_jumlahpesanan.setText(intent.getIntExtra(EXTRA_JUMLAHPESANAN, 1).toString())
            edit_totalpesanan.setText(intent.getIntExtra(EXTRA_TOTALPESANAN, 1).toString())
            edit_meja.setText(intent.getIntExtra(EXTRA_MEJA, 1).toString())
        }else if(intent.hasExtra(EXTRA_PESANAN_REQUEST)){
            edit_menupesanan.setText(intent.getStringExtra(EXTRA_MENUPESANAN))
            edit_hargapesanan.setText(intent.getIntExtra(EXTRA_HARGAPESANAN, 1).toString())
            kalkulasitotal()
        }else {
            title = "Tambah Pesanan"
        }

        //Button Simpan
        val btn_simpanpesanan = findViewById<Button>(R.id.btn_simpanpesanan)
        btn_simpanpesanan.setOnClickListener{

            if (intent.hasExtra(EXTRA_ID)) {
                savePesananUpdate()
            }else{
                savePesanan()
            }

        }

        //Edittext Jumlah
        val edit_jumlah = findViewById<EditText>(R.id.edit_jumlahpesanan)
        edit_jumlah.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(edit_jumlah.text.toString().trim().isBlank()){
                    jumlahexception()
                }else{
                    if(edit_jumlah.text.toString().toInt() >= 1) {
                        kalkulasitotal()
                    }else{
                        jumlahexception()
                    }
                }

            }

        })


    }

    //Override menu pada activity agar sesuai dengan custom menu kita
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_pesanan_menu, menu)
        return true
    }

    //Trigger event, ketika layout save di tekan, maka akan menjalankan function saveNote()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.delete_pesanan -> {
                deletePesanan()
                savePesanan()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun savePesananUpdate(){

        //Jika data yang dimasukkan masih kosong
        if (edit_nopesanan.text.toString().trim().isBlank() || edit_namapesanan.text.toString().trim().isBlank() || edit_menupesanan.text.toString().trim().isBlank() || edit_jumlahpesanan.text.toString().trim().isBlank() || edit_totalpesanan.text.toString().trim().isBlank() || edit_meja.text.toString().trim().isBlank()   ) {
            Toast.makeText(this, "Pesanan kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        //Jika data tidak kosong, maka kirim semua data ke intent dan kirim sebagai RESULT OK
        val data = Intent().apply {
            putExtra(EXTRA_NOPESANAN, edit_nopesanan.text.toString().toInt())
            putExtra(EXTRA_NAMAPESANAN, edit_namapesanan.text.toString())
            putExtra(EXTRA_MENUPESANAN, edit_menupesanan.text.toString())
            putExtra(EXTRA_HARGAPESANAN, edit_hargapesanan.text.toString().toInt())
            putExtra(EXTRA_JUMLAHPESANAN, edit_jumlahpesanan.text.toString().toInt())
            putExtra(EXTRA_TOTALPESANAN, edit_totalpesanan.text.toString().toInt())
            putExtra(EXTRA_MEJA, edit_meja.text.toString().toInt())
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

    private fun savePesanan(){

        //Jika data yang dimasukkan masih kosong
        if (edit_nopesanan.text.toString().trim().isBlank() || edit_namapesanan.text.toString().trim().isBlank() || edit_menupesanan.text.toString().trim().isBlank() || edit_jumlahpesanan.text.toString().trim().isBlank() || edit_totalpesanan.text.toString().trim().isBlank() || edit_meja.text.toString().trim().isBlank()   ) {
            Toast.makeText(this, "Pesanan kosong!", Toast.LENGTH_SHORT).show()
            return
        }

        //Jika data tidak kosong, maka kirim semua data ke intent dan kirim sebagai RESULT OK

        val pesanan = Intent(this, PesananActivity::class.java)
        pesanan.putExtra(EXTRA_NOPESANAN, edit_nopesanan.text.toString().toInt())
        pesanan.putExtra(EXTRA_NAMAPESANAN, edit_namapesanan.text.toString())
        pesanan.putExtra(EXTRA_MENUPESANAN, edit_menupesanan.text.toString())
        pesanan.putExtra(EXTRA_HARGAPESANAN, edit_hargapesanan.text.toString().toInt())
        pesanan.putExtra(EXTRA_JUMLAHPESANAN, edit_jumlahpesanan.text.toString().toInt())
        pesanan.putExtra(EXTRA_TOTALPESANAN, edit_totalpesanan.text.toString().toInt())
        pesanan.putExtra(EXTRA_MEJA, edit_meja.text.toString().toInt())
        pesanan.putExtra(PesananActivity.EXTRA_PESANAN_ACC, "true")

        if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
            pesanan.putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))

            if(deleteRequest == true){
                pesanan.putExtra(EXTRA_DELETE_REQUEST, "true")
            }
        }

        startActivity(pesanan)
        finish()
    }

    private fun deletePesanan(){
        deleteRequest = true;
        //Toast.makeText(this, "DELETE REQUEST SET TRUE!", Toast.LENGTH_SHORT).show()
    }

    private fun jumlahexception(){
        Toast.makeText(this, "Jumlah pesanan minimal 1", Toast.LENGTH_SHORT).show()

        val edit_total = findViewById<EditText>(R.id.edit_totalpesanan)
        edit_total.setText("0")
    }

    private fun taosss(){
        Toast.makeText(this, "TRUE WORK", Toast.LENGTH_SHORT).show()
    }

    private fun kalkulasitotal(){

        val harga_pesanan = intent.getIntExtra(EXTRA_HARGAPESANAN, 1)
        val edit_jumlah = findViewById<EditText>(R.id.edit_jumlahpesanan)

        if(edit_jumlah.text.toString().toInt() >= 1){

            val jumlah_pesanan = edit_jumlah.text.toString().toInt()
            val total_pesanan = harga_pesanan * jumlah_pesanan

            val edit_total = findViewById<EditText>(R.id.edit_totalpesanan)
            edit_total.setText(total_pesanan.toString())

            Toast.makeText(this, "Harga : " + harga_pesanan + " - Jumlah : " + jumlah_pesanan + " - Total : " + total_pesanan, Toast.LENGTH_SHORT).show()

        }


    }

}