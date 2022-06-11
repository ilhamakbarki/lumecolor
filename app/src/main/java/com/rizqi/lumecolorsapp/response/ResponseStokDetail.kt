package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MStok
import java.io.Serializable

class ResponseStokDetail (
    val status : Int,
    var message : String = "",
    val data : ArrayList<MStok>,
    val ref : String,
    val no_ref : String,
    val qty : String,
): Serializable