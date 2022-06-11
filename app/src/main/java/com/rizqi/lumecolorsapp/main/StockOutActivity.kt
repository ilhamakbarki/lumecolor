package com.rizqi.lumecolorsapp.main

import android.app.DatePickerDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rizqi.lumecolorsapp.R
import com.rizqi.lumecolorsapp.adapter.StockAdapter
import com.rizqi.lumecolorsapp.api.GetDataService
import com.rizqi.lumecolorsapp.api.RetrofitClients
import com.rizqi.lumecolorsapp.model.MStok
import com.rizqi.lumecolorsapp.response.ResponseStok
import com.rizqi.lumecolorsapp.response.ResponseStokDetail
import com.rizqi.lumecolorsapp.utils.Constants
import com.rizqi.lumecolorsapp.utils.Constants.STOCK_OUT
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class StockOutActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: StockAdapter
    private lateinit var datePicker: DatePickerDialog
    private lateinit var mLoading: ProgressDialog
//    Variable From Layout
    private lateinit var emptyState: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var txtDateFrom: TextView
    private lateinit var txtDateTo: TextView
    private lateinit var imgDateFrom: ImageView
    private lateinit var imgDateTo: ImageView
    private lateinit var lnrReferensi: LinearLayout
    private lateinit var lytReferensi: RelativeLayout
    private lateinit var vBack: LinearLayout
    private lateinit var emptyStateDetail: TextView
    private lateinit var lnrDetail: LinearLayout
    private lateinit var referensi: TextView
    private lateinit var noRef: TextView
    private lateinit var qty: TextView
    private lateinit var typeInsert: TextView
    private lateinit var dateInsert: TextView
    private lateinit var typeApprove: TextView
    private lateinit var dateApprove: TextView
    private lateinit var lnrImageShow: LinearLayout
    private lateinit var mImgShow: ImageView
    private lateinit var mImageSearch: ImageView
    private lateinit var lnrSearchView: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var rlHeader: RelativeLayout

    private lateinit var itemList: ArrayList<MStok>
    private lateinit var searchItem: ArrayList<MStok>

    var isDetail: Boolean = false
    var isImgShow: Boolean = false
    var isSearch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_out)

        mLoading = ProgressDialog(this@StockOutActivity)
        mLoading.setCancelable(false)

//        Variable From Layout
        emptyState = findViewById(R.id.empty_state)
        recyclerView = findViewById(R.id.rv_show)
        txtDateFrom = findViewById(R.id.txt_date_from)
        txtDateTo = findViewById(R.id.txt_date_to)
        imgDateFrom = findViewById(R.id.img_date_from)
        imgDateTo = findViewById(R.id.img_date_to)
//        Detail Variable
        lnrReferensi = findViewById(R.id.layout_refrensi_view)
        lytReferensi = findViewById(R.id.layout_refrensi)
        vBack = findViewById(R.id.view_back)
        emptyStateDetail = findViewById(R.id.empty_state_detail)
        lnrDetail = findViewById(R.id.lnr_detail)
        referensi = findViewById(R.id.refrensi_text)
        noRef = findViewById(R.id.no_refrensi_text)
        qty = findViewById(R.id.qty_text)
        typeInsert = findViewById(R.id.insert_text)
        dateInsert = findViewById(R.id.insert_date_text)
        typeApprove = findViewById(R.id.approve_text)
        dateApprove = findViewById(R.id.approve_date_text)
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

        txtDateFrom.text = dateNow
        txtDateTo.text = dateNow

        setOnClickHandler()

        getListStockOut(dateNow, dateNow)

        setDateRange(day, month, year)

        searchAction()
    }

    private fun setOnClickHandler() {
        mImageSearch.setOnClickListener {
            showSearch(true)
        }

        lnrReferensi.setOnClickListener {
            lnrReferensi.visibility = View.GONE
            isDetail = false
        }

        lytReferensi.setOnClickListener {  }
    }

    private fun getListStockOut(dari: String, sampai: String) {
        mLoading.setMessage(Constants.LOADING_MSG)
        mLoading.show()

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.stokOut(dari, sampai)

        call.enqueue(object : Callback<ResponseStok> {

            override fun onFailure(call: Call<ResponseStok>, t: Throwable) {

                Toast.makeText(
                    this@StockOutActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d("FAILED :", t.message.toString())

                emptyState.text = "Terjadi kesalahan saat memuat data."
                emptyState.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                mLoading.dismiss()
            }

            override fun onResponse(call: Call<ResponseStok>, response: Response<ResponseStok>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {

                    itemList = res.data

                    if(res.data.size != 0) {
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        setRecyclerView(res.data)
                    } else {
                        emptyState.text = "Tidak ada data pada jangka waktu ini."
                        emptyState.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }

                } else {

                    Toast.makeText(
                        this@StockOutActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyState.text = res.message
                    emptyState.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE

                }

                mLoading.dismiss()
            }

        })
    }

    private fun setRecyclerView(data: ArrayList<MStok>) {
        linearLayoutManager = LinearLayoutManager(this@StockOutActivity)
        mAdapter = StockAdapter(data, this@StockOutActivity, STOCK_OUT)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = mAdapter
        }

        vBack.setOnClickListener {
            lnrReferensi.visibility = View.GONE
            isDetail = false
        }

        mAdapter.interfaAction(object: StockAdapter.InterfaceAdapter{
            override fun onBtnClick(data: MStok) {
                lnrReferensi.visibility = View.VISIBLE
                isDetail = true

                setReferensi(data)
            }

            override fun onBtnClickImage(data: MStok) {
                lnrImageShow.visibility = View.VISIBLE
                isImgShow = true

                Glide.with(this@StockOutActivity)
                    .load(Constants.URL_GAMBAR + data.gambar)
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
                        setRecyclerView(searchItem)
                        emptyState.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
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

    private fun setReferensi(data: MStok) {
        emptyStateDetail.visibility = View.VISIBLE
        lnrDetail.visibility = View.GONE
        emptyStateDetail.text = "Memuat..."

        val service = RetrofitClients().getRetrofitInstance().create(GetDataService::class.java)
        val call = service.detailStok(data.id)

        Log.d("STOKOUTID: ", data.id)

        call.enqueue(object : Callback<ResponseStokDetail> {

            override fun onFailure(call: Call<ResponseStokDetail>, t: Throwable) {

                Toast.makeText(
                    this@StockOutActivity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()

//                Log.d("FAILED :", t.message.toString())

                emptyStateDetail.visibility = View.VISIBLE
                lnrDetail.visibility = View.GONE
                emptyStateDetail.text = "Terjadi kesalahan saat memuat data."
            }

            override fun onResponse(call: Call<ResponseStokDetail>, response: Response<ResponseStokDetail>) {

                val res = response.body()!!

                if (res.status == Constants.STAT200) {
                    if(res.data.size != 0) {
                        emptyStateDetail.visibility = View.GONE
                        lnrDetail.visibility = View.VISIBLE

                        setDataDetail(res)
                    } else {
                        emptyStateDetail.visibility = View.VISIBLE
                        lnrDetail.visibility = View.GONE
                        emptyStateDetail.text = "Tidak ada data."
                    }


                } else {

                    Toast.makeText(
                        this@StockOutActivity,
                        "GAGAL",
                        Toast.LENGTH_LONG
                    ).show()

                    emptyStateDetail.visibility = View.VISIBLE
                    lnrDetail.visibility = View.GONE
                    emptyStateDetail.text = "${res.message}"
                }
            }

        })
    }

    private fun setDataDetail(res: ResponseStokDetail) {
        val data = res.data[0]

        referensi.text = res.ref
        noRef.text = res.no_ref
        qty.text = res.qty
        typeInsert.text = data.insert_by
        dateInsert.text = data.insert_dt
        typeApprove.text = data.approve_by
        dateApprove.text = data.approve_dt
    }

    private fun setDateRange(day: Int, month: Int, year: Int) {
        imgDateFrom.setOnClickListener {
            datePicker = DatePickerDialog(this@StockOutActivity,
                { view, year, month, dayOfMonth ->
                    var fixMonth:String = (month + 1).toString()
                    var fixDay:String = dayOfMonth.toString()

                    if(fixMonth.length == 1) {
                        fixMonth = "0$fixMonth"
                    }
                    if(fixDay.length == 1) {
                        fixDay = "0$fixDay"
                    }
                    txtDateFrom.text = "${year}-${fixMonth}-${fixDay}"
                }, year, month, day)
            datePicker.show()
        }

        txtDateFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                showSearch(false)
                getListStockOut(txtDateFrom.text.toString(), txtDateTo.text.toString())
            }

        })

        imgDateTo.setOnClickListener {
            datePicker = DatePickerDialog(this@StockOutActivity,
                { view, year, month, dayOfMonth ->
                    var fixMonth:String = (month + 1).toString()
                    var fixDay:String = dayOfMonth.toString()

                    if(fixMonth.length == 1) {
                        fixMonth = "0$fixMonth"
                    }
                    if(fixDay.length == 1) {
                        fixDay = "0$fixDay"
                    }
                    txtDateTo.text = "${year}-${fixMonth}-${fixDay}"
                }, year, month, day)
            datePicker.show()
        }

        txtDateTo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                Do Something
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                Do Something
            }

            override fun afterTextChanged(s: Editable?) {
                showSearch(false)
                getListStockOut(txtDateFrom.text.toString(), txtDateTo.text.toString())
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
            lnrReferensi.visibility = View.GONE
            return
        }
        if(isSearch) {
            showSearch(false)
            setRecyclerView(itemList)
            return
        }
        super.onBackPressed()
    }
}