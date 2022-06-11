package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MOpname
import java.io.Serializable

class ResponseOpname (
    val status : Int,
    val message : String,
    val data : ArrayList<MOpname>,
): Serializable