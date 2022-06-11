package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MListQRSampai
import java.io.Serializable

class ResponseListQRSampai (

    val status : Int,
    val message : String,
    val data : ArrayList<MListQRSampai>,

) :Serializable
