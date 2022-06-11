package com.rizqi.lumecolorsapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rizqi.lumecolorsapp.R

class ApproveInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve_in)

// if status Pending (ketika baru tampil dari loading IN)
// if status Approve (ketika sudah input edit text qty_pass dan kondisi == qty_input || qty_pass != 0 dan visible approve view )
// if status Reject (ketika sudah input edit text qty_pass == 0 dan visible reject_view, visible button_show_detail )
// kondisi awal pending, jika ganti kondisi maka pending_view di gone dan visible view reject / approve

// Qty Reject di dapat dengan perhitungan qty_input loadingIn - qty_approve

    }
}