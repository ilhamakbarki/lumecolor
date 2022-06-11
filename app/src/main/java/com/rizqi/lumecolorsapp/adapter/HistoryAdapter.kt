package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.util.Log
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
import com.rizqi.lumecolorsapp.utils.Constants.URL_GAMBAR

class HistoryAdapter(private val mData: List<MHistory>, private val mContext: Context): RecyclerView.Adapter<ViewHolder>() {

    var mClickListener: BtnClickListener? = null

    fun interfaceClick(interfaces: BtnClickListener) {
        this.mClickListener = interfaces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_show_barang, parent, false)

        return ViewHolder(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHistory(mData[position])
        holder.mBtnListQr.setOnClickListener {
//            Log.d("CLICKME: ", mData[position].id)
            mClickListener!!.onBtnClick(mData[position])
        }
        holder.mGambar.setOnClickListener {
            mClickListener!!.onBtnClickImage(mData[position])
        }
    }

    interface BtnClickListener {
        fun onBtnClick(data: MHistory)
        fun onBtnClickImage(data: MHistory)
    }
}

class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    var mGambar = view.findViewById<ImageView>(R.id.image_barang)
    var mName = view.findViewById<TextView>(R.id.name)
    var mDateEntry = view.findViewById<TextView>(R.id.date_entry_text)
    var mDateExp = view.findViewById<TextView>(R.id.exp_date_text)
    var mNoDelivery = view.findViewById<TextView>(R.id.no_delivery_text)
    var mNoBatch = view.findViewById<TextView>(R.id.no_batch_text)
    var mQty = view.findViewById<TextView>(R.id.qty)
    var mBtnListQr = view.findViewById<LinearLayout>(R.id.button_list_qr)

    fun bindHistory(data: MHistory) {
        Glide.with(context)
            .load(URL_GAMBAR + data.gambar)
            .into(mGambar)

        mName.text = data.nama_produk
        mDateEntry.text = data.insert_dt
        mDateExp.text = data.exp_date
        mNoDelivery.text = data.no_delivery
        mNoBatch.text = data.no_batch
        mQty.text = data.qty_lolos
    }
}