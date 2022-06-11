package com.rizqi.lumecolorsapp.main

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.HistoryAdapter
import com.rizqi.lumecolorsapp.adapter.QRListAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MHistory
import com.rizqi.lumecolorsapp.model.MListQR
import com.rizqi.lumecolorsapp.response.ResponseHistory
import com.rizqi.lumecolorsapp.response.ResponseListQR
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.LOADING_MSG
import com.rizqi.lumecolorsapp.utils.Constants.URL_GAMBAR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class HistoryLoadingInActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: HistoryAdapter
    private lateinit var mAdapterQR: QRListAdapter
    private lateinit var datePicker: DatePickerDialog
    private lateinit var mLoading: ProgressDialog
//    Variable From Layout
    private lateinit var emptyState: TextView
    private lateinit var emptyStateQR: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var listQRShow: RecyclerView
    private lateinit var textDateFrom: TextView
    private lateinit var textDateTo: TextView
    private lateinit var imgDateFrom: ImageView
    private lateinit var imgDateTo: ImageView
    private lateinit var lytQrList: LinearLayout
    private lateinit var lytQr: RelativeLayout
    private lateinit var vBack: LinearLayout
    private lateinit var lnrImageShow: LinearLayout
    private lateinit var mImgShow: ImageView
    private lateinit var mImageSearch: ImageView
    private lateinit var lnrSearchView: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var rlHeader: RelativeLayout

    private lateinit var itemList: ArrayList<MHistory>
    private lateinit var searchItem: ArrayList<MHistory>

    var isDetail: Boolean = false
    var isImgShow: Boolean = false
    var isSearch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_loading_in)


        mLoading = ProgressDialog(this@HistoryLoadingInActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        emptyState = findViewById(R.id.empty_state)
        emptyStateQR = findViewById(R.id.empty_state_qr)
        recyclerView = findViewById(R.id.rv_show)
        listQRShow = findViewById(R.id.list_qr_show)
        textDateFrom = findViewById(R.id.txt_date_from)
        textDateTo = findViewById(R.id.txt_date_to)
        imgDateFrom = findViewById(R.id.img_date_from)
        imgDateTo = findViewById(R.id.img_date_to)
        lytQrList = findViewById(R.id.layout_qr_list)
        lytQr = findViewById(R.id.layout_qr)
        vBack = findViewById(R.id.view_back)
        lnrImageShow = findViewById(R.id.linear_image_show)
        mImgShow = findViewById(R.id.image_show)
        mImageSearch = findViewById(R.id.logo_search)
        lnrSearchView = findViewById(R.id.search_view)
        etSearch = findViewById(R.id.edit_text_search)
        rlHeader = findViewById(R.id.header_title)

        itemList = ArrayList()
        searchItem = ArrayList()

        isDetail = false
        isImgShow = false
        isSearch = false

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        var fixMonth:String = (month + 1).toString()
        var fixDay:String = day.toString()

        if(fixMonth.length == 1) {
            fixMonth = "0$fixMonth"
        }
        if(fixDay.length == 1) {
            fixDay = "0$fixDay"
        }

        val dateNow = "${year}-${fixMonth}-${fixDay}"

        textDateFrom.text = dateNow
        textDateTo.text = dateNow

        setOnClickHanlder()

        getListHistory(dateNow, dateNow)

        setDateRange(day, month, year)

        searchAction()
    }

    private fun setOnClickHanlder() {
        mImageSearch.setOnClickListener {
            showSearch(true)
        }

        lytQrList.setOnClickListener {
            lytQrList.visibility = View.GONE
            isDetail = false
        }

        lytQr.setOnClickListener {  }
    }
    
    private fun getListHistory(dari: String, sampai: String) {
        mLoading.setMessage(LOADING_MSG)
        mLoading.show()
        
        emptyState.visibility = View.GONE
        recyclerView.visibility = View.GONE
        
        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.listHistory(dari, sampai)

        call.enqueue(object : Callback<ResponseHistory> {

            override fun onFailure(call: Call<ResponseHistory>, t: Throwable) {

                Toast.makeText(
                    this@HistoryLoadingInActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                emptyState.text = "Terjadi kesalahan saat memuat data."

                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseHistory>, response: Response<ResponseHistory>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

                    itemList = res.data

                    if(res.data.size != 0) {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        setRecyclerView(res.data)
                    } else {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        emptyState.text = "Tidak ada data pada jangka waktu ini."
                    }

//                    Log.d("DATASIZE: ", "${res.data.size}")

                } else {

                    Toast.makeText(
                        this@HistoryLoadingInActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyState.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    emptyState.text = res.message
                }

                mLoading.dismiss()
            }

        })
    }

    private fun setRecyclerView(data: ArrayList<MHistory>) {
        linearLayoutManager = LinearLayoutManager(this@HistoryLoadingInActivity)
        mAdapter = HistoryAdapter(data, this@HistoryLoadingInActivity)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }

        vBack.setOnClickListener {
            lytQrList.visibility = View.GONE
            isDetail = false
        }

        mAdapter.interfaceClick(object: HistoryAdapter.BtnClickListener{
            override fun onBtnClick(data: MHistory) {
                lytQrList.visibility = View.VISIBLE
                isDetail = true

                fetchListQR(data)
            }

            override fun onBtnClickImage(data: MHistory) {
                lnrImageShow.visibility = View.VISIBLE
                isImgShow = true

                Glide.with(this@HistoryLoadingInActivity)
                    .load(URL_GAMBAR + data.gambar)
                    .into(mImgShow)

                lnrImageShow.setOnClickListener {
                    lnrImageShow.visibility = View.GONE
                    isImgShow = false

                }
            }

        })
    }

    private fun searchAction() {
        etSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(isSearch && etSearch.text.isNotEmpty()) {
                    searchItem = ArrayList()

                    for (i in 0 until itemList.size) {
                        val item = itemList[i]
                        if(item.nama_produk.contains(etSearch.text, ignoreCase = true)) {
                            searchItem.add(item)
                        }
                    }

                    if(searchItem.size != 0) {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        setRecyclerView(searchItem)
                    } else {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        if(itemList.size == 0) emptyState.text = "Tidak ada data pada jangka waktu ini."
                        else emptyState.text = "Barang tidak ditemukan."
                    }
                } else if(isSearch && etSearch.text.isEmpty()) {
                    setRecyclerView(itemList)
                    if(itemList.size == 0) {
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        emptyState.text = "Tidak ada data pada jangka waktu ini."
                    } else {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        emptyState.text = ""
                    }
                }
            }

        })
    }

    private fun fetchListQR(data: MHistory) {
        emptyStateQR.visibility = View.VISIBLE
        listQRShow.visibility = View.GONE
        emptyStateQR.text = "Memuat..."

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.listQr(data.id)

        call.enqueue(object : Callback<ResponseListQR> {

            override fun onFailure(call: Call<ResponseListQR>, t: Throwable) {

                Toast.makeText(
                    this@HistoryLoadingInActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyStateQR.visibility = View.VISIBLE
                listQRShow.visibility = View.GONE
                emptyStateQR.text = "Terjadi kesalahan saat memuat data."
            }

            override fun onResponse(call: Call<ResponseListQR>, response: Response<ResponseListQR>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {
                    if(res.data.size != 0) {
                        emptyStateQR.visibility = View.GONE
                        listQRShow.visibility = View.VISIBLE

                        setRecyclerQR(res.data)

                    } else {
                        emptyStateQR.visibility = View.VISIBLE
                        listQRShow.visibility = View.GONE
                        emptyStateQR.text = "Tidak ada data."
                    }


                } else {

                    Toast.makeText(
                        this@HistoryLoadingInActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyStateQR.visibility = View.VISIBLE
                    listQRShow.visibility = View.GONE
                    emptyStateQR.text = "${res.message}"
                }
            }

        })
    }

    private fun setRecyclerQR(data: ArrayList<MListQR>) {
        linearLayoutManager = LinearLayoutManager(this@HistoryLoadingInActivity)
        mAdapterQR = QRListAdapter(data, this@HistoryLoadingInActivity)
        listQRShow.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapterQR
        }
    }

    private fun setDateRange(day: Int, month: Int, year: Int) {
        imgDateFrom.setOnClickListener {
            datePicker = DatePickerDialog(this@HistoryLoadingInActivity,
                { view, year, month, dayOfMonth ->
                    var fixMonth:String = (month + 1).toString()
                    var fixDay:String = dayOfMonth.toString()

                    if(fixMonth.length == 1) {
                        fixMonth = "0$fixMonth"
                    }
                    if(fixDay.length == 1) {
                        fixDay = "0$fixDay"
                    }
                    textDateFrom.text = "${year}-${fixMonth}-${fixDay}"
                }, year, month, day)
            datePicker.show()
        }

        textDateFrom.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                showSearch(false)
                getListHistory(textDateFrom.text.toString(), textDateTo.text.toString())
            }

        })

        imgDateTo.setOnClickListener {
            datePicker = DatePickerDialog(this@HistoryLoadingInActivity,
                { view, year, month, dayOfMonth ->
                    var fixMonth:String = (month + 1).toString()
                    var fixDay:String = dayOfMonth.toString()

                    if(fixMonth.length == 1) {
                        fixMonth = "0$fixMonth"
                    }
                    if(fixDay.length == 1) {
                        fixDay = "0$fixDay"
                    }
                    textDateTo.text = "${year}-${fixMonth}-${fixDay}"
                }, year, month, day)
            datePicker.show()
        }

        textDateTo.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                showSearch(false)
                getListHistory(textDateFrom.text.toString(), textDateTo.text.toString())
            }

        })
    }

    private fun showSearch(show: Boolean) {
        if(show) {
            isSearch = true
            lnrSearchView.visibility = View.VISIBLE
            rlHeader.visibility = View.GONE
        } else {
            isSearch = false
            lnrSearchView.visibility = View.GONE
            rlHeader.visibility = View.VISIBLE
        }
        etSearch.setText("")
    }

    override fun onBackPressed() {
        if(isImgShow) {
            isImgShow = false
            lnrImageShow.visibility = View.GONE
            return
        }
        if(isDetail) {
            isDetail = false
            lytQrList.visibility = View.GONE
            return
        }
        if(isSearch) {
            showSearch(false)
            if(itemList.size != 0) {
                emptyState.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE

                setRecyclerView(itemList)
            } else {
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                emptyState.text = "Tidak ada data pada jangka waktu ini."
            }
            return
        }
        super.onBackPressed()
    }
}