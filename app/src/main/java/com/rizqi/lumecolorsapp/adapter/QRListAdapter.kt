package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MListQR
import com.rizqi.lumecolorsapp.utils.Constants

class QRListAdapter(private val mData: List<MListQR>, private val mContext: Context): RecyclerView.Adapter<ViewHolderListQr>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderListQr {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_qr, parent, false)

        return ViewHolderListQr(view, mContext)
    }

    override fun onBindViewHolder(holder: ViewHolderListQr, position: Int) {
        holder.bindData(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}

class ViewHolderListQr(view: View,private val mContext: Context): RecyclerView.ViewHolder(view) {
    var mImage = view.findViewById<ImageView>(R.id.qr_show)
    var QRId = view.findViewById<TextView>(R.id.qr_id)

    fun bindData(data: MListQR) {

        if (data.id != ""){
            Glide.with(mContext)
                .load(Constants.URL_GAMBAR_QR + data.id)
                .into(mImage)
        } else{
            mImage.setBackgroundResource(R.drawable.qr_code)
        }

        QRId.text = data.id
    }
}