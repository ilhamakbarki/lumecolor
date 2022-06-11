package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MApprove
import com.rizqi.lumecolorsapp.model.MTabQR
import java.io.Serializable

class ResponseTabQR(

    val status : Int,
    val message : String,
    val data : ArrayList<MTabQR>,

    ): Serializable


