package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MStok
import java.io.Serializable

class ResponseStok (
    val status : Int,
    val message : String,
    val data : ArrayList<MStok>,
): Serializable