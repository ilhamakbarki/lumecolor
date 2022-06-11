package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MProduk
import java.io.Serializable

class ResponseProduk(

    val status : Int,
    val message : String,
    val data : ArrayList<MProduk>,

    ): Serializable


