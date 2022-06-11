package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MOpname
import com.rizqi.lumecolorsapp.model.MStok
import com.rizqi.lumecolorsapp.utils.Constants

class OpnameAdapter(private val mData: List<MOpname>, private val mContext: Context): RecyclerView.Adapter<ViewHolderOpname>() {
    var interfaceAdapter: InterfaceAdapter? = null

    fun interfaAction(interfaces: InterfaceAdapter) {
        this.interfaceAdapter = interfaces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderOpname {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_opname, parent, false)

        return ViewHolderOpname(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderOpname, position: Int) {
        holder.bindData(mData[position])

        holder.mImage.setOnClickListener {
            interfaceAdapter!!.onBtnClickImage(mData[position])
        }

        holder.btnListQr.setOnClickListener {
            interfaceAdapter!!.onBtnClick(mData[position])

        }
    }

    interface InterfaceAdapter {
        fun onBtnClick(data: MOpname)
        fun onBtnClickImage(data: MOpname)
    }
}

class ViewHolderOpname(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    var mImage = view.findViewById<ImageView>(R.id.image_barang)
    var mName = view.findViewById<TextView>(R.id.name)
    var mKode = view.findViewById<TextView>(R.id.kode_text)
    var mKategori = view.findViewById<TextView>(R.id.kategori_text)
    var mQty = view.findViewById<TextView>(R.id.qty_text)
    var btnListQr = view.findViewById<LinearLayout>(R.id.button_kartu)
    var mIdProduk = ""

    fun bindData(data: MOpname) {

        Glide.with(context)
            .load(Constants.URL_GAMBAR + data.gambar)
            .into(mImage)

        mName.text = data.nama_produk
        mKode.text = data.kode_produk
        mKategori.text = data.nama_kategori
        mQty.text = data.qty
        mIdProduk = data.id_produk
        println("produkId $mIdProduk")
    }
}