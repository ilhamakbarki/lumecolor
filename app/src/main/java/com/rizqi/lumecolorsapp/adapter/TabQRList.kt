package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MTabQR

class TabQRList(private val mData: List<MTabQR>, private val mContext: Context): RecyclerView.Adapter<ViewHolderTabQR>() {

    private var interfaceAdapter: InterfaceAdapter? = null

    fun interfaAction(interfaces: InterfaceAdapter) {
        this.interfaceAdapter = interfaces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTabQR {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_qr_approve_out, parent, false)

        return ViewHolderTabQR(view, mContext)
    }

    override fun onBindViewHolder(holder: ViewHolderTabQR, position: Int) {
        holder.bindData(mData[position])

        holder.btnDelete.setOnClickListener {
            interfaceAdapter!!.onDelete(mData[position])
        }
    }

    override fun getItemCount(): Int = mData.size

    interface InterfaceAdapter {
        fun onDelete(data: MTabQR)
    }
}

class ViewHolderTabQR(view: View,private val mContext: Context): RecyclerView.ViewHolder(view) {
    var qrCode = view.findViewById<TextView>(R.id.qr_code)
    var produkName = view.findViewById<TextView>(R.id.produk_name)
    var btnDelete = view.findViewById<ImageView>(R.id.button_delete)

    fun bindData(data: MTabQR) {
        qrCode.text = data.qrcode
        produkName.text = data.nama_produk
    }
}