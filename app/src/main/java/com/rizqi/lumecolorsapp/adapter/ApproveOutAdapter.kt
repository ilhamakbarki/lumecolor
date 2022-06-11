package com.rizqi.lumecolorsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.model.MApprove
import com.rizqi.lumecolorsapp.utils.Constants.SP_LEVEL
import com.rizqi.lumecolorsapp.utils.SharedPreferencesUtils

class ApproveOutAdapter(private val mData: List<MApprove>, private val mContext: Context) :
    RecyclerView.Adapter<ViewHolderApprove>() {

    private var interfaceAdapter: InterfaceAdapter? = null

    fun interfaAction(interfaces: InterfaceAdapter) {
        this.interfaceAdapter = interfaces
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderApprove {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_approve_out, parent, false)

        return ViewHolderApprove(view, mContext)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: ViewHolderApprove, position: Int) {
        holder.bindData(mData[position])
        holder.setLevelApprove(mData[position])

        holder.buttonListQR.setOnClickListener {
            interfaceAdapter!!.onBtnClick(mData[position])
        }

        holder.buttonPacking.setOnClickListener {
            interfaceAdapter!!.onApprovePacking(mData[position])
        }

        holder.buttonSender.setOnClickListener {
            interfaceAdapter!!.onApproveSender(mData[position])
        }

        holder.buttonPicker.setOnClickListener {
            interfaceAdapter!!.onApprovePicker(mData[position])
        }

        holder.buttonChecker.setOnClickListener {
            interfaceAdapter!!.onApproveChecker(mData[position])
        }

        holder.buttonPrintQR.setOnClickListener{
            interfaceAdapter!!.onPrintClick(mData[position])
        }
    }

    interface InterfaceAdapter {
        fun onBtnClick(data: MApprove)
        fun onApprovePacking(data: MApprove)
        fun onApproveSender(data: MApprove)
        fun onApprovePicker(data: MApprove)
        fun onApproveChecker(data: MApprove)
        fun onBtnClickImage(data: MApprove)
        fun onPrintClick(data: MApprove)
    }
}

class ViewHolderApprove(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
    private var mMerchant = view.findViewById<TextView>(R.id.merchant_text)
    private var mOrder = view.findViewById<TextView>(R.id.no_order_text)
    private var mCustomer = view.findViewById<TextView>(R.id.customer_text)
    private var mCart = view.findViewById<TextView>(R.id.cart_text)
    private var mTanggal = view.findViewById<TextView>(R.id.tgl_validasi)
    var buttonListQR = view.findViewById<LinearLayout>(R.id.button_list_qr)
    var buttonPacking = view.findViewById<LinearLayout>(R.id.button_packing)
    var buttonPrintQR = view.findViewById<LinearLayout>(R.id.button_print_qr)
    var buttonSender = view.findViewById<LinearLayout>(R.id.button_sender)
    var buttonPicker = view.findViewById<LinearLayout>(R.id.button_picker)
    var buttonChecker = view.findViewById<LinearLayout>(R.id.button_checker)
    private var buttonApproved = view.findViewById<LinearLayout>(R.id.button_approved)

    private val sharedPreferences = SharedPreferencesUtils(context)

    private val _SPLEVEL = sharedPreferences.getStringSharedPreferences(SP_LEVEL)

    fun bindData(data: MApprove) {
        mMerchant.text = data.nama_vendor
        mOrder.text = data.order_id
        mCustomer.text = data.penerima
        mCart.text = data.cart
        mTanggal.text = data.dt_approve_act
    }

    //  Set Status Approve
    fun setLevelApprove(data: MApprove) {

        if (_SPLEVEL == "ADMIN") {
            buttonPacking.visibility = View.GONE
            buttonSender.visibility = View.GONE

            if (data.approve_picker == "1" || data.approve_checker == "1") {
                buttonApproved.visibility = View.VISIBLE
                buttonListQR.visibility = View.GONE
            } else {
                buttonApproved.visibility = View.GONE
                buttonListQR.visibility = View.VISIBLE
            }
        }

        if (_SPLEVEL == "PICKER") {
            buttonPacking.visibility = View.GONE
            buttonSender.visibility = View.GONE

            if (data.approve_picker == "1") {
                buttonApproved.visibility = View.VISIBLE
                buttonListQR.visibility = View.GONE
            } else {
                buttonApproved.visibility = View.GONE
                buttonListQR.visibility = View.GONE
                buttonPicker.visibility = View.VISIBLE
            }
        }

        if (_SPLEVEL == "CHECKER") {
            buttonPacking.visibility = View.GONE
            buttonSender.visibility = View.GONE

            if (data.approve_checker == "1") {
                buttonApproved.visibility = View.VISIBLE
                buttonListQR.visibility = View.GONE
                buttonPrintQR.visibility = View.VISIBLE
            } else {
                if (data.approve_picker == "1") {
                    buttonApproved.visibility = View.GONE
                    buttonChecker.visibility = View.VISIBLE
                } else {
                    buttonApproved.visibility = View.GONE
                    buttonChecker.visibility = View.GONE
                    buttonPrintQR.visibility = View.GONE
                }
            }
        }

        if (_SPLEVEL == "PACKING") {
            buttonListQR.visibility = View.GONE
            buttonSender.visibility = View.GONE

            if (data.approve_packing == "1") {
                buttonApproved.visibility = View.VISIBLE
                buttonPacking.visibility = View.GONE
            } else {
                if (data.approve_checker == "1") {
                    buttonApproved.visibility = View.GONE
                    buttonPacking.visibility = View.VISIBLE
                } else {
                    buttonApproved.visibility = View.GONE
                    buttonPacking.visibility = View.GONE
                }
            }

        }

        if (_SPLEVEL == "SENDER") {
            buttonListQR.visibility = View.GONE
            buttonPacking.visibility = View.GONE

            if (data.approve_sender == "1") {
                buttonApproved.visibility = View.VISIBLE
                buttonSender.visibility = View.GONE
            } else {
                if (data.approve_packing == "1") {
                    buttonApproved.visibility = View.GONE
                    buttonSender.visibility = View.VISIBLE
                } else {
                    buttonApproved.visibility = View.GONE
                    buttonSender.visibility = View.GONE
                }
            }
        }

    }
}