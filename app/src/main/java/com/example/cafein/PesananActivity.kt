package com.example.cafein

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cafein.pesanan.Pesanan
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.recycler_view
import kotlinx.android.synthetic.main.activity_pesanan.*

class PesananActivity : AppCompatActivity() {

    companion object {
        const val ADD_PESANAN_REQUEST = 1
        const val EDIT_PEsANAN_REQUEST = 2
        const val EXTRA_MENUPESANAN = "EXTRA_MENUPESANAN"
        const val EXTRA_HARGAPESANAN = "EXTRA_HARGAPESANAN"
        const val EXTRA_PESANAN_REQUEST = "EXTRA_PESANAN_REQUEST"
        const val EXTRA_PESANAN_ACC = "EXTRA_PESANAN_ACC"
    }

    private lateinit var pesananViewModel: PesananViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesanan)

        //Settting RecyclerView
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        val adapter = PesananAdapter()
        recycler_view.adapter = adapter

        //Setting ViewModel
        pesananViewModel = ViewModelProviders.of(this).get(PesananViewModel::class.java)
        pesananViewModel.getAllPesanan().observe(this, Observer<List<Pesanan>> {
            adapter.submitList(it)
        })

        //Ketika Action button di klik
        /*buttonPesanan.setOnClickListener {
            startActivityForResult(
                Intent(this, PesananAddEdit::class.java), PesananActivity.ADD_PESANAN_REQUEST
            )
        }*/

        //Ketika Action button di klik
        if (intent.hasExtra(EXTRA_PESANAN_REQUEST)) {
            //Toast.makeText(baseContext, "Gasken disini mamang", Toast.LENGTH_SHORT).show()
            val menu_pesanan = intent.getStringExtra(EXTRA_MENUPESANAN)
            val harga_pesanan = intent.getIntExtra(EXTRA_HARGAPESANAN, 1)

            val pesanan = Intent(this, PesananAddEdit::class.java)

            pesanan.putExtra(PesananAddEdit.EXTRA_MENUPESANAN, menu_pesanan)
            pesanan.putExtra(PesananAddEdit.EXTRA_HARGAPESANAN, harga_pesanan)
            pesanan.putExtra(PesananAddEdit.EXTRA_PESANAN_REQUEST, "true")

            startActivity(pesanan)
            finish()
        }else if(intent.hasExtra(EXTRA_PESANAN_ACC)){
            //Toast.makeText(baseContext, "Gasken disini mamang", Toast.LENGTH_SHORT).show()

            val no_pesanan = intent.getIntExtra(PesananAddEdit.EXTRA_NOPESANAN,1)
            val nama_pesanan = intent.getStringExtra(PesananAddEdit.EXTRA_NAMAPESANAN).toString()
            val menu_pesanan = intent.getStringExtra(PesananAddEdit.EXTRA_MENUPESANAN).toString()
            val harga_pesanan = intent.getIntExtra(PesananAddEdit.EXTRA_HARGAPESANAN,1)
            val jumlah_pesanan = intent.getIntExtra(PesananAddEdit.EXTRA_JUMLAHPESANAN,1)
            val total_pesanan = intent.getIntExtra(PesananAddEdit.EXTRA_TOTALPESANAN,1)
            val meja = intent.getIntExtra(PesananAddEdit.EXTRA_MEJA,1)

            val newPesanan = Pesanan(no_pesanan, nama_pesanan, menu_pesanan, harga_pesanan, jumlah_pesanan, total_pesanan, meja)
            pesananViewModel.insert(newPesanan)
            //Toast.makeText(this, "Harga : " + harga_pesanan, Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Pesanan baru disimpan!", Toast.LENGTH_SHORT).show()
        }

        //Item touch helper untuk membantu melakukan trigger action ketika event terjadi pada item
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {

            //Ketika menambahkan data baru, maka memperbaharui instance recyclerview, viewholder, dan target
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            //Ketika item di swiped, maka akan menjalankan function delete() dan data akan terhapus
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                pesananViewModel.delete(adapter.getPesananAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Pesanan Diselesaikan!", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recycler_view)

        //Ketika item dipilih, maka akan mengirimkan data dari item yg dipilih dan intent berupa Edit Request
        adapter.setOnItemClickListener(object : PesananAdapter.OnItemClickListener {
            override fun onItemClick(pesanan: Pesanan) {
                val intent = Intent(baseContext, PesananAddEdit::class.java)
                intent.putExtra(PesananAddEdit.EXTRA_ID, pesanan.id_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_NOPESANAN, pesanan.no_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_NAMAPESANAN, pesanan.nama_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_MENUPESANAN, pesanan.menu_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_HARGAPESANAN, pesanan.harga_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_JUMLAHPESANAN, pesanan.jumlah_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_TOTALPESANAN, pesanan.total_pesanan)
                intent.putExtra(PesananAddEdit.EXTRA_MEJA, pesanan.meja)
                intent.putExtra(PesananAddEdit.EXTRA_DELETE_REQUEST, "false")
                startActivityForResult(intent, EDIT_PEsANAN_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
           menuInflater.inflate(R.menu.pesanan_menu, menu)
           return true
    }

    //Ketika option menu dipilih
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {

            //Ketika menu option delete all menu dipilih, maka akan menjalankan function deleteAllNotes()
            // untuk menghapus semua data note yang tersimpan
            R.id.hapus_pesanan -> {
                pesananViewModel.deleteAllPesanan()
                pesananViewModel.getAllPesanan()
                Toast.makeText(this, "Semua Pesanan dihapus!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.refresh_pesanan -> {
                pesananViewModel.getAllPesanan()
                Toast.makeText(this, "Pesanan Diperbarui", Toast.LENGTH_SHORT).show()
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
        if (requestCode == ADD_PESANAN_REQUEST && resultCode == Activity.RESULT_OK) {
            val newPesanan = Pesanan(
                data!!.getIntExtra(PesananAddEdit.EXTRA_NOPESANAN,1),
                data.getStringExtra(PesananAddEdit.EXTRA_NAMAPESANAN).toString(),
                data.getStringExtra(PesananAddEdit.EXTRA_MENUPESANAN).toString(),
                data.getIntExtra(PesananAddEdit.EXTRA_HARGAPESANAN,1),
                data.getIntExtra(PesananAddEdit.EXTRA_JUMLAHPESANAN,1),
                data.getIntExtra(PesananAddEdit.EXTRA_TOTALPESANAN,1),
                data.getIntExtra(PesananAddEdit.EXTRA_MEJA,1)
            )
            pesananViewModel.insert(newPesanan)
            Toast.makeText(this, "Pesanan baru disimpan!", Toast.LENGTH_SHORT).show()

            //Jika result intent berupa EDIT_NOTE_REQUEST dan RESULT_OK, maka jalankan update()
        } else if (requestCode == EDIT_PEsANAN_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(PesananAddEdit.EXTRA_ID, -1)
            if (id == -1) {
                Toast.makeText(this, "Pembaharuan gagal!", Toast.LENGTH_SHORT).show()
            }

            val Pesanan = Pesanan(
                data!!.getIntExtra(PesananAddEdit.EXTRA_NOPESANAN,1),
                data.getStringExtra(PesananAddEdit.EXTRA_NAMAPESANAN).toString(),
                data.getStringExtra(PesananAddEdit.EXTRA_MENUPESANAN).toString(),
                data.getIntExtra(PesananAddEdit.EXTRA_HARGAPESANAN,1),
                data.getIntExtra(PesananAddEdit.EXTRA_JUMLAHPESANAN,1),
                data.getIntExtra(PesananAddEdit.EXTRA_TOTALPESANAN,1),
                data.getIntExtra(PesananAddEdit.EXTRA_MEJA,1)
            )
            Pesanan.id_pesanan = data.getIntExtra(PesananAddEdit.EXTRA_ID, -1)
            val deleterequest = data.getStringExtra(PesananAddEdit.EXTRA_DELETE_REQUEST)

            if(deleterequest.equals("true")){
                pesananViewModel.delete(Pesanan)
                //Toast.makeText(this, "DO DELETE!", Toast.LENGTH_SHORT).show()
            }else{
                pesananViewModel.update(Pesanan)
                //Toast.makeText(this, "DO UPDATE!", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Pesanan tidak disimpan!", Toast.LENGTH_SHORT).show()
        }
    }
}