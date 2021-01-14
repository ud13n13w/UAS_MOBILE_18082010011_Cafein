package com.example.cafein

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cafein.pesanan.Pesanan
import kotlinx.android.synthetic.main.pesanan_item.view.*

//Adapter digunakan untuk melakukan perulangan Item yang bersifat dinamis pada Item Layout (RecyclerView_item)
class PesananAdapter : ListAdapter<Pesanan, PesananAdapter.PesananHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Pesanan>() {
            override fun areItemsTheSame(oldItem: Pesanan, newItem: Pesanan): Boolean {
                return oldItem.id_pesanan == newItem.id_pesanan
            }
            override fun areContentsTheSame(oldItem: Pesanan, newItem: Pesanan): Boolean {
                return oldItem.meja == newItem.meja && oldItem.nama_pesanan == newItem.nama_pesanan
                        && oldItem.total_pesanan == newItem.total_pesanan && oldItem.jumlah_pesanan == newItem.jumlah_pesanan
                        && oldItem.no_pesanan == newItem.no_pesanan
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PesananHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.pesanan_item, parent, false)
        return PesananHolder(itemView)
    }

    override fun onBindViewHolder(holder: PesananHolder, position: Int) {
        val currentPesanan: Pesanan = getItem(position)

        holder.tv_meja.text = currentPesanan.meja.toString()
        holder.tv_menupesanan.text = currentPesanan.menu_pesanan
        holder.tv_namapesanan.text = currentPesanan.nama_pesanan
        holder.tv_hargapesanan.text = currentPesanan.harga_pesanan.toString()
        holder.tv_totalpesanan.text = currentPesanan.total_pesanan.toString()
        holder.tv_jumlahpesanan.text = currentPesanan.jumlah_pesanan.toString()
        holder.tv_nopesanan.text = currentPesanan.no_pesanan.toString()

    }

    fun getPesananAt(position: Int): Pesanan {
        return getItem(position)
    }

    inner class PesananHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var tv_meja: TextView = itemView.tv_meja
        var tv_namapesanan: TextView = itemView.tv_namapesanan
        var tv_menupesanan: TextView = itemView.tv_menupesanan
        var tv_hargapesanan: TextView = itemView.tv_hargapesanan
        var tv_totalpesanan: TextView = itemView.tv_totalpesanan
        var tv_jumlahpesanan: TextView = itemView.tv_jumlahpesanan
        var tv_nopesanan: TextView = itemView.tv_nopesanan

    }

    interface OnItemClickListener {
        fun onItemClick(pesanan : Pesanan)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}