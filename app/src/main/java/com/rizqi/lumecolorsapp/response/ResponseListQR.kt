package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MListQR
import java.io.Serializable

class ResponseListQR(

    val status : Int,
    val message : String,
    val data : ArrayList<MListQR>,

): Serializable


