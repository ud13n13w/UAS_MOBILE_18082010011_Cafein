package com.example.cafein

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cafein.menu.Menu
import kotlinx.android.synthetic.main.menu_item.view.*

//Adapter digunakan untuk melakukan perulangan Item yang bersifat dinamis pada Item Layout (RecyclerView_item)
class MenuAdapter : ListAdapter<Menu, MenuAdapter.MenuHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.id_menu == newItem.id_menu
            }
            override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                return oldItem.nama_menu == newItem.nama_menu && oldItem.harga_menu == newItem.harga_menu
                        && oldItem.deskripsi_menu == newItem.deskripsi_menu
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuHolder(itemView)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        val currentMenu: Menu = getItem(position)

        holder.tv_namamenu.text = currentMenu.nama_menu
        holder.tv_harga.text = currentMenu.harga_menu.toString()
        holder.tv_idmenu.text = currentMenu.id_menu.toString()
        holder.iv_gambarmenu.setImageResource(currentMenu.gambar_menu)

    }

    fun getMenuAt(position: Int): Menu {
        return getItem(position)
    }

    inner class MenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var tv_namamenu: TextView = itemView.tv_namamenu
        var tv_harga: TextView = itemView.tv_hargamenu
        var tv_idmenu: TextView = itemView.tv_idmenu
        var iv_gambarmenu : ImageView = itemView.iv_gambarmenu

    }

    interface OnItemClickListener {
        fun onItemClick(menu : Menu)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

}