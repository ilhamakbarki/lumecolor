package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MHistory
import com.rizqi.lumecolorsapp.model.MStok
import com.rizqi.lumecolorsapp.utils.Constants.STOCK_IN
import com.rizqi.lumecolorsapp.utils.Constants.STOCK_OUT
import com.rizqi.lumecolorsapp.utils.Constants.URL_GAMBAR

class StockAdapter(private val mData: List<MStok>, private val mContext: Context, private val mType: String): RecyclerView.Adapter<ViewHolderStok>() {

    var interfaceAdapter: InterfaceAdapter? = null

    fun interfaAction(interfaces: InterfaceAdapter) {
        this.interfaceAdapter = interfaces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderStok {
        var type = R.layout.item_stock_in

        if(mType == STOCK_OUT) {
            type = R.layout.item_stock_out
        }

        val view = LayoutInflater.from(parent.context).inflate(type, parent, false)

        return ViewHolderStok(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderStok, position: Int) {
        holder.bindData(mData[position])

        holder.btnReferensi.setOnClickListener {
            interfaceAdapter!!.onBtnClick(mData[position])
        }

        holder.mImage.setOnClickListener {
            interfaceAdapter!!.onBtnClickImage(mData[position])
        }
    }

    interface InterfaceAdapter {
        fun onBtnClick(data: MStok)
        fun onBtnClickImage(data: MStok)
    }
}

class ViewHolderStok(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    var mImage = view.findViewById<ImageView>(R.id.image_barang)
    var mName = view.findViewById<TextView>(R.id.name)
    var mDateEntry = view.findViewById<TextView>(R.id.date_entry)
    var mKode = view.findViewById<TextView>(R.id.kode_text)
    var mId = view.findViewById<TextView>(R.id.id_text)
    var mQty = view.findViewById<TextView>(R.id.qty)
    var btnReferensi = view.findViewById<LinearLayout>(R.id.button_ref)

    fun bindData(data: MStok) {
        Glide.with(context)
            .load(URL_GAMBAR + data.gambar)
            .into(mImage)

        mName.text = data.nama_produk
        mDateEntry.text = data.insert_dt
        mKode.text = data.kode_produk
        mId.text = data.id
        mQty.text = data.qty
    }
}